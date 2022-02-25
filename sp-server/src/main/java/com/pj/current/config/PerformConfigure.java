package com.pj.current.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pj.utils.sg.AjaxError;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;

/**
 * 演示模式的拦截器（如果你不准备搭建演示程序，可以删除此配置类） 
 * <p> 使用方法：
 * 	配置文件里增加以下配置： 
 * 		per=true 
 *  即可打开
 * @author kong 
 */
@Configuration
@ConditionalOnProperty("per")
public class PerformConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	System.out.println("\n---------------------- 演示模式启动 \n");
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler)->{
        	// 演示模式校验 
			SaRouter.match(
					// 管理员 
					"/admin/add", "/admin/delete", "/admin/deleteByIds", "/admin/update", "/admin/updatePassword", 
					"/admin/updateAvatar", "/admin/updateStatus", "/admin/updateRole", "/admin/updateInfo", 
					
					// 修改密码
					"/AdminPassword/update", 
					
					// 全局日志
					"/SgApilog/delete", "/SgApilog/deleteByIds", "/SgApilog/deleteByStartEnd", 
					
					// redis
					"/RedisConsole/set", "/RedisConsole/del", "/RedisConsole/updateValue", "/RedisConsole/updateTTL", "/RedisConsole/deleteByKeys", 
					
					// 角色 
					"/role/delete", "/role/update", 
					
					// 权限 
					"/SpRolePermission/updatePcodeByRid", 
					
					// 全局配置 
					"/SpCfg/updateCfg", 
					
					// sql控制台
					"/druid/**", "/druid/sql.html",
					
					// 业务代码 
					"/SerArticle/delete", "/SerArticle/deleteByIds",
					"/SerGoods/delete", "/SerGoods/deleteByIds",
					"/SysDept/delete", "/SysDept/deleteByIds",
					"/SysNotice/delete", "/SysNotice/deleteByIds",
					"/SysRedeem/delete", "/SysRedeem/deleteByIds",
					"/SysType/delete", "/SysType/deleteByIds"
					
					)
				.check(r -> {
					throw new AjaxError("演示模式，不可操作，将项目部署到本地预览即可测试此功能");
				})
				;
        })).addPathPatterns("/**").order(-1000); 
    }
    
}
