<!DOCTYPE html>
<html>
	<head>
		<title>${t.tableComment}-列表</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- 所有的 css & js 资源 -->
		<link rel="stylesheet" href="https://unpkg.com/element-ui@2.13.0/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="../../static/sa.css">
		<script src="https://unpkg.com/vue@2.6.10/dist/vue.min.js"></script>
		<script src="https://unpkg.com/element-ui@2.13.0/lib/index.js"></script>
		<script src="https://unpkg.com/jquery@3.4.1/dist/jquery.js"></script>
		<script src="https://www.layuicdn.com/layer-v3.1.1/layer.js"></script>
		<script src="../../static/sa.js"></script>
	</head>
	<body>
		<div class="vue-box" style="display: none;" :style="'display: block;'">
			<div class="c-panel">
				<!-- ------------- 检索参数 ------------- -->
				<div class="c-title">检索参数</div>
				<el-form ref="form" :model='p' @submit.native.prevent>
<#-- --------------------------------------- 循环：list-查询条件 --------------------------------------- -->
<#list t.columnList as c>
	<#if c.isFoType('text', 'textarea', 'richtext', 'file', 'num', 'fk-2', 'no')>
					<div class="c-item">
						<label class="c-label">${c.columnComment3}：</label>
						<el-input size="mini" v-model="p.${c.fieldName}" <#if c.foType == 'num'>type="number" </#if>></el-input>
					</div>
	<#elseif c.isFoType('img', 'audio', 'video', 'file')>
	<#elseif c.isFoType('img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list')>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
	<#elseif c.foType == 'enum'>
					<div class="c-item">
						<label class="c-label">${c.columnComment3}：</label>
			<#if c.getTx('s-type') == '1' || c.getTx('s-type') == '2'>
						<el-radio-group v-model="p.${c.fieldName}" size="mini" <#if c.getTx('s-type') == '2'>class="s-radio-text" </#if>>
							<el-radio :label="0">不限</el-radio>
							<#list c.jvList?keys as jv>
							<el-radio :label="${jv}">${c.jvList[jv]}</el-radio>
							</#list>
						</el-radio-group>
			<#elseif c.getTx('s-type') == '3'>
						<el-radio-group v-model="p.${c.fieldName}" size="mini">
							<el-radio-button :label="0">不限</el-radio-button>
							<#list c.jvList?keys as jv>
							<el-radio-button :label="${jv}">${c.jvList[jv]}</el-radio-button>
							</#list>
						</el-radio-group>
			<#elseif c.getTx('s-type') == '4'>
						<el-select v-model="p.${c.fieldName}" size="mini">
							<el-option label="不限" :value="0"></el-option>
							<#list c.jvList?keys as jv>
							<el-option label="${c.jvList[jv]}" :value="${jv}"></el-option>
							</#list>
						</el-select>
			</#if>
					</div>
	<#elseif c.foType == 'fk-1'>
					<div class="c-item">
						<label class="c-label">${c.fkPkConcatComment}：</label>
						<el-select size="mini" v-model="p.${c.fieldName}">
							<el-option label="不限" :value="0"></el-option>
							<el-option v-for="item in ${c.fkPkTableName2}List" :label="item.${c.fkPkConcatName}" :value="item.${c.fkPkFieldName}" :key="item.${c.fkPkColumnName}"></el-option>
						</el-select>
					</div>
	<#else>
					<!-- 未识别类型：${c.columnComment3}: p.${c.fieldName} -->
	</#if>
</#list>
					<div class="c-item" style="min-width: 0px;">
						<el-button size="mini" type="primary" icon="el-icon-search" @click="p.pageNo = 1; f5()">查询</el-button>
					</div>
					<br />
					<div class="c-item s-radio-text">
						<label class="c-label">综合排序：</label>
						<el-radio-group v-model="p.sortType">
							<el-radio :label="0">最近添加</el-radio>
<#list t.columnList as c>
						<#if c_index <= 3>
							<el-radio :label="${c_index + 1}">${c.columnComment3}</el-radio>
						<#else>
							<!-- <el-radio :label="${c_index + 1}">${c.columnComment3}</el-radio> -->
						</#if>
</#list>
						</el-radio-group>
					</div>
				</el-form>
				<!-- ------------- 快捷按钮 ------------- -->
				<div class="fast-btn">
					<el-button size="mini" type="primary" icon="el-icon-plus" @click="add()">新增</el-button>
					<el-button size="mini" type="success" icon="el-icon-edit" @click="update($refs['data-table'].selection[0])"
						:disabled="!$refs['data-table'] || $refs['data-table'].selection.length != 1">修改</el-button>
					<el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteByIds()"
						:disabled="!$refs['data-table'] || $refs['data-table'].selection.length < 1">删除</el-button>
					<el-button size="mini" type="warning" icon="el-icon-download" @click="sa.exportExcel()">导出</el-button>
					<el-button size="mini" type="info"  icon="el-icon-refresh"  @click="sa.f5()">重置</el-button>
				</div>
				<!-- ------------- 数据列表 ------------- -->
				<el-table class="data-table" ref="data-table" :data="dataList" size="small">
					<el-table-column type="selection" width="45px"></el-table-column>
<#list t.columnList as c>
	<#if c.isFoType('text', 'textarea', 'num')>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" ></el-table-column>
	<#elseif c.foType == 'richtext'>
					<el-table-column label="${c.columnComment3}" min-width="150px">
						<template slot-scope="s"><span>{{sa.maxLength(sa.text(s.row.${c.fieldName}), 100)}}</span></template>
					</el-table-column>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
					<el-table-column label="${c.columnComment3}" min-width="150px">
						<template slot-scope="s"><span>{{sa.forDate(s.row.${c.fieldName}, 2)}}</span></template>
					</el-table-column>
	<#elseif c.foType == 'img'>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<img :src="s.row.${c.fieldName}" style="width: 3em; height: 3em; border-radius: 3px; cursor: pointer;" 
								@click="sa.showImage(s.row.${c.fieldName}, '400px', '400px')" v-if="s.row.${c.fieldName}" />
							<div v-else>无</div>
						</template>
					</el-table-column>
	<#elseif c.isFoType('audio', 'video', 'file')>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<el-link type="info" :href="s.row.${c.fieldName}" target="_blank" v-if="!sa.isNull(s.row.${c.fieldName})">预览</el-link>
							<div v-else>无</div>
						</template>
					</el-table-column>
	<#elseif c.foType == 'img-list'>
					<el-table-column label="${c.columnComment3}" min-width="120px">
						<template slot-scope="s">
							<div @click="sa.showImageList(s.row.${c.fieldName}.split(','))" style="cursor: pointer;" v-if="s.row.${c.fieldName}">
								<img :src="s.row.${c.fieldName}.split(',')[0]" style="width: 3em; height: 3em; border-radius: 3px; cursor: pointer;" />
								<span style="color: #999; padding-left: 0.5em;">点击预览</span>
							</div>
							<div v-else>无</div>
						</template>
					</el-table-column>
	<#elseif c.isFoType('audio-list', 'video-list', 'file-list', 'img-video-list')>
					<el-table-column label="${c.columnComment3}" min-width="70px">
						<template slot-scope="s">
							<span v-if="s.row.${c.fieldName}" style="color: #666;">共{{s.row.${c.fieldName}.split(',').length}}个</span>
							<span v-else>无</span>
						</template>
					</el-table-column>
	<#elseif c.foType == 'enum'>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
		<#list c.jvList?keys as jv>
							<p v-if="s.row.${c.fieldName} == ${jv}">${c.jvList[jv]}</p>
		</#list>
						</template>
					</el-table-column>
	<#elseif c.isFoType('fk-1', 'fk-2')>
			<#if c.isTx('showfk')>
				<#if c.isTx('link')>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<el-link type="primary" @click="sa.showIframe(' id = ' + s.row.${c.fieldName} + '  详细信息', '../${c.fkPkTableKebabName}/${c.fkPkTableKebabName}-info.html?id=' + s.row.${c.fieldName})">
								{{s.row.${c.fieldName}}} 
							</el-link>
						</template>
					</el-table-column>
				<#else>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" ></el-table-column>
				</#if>
			</#if>
			<#list c.fkPkConcatList as fk>
				<#if c.isTx('link')>
					<el-table-column label="${fk.fkPkConcatComment}">
						<template slot-scope="s">
							<el-link type="primary" @click="sa.showIframe('id = ' + s.row.${c.fieldName} + ' 详细信息', '../${c.fkPkTableKebabName}/${c.fkPkTableKebabName}-info.html?id=' + s.row.${c.fieldName})">
								{{s.row.${fk.fieldName}}} 
							</el-link>
						</template>
					</el-table-column>
				<#else>
					<el-table-column label="${fk.fkPkConcatComment}" prop="${fk.fieldName}" ></el-table-column>
				</#if>
			</#list>
	<#else>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" ></el-table-column>
	</#if>
</#list>
<#list t.getAllDbFk_jh() as fk>
					<el-table-column label="${fk.tx.comment}" prop="${fk.getAsColumnName_fs()}" ></el-table-column>
</#list>
					<el-table-column label="操作" width="240px">
						<template slot-scope="s">
							<el-button class="c-btn" type="success" icon="el-icon-view" @click="get(s.row)">查看</el-button>
							<el-button class="c-btn" type="primary" icon="el-icon-edit" @click="update(s.row)">修改</el-button>
							<el-button class="c-btn" type="danger" icon="el-icon-delete" @click="del(s.row)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
				<!-- ------------- 分页 ------------- -->
				<div class="page-box">
					<el-pagination background
						layout="total, prev, pager, next, sizes, jumper" 
						:current-page.sync="p.pageNo" 
						:page-size.sync="p.pageSize" 
						:total="dataCount" 
						:page-sizes="[1, 10, 20, 30, 40, 50, 100, 1000]" 
						@current-change="f5()" 
						@size-change="f5()">
					</el-pagination>
				</div>
			</div>
		</div>
		<script>
			var app = new Vue({
				el: '.vue-box',
				data: {
					p: { // 查询参数  
				<#list t.columnList as c>
					<#if c.foType == 'enum'>
						${c.fieldName}: 0,		// ${c.columnComment} 
					<#else>
						${c.fieldName}: '',		// ${c.columnComment} 
					</#if>
				</#list>
						pageNo: 1,		// 当前页 
						pageSize: 10,	// 页大小 
						sortType: 0	// 排序方式 
					},
					dataCount: 0,
					dataList: [], // 数据集合 
				<#list t.getColumnListBy('fk-1') as c>
					${c.fkPkTableName2}List: [],		// ${c.fkPkConcatComment}集合
				</#list>
				},
				methods: {
					// 刷新
					f5: function() {
						sa.ajax('/${t.mkNameBig}/getList', sa.removeNull(this.p), function(res) {
							this.dataList = res.data; // 数据
							this.dataCount = res.dataCount; // 数据总数 
							sa.f5TableHeight();		// 刷新表格高度 
						}.bind(this));
					},
					// 查看
					get: function(data) {
						sa.showIframe('数据详情', '${t.kebabName}-info.html?id=' + data.id, '950px', '90%');
					},
					// 修改
					update: function(data) {
						sa.showIframe('修改数据', '${t.kebabName}-add.html?id=' + data.id, '900px', '90%');
					},
					// 新增
					add: function(data) {
						sa.showIframe('新增数据', '${t.kebabName}-add.html?id=-1', '900px', '90%');
					},
					// 删除
					del: function(data) {
						sa.confirm('是否删除，此操作不可撤销', function() {
							sa.ajax('/${t.mkNameBig}/delete?id=' + data.${t.primaryKey.fieldName}, function(res) {
								sa.arrayDelete(this.dataList, data);
								sa.ok('删除成功');
								sa.f5TableHeight();		// 刷新表格高度 
							}.bind(this))
						}.bind(this));
					},
					// 批量删除
					deleteByIds: function() {
						// 获取选中元素的id列表
						let selection = this.$refs['data-table'].selection;
						let ids = sa.getArrayField(selection, 'id');
						// 提交删除 
						sa.confirm('是否批量删除选中数据？此操作不可撤销', function() {
							sa.ajax('/${t.mkNameBig}/deleteByIds', {ids: ids.join(',')}, function(res) {
								sa.arrayDelete(this.dataList, selection);
								sa.ok('删除成功');
								sa.f5TableHeight();		// 刷新表格高度 
							}.bind(this))
						}.bind(this));
					},
				},
				created: function() {
					this.f5();
					sa.onInputEnter();
			<#if t.getColumnListBy('fk-1')?size != 0>
				
					// ------------- 加载所需外键列表 -------------
				<#list t.getColumnListBy('fk-1') as c>
					// 加载 ${c.fkPkConcatComment}
					sa.ajax('/${c.fkPkTableMkName}/getList?pageSize=1000', function(res) {
						this.${c.fkPkTableName2}List = res.data; // 数据集合 
					}.bind(this), {msg: null});
				</#list>
			</#if>
				}
			})
		</script>
	</body>
</html>
