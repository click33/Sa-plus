package com.pj.project4sp.admin4login;

import java.io.Serializable;
import java.util.*;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Model: sp_admin_login -- 管理员登录日志表 
 * 
 * @author shengzhang 
 */
@Data
@Accessors(chain = true)
public class SpAdminLogin implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;	
	/**
	 * 此模块对应的表名 
	 */
	public static final String TABLE_NAME = "sp_admin_login";	
	/**
	 * 此模块对应的权限码 
	 */
	public static final String PERMISSION_CODE = "sp-admin-login";	


	// ---------- 表中字段 ----------
	/**
	 * id号 
	 */
	private Long id;	

	/**
	 * 管理员账号id 
	 */
	private Long accId;	

	/**
	 * 本次登录Token 
	 */
	private String accToken;	

	/**
	 * 登陆IP 
	 */
	private String loginIp;	

	/**
	 * 登陆地点
	 */
	private String address;	

	/**
	 * 客户端设备标识
	 */
	private String device;	

	/**
	 * 客户端系统标识 
	 */
	private String system;	

	/**
	 * 创建时间 
	 */
	private Date createTime;	



	// ---------- 额外字段 ----------
	/**
	 * 管理员名称 
	 */
	private String spAdminName;	

	/**
	 * 管理员头像 
	 */
	private String spAdminAvatar;	
	


}
