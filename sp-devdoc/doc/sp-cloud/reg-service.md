# 启动服务

搭建好项目基础框架之后，我们便可以以子模块的形式创建项目服务


### 启动子服务
- 在源码 `sp-service\`目录下，有四个子项目：`sp-admin`、`sp-home`、`sp-more`、`sp-task`
- 这四个子项目便是我们的业务模块，这四个服务彼此独立、互不干扰，你可以依次启动这四个服务
- 启动步骤：
	- 建数据库`sp-dev`，导入脚本`/sql/sa-plus.sql`
	- 启动`redis`，并确保`nacos`中的配置信息正确，然后依次启动四个服务
	- 然后访问`nacos`控制台, 可以看到四个服务已经成功注册到`nacos`

![nacos服务中心](http://oss.dev33.cn/sp-cloud/nacos-service-list.png)


### 注册中心地址
- 那么，我们的服务是如何注册到注册中心的呢？
- 在`sp-core`下有个子模块`sp-nacos`，这里有一个配置文件`bootstrap.properties`, 
- 可以看到这个文件里配置了我们`nacos`的注册地址，并且此模块被所有服务引用，有了这个配置文件后，每个服务启动后就会自动注册到`nacos`中
- 如果此配置文件乱码，将格式改为`utf-8`即可


### 配置中心地址
- 为什么名字叫做`bootstrap.properties`，而不是我们熟悉的`application.yml`呢？
- 因为`application.yml`已经被我们放到了配置中心，只需要在`bootstrap.properties`指定中心地址，即可自动加载相应的配置文件 
- 这样的话，我们就不用一个服务一套配置文件了，而是多个服务共享一套配置，有效的减少了重复代码
- 可以看到，服务启动时，会根据服务名称加载对应的配置文件

![加载nacos配置](http://oss.dev33.cn/sp-cloud/load-nacos-config.png)



