package com.pj.project4sp.role;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Model: 系统角色表
 * @author kong
 */
@Data
public class SpRole implements Serializable  {

	private static final long serialVersionUID = 1L;

	private long id;		// 角色id，--主键、自增
	private String role_name;		// 角色名称,唯一约束
	private String role_info;		// 角色详细描述
	private int is_lock;		// 是否锁定(1=是,2=否),锁定之后不可随意删除,防止用户误操作
	private Date create_time;		// 创建时间



}
