package com.pj.project4sp.role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pj.utils.so.SoMap;

/**
 * Mapper: 系统角色表
 * @author kong
 */
@Mapper
public interface SpRoleMapper {


	/**
	 * 增 
	 */
	int add(SpRole obj);

	/**
	 * 删 
	 */
	int delete(long id);

	/**
	 * 改 
	 */
	int update(SpRole obj);

	/**
	 * 查 
	 */
	SpRole getById(long id);

	/**
	 * 查 - 集合（参数为null或0时默认忽略此条件） 
	 */
	List<SpRole> getList(SoMap soMap);


	/**
	 * 查，根据角色名字
	 */
	SpRole getByRoleName(String role_name);

	
	int add2(String role_name);
	
}
