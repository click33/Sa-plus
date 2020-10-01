package com.pj.project4sp.admin4password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.config.SystemObject;
import com.pj.project4sp.admin.SpAdmin;
import com.pj.project4sp.admin.SpAdminUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.NbUtil;

/**
 * admin表 密码相关 
 * 
 * @author shengzhang
 */
@RestController
@RequestMapping("/AdminPassword/")
public class SpAdminPasswordController {

	
	@Autowired
	SpAdminPasswordService spAdminPasswordService;

	// 指定用户修改自己密码
	@RequestMapping("update")
	AjaxJson updatePassword(String old_pwd, String new_pwd) {
		// 1、转md5
		SpAdmin a = SpAdminUtil.getCurrAdmin();
		String old_pwd_md5 = SystemObject.getPasswordMd5(a.getId(), old_pwd);
		
		// 2、验证
		if(NbUtil.isNull(a.getPassword2()) && NbUtil.isNull(old_pwd)) {
			// 如果没有旧密码，则不用取验证 
		} else {
			if(old_pwd_md5.equals(a.getPassword2()) == false) {
				return AjaxJson.getError("旧密码输入错误");
			}
		}
		
		// 3、开始改 
		int line = spAdminPasswordService.updatePassword(a.getId(), new_pwd);
		return AjaxJson.getByLine(line);
	}
	
	
	
	
}
