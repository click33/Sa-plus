# 启动服务

- 假设我们`sp-home服务`提供了一个接口
``` java
/**
 * 服务实现者
 */
@RestController
@RequestMapping("/SpCfgApi/")
public class SpCfgInterfaceImpl {
	// 获取App端指定配置信息 
	@RequestMapping("getAppCfg")
	public String getAppCfg(String key, String defaultValue) {
		return SpCfgUtil.getCfgBy("app_cfg", key, defaultValue);
	}
}
```
- 我们在`sp-admin服务`该如何调用它呢？


### 方式一 Ribbon方式调用 
通过`Ribbon`+`RestTemplate`方式进行服务消费

- 首先新建工厂类
``` java
@Component
public class FC {
	// 返回RestTemplate实例 
	@Bean
	@LoadBalanced	// 使其可以解析自定义host并具有负载均衡的功能 
	public RestTemplate getTestTemplate(){
		return new RestTemplate();
	}
	// http操作类
	public static RestTemplate restTemplate;
	@Autowired
	private void setRestTemplage(RestTemplate restTemplate) {
		FC.restTemplate = restTemplate;
	}
}
``` 
- 然后在需要调用的地方
``` java
	@RequestMapping("test")
	public String test(){
		try {
			return FC.restTemplate.getForObject("http://sp-home/SpCfgApi/getAppCfg?key=name&defaultValue=123", String.class); 
		} catch (Exception e) {
			log.error("调用出错: {}", e.getMessage());
		}
	}
```
- 输出如下
``` text
	123
```

- **以上示例的调用方式虽然足够简单，但至少存在三个问题**
	- 所调用的服务地址、路由地址、调用参数需要硬编码写死，一旦发生变化，非常容易出现问题
	- 需要手动处理调用失败的异常等操作，如果需要一些`自定义header`信息，更加麻烦
	- 只能像调用外部普通`http接口`一样调用自己的服务，对复杂Model模型的处理能力很弱
- **feign的出现就是为了解决这几个问题的**


### 方式二 使用feign进行rpc调用 
通过`feign`方式进行服务消费 <br>
所谓`rpc`方式，就是指将远程接口进行一定的封装，使得我们可以像调用本地方法一样调用远程接口

- 经过一定的的封装 
``` java
/**
 * 系统配置 服务
 */
@FeignClient(
		name = FeignConsts.SP_HOME, 				// 服务名称 
		configuration = FeignInterceptor.class,		// 请求拦截器 
		fallbackFactory = SpCfgInterfaceFallback.class	// 服务降级 
		)	
public interface SpCfgInterface {
	// 获取App端指定配置信息 
	@RequestMapping("/SpCfgApi/getAppCfg")
	public String getAppCfg(@RequestParam("key")String key, @RequestParam("defaultValue")String defaultValue);
}
```
- 新建工厂类
``` java
@Component
public class FeignFactory {
	/**
	 *  系统配置 通信接口
	 */
	public static SpCfgInterface spCfgInterface;
	@Autowired
	public void setSpCfgInterface(SpCfgInterface spCfgInterface) {
		FeignFactory.spCfgInterface = spCfgInterface;
	}
}
```
- 然后在需要的地方像调用本地方法一样调用远程接口
``` java
	System.out.println(FeignFactory.spCfgInterface.getAppCfg("app_name", "默认值"));
```
- 输出如下
``` text
	123
```

**源码在`sp-api`模块中，可结合源码查看**




