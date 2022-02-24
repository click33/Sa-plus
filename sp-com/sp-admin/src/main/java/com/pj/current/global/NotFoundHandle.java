package com.pj.current.global;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.project4sp.apilog.SpApilogUtil;
import com.pj.utils.sg.AjaxJson;

/**
 * 404 处理 
 * 
 * @author kong 
 */
@RestController
public class NotFoundHandle implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	// 由于除404以外的异常都会被全局异常处理掉，所以走到这里的请求都是404了 
	@RequestMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 记录API访问日志 
		AjaxJson aj = AjaxJson.get(404, "not found");
		SpApilogUtil.endRequest(aj);
		
		// 返回json消息 
		response.setStatus(200);
		return aj;
    }
	
}