## 介绍
此为Vue单页版UI：vue2、element-ui、js、vue-cli、vuex、vue-router、axios、xlsx

跟随 Sa-Plus 主版本：v1.27.0 

## 运行 

```bash
# 从根目录进入cmd，然后安装依赖
npm install

# 上面的命令可能会下载较慢，使用这个可以提高速度 
npm install --registry=https://registry.npm.taobao.org

# 启动服务，然后浏览器访问 http://localhost:9527
npm run dev
```


## 发布

```bash
# 构建测试环境
npm run build:test

# 构建生产环境
npm run build:prod
```

## 其它

```bash
# 预览发布环境效果
npm run preview

# 预览发布环境效果 + 静态资源分析
npm run preview -- --report

# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix
```

## 优化点
- 标签栏 TagView 增加拖拽排序 
- 工具栏增加小便签功能，记载临时数据 
- 优化菜单搜索能力，增加快捷键、默认展开、菜单索引等能力 
- 全局布局设置增加持久化能力 
- 标签栏右键菜单增加展开动画 
- 重构全局权限设计，更简单的做到动态菜单、按钮级权限 
- 提供 sa.js 全局对象，提供各种工具函数封装 
- 增加全局 dialog 功能，纯js弹出vue组件，少写代码 
- 增加表单组件封装，更方便的增删改查 
- 提供全新的登录页面
- 优化部分组件样式、配色、动画 
- 增加和后端对接的页面 
- 删除一些不必要的代码、精简代码结构 

## 参考

更多信息请参考 [使用文档](https://panjiachen.github.io/vue-element-admin-site/zh/)
