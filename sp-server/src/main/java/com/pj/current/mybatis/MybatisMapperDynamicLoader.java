package com.pj.current.mybatis;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * mapper.xml热刷新操作类
 * @author kong 
 */
public class MybatisMapperDynamicLoader implements InitializingBean, ApplicationContextAware {

	public boolean enabled = true;	
	public MybatisMapperDynamicLoader(boolean enabled) {
		this.enabled = enabled;
	}
	
    private final HashMap<String, String> mappers = new HashMap<String, String>();
    private volatile ConfigurableApplicationContext context = null;
    private volatile Scanner scanner = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    	// 如果未开启 直接返回
    	if(!enabled) {
    		return;
    	}
        try {
            scanner = new Scanner();
            new Timer(true).schedule(new TimerTask() {
            	@Override
                public void run() {
                    try {
                        if (scanner.isChanged()) {
                            // System.out.println("load mapper.xml");
                        	String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        	System.out.println("mapper.xml热刷新成功，当前时间：" + now);
                            scanner.reloadXml();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 10 * 1000, 3 * 1000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    class Scanner {
        private static final String XML_RESOURCE_PATTERN = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "**/*Mapper.xml";
        private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        public Scanner() throws IOException {
            Resource[] resources = findResource();
            if (resources != null) {
                for (Resource resource : resources) {
                    String key = resource.getURI().toString();
                    String value = getMd(resource);
                    mappers.put(key, value);
                }
            }
        }
        public void reloadXml() throws Exception {
            SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
            Configuration configuration = factory.getConfiguration();
            
//            org.apache.ibatis.session.Configuration
//            com.baomidou.mybatisplus.core.MybatisConfiguration
            
            removeConfig(configuration);
            for (Resource resource : findResource()) {
                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(), configuration, resource.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } finally {
                    ErrorContext.instance().reset();
                }
            }
        }
        private void removeConfig(Configuration configuration) throws Exception {
            Class<?> classConfig = configuration.getClass();
            // 如果使用了mybatis-plus，可能会获取不到真正的 Configuration类，需要加上以下代码，才能正确运行   ====== start
            if(classConfig.equals(org.apache.ibatis.session.Configuration.class) == false) {
//            	System.err.println("开始 转化");
            	classConfig = classConfig.getSuperclass();
            }
            //  ====== end 
            clearMap(classConfig, configuration, "mappedStatements");
            clearMap(classConfig, configuration, "caches");
            clearMap(classConfig, configuration, "resultMaps");
            clearMap(classConfig, configuration, "parameterMaps");
            clearMap(classConfig, configuration, "keyGenerators");
            clearMap(classConfig, configuration, "sqlFragments");
            clearSet(classConfig, configuration, "loadedResources");
        }
        @SuppressWarnings("rawtypes")
		private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
            Field field = classConfig.getDeclaredField(fieldName);
            field.setAccessible(true);
            ((Map) field.get(configuration)).clear();
        }
        @SuppressWarnings("rawtypes")
		private void clearSet(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
            Field field = classConfig.getDeclaredField(fieldName);
            field.setAccessible(true);
            ((Set) field.get(configuration)).clear();
        }
        public boolean isChanged() throws IOException {
            boolean isChanged = false;
            for (Resource resource : findResource()) {
                String key = resource.getURI().toString();
                String value = getMd(resource);
                if (!value.equals(mappers.get(key))) {
                    isChanged = true;
                    mappers.put(key, value);
                }
            }
            return isChanged;
        }
        private Resource[] findResource() throws IOException {
            return resourcePatternResolver.getResources(XML_RESOURCE_PATTERN);
        }
        private String getMd(Resource resource) throws IOException {
            return new StringBuilder().append(resource.contentLength()).append("-").append(resource.lastModified()).toString();
        }
    }
}