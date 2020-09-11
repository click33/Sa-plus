<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${t.packagePath}.${t.mkNameBig}Mapper">

	<!-- 增【G】 -->
	<insert id="add">
		insert into 
		`${t.tableName}` (${t.getAllColumnString()}) 
		values (<#list t.columnList as c><#if c.isFoType('date-create', 'date-update')>now()<#else><#noparse>#</#noparse>{${c.fieldName}}</#if><#if c_index != t.columnList?size - 1>, </#if></#list>) 
	</insert>

	<!-- 删 -->
	<delete id="delete">
		delete from `${t.tableName}` 
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}}
	</delete>
	
	<!-- 改【G】 -->
	<update id="update">
		update `${t.tableName}` set
<#list t.columnList as c>
	<#if c.isFoType('no', 'date-create')>
	<#elseif c.foType == 'date-update'>
		`${c.columnName}` = now(),
	<#else>
		`${c.columnName}` = <#noparse>#</#noparse>{${c.fieldName}},
	</#if>
</#list>
		`${t.primaryKey.columnName}` = ${t.primaryKey.columnName} 
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}}
	</update>
	
	
	<!-- ================================== 查询相关 ================================== -->
	<#if cfg.sqlSelectColumnWay == 1>
	<!-- select ${t.getAllColumnString()} from ${t.tableName}  --></#if>
	
	<!-- 通用映射：自动模式 --><#if cfg.resultMapWay == 2>
	<!--</#if>
	<resultMap id="model" autoMapping="true" type="${t.packagePath}.${t.modelName}"></resultMap>
	<#if cfg.resultMapWay == 2>--></#if>
	
	<!-- 通用映射：手动模式 --><#if cfg.resultMapWay == 1>
	<!--</#if>
	<resultMap id="model" type="${t.packagePath}.${t.modelName}">
	<#list t.columnList as c>
		<result property="${c.fieldName}" column="${c.columnName}" />
	</#list>
	<#list t.getAllDbFk_12() as fk>
		<result property="${fk.fieldName}" column="${fk.columnName}" />
	</#list>
	<#list t.getAllDbFk_jh() as fk>
		<result property="${fk.getAsColumnName_fs()}" column="${fk.getAsColumnName()}" />
	</#list>
	</resultMap>
	<#if cfg.resultMapWay == 1>--></#if>
	
	
	<!-- 公共查询sql片段 -->
	<sql id="select_sql">
		select ${t.getAllColumnStringOrStar()}<#if t.getAllDbFk_12()?size != 0 || t.getAllDbFk_jh()?size != 0>,</#if> 
<#list t.getAllDbFk_12() as fk>
		(select `${fk.fkPkConcatName}` from `${fk.dc.fkPkTableName}` where `${fk.dc.fkPkColumnName}` = ${t.tableName}.${fk.dc.columnName}) as ${fk.columnName}<#if fk_index != t.getAllDbFk_12()?size-1 || t.getAllDbFk_jh()?size != 0>, </#if>
</#list>
<#list t.getAllDbFk_jh() as fk>
		${fk.getJhSql()}<#if fk_index != t.getAllDbFk_jh()?size-1>, </#if>
</#list>
		from `${t.tableName}` 
	</sql>
	
	
	<!-- 查 -->
	<select id="getById" resultMap="model">
		<include refid="select_sql"></include>
		where `${t.primaryKey.columnName}` = <#noparse>#</#noparse>{${t.primaryKey.fieldName}}
	</select>
	
	<!-- 查询，根据条件(参数为null或0时默认忽略此条件)【G】 -->
	<select id="getList" resultMap="model">
		<include refid="select_sql"></include>
		<where>
<#list t.columnList as c>
	<#if c.isFoType('date', 'date-create', 'date-update', 'img', 'img-list', 'audio', 'audio-list', 'video', 'video-list', 'file', 'file-list')>
	<#else>
			<if test=' this.isNotNull("${c.fieldName}") '><#rt>
				<#if c.getTx('j') == 'like'> <#t>
				and `${c.columnName}` like concat('%', <#noparse>#</#noparse>{${c.fieldName}}, '%') <#t>
				<#else> <#t>
				and `${c.columnName}` = <#noparse>#</#noparse>{${c.fieldName}} <#t>
				</#if><#t>
			</if><#lt>
	</#if>
</#list>
		</where>
		order by 
		<choose>
			<#list t.columnList as c>
			<when test='sortType == ${c_index + 1}'> `${c.columnName}` desc </when> 
			</#list>
		 	<otherwise> `${t.primaryKey.columnName}` desc </otherwise>
		 </choose>
	</select>


</mapper>
