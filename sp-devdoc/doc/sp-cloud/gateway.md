# 启动网关 
采用`gateway`作为服务网关组件 

--- 

## 1. 为什么启动网关？
网关主要做三件事
- 聚合服务：如果没有网关聚合服务，那我们每启动一个服务都要告诉前端一个新地址，前端会砍死我们
- 统一处理：请求先进入网关，再转发至具体的服务，方便我们进行一些IP限流、日志记录等统一操作
- 安全认证：生产环境中，内部子服务一般不宜对外网暴露，而是先经网关进行权限认证，再进入子服务


## 2. 启动网关
- 启动项目`sp-gateway`，然后浏览器访问：`http://127.0.0.1:8080/sp-admin/test`
- 网关接受到请求后，将会转发到`sp-admin`服务的`/test`接口
- 当你从浏览器看到如图所示时，证明整个链路已经打通

![网关转发测试](http://oss.dev33.cn/sp-cloud/sp-admin-test.png)


## 3. 网关鉴权
- 如果你绕过网关，直接访问子服务会怎么样呢？

![绕过网关测试](http://oss.dev33.cn/sp-cloud/sp-admin-zj-test.png)

- 如图所示，如果一个请求未经网关，直接访问子服务上，会被识别为无效请求，拒绝提供服务
- 而提供网关鉴权功能的正是 Sa-Token 的 `Id-Token` 模块（[详细了解](https://sa-token.dev33.cn/doc/index.html#/micro/id-token)），这个模块会在内部生成一个`token`存储到`redis`上，只有通过网关的请求，才会携带token进入子服务
- 如果请求未经网关，直接访问子服务上，则无法通过token验证，而被拒之门外


#### 你可能会问，那么如果这个token泄露了呢？
为了防止`token泄露`带来的安全问题，`token`会每隔五分钟自动刷新一次


#### 如果网关携带token转发的请求在落到子服务的节点上时，恰好刷新了token，导致鉴权未通过怎么办？
`Id-Token`模块在设计时，充分考虑到了这一点，在每次刷新`token`时，`旧token`会被作为`次级token`存储起来，
只要网关携带的`token`符合`新token`与`次级token`任意一个即可通过认证，直至下一次刷新，`新token`再次作为`次级token`将此替换掉


#### 微服务之间的内部调用也会验证token吗？
是的，微服务之间的调用同样需要验证`token`信息，只不过这一切都已经被封装好，你只需当做网关验证不存在一样直接调用即可


## 4. 网关转发规则
- 你可能想问一下，`gateway`的转发规则是什么，难道只能通过服务名称进行请求转发吗？当然不是
- 你有三种方式配置转发规则

#### 方式1，根据服务名自动转发
``` yml
spring:
    cloud:
        gateway:
			discovery:
				locator:
					#开启根据微服务名称自动转发
					enabled: true
					#微服务名称以小写形式呈现 
					lower-case-service-id: true
```

#### 方式2，配置路由表转发规则
``` yml
spring:
	cloud:
		gateway:
			# 配置转发路由表 
			routes:
				# 后台管理
				- id: sp-admin
				  uri: lb://sp-admin
				  predicates:
					- Path=/sp-admin/**
				  filters:
					- StripPrefix=1
				# 前台接口
				- id: sp-home
				  uri: lb://sp-home
				  predicates:
					- Path=/sp-home/**
				  filters:
					- StripPrefix=1
```

#### 方式3，通过代码配置路由表转发规则
``` java
@Configuration
public class RouteLocatorBean {
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
	    return builder.routes()
	        .route(p -> p
	            .path("/sp-admin/**")
				.filters(f -> f.stripPrefix(1).addRequestHeader(SaIdUtil.ID_TOKEN, SaIdUtil.getToken()))
	            .uri("lb://sp-admin")
	        ).build();
	}
}
```

#### Sp-Cloud源码使用的哪一种？
- `Sp-Cloud`使用的第一种，通过服务列表自动转发，只不过将配置信息放在了`nacos`中，就是在配置`nacos`时添加的`sp-gateway.yml`文件
- 当`gateway`集成`nacos`后，将可以做到实时监听注册中心，每次有新服务注册或者退出时，`gateway`将自动刷新路由表规则

