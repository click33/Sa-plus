# nacos - 注册中心 & 配置中心


## 微服务是什么？
微服务架构就是将一个完整的应用拆分成几个独立的服务，每个服务都能独立部署、独立维护、独立扩展 <br>
为什么要使用微服务呢？简而言之就是单体项目随着时间推移，代码越来越多，架构越来越乱，启动时间越来越长，整个项目愈发变的不可控制，微服务的出现就是为了解决这个问题的

- 因为`代码`越来越多，所以要将代码拆分到不同的`函数`里
- 因为`函数`越来越多，所以要将函数拆分到不同的`类`里
- 因为`类`越来越多，所以要将类拆分到不同的`包`下
- 因为`包`越来越多，所以要将包拆分到不同的`模块`下
- 因为`模块`越来越多，所以要将模块拆分到不同的`服务`中



## nacos是什么？为什么要搭建nacos？

虽然我们将代码按照模块拆分到了不同的服务中，然而“完全独立”毕竟只是一种理想状态，在实际的开发中，我们将不可避免的出现类似于`A服务`要调用`B服务`某个接口的情况 

在这种需求只出现一到两次的时候，我们尚可将`B服务的地址`直接硬编码到`A服务`里，但是我们不妨假设一种比较极端的情况，假设A服务需要依赖的其它服务有几十个呢？

如果这种情况下我们将所有依赖服务的地址直接写死到A服务的代码里，那么将来这些服务的地址一旦发生变化，那么我们仅仅是修改服务地址就将是一个浩大的工程，
这是绝对不允许出现的情况，除非你的公司需要增加工作岗位

实际的情况比这更加可怕，我们拆分的几十个服务可能每个都对其它服务有着或多或少的调用，整个系统的依赖关系揉成一团，如果没有一个妥善的方法治理这些服务，将是一场灾难

而`nacos`，就是为了解决这个难题的

作为微服务的基础设施，`nacos`提供了两个基本的功能：
- 服务注册中心
- 分布式配置中心

**所谓服务注册**，就是指`nacos`作为注册中心，提供了一个公共地址，我们的服务在启动之后，首先向`nacos`打个招呼:

"嗨，我的名字叫做xxx，以后有人需要我的服务请到地址xxx联系我"

如此一来，`nacos`就记录了系统中所有服务的联系地址，我们在服务中调用其它服务时，不必知晓其地址所在，只需知道其服务名称，`nacos`便会为我们找到对应的服务

**配置中心**的概念就更好理解了，举个例子：假设我们将配置文件放在服务的jar包文件中，而当我们几十个服务某个关键配置信息发生变化的时候，
我们将需要去重复性的修改几十个服务的配置文件，如果软件已经上线，我们还需要将这几十个服务重新打包部署一遍，这还没有考虑集群的情况

而如果我们将配置信息放在`nacos`中，我们只需要修改一次，并且这些配置是实时刷新的，不用重启服务就可即时生效



## nacos搭建步骤

说了这么多`nacos`的好处，那么我们如何搭建`nacos`呢？<br>


### 1. 下载
- 下载 `nacos-server-1.3.2.zip`
- GitHub直达链接：[nacos-server-1.3.2.zip](https://github.com/alibaba/nacos/releases/download/1.3.2/nacos-server-1.3.2.zip)
- 所有发行版：[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)
- 下载后解压


### 2. 导入数据库
- 在mysql新建数据库: `sp-nacos`
- 导入脚本: `\conf\nacos-mysql.sql`


### 3. 更改配置文件 
打开配置文件 `\conf\application.properties`
``` js
// 第21行附近，改启动端口
server.port=8001

// 第42行附近，配置据库连接 
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/sp-nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user=root
db.password=root123456

// 第124行附近，打开注册验证 
nacos.core.auth.enabled=true
```


### 4. 更改启动文件
打开配置文件`\bin\startup.sh` (window下打开`startup.cmd`)

``` js
// 第55行附近，改为单机模式  (原值cluster=集群模式)
export MODE="standalone"

// window下在文件startup.cmd第27行附近，修改为
set MODE="standalone"
```


### 5. 运行
进入命令行
``` js
// linux下运行: 
sh ./startup.sh

// window下运行:
startup.cmd
```


### 6. 浏览器访问

``` js
// 直接浏览器打开链接即可
http://127.0.0.1:8001/nacos		// 默认账号: 	nacos	nacos
```


### 7. 当看到类似于如下页面时就代表已经启动成功

![nacos启动成功](http://oss.dev33.cn/sp-cloud/nacos-ui.png)


### 8. 添加配置文件

在`\doc\yml-config\DEFAULT_GROUP`目录下，有三个配置文件，依次添加到`nacos`中，如图所示 

![nacos配置文件](http://oss.dev33.cn/sp-cloud/nacos-config.png)

- 注意：添加时，可直接将 `DEFAULT_GROUP`文件夹打个`zip压缩包`，然后导入即可
- 请注意修改`mysql`与`redis`的配置信息 
