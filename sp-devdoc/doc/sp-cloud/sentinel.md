# sentinel
分布式流量防卫兵


--- 

## sentinel是什么？
- sentinel是处理高并发的利器，它主要有三大作用：熔断、降级、限流
	- 熔断：`A服务`调用`B服务`时发现：“卧槽小老弟你这失败概率有点高啊，我调用你十次得有五次发生异常，你老是这样我没法向上面交代啊，你先歇会吧(熔断)，等五分种后我再来看看你好了没”
	- 降级：`A服务`调用`B服务`时发现：“卧槽我都等了半分钟了你还没响应，不行不行用户等不及了，我先自己模拟个假值返回给用户算了(降级处理)”; 为了降低系统整体负荷也可以考虑服务降级
	- 限流：秒杀活动开始了，卧槽没想到大家这么热情，一瞬间海量请求涌了过来，我势单力薄处理不过来怎么办？限定一下`qps`为`100`吧(限制流量)，超出`qps`的请求一律返回: `服务器繁忙请稍后再试` (`qps`指每秒访问量)



## 如何使用？
- `sentinel`有两种使用方式，一是通过代码配置防卫策略，二是通过`sentinel-dashboard`控制台配置防卫策略
- 由于`sentinel-dashboard`控制台方式更加灵活更加自动化，在此仅演示`sentinel-dashboard`控制台方式，对代码配置感兴趣的同学可自行搜索相关资料
- 以针对秒杀活动的限流策略举例:


## 搭建步骤

### 1. 下载 
- 下载`sentinel-dashboard-1.8.0.jar`
- GitHub直达链接: [sentinel-dashboard-1.8.0.jar](https://github.com/alibaba/Sentinel/releases/download/v1.8.0/sentinel-dashboard-1.8.0.jar)
- 所有发行版: [https://github.com/alibaba/Sentinel/releases](https://github.com/alibaba/Sentinel/releases)
- 如果网速太慢，也可以将仓库克隆到`gitee`，拉取源码后手动编译 (在`\sentinel-dashboard` 目录执行 `mvn clear package`)

### 2. 启动
进入cmd，运行以下命令
``` cmd
	java -Dserver.port=8002 -Dsentinel.dashboard.auth.username=sentinel -Dsentinel.dashboard.auth.password=sentinel -jar sentinel-dashboard.jar
```

### 3. 访问
- 浏览器访问: `http://127.0.0.1:8002`
- 账号: `sentinel`
- 密码: `sentinel`
- 登录成功如图所示: 

![sentinel首页](http://oss.dev33.cn/sp-cloud/sentinel-home.png)

### 4. 配置限流策略
- 浏览器访问: `http://127.0.0.1:8080/sp-admin/test`
- 一直使劲刷新，此时此接口是可以畅通无阻的 
- 打开`sentinel`控制台，按照如图所示，配置qps 

![配置qps限流](http://oss.dev33.cn/sp-cloud/sentinel-test-qps.png)

- 再次使劲刷新接口，会看到如图所示

![测试qps限流](http://oss.dev33.cn/sp-cloud/sentinel-test-qps-xz.png)


## 防卫配置持久化
- 普通状态下，服务重启后，限流配置信息将会丢失，而如果将`sentinel`的配置信息储存到`nacos`中，将可以持久化
- 打开模块`sp-nacos`查看`bootstrap.properties`，与`nacos`的集成信息已经配置完毕，开发者无须修改，正常启动项目即可


