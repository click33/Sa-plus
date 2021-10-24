package com.pj.project4sp.admin4login;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pj.current.config.SystemObject;
import com.pj.project4sp.SP;
import com.pj.project4sp.admin.SpAdmin;
import com.pj.project4sp.admin.SpAdminMapper;
import com.pj.project4sp.role4permission.SpRolePermissionService;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.NbUtil;
import com.pj.utils.sg.WebNbUtil;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;

/**
 * service：admin账号相关
 * @author kong
 *
 */
@Service
public class SpAccAdminService {

	

	@Autowired
	SpAccAdminMapper spAccAdminMapper;

	@Autowired
	SpAdminMapper spAdminMapper;
	
	@Autowired
	SpRolePermissionService spRolePermissionService;
	
	
	/**
	  * 登录 
	 * @param name 店铺名称
	 * @param password 店铺密码 
	 * @return
	 */
	AjaxJson doLogin(String key, String password) {
		
		// 0、判断 way (1=ID, 2=昵称，3=手机号  )
    	int way = 2;	
    	if(NbUtil.isNumber(key) == true){
    		way = 1;
    		if(key.length() == 11){
    			way = 3;
    		}
    	}
		
		// 2、获取admin
        SpAdmin admin = null;	
        if(way == 1) {
        	admin = spAdminMapper.getById(Long.parseLong(key)); 
        }
        if(way == 2) {
        	admin = spAdminMapper.getByName(key); 
        }
        if(way == 3) {
        	admin = spAdminMapper.getByPhone(key); 
        }
        

        // 3、开始验证
        if(admin == null){
        	return AjaxJson.getError("无此账号");	
        }
        if(NbUtil.isNull(admin.getPassword2())) {
        	return AjaxJson.getError("此账号尚未设置密码，无法登陆");
        }
        String md5Password = SystemObject.getPasswordMd5(admin.getId(), password);
        if(admin.getPassword2().equals(md5Password) == false){
        	return AjaxJson.getError("密码错误");	
        }
        
        // 4、是否禁用
        if(admin.getStatus() == 2) {
        	return AjaxJson.getError("此账号已被禁用，如有疑问，请联系管理员");	
        }

        // =========== 至此, 已登录成功 ============ 
        successLogin(admin);
        StpUtil.login(admin.getId()); 		
        
        // 组织返回参数  
		SoMap map = new SoMap();
		map.put("admin", admin);
		map.put("per_list", spRolePermissionService.getPcodeByRid2(admin.getRoleId()));
		map.put("tokenInfo", StpUtil.getTokenInfo());
		return AjaxJson.getSuccessData(map);	
	}
	
	
	/**
	 * 指定id的账号成功登录一次 （修改最后登录时间等数据 ）
	 * @param s
	 * @return
	 */
	public int successLogin(SpAdmin s){
		String loginIp = WebNbUtil.getIP(SpringMVCUtil.getRequest());
		int line = spAccAdminMapper.successLogin(s.getId(), loginIp);
		if(line > 0) {
	        s.setLoginIp(loginIp);
	        s.setLoginTime(new Date());
	        s.setLoginCount(s.getLoginCount() + 1);
		}
        return line;
	}
	
	/**
	 * 修改手机号  
	 * @param adminId
	 * @param newPhone
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	public AjaxJson updatePhone(long adminId, String newPhone) {
		// 修改admin手机号
		int line = SP.publicMapper.updateColumnById("sys_admin", "phone", newPhone, adminId);
		return AjaxJson.getByLine(line);
	}
	
	
	
	
}
