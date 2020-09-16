package com.pj.project4sp.role4permission;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper: 角色权限中间表
 * @author kong
 */
@Mapper
public interface SpRolePermissionMapper {


	/**增 */
	int add(@Param("role_id")long role_id, @Param("pcode")String pcode);

	
	/** 删除指定角色的所有权限 */
	int deleteByRoleId(long role_id);
	
	
	/** 指定role_id的所有权限码   */
	List<String> getPcodeByRoleId(long role_id);
	
	

}
