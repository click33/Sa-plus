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
		<script src="https://unpkg.com/vue@2.6.10/dist/vue.js"></script>
		<script src="https://unpkg.com/element-ui@2.13.0/lib/index.js"></script>
		<script src="https://unpkg.com/http-vue-loader@1.4.2/src/httpVueLoader.js"></script>
		<script src="https://unpkg.com/jquery@3.4.1/dist/jquery.js"></script>
		<script src="https://www.layuicdn.com/layer-v3.1.1/layer.js"></script>
		<script src="../../static/sa.js"></script>
</#if>
<#if cfg.webLibImportWay == 2 >
		<link rel="stylesheet" href="../../static/kj/element-ui/theme-chalk/index.css">
		<link rel="stylesheet" href="../../static/sa.css">
		<script src="../../static/kj/vue.min.js"></script>
		<script src="../../static/kj/element-ui/index.js"></script>
		<script src="../../static/kj/httpVueLoader.js"></script>
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
					<sa-item type="text" name="${c.columnComment3}" v-model="p.${c.fieldName}"></sa-item>
	<#elseif c.isFoType('num')>
					<sa-item type="num" name="${c.columnComment3}" v-model="p.${c.fieldName}"></sa-item>
	<#elseif c.foType == 'enum'>
					<sa-item type="enum" name="${c.columnComment3}" v-model="p.${c.fieldName}" 
						:jv="${c.getJvJson()}" jtype="${c.gtx('s-type')}" def="不限"></sa-item>
	<#elseif c.foType == 'fk-s'>
			<#if c.istx('drop')>
					<sa-item name="${c.columnComment3}">
						<el-select v-model="p.${c.fkSCurrDc.fieldName}">
							<el-option label="不限" value=""></el-option>
							<el-option v-for="item in ${c.fieldName}List" :label="item.${c.tx.catc}" :value="item.${c.tx.jc}" :key="item.${c.tx.jc}"></el-option>
						</el-select>
					</sa-item>
			</#if>
	<#elseif c.foType == 'fk-p'>
	<#else>
					<!-- 未识别类型：${c.columnComment3}: p.${c.fieldName} 请检查配置 -->
	</#if>
</#list>
					<el-button type="primary" icon="el-icon-search" @click="p.pageNo = 1; f5()">查询</el-button>
					<br />
					<sa-item name="综合排序">
						<el-radio-group v-model="p.sortType" class="s-radio-text">
							<el-radio :label="0">默认</el-radio>
						<#list t.getTallListBySort() as c>
							<el-radio :label="${c_index + 1}">${c.columnComment3}</el-radio>
						</#list>
						</el-radio-group>
					</sa-item>
				</el-form>
				<!-- ------------- 快捷按钮 ------------- -->
				<sa-item type="fast-btn" show="add,get,delete,export,reset"></sa-item>
				<!-- ------------- 数据列表 ------------- -->
				<el-table class="data-table" ref="data-table" :data="dataList" <#if t.hasFt('tree')> row-key="${t.primaryKey.fieldName}" border @expand-change="sa.f5TableHeight()"</#if><#if t.hasFt('tree-lazy')> row-key="${t.primaryKey.fieldName}" border lazy :load="loadChildren" @expand-change="sa.f5TableHeight()"</#if>>
					<sa-td type="selection"></sa-td>
<#list t.tallList as c>
	<#if c.istx('no-show')>
	<#elseif c.foType == 'logic-delete'>
	<#elseif c.istx('click')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="link-btn" @click="s => sa.showIframe(' id = ' + s.row.${c.getClickCatKeyColumn()} + '  详细信息', '../${c.getClickCatTableKebabName()}/${c.getClickCatTableKebabName()}-info.html?id=' + s.row.${c.getClickCatKeyColumn()})"></sa-td>
	<#elseif c.istx('switch')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="switch" :jv="${c.getJvJson()}" @change="s => update${c.fieldNameFnCat}(s.row)"></sa-td>
	<#elseif c.istx('fast-update')>
					<el-table-column label="${c.columnComment3}">
						<template slot-scope="s">
							<span>{{s.row.${c.fieldName}}}</span>
							<el-button type="text" @click="update${c.fieldNameFnCat}(s.row)">改</el-button>
						</template>
					</el-table-column>
	<#elseif c.isFoType('text', 'fk-s', 'fk-p')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" ></sa-td>
	<#elseif c.isFoType('textarea')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="textarea"></sa-td>
	<#elseif c.isFoType('num')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="num"></sa-td>
	<#elseif c.foType == 'richtext'>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="richtext"></sa-td>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="datetime"></sa-td>
	<#elseif c.isFoType('time')>
					<el-table-column label="${c.columnComment3}" prop="${c.fieldName}" class-name="tc-date"></el-table-column>
	<#elseif c.foType == 'img'>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="img"></sa-td>
	<#elseif c.isFoType('audio', 'video', 'file')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="${c.foType}"></sa-td>
	<#elseif c.foType == 'img-list'>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="img-list"></sa-td>
	<#elseif c.isFoType('audio-list', 'video-list', 'file-list', 'img-video-list')>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="${c.foType}"></sa-td>
	<#elseif c.foType == 'link'>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="link"></sa-td>
	<#elseif c.foType == 'enum'>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" type="enum" :jv="${c.getJvJson()}"></sa-td>
	<#else>
					<sa-td name="${c.columnComment3}" prop="${c.fieldName}" ></sa-td>
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
				<sa-item type="page" :curr.sync="p.pageNo" :size.sync="p.pageSize" :total="dataCount" @change="f5()"<#if t.hasFt('tree')> :sizes="[1000]"</#if>></sa-item>
			</div>
		</div>
		<script>
			var app = new Vue({
				components: {
					"sa-item": httpVueLoader('../../sa-frame/com/sa-item.vue'),  
					"sa-td": httpVueLoader('../../sa-frame/com/sa-td.vue'),		
				},
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
<#else>
						sa.ajax('/${t.mkNameBig}/getList', sa.removeNull(this.p), function(res) {
</#if>
							this.dataList = res.data; // 数据
							this.dataCount = res.dataCount; // 数据总数 
							sa.f5TableHeight();		// 刷新表格高度 
						}.bind(this));
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
						sa.showIframe('数据详情', '${t.kebabName}-info.html?id=' + data.id, '1050px', '90%');
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
						sa.showIframe('修改数据', '${t.kebabName}-add.html?id=' + data.id, '1000px', '90%');
					},
					// 新增
					add: function(data) {
						sa.showIframe('新增数据', '${t.kebabName}-add.html?id=-1', '1000px', '90%');
					},
<#if t.hasFt('tree', 'tree-lazy')>
					// 新增子级
					addChildren: function(data) {
						sa.showIframe('新增数据', '${t.kebabName}-add.html?id=-1&${t.getTreeFkey()}=' + data.${t.getTreeIdkey()}, '1000px', '90%');
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
