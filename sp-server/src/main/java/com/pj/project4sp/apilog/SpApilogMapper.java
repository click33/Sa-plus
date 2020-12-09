package com.pj.project4sp.apilog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pj.utils.so.SoMap;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * Mapper: api请求记录表
 * @author kong 
 */
@Mapper
public interface SpApilogMapper {

	/**
	 * 保存入库 
	 * @param apiLog
	 * @return
	 */
	int saveObj(SpApilog apiLog);
	
	/**
	 * 增 
	 * @param apiLog
	 * @return
	 */
	int add(SpApilog apiLog);

	/**
	 * 删 
	 * @param id
	 * @return
	 */
	int delete(String id);	 

	/**
	 * 删 - 根据日期范围 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int deleteByStartEnd(@Param("startTime")String startTime, @Param("endTime")String endTime);	 
	
	/**
	 * 改 
	 * @param apiLog
	 * @return
	 */
	int update(SpApilog apiLog);
	
	/**
	 * 查 - 集合 
	 * @param so
	 * @return
	 */
	List<SpApilog> getList(SoMap so);

	/**
	 * 查 - 集合 
	 * @param so
	 * @return
	 */
	SoMap staBy(SoMap so);


}
