<div align="center">
	<!-- <img alt="logo" src="https://gitee.com/click33/sa-plus/raw/master/sp-devdoc/sp-cloud.png" width="330"> -->
	<h2 align="center" style="font-size: 80px; font-weight: 700;">Sp-Cloud</h2>
</div>
<div align="center" style="margin-top: 30px; font-size: 22px;"><b>v1.26.0</b></div>
<h4 align="center">Sa-plus 的微服务版本，基于 Spring-Cloud-Alibaba</h4>
<p align="center">
	<a href="https://gitee.com/click33/sp-cloud/stargazers"><img src="https://gitee.com/click33/sp-cloud/badge/star.svg?theme=dark"></a>
	<a href='https://gitee.com/click33/sp-cloud/members'><img src='https://gitee.com/click33/sp-cloud/badge/fork.svg?theme=dark'></a>
	<a href="https://gitee.com/click33/sp-cloud"><img src="https://img.shields.io/badge/Sp--Cloud-v1.26.0-2B9939?style=flat-square&logo=GitHub"></a>
	<a href="https://gitee.com/click33/sp-cloud"><img src="https://img.shields.io/badge/language-java-2B9939?style=flat-square&logo=GitHub"></a>
	<a href="http://sa-plus.dev33.cn/"><img src="https://img.shields.io/badge/based-sa--plus-4183C4?style=flat-square&logo=GitHub"></a>
	<a href="https://gitee.com/click33/sp-cloud/blob/master/LICENSE"><img src="https://img.shields.io/github/license/click33/sa-plus.svg?style=flat-square&logo=GitHub"></a>
</p>

 
## Sp-Cloud介绍 😘
- 本项目为`sa-plus`的微服务版本，在其基础架构下，将系统功能拆分成若干个服务，每个服务都能独立部署、独立维护、独立扩展，更适合多人协作的开发模式
- 如需要单体架构模式，请移步: [Sa-plus基础版](https://github.com/click33/sa-plus)


## 在线资料
- 官网首页：[http://sa-plus.dev33.cn/](http://sa-plus.dev33.cn/)
- 在线演示：[http://demo.dev33.cn/sp-admin/index.html](http://demo.dev33.cn/sp-admin/index.html)
- 在线文档: [http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/README](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/README)
- 需求提交：[我们深知一个优秀的项目需要海纳百川，点我在线提交需求](http://sa-app.dev33.cn/wall.html?name=sa-plus)
- Gitee地址: [https://gitee.com/click33/sp-cloud](https://gitee.com/click33/sp-cloud)


## 文档目录
- 版本介绍：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/README](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/README)
- 注册中心-nacos：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/nacos](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/nacos)
- 搭建项目基架：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/create-projec](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/create-projec)
- 注册服务到nacos：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/reg-service](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/reg-service)
- 启动网关-gateway：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/gateway](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/gateway)
- 使用feign实现rpc调用：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/feign](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/feign)
- 熔断降级限流-sentinel：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/sentinel](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/sentinel)
- 服务信息监控-springboot-admin：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/springboot-admin](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/springboot-admin)
- 启动后台管理UI页面-sp-admin：[http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/sp-admin](http://sa-plus.dev33.cn/doc/index.html#/sp-cloud/sp-admin)




## 技术选型
- **微服务:**
- SpringBoot版本: `2.3.3.RELEASE`
- SpringCloud版本: `Hoxton.RELEASE`
- SpringCloud-Alibaba版本: `2.2.2.RELEASE`
- 服务注册中心：`nacos`
- 分布式配置中心：`nacos`
- RPC通信: `feign`
- 服务网关: `gateway`
- 流量控制: `sentinel`
- 流量监控台: `sentinel-dashboard`
- 限流持久化: `sentinel-datasource-nacos`
- 服务健康监控台: `springboot-admin`
- **基础框架:**
- 数据库: `mysql`
- 持久层: `mybatis-plus`
- 分页插件: `pagehelper`
- 连接池: `druid`
- 缓存层: `redis`
- 权限认证: `sa-token`
- Model构建: `lombok`
- JSON插件: `fastjson`
- 工具类: `hutool`
- 前端：`vue`+`element-ui`
- 接口文档：`sa-doc`

## 项目模块
```
── sp-cloud
	├── sp-core                        // 内部核心模块
	│       └── sp-api                          // API接口包模块
	│       └── sp-nacos                        // nacos统一配置文件模块 
	│       └── sp-base                         // 基础模块的汇总，方便引用 
	├── sp-native                      // springcloud相关服务 
	│       └── sp-gateway                      // 服务网关 [8080]
	│       └── sp-boot-admin                   // 服务监控台 [8003]
	├── sp-service                     // 系统业务相关服务
	│       └── sp-task                         // 定时任务模块 [8010]
	│       └── sp-admin                        // 后台管理接口 [8011]
	│       └── sp-home                         // 系统基础接口 [8012]
	│       └── sp-more                         // 其它模块,杂七杂八接口 [8013]
	├──pom.xml

	// 其它端口：nacos[8001]、sentinel-dashboard[8002]
```



## 演示预览
<table>
    <tr>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-1.png"/></td>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-3.png"/></td>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-5.png"/></td>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-6.png"/></td>
    </tr>
    <tr>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-7.png"/></td>
        <td><img src="https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/pre-8.png"/></td>
    </tr>
</table>




## 贡献代码
1. fork一份源码到自己的仓库
2. clone自己的仓库到本地电脑
3. 在本地电脑修改、commit、push
4. 提交pr（点击：New Pull Request）（提交pr前请保证自己fork的仓库是最新版本，如若不是先强制更新一下）
5. 等待合并


## 建议贡献的地方
- 修复源码现有bug，或增加新的实用功能
- 完善在线文档，或者修复现有错误之处
- 如果更新实用功能，可在文档友情链接处留下自己的推广链接


## QQ群 
- QQ交流群：[782974737 点击加入](https://jq.qq.com/?_wv=1027&k=5DHN5Ib)
- 如遇bug或者有好想法，请加入qq群一起交流  



