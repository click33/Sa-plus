package com.pj.project4sp.admin;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Model: 系统管理员表
 * @author kong
 */
@Data
public class SpAdmin implements Serializable  {

	private static final long serialVersionUID = 1L;


	private long id;		// id，--主键、自增 
	private String name;		// admin名称 
	private String avatar;		// 头像  
	private String password;		// 密码 
	private String pw;		// 明文密码 
	private String phone;		// 手机号 
	private int role_id;		// 角色id
	private int status;		// 状态 （1=是，2=否） 
	private long create_by_aid;		// 创建自哪个管理员 
	private Date create_time;		// 创建时间 
	private Date login_time;		// 上次登陆时间 
	private String login_ip;		// 上次登陆IP 
	private int login_count;		// 登陆次数 
	
	// 额外字段 
	private String role_name;		// 所属角色名称  


	// 防止密码被传递到前台 
    public String getPassword(){
    	return "********";
    }
    // 获取真实密码 
    @JsonIgnore()
    public String getPassword2(){
    	return this.password;
    }
	
}
