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
		<style type="text/css">
			.c-panel .c-label{width: 8em;}
		<#if t.hasFo('richtext') >
			/* 富文本样式 */
			.content-box{width: 700px; min-height: 100px; border: 1px #ddd solid; padding: 1em; transition: all 0.2s;overflow: hidden;}
			.content-box img{max-width: 200px !important;}
			.c-item-mline{width: 700px;}
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
					<el-form size="mini" v-if="m">
<#list t.tallList as c>
	<#if c.istx('no-show')>
	<#elseif c.foType == 'logic-delete'>	
	<#elseif c.foType == 'text'>	
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<span>{{m.${c.fieldName}}}</span>
						</div>
	<#elseif c.foType == 'num'>	
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<span class="tc-num">{{m.${c.fieldName}}}</span>
						</div>
	<#elseif c.foType == 'richtext'>
						<div class="c-item br">
							<label class="c-label" style="float: left;">${c.columnComment3}：</label>
							<div class="content-box" style="float: left;">
								<div v-html="m.${c.fieldName}"></div>
							</div>
						</div>
						<div style="clear: both;"></div>
	<#elseif c.foType == 'enum'>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
		<#list c.jvList?keys as jv>
							<b v-if="m.${c.fieldName} == ${jv}">${c.jvList[jv]}</b>
		</#list>
						</div>
	<#elseif c.foType == 'img'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<img :src="m.${c.fieldName}" style="width: 3em; height: 3em; cursor: pointer;" 
								@click="sa.showImage(m.${c.fieldName}, '400px', '400px')" v-if="m.${c.fieldName}">
							<span v-else>无</span>
						</div>
	<#elseif c.isFoType('audio', 'video', 'file')>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<el-link type="info" :href="m.${c.fieldName}" target="_blank" v-if="!sa.isNull(m.${c.fieldName})">{{m.${c.fieldName}}}</el-link>
							<span v-else>无</span>
						</div>
	<#elseif c.foType == 'img-list'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<div class="c-item-mline image-box" v-if="m.${c.fieldName}">
								<div class="image-box-2" v-for="image in m.${c.fieldName}.split(',')">
									<img :src="image" @click="sa.showImage(image, '500px', '400px')" />
								</div>
							</div>
							<span v-else>无</span>
						</div>
	<#elseif c.isFoType('audio-list', 'video-list', 'file-list', 'img-video-list')>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<div class="c-item-mline" v-if="m.${c.fieldName}">
								<div v-for="item in m.${c.fieldName}.split(',')">
									<el-link type="info" :href="item" target="_blank">{{item}}</el-link>
								</div>
							</div>
							<span v-else>无</span>
						</div>
	<#elseif c.isFoType('date', 'date-create', 'date-update')>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<span class="tc-date">{{sa.forDate(m.${c.fieldName}, 2)}}</span>
						</div>
	<#elseif c.isFoType('time')>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<span class="tc-num" class="tc-date">{{m.${c.fieldName}}}</span>
						</div>
	<#elseif c.foType == 'fk-1' || c.foType == 'fk-2'>
					<#if c.isTx('showfk')>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<span>{{m.${c.fieldName}}}</span>
						</div>
					</#if>
					<#list c.fkPkConcatList as fk>
						<div class="c-item">
							<label class="c-label">${fk.fkPkConcatComment}：</label>
							<span>{{m.${fk.fieldName}}}</span>
						</div>
					</#list>
	<#else>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<span>{{m.${c.fieldName}}}</span>
						</div>
	</#if>
</#list>
					</el-form>
				</div>
			</div>
			<!-- ------- 底部按钮 ------- -->
			<div class="s-foot">
				<el-button size="mini" type="success" @click="sa.closeCurrIframe()">确定</el-button>
				<el-button size="mini" @click="sa.closeCurrIframe()">取消</el-button>
			</div>
		</div>
		<script>
			var app = new Vue({
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
