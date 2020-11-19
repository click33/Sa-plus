package com.pj.project4sp.public4mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.utils.so.SoMap;

/**
 * 公用Mapper，一大堆常见方法 
 * @author kong
 * 更新于2020-6-22 更换部分API命名 
 *
 */
@Mapper
public interface PublicMapper {

	/**
	 * 返回上一句SQL插入的自增主键值
	 */
	public long getPrimarykey();

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
	
	
	/**
	 * 指定表的指定字段增加指定值，可以为负值 
	 * <p>
	 * 参数：表明、列表、增加的值、id值 
	 */
	public int columnAdd(
			@Param("tableName") String tableName, 
			@Param("columnName") String columnName, 
			@Param("num") int num,  
			@Param("id") Object id 
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
	
	
	
}
