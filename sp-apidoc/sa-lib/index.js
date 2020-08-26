// 声明所有全局内容 
var sa_mcontent = {
	// 例如：定义res, 你可以在md文档中使用 import(res)，来导入这一段话 
	res: '``` js\n\t{\n\t\t"code": 200,	// 成功时=200, 失败时=500  msg=失败原因\n\t\t"msg": "ok",\n\t\t"data": {}\n\t} \n```'
};


// 声明插件 
var sa_plugins = function(hook) {
	// 解析之前执行
	hook.beforeEach(function(content) {
		content = refMd_p2table(content); 	// 参数转表格  
		content = refMd_import2content(content);	// 加载import  
		content = refMd_api2trim(content);	// api去除空格  
		content = refMd_var(content);		// 更换变量 
		return content;
	});

	// 解析之后执行 
	hook.afterEach(function(html) {
		// 添加页脚 
		var footer = [
			'<br/><br/><br/><br/><br/><br/><br/><hr/>',
			'<footer>',
			'&emsp;<span>本接口文档使用 <a href="https://github.com/click33/sa-doc" target="_blank">sa-doc</a> 编写</span>',
			// '<span>Proudly published with <a href="https://github.com/docsifyjs/docsify" target="_blank">docsify</a>.</span>',
			'</footer>'
		].join('');
		return html + footer;
	});
	
	
	function f5_test_btn() {
		// console.log(123);
		$('.anchor').each(function(index, el) {
			try{
				// 获取父元素
				var parTag = $(this).parent('h3');
				if(parTag == null) {
					return;
				}
				var tagName = parTag.prop("tagName");
				if(tagName != 'H3') {
					return;
				}
				// 地址、参数等信息 
				var cc_id = sa.randomString(16);	// id号码 
				window.apiInfoMap = window.apiInfoMap || {};	// 存储当前页面所有api信息 
				window.apiInfoMap[cc_id] = {	// 此项的所有信息 
					// api_title: parTag.find('a span').text(),	// 接口标题 
					ajaxUrl: '',	// 接口地址 
					ajaxType: 'GET',	// 接口请求方式 
					headerList: [],	// 请求头参数  
					bodyList: [],	// 请求体参数 
				};	
				var apiInfo = window.apiInfoMap[cc_id];
				var nextTagArr = $(parTag).nextAll();	
				for (var i = 0; i < nextTagArr.length; i++) {
					var tag = $(nextTagArr.get(i));
					// 如果已经到了H3了，则可以结束了 
					if(tag.prop("tagName") == 'H3') {
						break;
					}
					// 如果是参数 
					if(tag.prop("tagName") == 'TABLE') {
						var trArr = $(tag).find('tbody tr');
						for (var j = 0; j < trArr.length; j++) {
							var tdArr = $(trArr.get(j)).find('td');
							apiInfo.bodyList.push({name: tdArr.get(0).innerHTML, value: '', tips: tdArr.get(3).innerHTML});	// , value: tdArr.get(3)
						}
					}
					// 如果是参数
					// if(tag.prop("tagName") == 'DIV' && tag.prop('class') == 'body-p-list') {
					// 	var text = tag.text();
					// 	var trArr = JSON.parse(text);
					// 	console.log(trArr.length);
					// 	for (var j = 0; j < trArr.length; j++) {
					// 		var tdArr = trArr[j];
					// 		apiInfo.bodyList.push({name: tdArr[0], value: '', tips: tdArr[3]});	// , value: tdArr[2]
					// 	}
					// }
					// 如果是地址 
					if(tag.prop("tagName") == 'UL') {
						// 如果能搜索到 data-lang="api"
						var tg = $(tag).find('[class=lang-api]');
						if(tg.length > 0) {
							var url_i = tg.get(0).innerHTML;
							if(url_i.indexOf('http') != 0) {
								url_i = sa_doc_cfg.server_url + url_i;;
							}
							apiInfo.ajaxUrl = url_i;
						}
					}
				}
				// console.log(nextAll.length);
				
				// console.log(tagName);
				// 后面添加一个按钮
				// parTag.after('<button>测试接口</button>');
				// console.log(parTag.find('a span').text());
				var button = '<button class="test-api-btn" type="button" '+
					' cc-id="' + cc_id + '" ' + 
					' api-title="' + parTag.find('a span').text() + '" ' + 
					' onclick="test_api(this)">接口测试</button>';
				parTag.append(button);
			}catch(e){
				console.err(e);
			}
		})
	}
	
	// 渲染完全完成之后
	hook.doneEach(function() {
		f5_test_btn();
	});
	
};


// 点击测试接口的按钮
function test_api(event) {
	// console.log(event.classList);
	// 获取参数 
	// 获取title
	var j_index = document.title.indexOf('-');
	var title = document.title;
	if(j_index > -1) {
		title = title.substr(0, j_index);
		title = event.getAttribute('api-title') + '	&emsp;&emsp;----by&emsp; ' + title;
	}
	
	// 获取地址、参数等信息 
	var id = event.getAttribute('cc-id');
	var cc = window.apiInfoMap[id];	
	console.log(cc);
	// var cc = {
	// 	ajaxType: 'GET',
	// 	ajaxUrl: 'dsada',
	// 	headerList: [],
	// 	bodyList: []
	// }
	sessionStorage.setItem('sa-doc-cc-' + id, JSON.stringify(cc));
	
	sa.showIframe3(title, './sa-lib/api-test/index.html?id=' + id, '1000px', '90%');
}



// 移除数组中所有空白字符串 
function arrayTrimSpace(array) {
	for (var i = 0; i < array.length; i++) {
		if (array[i] == "" || array[i] == " " || array[i] == null || typeof(array[i]) == "undefined") {
			array.splice(i, 1); 
			i = i - 1; 
		}
	}
	return array;
}


// 获取一行表格 
// 参数名字，类型，默认值，说明 
function getTrMd(name, type, default_value, remrak) {
	var str = '\n|' + name + '|' + type + '|' + default_value + '|' + remrak + '|';
	return str;
}

// 加工md，将其中的```p 转换为table格式 
function refMd_p2table(content) {
	// 1、取出全文中所有的 ```d 
	var reg = /```\s*p[\s\S]*?```/igm; // [\s\S]*=任意字符n次，?=非贪婪模式
	var pArr = content.match(reg)|| [];; // 返回所有匹配项数组 
	// 遍历并转换 
	pArr.forEach(function(p) {
		// 声明表头 
		var table = '\n' +
			'| 参数名称| 类型| 默认值|说明|\n' +
			'| :--------| :--------| :--------|:--------|';	
			
		// 将这个p内容按换行符切割  
		var canStr = p.replace(/```\s*p/, '').replace('```', '').trim();	// 去除首行尾行 
		var canArr = canStr.split('\n') || [];	// 按行切割 
		// var bodyPList = [];	// 二维数组记录一下参数集合 
		
		// 开始逐行转换为tr 
		canArr.forEach(function(canStr) {
			
			// 去除空格 
			canStr = canStr.trim();
			
			// 声明四大变量的值   
			let name = '';		// 参数名子 
			let type = '';		// 数据类型
			let default_value = '';	// 默认值 
			let remrak = '';		// 参数说明 
			
			// 如果带有数据类型 
			if(canStr.indexOf('{') > -1 && canStr.indexOf('}') > -1) {
				canStr = canStr.replace('{', '');	// 去除掉前{
				let sjArr = canStr.split('}');	// 按照}分割 
				type = sjArr[0].trim();
				canStr = sjArr[1].trim();
			}
			
			// 切割成数组 
			var canArray = canStr.split(/[\s\t\n]/) || [];	
			// canArray = arrayTrimSpace(canArray); 	// 去除空格元素  
			// 如果开发者写的不规范，使其超过2个元素，则强制改为2个元素  
			if(canArray.length == 0) {
				return;
			}
			if(canArray.length == 1) {
				canArray.push('');
			}
			
			// =========== 开始判断 5种情况 ==================
			let one = canArray[0];
			let two = canArray[1];
			
			// 情况1   id=1  xxxx 
			if(one.indexOf('=') > -1 && one.indexOf('=') < one.length - 1) {
				name = one.split('=')[0];
				default_value = one.split('=')[1];
				canArray.splice(0, 1);
			}
			// 情况2   id= 1  xxxx 
			else if(one.indexOf('=') == one.length - 1) {
				name = one.split('=')[0];
				default_value = two;
				canArray.splice(0, 2);
			}
			// 情况3   id =1  xxxx 
			else if(one.indexOf('=') == -1 && two.indexOf('=') == 0 && two.length > 1) {
				name = one;
				default_value = two.split('=')[1];
				canArray.splice(0, 2);
			}
			// 情况4   id = 1  xxxx 
			else if(one.indexOf('=') == -1 && two == '=') {
				name = one;
				default_value = canArray[2] || '';
				canArray.splice(0, 3);
			}
			// 情况5 	id 	1	xxxx   或其它 
			// else if(one.indexOf('=') == -1 && two.indexOf('=') != 0) {
			else {
				name = one;
				canArray.splice(0, 1);
			}
			
			// 剩下的元素，都拼接remrak
			remrak = canArray.join('');
			
			// 添加到表格 
			table += getTrMd(name, type, default_value, remrak);
			// bodyPList.push([name, type, default_value, remrak]);
		})
		
		// console.log(p);
		// 将原始p内容替换成为table内容  
		table += '\n';
		// table += '\n<div class="body-p-list" style="display: none;">' + JSON.stringify(bodyPList) + '</div>\n';	// 增加上参数信息 
		content = content.replace(p, table); 
	});
	return content;
}


// 加工md，将其中的@import  转换为真实内容 
function refMd_import2content(content) {
	// 1、取出全文中所有的 ```d
	var reg = /@import\([\s\S]*?\)/igm; // [\s\S]*=任意字符n次，?=非贪婪模式
	var importArr = content.match(reg)|| []; // 返回所有匹配项数组 
	
	// 遍历并转换
	importArr.forEach(function(import_item) {
		// 过滤空格 
		// console.log(import_item);
		var item = import_item.replace(' ', '').replace('@import(', '').replace(')', '');
		
		// 开始替换 
		content = content.replace(import_item, sa_mcontent[item] || ''); 
	});
	
	return content;
}



// 加工md，将其中的```api 去除空格
function refMd_api2trim(content) {
	// 1、取出全文中所有的 ```d 
	var reg = /```\s*api[\s\S]*?```/igm; // [\s\S]*=任意字符n次，?=非贪婪模式
	var pArr = content.match(reg)|| [];; // 返回所有匹配项数组 
	
	// 遍历并转换 
	pArr.forEach(function(p) {
		// 将这个p内容按换行符切割
		var canStr = p.replace(/```\s*api/, '').replace('```', '').trim();	// 去除首行尾行 
		var canArr = canStr.split('\n') || [];	// 按行切割 
		var str = '';
		
		// 遍历并转换 
		canArr.forEach(function(p) {
			str += p;
		});
		
		str = '``` api\n' + str + '```\n';
		// console.log(str);
		
		// 加上按钮
		// str = '<button>测试接口</button>\n' + str;
		
		// 将原始p内容替换成为后来内容  
		content = content.replace(p, str); 
	})
	
	
	
	return content;
}

// 加工md，将其中的变量
function refMd_var(content) {
	content = content.replace('${sa_doc_cfg.server_url}', window.sa_doc_cfg.server_url);
	return content;
}


// 打印信息 
console.log('欢迎使用sa-doc(一个基于markdown的接口文档编写工具)，当前版本：v1.3.0，更新于：2020-07-2，GitHub地址：https://github.com/click33/sa-doc');
console.log('如在使用中发现任何bug或者疑问，请加入QQ群交流：782974737，点击加入：https://jq.qq.com/?_wv=1027&k=5DHN5Ib');
