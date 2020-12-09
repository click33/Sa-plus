package com.pj.current;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * springboot启动之后 
 * @author kong
 *
 */
@Component
public class SaPlusStartup implements CommandLineRunner {

	@Value("${spring.application.name:sa-plus}")
    private String applicationName;
	
	@Value("${server.port:8080}")
    private String port;

    @Value("${server.servlet.context-path:}")
    private String path;

    @Value("${spring.profiles.active:}")
    private String active;
   
    @Override
    public void run(String... args) throws Exception {
    	 String ip = InetAddress.getLocalHost().getHostAddress();
         String str = "\n------------- " + applicationName + " (" + active + ") 启动成功 --by " + getNow() + " -------------\n" + 
                 "\t- Local:   http://localhost:" + port + path + "\n" +
                 "\t- Local2:  http://127.0.0.1:" + port + path + "\n" +
                 "\t- Network: http://" + ip + ":" + port + path + "\n";
         System.out.println(str);
    }

    
	
	/**
	 * 返回系统当前时间的YYYY-MM-dd hh:mm:ss 字符串格式
	 */
	private static String getNow(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
    

}

