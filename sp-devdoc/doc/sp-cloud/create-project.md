# 创建项目 

- 搭建好nacos后，我们即可开始搭建项目
- **注意：以下文档仅做示例，大家不必从零开始新建项目，可以直接在ide中导入项目`sp-cloud`，结合源码进行阅读文档**

## 新建项目，添加依赖
在`ide`中新建`maven多模块`项目，并在`pom.xml`中添加以下依赖：

``` xml
<!-- 依赖管理 (不会真正的引入依赖，只会限定其版本) -->
	<dependencyManagement>
		<dependencies>
		
			<!-- SpringCloud -->
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Hoxton.RELEASE</version>
				<!-- <version>Hoxton.SR4</version> -->
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			
			<!-- SpringCloud Alibaba -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <!-- <version>2.2.3.RELEASE</version> -->
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- Alibaba Nacos -->
			<dependency>
			    <groupId>com.alibaba.nacos</groupId>
			    <artifactId>nacos-client</artifactId>
			    <version>1.3.2</version>
			</dependency>
			
			<!-- SpringBoot -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.3.3.RELEASE</version> 
                <type>pom</type>
                <scope>import</scope>
            </dependency>

		</dependencies>
	</dependencyManagement>
```

注：如无必要，请勿更改版本，否则容易出现版本兼容问题

