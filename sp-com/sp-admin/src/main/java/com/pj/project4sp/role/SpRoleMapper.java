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
	 * @param obj
	 * @return
	 */
	int add(SpRole obj);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	int delete(long id);

	/**
	 * 改 
	 * @param obj
	 * @return
	 */
	int update(SpRole obj);

	/**
	 *  查 
	 * @param id
	 * @return
	 */
	SpRole getById(long id);

	/**
	 * 查 
	 * @param soMap
	 * @return
	 */
	List<SpRole> getList(SoMap soMap);


	/**
	 * 查，根据角色名字
	 * @param name
	 * @return
	 */
	SpRole getByRoleName(String name);

}
