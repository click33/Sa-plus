# 项目架构

打包部署到服务器 

---


## 1. 后台接口部署

##### 首次部署
1. pom.xml配置：已经配置完毕，无需更改
2. 在文件管理器中进入项目根目录(pom.xml所在目录)，进入cmd，运行命令：`mvn package`   
3. 等待半分钟左右，然后再`target`目录下会多出：
	- 一个文件：`sp-server-0.0.1-SNAPSHOT.jar`		(你写的代码jar包)
	- 和一个文件夹：`lib\`						(`pom.xml`中所有依赖包)
4. 将这两个复制到linux服务器
5. 先cd到项目目录，然后运行`shell`脚本：`nohup java -jar sp-server-0.0.1-SNAPSHOT.jar`

#####  以后再次部署的时候，因为项目已经在运行，所以需要先关掉，才能再次部署
1. 运行：`ps -ef|grep java`，搜寻所有java相关进程
2. 找到项目所属的进程，记住进程id：pid，比如是：`13310`
3. 运行：`kill -9 -13310`  强制杀掉进程
再次重复上面的【首次部署】

##### 为什么打成分散包？
- 这样有一个好处，就是在你多次部署项目时，如没有变更`pom.xml`, `lib\`目录也是不变的， 这样每次只上传你的`xxx.jar`代码包就好了，节省部署时间  
- 如一定需要打成完整包，将`pom.xml`中的`build`节点改为如下样式即可

``` xml 

<!-- 构建配置 -->
    <build>
        <!-- 配置资源目录  -->
		<!-- resources配置... -->
		
		<!-- 打包插件(完整包) -->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
		
    </build>
	
```

- 注意不要删除文件中的`<resources>`节点，将`plugins`节点改为如上所示即可，其它步骤不变


## 2. admin后台管理部署
1. 后台管理基于前后台分离模式，里面都是`纯html`
2. 直接复制到`linux`上，然后将所在文件夹开放静态目录访问就好了
3. 如果觉得`nginx`等服务器配置麻烦，也可以直接在`springboot`中配置一个静态资源映射即可
4. 例如你的后台管理页面放在了 `/app/sp/html/sp-admin/`下
5. 你只需要在`springboot`的yml配置文件的`spring.resources.static-locations` 配置项中再追加一个静态资源目录即可：`file:/app/sp/html`
6. 重新打包部署服务端代码，你便可以通过如下方式访问后台管理了：`http://xxx.com/sp-admin/index.html`。
7. 对springboot静态资源映射不熟悉的同学，请移步百度：[springboot静态资源映射](https://www.baidu.com/s?ie=UTF-8&wd=springboot%E9%9D%99%E6%80%81%E8%B5%84%E6%BA%90%E6%98%A0%E5%B0%84)


## 3. api接口文档部署 
- 同样只是纯静态文件，部署步骤同上，不再赘述  













