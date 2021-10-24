<!DOCTYPE html>
<html>
	<head>
		<title>${t.tableComment}-添加/修改</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- 所有的 css js 资源 -->
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
<#if t.hasFo('img', 'audio', 'video', 'file', 'img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list', 'richtext') >
	<#if cfg.fileUploadWay == 1 >
		<script src="../../static/kj/upload-util.js"></script>
	</#if>
	<#if cfg.fileUploadWay == 2 >
		<script src="../../static/kj/oss-util.js"></script>
	</#if>
</#if>
<#if t.hasFo('richtext') >
	<#if cfg.webLibImportWay == 1 >
		<script src="https://unpkg.com/wangeditor@4.7.8/dist/wangEditor.min.js"></script>
	</#if>
	<#if cfg.webLibImportWay == 2 >
		<script src="../../static/kj/wangEditor.min.js"></script>
	</#if>
</#if>
		<style type="text/css">
			.c-panel .el-form .c-label{width: 7em !important;}
			.c-panel .el-form .el-input, .c-panel .el-form .el-textarea__inner{width: 250px;}
		<#if t.hasFo('richtext') >
			/*  普通文本和富文本一起变长  */
			.c-panel .el-form .el-input, .c-panel .el-form .el-textarea__inner{width: 800px;}
			.c-item-mline{width: 800px;}
			.editor-box{display: inline-block;}
			.c-item .editor-box, .editor-box #editor{width: 800px;}
		</#if>
		</style>
	</head>
	<body>
		<div class="vue-box" :class="{sbot: id}" style="display: none;" :style="'display: block;'">
			<!-- ------- 内容部分 ------- -->
			<div class="s-body">
				<div class="c-panel">
                    <div class="c-title" v-if="id == 0">数据添加</div>
					<div class="c-title" v-else>数据修改</div>
					<el-form v-if="m">
<#list t.t12List as c>
	<#if c.istx('no-add')>	
	<#elseif c.foType == 'logic-delete'>	
	<#elseif c.getFlag() == 'tree-parent-id'>	
						<sa-item type="text" name="${c.columnComment3}" v-model="m.${c.fieldName}" v-if="sa.p('${t.getTreeFkey()}', 'nof') == 'nof'" br></sa-item>
	<#elseif c.foType == 'text'>	
						<sa-item type="text" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'num'>
						<sa-item type="num" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'textarea'>
						<sa-item type="textarea" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'richtext'>
						<sa-item type="richtext" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'enum'>
						<sa-item type="enum" name="${c.columnComment3}" v-model="m.${c.fieldName}" :jv="${c.getJvJson()}" jtype="${c.gtx('a-type')}" br></sa-item>
	<#elseif c.foType == 'img'>
						<sa-item type="img" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'audio'>
						<sa-item type="audio" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'video'>
						<sa-item type="video" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'file'>
						<sa-item type="file" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'img-list'>
						<sa-item type="img-list" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.isFoType('audio-list', 'video-list', 'file-list', 'img-video-list')>
						<sa-item type="${c.foType}" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'date'>
						<sa-item type="datetime" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.isFoType('date-create', 'date-update')>
						<!-- ${c.foType}字段： m.${c.fieldName} - ${c.columnComment3} -->
	<#elseif c.foType == 'time'>
						<sa-item type="time" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	<#elseif c.foType == 'fk-s'>
				<#if c.istx('drop')>
						<sa-item name="${c.columnComment3}" br>
							<el-select v-model="m.${c.fkSCurrDc.fieldName}">
								<el-option label="请选择" value="" disabled></el-option>
								<el-option v-for="item in ${c.fieldName}List" :label="item.${c.tx.catc}" :value="item.${c.tx.jc}" :key="item.${c.tx.jc}"></el-option>
							</el-select>
						</sa-item>
				</#if>
	<#elseif c.foType == 'no'>
						<!-- no字段： m.${c.fieldName} - ${c.columnComment3} -->
	<#else>
						<sa-item type="text" name="${c.columnComment3}" v-model="m.${c.fieldName}" br></sa-item>
	</#if>
</#list>
						<sa-item name="" class="s-ok" br>
							<el-button type="primary" icon="el-icon-plus" @click="ok()">保存</el-button>
						</sa-item>
					</el-form>
				</div>
			</div>
			<!-- ------- 底部按钮 ------- -->
			<div class="s-foot">
				<el-button type="primary" @click="ok()">确定</el-button>
				<el-button @click="sa.closeCurrIframe()">取消</el-button>
			</div>
		</div>
        <script>
			
			var app = new Vue({
				components: {
					"sa-item": httpVueLoader('../../sa-frame/com/sa-item.vue')
				},
				el: '.vue-box',
				data: {
					id: sa.p('id', 0),		// 获取超链接中的id参数（0=添加，非0=修改） 
					m: null,		// 实体对象 
				<#list t.getT2DropList() as c>
					${c.fieldName}List: [],		// ${c.columnComment} 集合 
				</#list>
				},
				methods: {
					// 创建一个 默认Model 
					createModel: function() {
						return {
					<#list t.t1List as c>
						<#if c.getFlag() == 'tree-parent-id'>
							${c.fieldName}: sa.p('${c.fieldName}', '${t.getFt('tree', 'tree-lazy').top}'),		// ${c.columnComment} 
						<#elseif c.isFoType('no', 'logic-delete', 'date-create', 'date-update')>
							// ${c.fieldName}: '',		// ${c.columnComment} 
						<#elseif c.isFoType('img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list')>
							${c.fieldName}: '',		// ${c.columnComment} 
						<#else>
							${c.fieldName}: '',		// ${c.columnComment} 
						</#if>
					</#list>
						}
					},
					// 提交数据 
					ok: function(){
						// 表单校验 
						let m = this.m;
				<#list t.t1List as c>
					<#if c.istx('no-add') || c.isFoType('no', 'logic-delete', 'date-create', 'date-update')>
						// sa.checkNull(m.${c.fieldName}, '请输入 [${c.columnComment3}]');
					<#else>
						sa.checkNull(m.${c.fieldName}, '请输入 [${c.columnComment3}]');
					</#if>
				</#list>
				
						// 开始增加或修改
					<#list t.getT1ListByNotAdd() as c>
						this.m.${c.fieldName} = undefined;		// 不提交属性：${c.columnComment3}
					</#list>
						if(this.id <= 0) {	// 添加
							sa.ajax('/${t.mkNameBig}/add', m, function(res){
								sa.alert('增加成功', this.clean); 
							}.bind(this));
						} else {	// 修改
							sa.ajax('/${t.mkNameBig}/update', m, function(res){
								sa.alert('修改成功', this.clean);
							}.bind(this));
						}
					},
					// 添加/修改 完成后的动作
					clean: function() {
						if(this.id == 0) {
							this.m = this.createModel();
						} else {
							parent.app.f5();		// 刷新父页面列表
							sa.closeCurrIframe();	// 关闭本页 
<#if t.hasFt('tree-lazy')>
							parent.sa.f5();		// 刷新父页面
</#if>
						}
					}
				},
				mounted: function(){
					// 初始化数据 
					if(this.id <= 0) {	
						this.m = this.createModel();
					} else {	
						sa.ajax('/${t.mkNameBig}/getById?id=' + this.id, function(res) {
							this.m = res.data;
							if(res.data == null) {
								sa.alert('未能查找到 id=' + this.id + " 详细数据");
							}
						}.bind(this))
					}
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