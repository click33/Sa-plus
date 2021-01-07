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
<#if t.hasFo('img', 'audio', 'video', 'file', 'img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list', 'richtext') >
	<#if cfg.fileUploadWay == 1 >
		<script src="../../static/kj/upload-util.js"></script>
	</#if>
	<#if cfg.fileUploadWay == 2 >
		<script src="../../static/kj/oss-util.js"></script>
	</#if>
</#if>
	<#if t.hasFo('richtext') >
		<script src="../../static/kj/wangEditor.up.js"></script>
	</#if>
		<style type="text/css">
			.c-panel .el-form .c-label{width: 7em !important;}
			.c-panel .el-form .el-input, .c-panel .el-form .el-textarea__inner{width: 250px;}
		<#if t.hasFo('richtext') >
			/*  普通文本和富文本一起变长  */
			.c-panel .el-form .el-input, .c-panel .el-form .el-textarea__inner{width: 700px;}
			.c-item-mline{width: 700px;}
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
					<el-form size="mini" v-if="m">
<#list t.t12List as c>
	<#if c.istx('no-add')>	
	<#elseif c.foType == 'logic-delete'>	
	<#elseif c.getFlag() == 'tree-parent-id'>	
						<div class="c-item br" v-if="sa.p('${t.getTreeFkey()}', 'nof') == 'nof'">
							<label class="c-label">${c.columnComment3}：</label>
							<el-input size="mini" v-model="m.${c.fieldName}"></el-input>
						</div>
	<#elseif c.foType == 'text'>	
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<el-input size="mini" v-model="m.${c.fieldName}"></el-input>
						</div>
	<#elseif c.foType == 'num'>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<el-input size="mini" v-model="m.${c.fieldName}" type="number"></el-input>
						</div>
	<#elseif c.foType == 'textarea'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<div style="display: inline-block;">
								<el-input size="mini" v-model="m.${c.fieldName}" type="textarea" :autosize="{ minRows: 3, maxRows: 5}"></el-input>
							</div>
						</div>
	<#elseif c.foType == 'richtext'>
						<div class="c-item br editor-item" style="margin-top: 10px;">
							<label class="c-label">${c.columnComment3}：</label>
							<div class="editor-box">
								<div id="editor"></div>
							</div>
							<div style="clear: both;"></div>
						</div>
	<#elseif c.foType == 'enum'>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
				<#if c.gtx('a-type') == '1' || c.gtx('a-type') == '2'>
							<el-radio-group v-model="m.${c.fieldName}" size="mini" <#if c.gtx('a-type') == '2'>class="s-radio-text" </#if>>
								<#list c.jvList?keys as jv>
								<el-radio :label="${jv}">${c.jvList[jv]}</el-radio>
								</#list>
							</el-radio-group>
				<#elseif c.gtx('a-type') == '3'>
							<el-radio-group v-model="m.${c.fieldName}" size="mini">
								<#list c.jvList?keys as jv>
								<el-radio-button :label="${jv}">${c.jvList[jv]}</el-radio-button>
								</#list>
							</el-radio-group>
				<#elseif c.gtx('a-type') == '4'>
							<el-select v-model="m.${c.fieldName}" size="mini">
								<el-option label="请选择" :value="0" disabled></el-option>
								<#list c.jvList?keys as jv>
								<el-option label="${c.jvList[jv]}" :value="${jv}"></el-option>
								</#list>
							</el-select>
				</#if>
						</div>
	<#elseif c.foType == 'img'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<img :src="m.${c.fieldName}" style="width: 3em; height: 3em; cursor: pointer;" 
								@click="sa.showImage(m.${c.fieldName}, '400px', '400px')" v-if="!sa.isNull(m.${c.fieldName})">
							<el-link type="primary" @click="sa.uploadImage(src => {m.${c.fieldName} = src; sa.ok2('上传成功');})">上传</el-link>
						</div>
	<#elseif c.foType == 'audio'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<el-link type="info" :href="m.${c.fieldName}" target="_blank" v-if="!sa.isNull(m.${c.fieldName})">{{m.${c.fieldName}}}</el-link>
							<el-link type="primary" @click="sa.uploadAudio(src => {m.${c.fieldName} = src; sa.ok2('上传成功');})">上传</el-link>
						</div>
	<#elseif c.foType == 'video'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<el-link type="info" :href="m.${c.fieldName}" target="_blank" v-if="!sa.isNull(m.${c.fieldName})">{{m.${c.fieldName}}}</el-link>
							<el-link type="primary" @click="sa.uploadVideo(src => {m.${c.fieldName} = src; sa.ok2('上传成功');})">上传</el-link>
						</div>
	<#elseif c.foType == 'file'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<el-link type="info" :href="m.${c.fieldName}" target="_blank" v-if="!sa.isNull(m.${c.fieldName})">{{m.${c.fieldName}}}</el-link>
							<el-link type="primary" @click="sa.uploadFile(src => {m.${c.fieldName} = src; sa.ok2('上传成功');})">上传</el-link>
						</div>
	<#elseif c.foType == 'img-list'>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<div class="c-item-mline image-box">
								<div class="image-box-2" v-for="item in m.${c.fieldName}_arr">
									<img :src="item" @click="sa.showImage(item, '500px', '400px')" />
									<p>
										<i class="el-icon-close" style="position: relative; top: 2px;"></i>
										<el-link @click="sa.arrayDelete(m.${c.fieldName}_arr, item)" style="color: #999;">删除这张 </el-link>
									</p>
								</div>
								<!-- 上传图集 -->
								<div class="image-box-2 up_img" @click="sa.uploadImageList(src => m.${c.fieldName}_arr.push(src))">
									<img src="../../static/img/up-icon.png">
								</div>
							</div>
						</div>
	<#elseif c.isFoType('audio-list', 'video-list', 'file-list', 'img-video-list')>
						<div class="c-item br">
							<label class="c-label" style="vertical-align: top;">${c.columnComment3}：</label>
							<div class="c-item-mline">
								<div v-for="item in m.${c.fieldName}_arr">
									<el-link type="info" :href="item" target="_blank">{{item}}</el-link>
									<el-link type="danger" class="del-rr" @click="sa.arrayDelete(m.${c.fieldName}_arr, item)">
										<i class="el-icon-close"></i>
										<small style="vertical-align: top;">删除</small>
									</el-link>
								</div>
			<#if c.foType == 'audio-list'>
								<el-link type="primary" @click="sa.uploadAudioList(src => m.${c.fieldName}_arr.push(src))">上传</el-link>
			<#elseif c.foType == 'video-list'>
								<el-link type="primary" @click="sa.uploadVideoList(src => m.${c.fieldName}_arr.push(src))">上传</el-link>
			<#elseif c.foType == 'file-list'>
								<el-link type="primary" @click="sa.uploadFileList(src => m.${c.fieldName}_arr.push(src))">上传</el-link>
			<#elseif c.foType == 'img-video-list'>
								<el-link type="primary" @click="sa.uploadImageList(src => m.${c.fieldName}_arr.push(src))">上传图片</el-link>
								<el-link type="primary" @click="sa.uploadVideoList(src => m.${c.fieldName}_arr.push(src))" style="margin-left: 7px;">上传视频</el-link>
			</#if>
							</div>
						</div>
	<#elseif c.foType == 'date'>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<el-date-picker size="mini" v-model="m.${c.fieldName}" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
						</div>
	<#elseif c.isFoType('date-create', 'date-update')>
						<!-- ${c.foType}字段： m.${c.fieldName} - ${c.columnComment3} -->
	<#elseif c.foType == 'time'>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<el-time-picker v-model="m.${c.fieldName}" size="mini" value-format="HH:mm:ss"></el-time-picker>
						</div>
	<#elseif c.foType == 'fk-s'>
				<#if c.istx('drop')>
						<div class="c-item">
							<label class="c-label">${c.columnComment3}：</label>
							<el-select size="mini" v-model="m.${c.fkSCurrDc.fieldName}">
								<el-option label="请选择" value="" disabled></el-option>
								<el-option v-for="item in ${c.fieldName}List" :label="item.${c.tx.catc}" :value="item.${c.tx.jc}" :key="item.${c.tx.jc}"></el-option>
							</el-select>
						</div>
				</#if>
	<#elseif c.foType == 'no'>
						<!-- no字段： m.${c.fieldName} - ${c.columnComment3} -->
	<#else>
						<div class="c-item br">
							<label class="c-label">${c.columnComment3}：</label>
							<el-input size="mini" v-model="m.${c.fieldName}"></el-input>
						</div>
	</#if>
</#list>
						<div class="c-item br s-ok">
							<label class="c-label"></label>
							<el-button size="mini" type="primary" icon="el-icon-plus" @click="ok()">保存</el-button>
						</div>
					</el-form>
				</div>
			</div>
			<!-- ------- 底部按钮 ------- -->
			<div class="s-foot">
				<el-button size="mini" type="primary" @click="ok()">确定</el-button>
				<el-button size="mini" @click="sa.closeCurrIframe()">取消</el-button>
			</div>
		</div>
	<#if t.hasFo('richtext') >
		<script>
			// 创建编辑器
			function create_editor(content) {
				var E = window.wangEditor;
				window.editor = new E('#editor');

				editor.customConfig.menus = [
					'head', 'fontSize', 'fontName', 'italic', 'underline', 'strikeThrough', 'foreColor', 'backColor', 'link', 'list',
					'justify', 'quote', 'emoticon', 'image', 'table', 'code', 'undo', 'redo' // 重复
				]
				editor.customConfig.debug = true; // debug模式
				// editor.customConfig.uploadFileName = 'file'; // 图片流name
				editor.customConfig.withCredentials = true; // 跨域携带cookie
				editor.customConfig.uploadImgMaxSize = 100 * 1024 * 1024;	// 图片大小最大100M
				// editor.customConfig.uploadImgShowBase64 = true   	// 使用 base64 保存图片
				// 重写上传图片的函数到OSS 
				editor.customConfig.customUploadImg = function(files, insert) {
					var file = files[0]; // 文件对象 
					startUploadImage2(file, function(src) {
						insert(src);
						sa.msg('上传成功');
					});
				}
				editor.create(); // 创建
				editor.txt.html(content);	// 为编辑器赋值
				setTimeout(function() {
					$('.editor-box').height($('.editor-box').height());
				})
			}
		</script>
	</#if>
        <script>
			
			var app = new Vue({
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
							${c.fieldName}_arr: [],		// ${c.columnComment} - 转数组
						<#else>
							${c.fieldName}: '',		// ${c.columnComment} 
						</#if>
					</#list>
						}
					},
					// 表单验证
					submitCheck: function() {
						try{
							var m = this.m;		// 获取 m对象 
							
					<#list t.t1List as c>
						<#if c.istx('no-add') || c.isFoType('no', 'logic-delete', 'date-create', 'date-update')>
							// sa.checkNull(m.${c.fieldName}, '请输入 [${c.columnComment3}]');
						<#else>
							sa.checkNull(m.${c.fieldName}, '请输入 [${c.columnComment3}]');
						</#if>
					</#list>
							return 'ok';	// 全部通过验证，返回ok 表示正确 
						} catch(e) {
							sa.error(e);
						}
					},
					// 提交数据 
					ok: function(){
						// 验证 
				<#list t.columnList as c>
					<#if c.isFoType('img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list')>
						this.m.${c.fieldName} = this.m.${c.fieldName}_arr.join(',');	// 图片数组转字符串 
					</#if>
					<#if c.foType == 'richtext'>
						this.m.${c.fieldName} = editor.txt.html();	// 获取富文本值 
					</#if>
					<#if c.foType == 'date'>
						this.m.${c.fieldName} = sa.forDate(this.m.${c.fieldName}, 2);	// 日期格式转换 
					</#if>
				</#list>
						if(this.submitCheck() != 'ok') {
							return;
						}
						// 开始增加或修改
					<#list t.getT1ListByNotAdd() as c>
						this.m.${c.fieldName} = undefined;		// 不提交属性：${c.columnComment3}
					</#list>
						if(this.id <= 0) {	// 添加
							sa.ajax('/${t.mkNameBig}/add', this.m, function(res){
								sa.alert('增加成功', this.clean); 
							}.bind(this));
						} else {	// 修改
							sa.ajax('/${t.mkNameBig}/update', this.m, function(res){
								sa.alert('修改成功', this.clean);
							}.bind(this));
						}
					},
					// 添加/修改 完成后的动作
					clean: function() {
						if(this.id == 0) {
							this.m = this.createModel();
						<#if t.hasFo('richtext')>
							this.$nextTick(function() {
								create_editor('');
							})
						</#if>
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
					<#if t.hasFo('richtext')>
						this.$nextTick(function() {
							create_editor('');
						})
					</#if>
					} else {	
						sa.ajax('/${t.mkNameBig}/getById?id=' + this.id, function(res) {
					<#list t.t1List as c>
						<#if c.foType == 'date'>
							res.data.${c.fieldName} = new Date(res.data.${c.fieldName});		// ${c.columnComment} 日期格式转换 
						</#if>
						<#if c.isFoType('img-list', 'audio-list', 'video-list', 'file-list', 'img-video-list')>
							res.data.${c.fieldName}_arr = sa.isNull(res.data.${c.fieldName}) ? [] : res.data.${c.fieldName}.split(',');		// ${c.columnComment} 字符串转数组 
						</#if>
					</#list>
							this.m = res.data;
							if(res.data == null) {
								sa.alert('未能查找到 id=' + this.id + " 详细数据");
							}
					<#list t.t1List as c>
						<#if c.foType == 'richtext'>
							this.$nextTick(function() {
								create_editor(this.m.${c.fieldName});
							})
						</#if>
					</#list>
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