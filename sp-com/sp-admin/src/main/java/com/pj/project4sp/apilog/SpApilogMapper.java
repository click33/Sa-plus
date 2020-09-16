package com.pj.project4sp.apilog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pj.utils.sg.SoMap;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * Mapper: api请求记录表
 * @author kong 
 */
@Mapper
public interface SpApilogMapper {

	/** 保存入库 */
	int saveObj(SpApilog apiLog);
	
	
	/** 增 */
	int add(SpApilog apiLog);

	/** 删 */
	int delete(String id);	 

	/** 删 - 根据日期范围 */
	int deleteByStartEnd(@Param("startTime")String startTime, @Param("endTime")String endTime);	 
	
	/** 改 */
	int update(SpApilog apiLog);
	

	

//	/** 查 */
//	ApiLog getById(String id);	 

	/** 查 - 集合（参数为null或0时默认忽略此条件） */
	List<SpApilog> getList(SoMap so);


}
