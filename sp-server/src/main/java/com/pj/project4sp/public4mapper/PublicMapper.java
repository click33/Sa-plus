package com.pj.project4sp.public4mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.utils.so.SoMap;

/**
 * 公用Mapper，封装一些常见的Mapper操作，避免某些及其简单的逻辑也要写一堆xml方法
 * @author kong
 * 更新于2020-12-1 新增部分方法 
 *
 */
@Mapper
public interface PublicMapper {

	
	// ------------------------ 一些工具方法 ------------------------

	/**
	 * 返回上一句SQL插入的自增主键值 
	 */
	public long getPrimarykey();

	
	// ------------------------ 新增SQL相关 ------------------------

	
	// ------------------------ 删除SQL相关 ------------------------

	/**
	 * 根据id删除一条记录 
	 * <p>
	 * 参数：表明、id值
	 */
	public int deleteById(
			@Param("tableName")String tableName, 
			@Param("id")Object id
			); 

	/**
	 * 根据指定列指定值删除一条记录 
	 * <p>
	 * 参数: 表名、条件列、条件列值 
	 */
	public int deleteBy(
			@Param("tableName")String tableName, 
			@Param("whereName") String whereName, 
			@Param("whereValue") Object whereValue
			);

	/**
	 * 根据id列表批量删除 
	 * <p>
	 * 参数：表明、id列表
	 */
	public int deleteByIds(
			@Param("tableName")String tableName, 
			@Param("ids")List<?> ids
			); 

	/**
	 * 根据指定列指定值删除多条记录 
	 * <p>
	 * 参数: 表名、列表、列值 
	 */
	public int deleteByWhereList(
			@Param("tableName")String tableName, 
			@Param("whereName") String whereName, 
			@Param("whereList") List<?> whereList
			);
	
	
	// ------------------------ 修改SQL相关 ------------------------

	/**
	 * 指定表的指定字段增加指定值，可以为负值 
	 * <p>
	 * 参数：表明、列表、增加的值、id值 
	 */
	public int columnAdd(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("num") long num,  
			@Param("id") Object id 
			);

	/**
	 * 指定表的指定字段增加指定值，可以为负值 
	 * <p>
	 * 参数：表明、列表、增加的值、id列表  
	 */
	public int columnAddByIds(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("num") long num,  
			@Param("ids") List<?> ids
			);

	/**
	 * 指定表的指定字段更新为指定值,根据指定id  
	 * <p>
	 * 参数：表名、列名、值、id值 
	 */
	public int updateColumnById(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("value") Object value, 
			@Param("id") Object id
			);

	/**
	 * 指定表的指定字段更新为指定值,根据指定id列表  
	 * <p>
	 * 参数：表名、列名、值、id值 
	 */
	public int updateColumnByIds(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("value") Object value, 
			@Param("ids") List<?> ids
			);

	/**
	 * 指定表的指定字段更新为指定值, 根据指定列的指定值 
	 * <p>
	 * 参数：表名、列名、列值、条件列名、条件列值  
	 */
	public int updateColumnBy(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("columnValue") Object columnValue, 
			@Param("whereName") String whereName, 
			@Param("whereValue") Object whereValue
			);

	/**
	 * 	指定表的指定字段SoMap集合更新为指定值,根据指定id  
	 * <p>
	 * 参数：表名、id值、列集合  
	 */
	public int updateBySoMapById(
			@Param("tableName") String tableName, 
			@Param("soMap") SoMap soMap,
			@Param("id") Object id
			);

	/**
	 * 	指定表的指定字段SoMap集合更新为指定值,指定列的指定值 
	 * <p>
	 * 参数：表名、id值、列集合  
	 */
	public int updateBySoMapBy(
			@Param("tableName") String tableName, 
			@Param("soMap") SoMap soMap,
			@Param("whereName") String whereName, 
			@Param("whereValue") Object whereValue
			);
	
	
	// ------------------------ 查询SQL相关 ------------------------
	
	/**
	 * 获取指定表的指定字段值,根据id值 
	 * <p>
	 * 参数：表名、列名、id值 
	 */
	public String getColumnById(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("id") Object id
			);

	/**
	 * 获取指定表的指定字段值,并转化为long,根据id值 
	 * <p>
	 * 参数：表名、列名、id值 
	 */
	public long getColumnByIdToLong(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("id") Object id
			);

	/**
	 * 获取指定表的指定字段值,根据指定条件(whereName=whereValue) 
	 * <p>
	 * 参数：表名、列名、id值 
	 */
	public String getColumnByWhere(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("whereName") String whereName, 
			@Param("whereValue") Object whereValue
			);
	
	/**
	 * 获取指定表的指定字段值列表,并转化为long, 根据指定条件(whereName=whereValue) 
	 * <p>
	 * 参数：表名、列名、id值 
	 */
	public List<Long> getColumnListToLongByWhere(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("whereName") String whereName, 
			@Param("whereValue") Object whereValue
			);

	/**
	 * 获取指定表的count数据,根据指定条件(whereName=whereValue)
	 * <p>
	 * 参数：表名、where条件、where值
	 */
	public long getCountBy(
			@Param("tableName") String tableName,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	// ------------------------ 查询集合SQL相关 ------------------------
	/**
	 * 获取指定表的全部字段全部数据转化为Map
	 */
	public List<SoMap> getListMap(@Param("tableName") String tableName);
	
	/**
	 * 获取指定表的全部字段全部数据转化为Map, 根据指定条件(whereName=whereValue)
	 */
	public List<SoMap> getListMapByWhere(
			@Param("tableName") String tableName, 
			@Param("whereName") String whereName, 
			@Param("whereValue") Object whereValue
			);

	/**
	 * 获取指定表的全部字段全部数据转化为Map, 根据指定条件(id=id)
	 */
	public List<SoMap> getListMapById(
			@Param("tableName") String tableName, 
			@Param("id") Object id
			);

	
}
