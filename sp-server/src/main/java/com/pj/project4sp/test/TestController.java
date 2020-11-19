package com.pj.project4sp.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.utils.sg.AjaxJson;

/**
 * 测试controller 
 * @author kong 
 */
@RestController
public class TestController {

	
	/**
	 * 测试请求，如果能正常访问此路由，则证明项目已经部署成功 
	 * @return
	 */
	@RequestMapping("/test")
	public AjaxJson test() {
		System.out.println("------------------ 成功进入请求 ------------------");
		return AjaxJson.getSuccess("请求成功");
	}

	
}
