package com.ding;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//服務註冊中心
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}
}


/***
所有的maven项目都是  quickstart 
 
1. @EnableEurekaServer表明這是註冊中心
2. 通過配置文件參數 表明這是註冊中心服務器  默认是true
	registerWithEureka: false
	fetchRegistry: false

如果不想使用主机名进行访问注册中心，也可以使用ip，但是需要先添加一条配置，该值默认false
eureka.instance.prefer-ip-address=true
	
访问 http://localhost:10088/	查看注册的服务



問題
1.@SpringBootApplication引用不到
將.m2\repository\org\springframework\boot\spring-boot-autoconfigure刪除 然後選擇項目 Maven--> update project

2.SpringApplication.run(EurekaApplication.class, args);錯誤如下	
The type org.springframework.context.ConfigurableApplicationContext cannot be resolved. It is indire

命令行形式在pom.xml所在的位置  執行mvn dependency:purge-local-repository
**/




	/**
	 
	 对于服务发现来说，应该有下面几个功能
	 服务注册
	 服务下线
	 服务剔除
	 服务租约
	 
	 服务注册
		理论上来说 服务在启动的时候，就应该调用eureka server的api进行注册。
		对于java 服务可以使用  eureka client封装的api去调用server的api进行注册
		对于spring cloud的应用 基于spring boot的自动配置，已经自动帮我们注册服务了
	
	服务剔除
		正常来说服务在关系应用的时候，应该通过钩子或者生命周期的回调方法去调用eureka server的de-register来删除自身的信息。
		但是为了解决服突然挂掉或其他情况没有及时删除自身的信息，eureka server要求client定时发送心跳（30s），来证明服务还存活，
		如果超过一定时间没有进行续约（90s），server就会认为该服务不健康自动剔除。
	 
	服务实例集群一致性
		一般来说对于高可用复制的情况分为：主从服务和对等复制
		主从复制：就好像mysql主从，写都写在主节点，然后由主节点复制给其他从节点，这样主节点的写压力大，但是从节点可以分担读压力
		对等复制：任何节点都可以接受写请求和读请求，
		
		CAP c:数据一致性，a任何时候集群都可以提供服务，p网络分区的时候集群可用    （p是无法避免的所以只能  cp或ap）
		
		zk和eureka对比
		
		zk可以说是cp的  但是也不能说是强一致性的，，因为客户端提交一个写请求，只要过半的节点写成功就返回，这时有个读请求刚好读到没有同步的节点，
		这个时候就读不到新的数据，如果需要强一致性，需要读取数据的时候先执行一下sync，即先向主节点同步一下数据。。
		但是在网络分区的情况下，主节点，不在non-quorum分区，这个分区不可用无法满足Availability的特性
		而且在选主节点的时候zk集群不可用
		
		eureka是ap的 数据不是强一致性的。。。
		
	 	eureka在下面两种情况会同步信息
	 	eureka刚启动的时候会有一个syncUp的操作，像其他的eureka server获取注册的应用实例信息
	 	
	 	服务注册或下线eureka会执行复制操作
	 	
	 	通过 header replocation标识将复制请求和正常的请求区分，其他的peer节点收到请求就不会向他复制，这样就可以避免死循环
	 
	 	处理数据冲突。一般来说直接比较version，但是eureka使用的是lastDirtyTimestamp来对比
	 	
	 	注册的时候请求lastDirtyTimestamp大于server本地的lastDirtyTimestamp 则冲突，要求重新注册
	 	复制的时候请求lastDirtyTimestamp大于server本地的lastDirtyTimestamp 则冲突，要求同步自身更新的信息
	 
	 	peer-peer复制不一定成功，所以server之间还可以通过hearbeat来修复
	 
	自我保护机制
		eureka.server.enable-self-preservation
		
		eureka会通过当前注册的实例数来计算没分钟应该收到的心跳数（假设10个实例  30s发送一次心跳   10*2）
		即每分钟期望收到20个心跳租约
		
		但是可能由于网络抖动或其他原因eureka server没有收到期望的20个心跳数，eureka就会开启自动保护机制
		关闭定时任务剔除失效的实例。
		
		eureka server收到的心跳数小于一定的阈值（20*0.85=17）就会开启
		
		阈值可以通过eureka.server.renewal-percent-threshold来设置 。设置小于0.5  即0.49
		
	高可用
	
		registerWithEureka，fetchRegistry都要开启true
		defaultZone配置所有的server排除自己
		
	https 。。。。 
	 
	原理参考 
	https://nobodyiam.com/2016/06/25/dive-into-eureka/ 




Feign
	Feign一种声明式的http客户端。可以用于服务之间的调用。。使用feign只需要在接口加上对应的注解FeignClent
	支持hystrix快速失败和ribbon负载均衡
	支持http请求和响应的压缩
	
使用
	1.引入依赖
	2.在启动类@EnableFeignClients
	3.在接口注解@FeignClent	
	FeignClent（name="" ,configuration="",fallback=""）
	name:
	configuration，配置编码  日志等级
	fallback    fallback=“xxx.class”  xxx.class要实现feignClient注释的接口
	
原理
	程序启动的时候会自动扫描FeignClent注入ioc容器，，当方法被调用的时候，会通过jdk自动代理的方式，会为没个方法生成一个RequsetTemple对象
	RequsetTemple对象封装了http的接口请求信息       

gzip压缩
	配置文件
	开启后feign是通过二进制来传输的，，，返回值需要改成ResponseEntity<byte[]>
	乱码


feign可以通过java类或者配置文件来配置
	配置文件会覆盖java类

feign超时（两种）
	feign整合了hystrix，hystrix默认的超时是1s。但是由于spring bean的懒加载机制 ，feign第一次调用就会比较慢，可能会超时，进入快速失败
	处理方案
		1.禁用hystrix  不推荐
		2.修改超时时间 5s  （感觉比较靠谱）
		3.禁用超时时间

	ribbon超时
		设置超时时间
		
		
feign替换http请求 比如使用okhttp
feign get传实体  不行  使用拦截器处理
文件上传
图片字节流 返回

Load balancer does not have available server for client: cloud-member





Hystrix
熔断器  快速失败，防止级联崩溃

启用断路器  在启动类@EnableHystrix

@HystrixCommand（fallbackMethod=“”）

feign 自带的Hystrix默认是关闭的
	feign.hystrix.enable=fasle
	@FeignClient（fallback=“xxx.class”）  xxx.class要实现feignClient注释的接口


hystrix异常
	下面5种会触发快速失败
	failure 异常
	timeout 超时
	short_circuited 断路器打开
	thread_pool_rejected 线程池拒绝
	semaphore_rejected 信号量
	
	HystrixRequestException非法参数，非系统异常   不会触发快速失败


一般实际项目中  会对超时时间，线程池大小。信号量  进行修改       见6.26
默认Hystrix超时时间默认是1s 但是在实际过程中  发现1s太短  通过会配置5~10s
一些文件上传的业务则更长
如果配置了Ribbon的话  一般来说Ribbon时间短于Hystrix超时时间

线程池的大小    每秒的峰值*响应时间+预留的值   30*0.2+4=10
请求缓存  只能在一次请求中有效
HystrixCommand是阻塞的  HystrixObservableCommand异步的
HystrixCommand的属性
	defaultFallback  
	fallbackMethod
	ignoreExceptions  忽略的异常不会进入断路器






zuul  路由
	使用
	1.引入依赖
	2.在启动类@EnableZuulProxy
	3.配置文件 
		zuul:
		  routes:
		    cloud-order:
		      path: /order/**
		      serviceId: cloud-order
		    cloud-member:
		      path: /member/**
		      serviceId: cloud-member

	转发到zuul本地
		cloud-order:
		      path: /order/**
		      url: forward:/client
	/** 匹配任意字符和路径
	/*  匹配任意的字符
	/?  匹配单个字符
	 
	动态路由
		1.配置中心的形式。。放在git上面
		2.数据库的形式。。（已实现）
	
	cookie传不过去后端服务，后端服务cookie传不到前面。。要设置敏感头
		
	可以设置排除的路径
	
	负载均衡？？？
	重定向问题
	权限验证问题
	
	问题  对于其他微服务抛出的异常  zuul会直接出现报错页面。。。
	
	灰度发布：：：可以利用eureka，，自定义元数据，，，讲对应得服务标记为正常服务，，和灰度服务。。
			然后在一个请求的header带上标记看看这个请求是否访问灰度服务
	
	
	
配置中心：
	原理
		配置客户端 向 配置服务器发起请求
		配置服务器 收到请求根据自身的配置（git的地址）从git拉取代码到本地的临时目录文件
		配置服务器读取本地文件返回给客户端
		这样即使git挂了 ，，配置服务器还能正常运行
	
	
	
	配置服务端
		引入依赖（2个）
		在启动类配置@EnableConfigServer
		在配置文件中配置git的地址
		
		search-paths：目录   可以搜索目录下所有满足条件的配置文件  多个用逗号隔开
		
		spring.cloud.config.server.git.uri=https://github.com/348786639/spring-cloud-config/{application}
		git的地址可以使用通配符  profile  label都可以
	配置客户端 
		引入依赖（1个）
		要配置  配置中心服务器
		
		
		要配置在bootstrap.yml
		不要配置在application.yml中
		因为bootstrap优先加载去远程拉取配置
		spring.cloud.config.label=master
		spring.cloud.config.profile=dev
		spring.cloud.config.uri=http://localhost:10098/
		spring.application.name=config-client
		
	
		application应用名，最好跟git的文件也保持一致
		profile：  dev  test  pro等环境
		label  可以省略 默认是master
	   http请求地址和资源文件映射如下:所以要注意应用的名称	
	   /{application}/{profile}[/{label}]
	   /{application}-{profile}.ym/{label}
	   /{application}-{profile}.yml
	   /{application}-{profile}.properties
	   /{label}/{application}-{profile}.properties
	
		
		
		集合spring cloud bus 来实现配置文件热刷新
		原理：
			1.当用户修改github上面的配置文件  触发hook去调用配置服务器
			2.config server接收到请求 会发布信息（publish message） bus然后去通知 config client
			3.config client在去请求config server   ，，config server根据配置去远程拉取配置信息
		
		
		@RefreshScope  修饰的bean都是延迟加载的。。只有第一次访问才会被初始化.

		
		
		在开发中我们经常会遇到需要用本地的配置或环境变量去覆盖远程的配置
		spinrg.cloud.config.allowOverride: true
		spring.cloud.config.overrideNonde: true
		overrideSystemProperties: false

		allowOverride 喝 overrideNone  都是true  外部配置优先级更低，，并且不能覆盖   默认是false
		overrideSystemProperties： 标识外部是否能够覆盖系统属性  默认是true
	
	
链路监控Sleuth
		
	
	
	
	
	
	
	
*/




