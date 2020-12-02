<!DOCTYPE html>
<html>
	<head>
		<title>${t.tableComment}-列表</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- 所有的 css & js 资源 -->
<#if cfg.webLibImportWay == 1 >
		<link rel="stylesheet" href="https://unpkg.com/element-ui@2.13.0/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="../../static/sa.css">
		<script src="https://unpkg.com/vue@2.6.10/dist/vue.min.js"></script>
		<script src="https://unpkg.com/element-ui@2.13.0/lib/index.js"></script>
		<script src="https://unpkg.com/jquery@3.4.1/dist/jquery.js"></script>
		<script src="https://www.layuicdn.com/layer-v3.1.1/layer.js"></script>
		<script src="../../static/sa.js"></script>
</#if>
<#if cfg.webLibImportWay == 2 >
		<link rel="stylesheet" href="../../static/kj/element-ui/theme-chalk/index.css">
		<link rel="stylesheet" href="../../static/sa.css">
		<script src="../../static/kj/vue.min.js"></script>
		<script src="../../static/kj/element-ui/index.js"></script>
		<script src="../../static/kj/jquery.min.js"></script>
		<script src="../../static/kj/layer/layer.js"></script>
		<script src="../../static/sa.js"></script>
</#if>
	</head>
	<body>
		<div class="vue-box" style="display: none;" :style="'display: block;'">
			<div class="c-panel">
				<!-- ------------- 检索参数 ------------- -->
				<div class="c-title">检索参数</div>
				<el-form ref="form" :model='p' @submit.native.prevent>
<#-- --------------------------------------- 循环：list-查询条件 --------------------------------------- -->
<#list t.getTallList() as c>
	<#if c.type == 3>
	<#elseif c.istx('no-s')>
	<#elseif c.istx('logic-delete')>
	<#elseif c.isFoType('img', 'audio', 'video', 'file')>
	<#elseif c.isFoType('img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list')>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
	<#elseif c.isFoType('time')>
	<#elseif c.isFoType('text', 'textarea', 'richtext', 'no')>
					<div class="c-item">
						<label class="c-label">${c.columnComment3}：</label>
						<el-input size="mini" v-model="p.${c.fieldName}"></el-input>
					</div>
	<#elseif c.isFoType('num')>
					<div class="c-item">
						<label class="c-label">${c.columnComment3}：</label>
						<el-input size="mini" v-model="p.${c.fieldName}" type="number"></el-input>
					</div>
	<#elseif c.foType == 'enum'>
					<div class="c-item">
						<label class="c-label">${c.columnComment3}：</label>
			<#if c.gtx('s-type') == '1' || c.gtx('s-type') == '2'>
						<el-radio-group v-model="p.${c.fieldName}" size="mini" <#if c.gtx('s-type') == '2'>class="s-radio-text" </#if>>
							<el-radio label="">不限</el-radio>
							<#list c.jvList?keys as jv>
							<el-radio :label="${jv}">${c.jvList[jv]}</el-radio>
							</#list>
						</el-radio-group>
			<#elseif c.gtx('s-type') == '3'>
						<el-radio-group v-model="p.${c.fieldName}" size="mini">
							<el-radio-button label="">不限</el-radio-button>
							<#list c.jvList?keys as jv>
							<el-radio-button :label="${jv}">${c.jvList[jv]}</el-radio-button>
							</#list>
						</el-radio-group>
			<#elseif c.gtx('s-type') == '4'>
						<el-select v-model="p.${c.fieldName}" size="mini">
							<el-option label="不限" value=""></el-option>
							<#list c.jvList?keys as jv>
							<el-option label="${c.jvList[jv]}" :value="${jv}"></el-option>
							</#list>
						</el-select>
			</#if>
					</div>
	<#elseif c.foType == 'fk-s'>
			<#if c.istx('drop')>
					<div class="c-item">
						<label class="c-label">${c.columnComment3}：</label>
						<el-select size="mini" v-model="p.${c.fkSCurrDc.fieldName}">
							<el-option label="不限" value=""></el-option>
							<el-option v-for="item in ${c.fieldName}List" :label="item.${c.tx.catc}" :value="item.${c.tx.jc}" :key="item.${c.tx.jc}"></el-option>
						</el-select>
					</div>
			</#if>
	<#elseif c.foType == 'fk-p'>
	<#else>
					<!-- 未识别类型：${c.columnComment3}: p.${c.fieldName} 请检查配置 -->
	</#if>
</#list>
					<div class="c-item" style="min-width: 0px;">
						<el-button size="mini" type="primary" icon="el-icon-search" @click="p.pageNo = 1; f5()">查询</el-button>
					</div>
					<br />
					<div class="c-item s-radio-text">
						<label class="c-label">综合排序：</label>
						<el-radio-group v-model="p.sortType">
							<el-radio :label="0">默认</el-radio>
<#list t.getTallListBySort() as c>
							<el-radio :label="${c_index + 1}">${c.columnComment3}</el-radio>
</#list>
						</el-radio-group>
					</div>
				</el-form>
				<!-- ------------- 快捷按钮 ------------- -->
				<div class="fast-btn">
					<el-button size="mini" type="primary" icon="el-icon-plus" @click="add()">新增</el-button>
					<el-button size="mini" type="success" icon="el-icon-view" @click="getBySelect()">查看</el-button>
					<el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteByIds()">删除</el-button>
					<el-button size="mini" type="warning" icon="el-icon-download" @click="sa.exportExcel()">导出</el-button>
					<el-button size="mini" type="info"  icon="el-icon-refresh"  @click="sa.f5()">重置</el-button>
				</div>
				<!-- ------------- 数据列表 ------------- -->
				<el-table class="data-table" ref="data-table" :data="dataList" size="small"<#if t.hasFt('tree')> row-key="${t.primaryKey.fieldName}" border @expand-change="sa.f5TableHeight()"</#if><#if t.hasFt('tree-lazy')> row-key="${t.primaryKey.fieldName}" border lazy :load="loadChildren" @expand-change="sa.f5TableHeight()"</#if>>
					<el-table-column type="selection" width="45px"></el-table-column>
<#list t.tallList as c>
	<#if c.istx('no-show')>
	<#elseif c.foType == 'logic-delete'>
	<#elseif c.istx('click')>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<el-link type="primary" @click="sa.showIframe(' id = ' + s.row.${c.getClickCatKeyColumn()} + '  详细信息', '../${c.getClickCatTableKebabName()}/${c.getClickCatTableKebabName()}-info.html?id=' + s.row.${c.getClickCatKeyColumn()})">
								{{s.row.${c.fieldName}}} 
							</el-link>
						</template>
					</el-table-column>
	<#elseif c.istx('switch')>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<el-switch v-model="s.row.${c.fieldName}" :active-value="${c.jvKeyList[0]}" :inactive-value="${c.jvKeyList[1]}" @change="update${c.fieldNameFnCat}(s.row)" inactive-color="#ff4949" style="vertical-align: top;"></el-switch>
							<span style="color: #999;" v-if="s.row.status == ${c.jvKeyList[0]}">${c.jvList[c.jvKeyList[0]]}</span>
							<span style="color: #999;" v-if="s.row.status == ${c.jvKeyList[1]}">${c.jvList[c.jvKeyList[1]]}</span>
						</template>
					</el-table-column>
	<#elseif c.istx('fast-update')>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<span>{{s.row.${c.fieldName}}}</span>
							<el-button type="text" size="small" @click="update${c.fieldNameFnCat}(s.row)">改</el-button>
						</template>
					</el-table-column>
	<#elseif c.isFoType('text', 'textarea', 'fk-s', 'fk-p')>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" ></el-table-column>
	<#elseif c.isFoType('num')>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" class-name="tc-num"></el-table-column>
	<#elseif c.foType == 'richtext'>
					<el-table-column label="${c.columnComment3}" show-overflow-tooltip>
						<template slot-scope="s"><span>{{sa.maxLength(sa.text(s.row.${c.fieldName}), 100)}}</span></template>
					</el-table-column>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
					<el-table-column label="${c.columnComment3}" class-name="tc-date">
						<template slot-scope="s">{{sa.forDate(s.row.${c.fieldName}, 2)}}</template>
					</el-table-column>
	<#elseif c.isFoType('time')>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" class-name="tc-date"></el-table-column>
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
	<#elseif c.foType == 'link'>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<el-link type="primary" :href="s.row.${c.fieldName}" target="_blank">{{s.row.${c.fieldName}}}</el-link>
						</template>
					</el-table-column>
	<#elseif c.foType == 'enum'>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
		<#list c.jvList?keys as jv>
							<b v-if="s.row.${c.fieldName} == ${jv}">${c.jvList[jv]}</b>
		</#list>
						</template>
					</el-table-column>
	<#else>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" ></el-table-column>
	</#if>
</#list>
					<el-table-column label="操作" fixed="right" <#if t.hasFt('tree', 'tree-lazy')> width="320px"<#else> width="240px"</#if>>
						<template slot-scope="s">
							<el-button class="c-btn" type="success" icon="el-icon-view" @click="get(s.row)">查看</el-button>
							<el-button class="c-btn" type="primary" icon="el-icon-edit" @click="update(s.row)">修改</el-button>
<#if t.hasFt('tree', 'tree-lazy')>
							<el-button class="c-btn" type="primary" icon="el-icon-plus" @click="addChildren(s.row)">添加子级</el-button>
</#if>
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
<#if t.hasFt('tree')>
						:page-sizes="[1000]" 
<#else>
						:page-sizes="[1, 10, 20, 30, 40, 50, 100, 1000]" 
</#if>
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
				<#list t.getT1List() as c>
					<#if c.getFlag() == 'tree-parent-id' && t.hasFt('tree-lazy')>
						${c.fieldName}: sa.p('${c.fieldName}', ${t.getFt('tree-lazy').top}),		// ${c.columnComment} 
					<#elseif c.isFoType("date", "date-create", "date-update", "time", "img", "img-list", "audio", "audio-list", "video", "video-list", "file", "file-list", "logic-delete")>
					<#else>
						${c.fieldName}: '',		// ${c.columnComment} 
					</#if>
				</#list>
						pageNo: 1,		// 当前页 
						pageSize: 10,	// 页大小 
						sortType: 0		// 排序方式 
					},
					dataCount: 0,
					dataList: [], // 数据集合 
				<#list t.getT2DropList() as c>
					${c.fieldName}List: [],		// ${c.columnComment} 集合 
				</#list>
				},
				methods: {
					// 刷新
					f5: function() {
<#if t.hasFt('tree')>
						sa.ajax('/${t.mkNameBig}/getTree', sa.removeNull(this.p), function(res) {
							this.dataList = res.data; // 数据
							this.dataCount = res.dataCount; // 数据总数 
							sa.f5TableHeight();		// 刷新表格高度 
						}.bind(this));
<#else>
						sa.ajax('/${t.mkNameBig}/getList', sa.removeNull(this.p), function(res) {
							this.dataList = res.data; // 数据
							this.dataCount = res.dataCount; // 数据总数 
							sa.f5TableHeight();		// 刷新表格高度 
						}.bind(this));
</#if>
					},
<#if t.hasFt('tree-lazy')>
					// 加载子节点 
					loadChildren: function(tree, treeNode, resolve) {
						var p2 = sa.copyJSON(sa.removeNull(this.p));
						p2.pageSize = 100;
						p2.${t.getTreeFkey()} = tree.${t.getTreeIdkey()};
						sa.ajax('/${t.mkNameBig}/getList', p2, function(res) {
							resolve(res.data);
							sa.f5TableHeight();		// 刷新表格高度 
						}.bind(this));
					},
</#if>
					// 查看
					get: function(data) {
						sa.showIframe('数据详情', '${t.kebabName}-info.html?id=' + data.id, '950px', '90%');
					},
					// 查看 - 根据选中的
					getBySelect: function(data) {
						var selection = this.$refs['data-table'].selection;
						if(selection.length == 0) {
							return sa.msg('请选择一条数据')
						}
						this.get(selection[0]);
					},
					// 修改
					update: function(data) {
						sa.showIframe('修改数据', '${t.kebabName}-add.html?id=' + data.id, '900px', '90%');
					},
					// 新增
					add: function(data) {
						sa.showIframe('新增数据', '${t.kebabName}-add.html?id=-1', '900px', '90%');
					},
<#if t.hasFt('tree', 'tree-lazy')>
					// 新增子级
					addChildren: function(data) {
						sa.showIframe('新增数据', '${t.kebabName}-add.html?id=-1&${t.getTreeFkey()}=' + data.${t.getTreeIdkey()}, '900px', '90%');
	<#if t.hasFt('tree-lazy')>
	</#if>
					},
</#if>
					// 删除
					del: function(data) {
						sa.confirm('是否删除，此操作不可撤销', function() {
							sa.ajax('/${t.mkNameBig}/delete?id=' + data.${t.primaryKey.fieldName}, function(res) {
<#if t.hasFt('tree')>
								this.f5();
<#elseif t.hasFt('tree-lazy')>
								this.dataList = [];
								this.f5();
<#else>
								sa.arrayDelete(this.dataList, data);
								sa.ok('删除成功');
								sa.f5TableHeight();		// 刷新表格高度 
</#if>
							}.bind(this))
						}.bind(this));
					},
					// 批量删除
					deleteByIds: function() {
						// 获取选中元素的id列表 
						let selection = this.$refs['data-table'].selection;
						let ids = sa.getArrayField(selection, 'id');
						if(selection.length == 0) {
							return sa.msg('请至少选择一条数据')
						}
						// 提交删除 
						sa.confirm('是否批量删除选中数据？此操作不可撤销', function() {
							sa.ajax('/${t.mkNameBig}/deleteByIds', {ids: ids.join(',')}, function(res) {
<#if t.hasFt('tree')>
								this.f5();
<#elseif t.hasFt('tree-lazy')>
								this.dataList = [];
								this.f5();
<#else>
								sa.arrayDelete(this.dataList, selection);
								sa.ok('删除成功');
								sa.f5TableHeight();		// 刷新表格高度 
</#if>
							}.bind(this))
						}.bind(this));
					},
			<#list t.getTallListByTxKey('switch') as c>
					// 改 - ${c.columnComment}
					update${c.fieldNameFnCat}: function(data) {
						// 声明变量记录是否成功 
						var isOk = false;	
						var oldValue = data.${c.fieldName};
						var ajax = sa.ajax('/${t.mkNameBig}/update${c.fieldNameFnCat}', {id: data.${t.primaryKey.fieldName}, value: data.${c.fieldName}}, function(res) {
							isOk = true;
							sa.msg('修改成功');
						}.bind(this));
						// 如果未能修改成功, 则回滚 
						$.when(ajax).done(function() {
							if(isOk == false) {
								data.status = oldValue; 
							}
						})
					},
			</#list>
			<#list t.getTallListByTxKey('fast-update') as c>
					// 改 - ${c.columnComment}
					update${c.fieldNameFnCat}: function(data) {
						sa.prompt('请输入${c.columnComment}', function(value, index){
							sa.ajax('/${t.mkNameBig}/update${c.fieldNameFnCat}', {id: data.${t.primaryKey.fieldName}, value: value}, function(res){
								data.${c.fieldName} = value;
								sa.ok2('修改成功');
							})
						});
					},
			</#list>
				},
				created: function() {
					this.f5();
					sa.onInputEnter();
			<#if t.getT2DropList()?size != 0>
				
					// ------------- 加载所需外键列表 -------------
				<#list t.getT2DropList() as c>
					// 加载 ${c.columnComment} 
					sa.ajax('/${c.getJtMkName()}/getList?pageSize=1000', function(res) {
						this.${c.fieldName}List = res.data; // 数据集合 
					}.bind(this), {msg: null});
				</#list>
			</#if>
				}
			})
		</script>
	</body>
</html>
