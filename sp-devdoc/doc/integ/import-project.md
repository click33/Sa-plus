# 项目基架
此篇介绍如何导入项目并且成功运行 

---



### 1. 创建数据库
1. 推荐数据库版本`mysql 5.7` 
2. 在mysql中创建数据，名字为：`sp-dev`
3. 导入脚本：`doc/sa-plus.sql`, 这个是`sa-plus`运行的内置库，必须存在 


### 2. 导入服务端代码
1. 在后端ide中导入项目 `sp-server`，此为接口服务端代码基架
2. 打开`application.yml`配置好mysql、redis环境后，启动运行 
3. (有关`project4sp`下的代码都是`sa-plus`内置代码，最好别动，你的模块包都写在`project`包下即可)


### 3. 导入后台管理
1. 在前端ide中导入 `sp-admin`, 此为后台管理代码基架
2. 打开`index.html`，预览html即可启动运行 
3. (有关`sa-html-sp`下的代码都时`sa-plus`内置代码，最好别动, 你的代码写在`sa-html\`文件夹下)
4. (`menu-list-sp.js`是内置菜单文件，不要动，如果要添加路由，请在`menu-list.js`里添加（如果找不到，请新建，和`menu-list-sp.js`目录同级）)

![](https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/sp-server-play.png)


### 4. 导入接口文档
1. 接口文档的导入方式与后台管理类似
2. 在前端ide中导入 `sp-apidoc`, 此为接口文档基架
3. 启动运行 
4. 基于markdown格式编写接口文档，菜单树定义在 `_sidebar.md`，你可以新建`project`文件夹书写接口文档，详细可参考：[http://sa-doc.dev33.cn/](http://sa-doc.dev33.cn/)

![](https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/sf-apidoc-play.png)



### 5. 后续
- 至此，你已经成功运行项目的每个组件，你可以继续深入: [项目架构](/integ/project-framework.md)
- 或者直接了解：[代码生成器](/gen/code-gener.md)

