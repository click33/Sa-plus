package com.pj.project4sp.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pj.utils.sg.SoMap;

/**
 * Mapper: 系统管理员表
 * @author kong
 */
@Mapper
public interface SpAdminMapper {


	/**
	 * 增 #{name}, #{password}, #{role_id}
	 */
	int add(SpAdmin obj);

	/**
	 * 删 
	 */
	int delete(long id);

	/**
	 * 改 
	 */
	int update(SpAdmin obj);

	/**
	 * 查 
	 */
	SpAdmin getById(long id);

	/**
	 * 查 - 集合（参数为null或0时默认忽略此条件） 
	 */
	List<SpAdmin> getList(SoMap so);


	/**
	 * 查询，根据name
	 */
	SpAdmin getByName(String name);
	
	
	/**
	 * 查询，根据 phone 
	 */
	SpAdmin getByPhone(String phone);



}
