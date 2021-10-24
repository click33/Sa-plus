<!DOCTYPE html>
<html>
	<head>
		<title>${t.tableComment}-详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<!-- 所有的 css js 资源 -->
<#if cfg.webLibImportWay == 1 >
		<link rel="stylesheet" href="https://unpkg.com/element-ui@2.13.0/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="../../static/sa.css">
		<script src="https://unpkg.com/vue@2.6.10/dist/vue.min.js"></script>
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
		<style type="text/css">
			.c-panel .c-label{width: 8em;}
		<#if t.hasFo('richtext') >
			/* 富文本样式 */
			.content-box{width: 800px; min-height: 100px; border: 1px #ddd solid; padding: 1em; transition: all 0.2s;overflow: hidden;}
			.content-box img{max-width: 200px !important;}
			.c-item-mline{width: 800px;}
		</#if>
		<#if t.hasFo('img-list') >
			.c-item .image-box-2{height: 90px;}
		</#if>
		</style>
	</head>
	<body>
		<div class="vue-box sbot" style="display: none;" :style="'display: block;'">
			<!-- ------- 内容部分 ------- -->
			<div class="s-body">
				<div class="c-panel">
					<el-form v-if="m">
<#list t.tallList as c>
	<#if c.istx('no-show')>
	<#elseif c.foType == 'logic-delete'>	
	<#elseif c.foType == 'text'>	
						<sa-info name="${c.columnComment3}" br>{{m.${c.fieldName}}}</sa-info>
	<#elseif c.foType == 'num'>	
						<sa-info type="num" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.foType == 'textarea'>	
						<sa-info type="textarea" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.foType == 'richtext'>
						<sa-info type="richtext" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.foType == 'enum'>
						<sa-info type="enum" name="${c.columnComment3}" :value="m.${c.fieldName}" :jv="${c.getJvJson()}" br></sa-info>
	<#elseif c.foType == 'img'>
						<sa-info type="img" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.isFoType('audio', 'video', 'file')>
						<sa-info type="${c.foType}" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.foType == 'img-list'>
						<sa-info type="img-list" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.isFoType('audio-list', 'video-list', 'file-list', 'img-video-list')>
						<sa-info type="${c.foType}" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
						<sa-info type="datetime" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.isFoType('time')>
						<sa-info type="time" name="${c.columnComment3}" :value="m.${c.fieldName}" br></sa-info>
	<#elseif c.foType == 'fk-1' || c.foType == 'fk-2'>
					<#if c.isTx('showfk')>
						<sa-info name="${c.columnComment3}">{{m.${c.fieldName}}}</sa-info>
					</#if>
					<#list c.fkPkConcatList as fk>
						<sa-info name="${fk.fkPkConcatComment}">{{m.${fk.fieldName}}}</sa-info>
					</#list>
	<#else>
						<sa-info name="${c.columnComment3}" br>{{m.${c.fieldName}}}</sa-info>
	</#if>
</#list>
					</el-form>
				</div>
			</div>
			<!-- ------- 底部按钮 ------- -->
			<div class="s-foot">
				<el-button type="success" @click="sa.closeCurrIframe()">确定</el-button>
				<el-button @click="sa.closeCurrIframe()">取消</el-button>
			</div>
		</div>
		<script>
			var app = new Vue({
				components: {
					"sa-info": httpVueLoader('../../sa-frame/com/sa-info.vue')
				},
				el: '.vue-box',
				data: {
					id: sa.p('id', 0),	// 获取数据ID 
					m: null
				},
				methods: {
				},
				mounted: function() {
					sa.ajax('/${t.mkNameBig}/getById?id=' + this.id, function(res) {
						this.m = res.data;
						if(res.data == null) {
							sa.alert('未能查找到 id=' + this.id + " 详细数据");
						}
					}.bind(this))
				}
			})
			
		</script>
	</body>
</html>
