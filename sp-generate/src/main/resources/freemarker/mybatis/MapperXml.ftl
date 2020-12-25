<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${t.packagePath}.${t.mkNameBig}Mapper">

	<!-- 增 [G] -->
	<insert id="add">
		insert into 
		`${t.tableName}` (${t.getT1List_ByMapperInsertColumn()}) 
		values (${t.getT1List_ByMapperInsertValues()}) 
	</insert>

	<!-- 删 -->
	<delete id="delete">
<#if t.hasFo("logic-delete")>
		update `${t.tableName}` 
		set `${t.getDbColumnByFoType('logic-delete').columnName}` = ${t.getDbColumnByFoType('logic-delete').tx.no} 
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}} 
<#else>
		delete from `${t.tableName}` 
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}}
</#if>
	</delete>

	<!-- 改 [G] -->
	<update id="update">
		update `${t.tableName}` set
${t.getT1List_ByMapperUpdateSet()}
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}}
	</update>


	<!-- ================================== 查询相关 ================================== -->
	<#if cfg.sqlSelectColumnWay == 1>
	<!-- select ${t.getT1List_ByMapperInsertColumn()} from ${t.tableName}  --></#if>
	
<#if cfg.resultMapWay == 1>
	<!-- 通用映射：自动模式 -->
	<resultMap id="model" autoMapping="true" type="${t.packagePath}.${t.modelName}"></resultMap>
</#if>
<#if cfg.resultMapWay == 2>
	<!-- 通用映射：手动模式 -->
	<resultMap id="model" type="${t.packagePath}.${t.modelName}">
	<#list t.getT1List() as c>
		<result property="${c.fieldName}" column="${c.columnName}" />
	</#list>
	<#list t.getT2List() as c>
		<result property="${c.fieldName}" column="${c.columnName}" />
	</#list>
	<#list t.getT3List() as c>
		<result property="${c.fieldName}" column="${c.columnName}" />
	</#list>
	</resultMap>
</#if>
	
	<!-- 公共查询sql片段 -->
	<sql id="select_sql">
		select ${t.getT1ListCatStringOrStar()}<#if t.getT2List()?size != 0 || t.getT3List()?size != 0>,</#if> 
<#list t.getT2List() as c>
		${c.getT2Sql()}<#if c_index != t.getT2List()?size-1 || t.getT3List()?size != 0>, </#if>
</#list>
<#list t.getT3List() as c>
		${c.getT3Sql()}<#if c_index != t.getT3List()?size-1>, </#if>
</#list>
		from `${t.tableName}` 
	</sql>
	
	<!-- 查 - 根据id -->
	<select id="getById" resultMap="model">
		<include refid="select_sql"></include>
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}}
	</select>
	
	<!-- 查集合 - 根据条件（参数为空时代表忽略指定条件） [G] -->
	<select id="getList" resultMap="model">
		<include refid="select_sql"></include>
		<where>
${t.getT1List_ByMapperGetListWhere()}<#rt>
		</where>
		order by
		<choose>
${t.getT1List_ByMapperGetListSort()}<#rt>
		</choose>
	</select>
	
	
	
	
	
	
	
	
	

</mapper>
