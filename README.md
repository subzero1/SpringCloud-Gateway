#spring gateway demo 简介
spring cloud 子项目，网关服务，提供静态路由，请求转发等功能，作为整个微服务对外入口
###依赖库
* Java 8
* Spring Framework 5
* Spring Boot 2
### 依赖项目
* [EurekaServerDemo1(注册中心)](https://github.com/subzero1/SpringCloud-Eureka.git)
* [SpringCloud Config](https://github.com/subzero1/SpringCloud-ConfgService.git~~~~)
* [ConfigService(微服务1)](https://github.com/subzero1/Service1.git)  
* [service2（微服务2）](https://github.com/subzero1/Service2.git)
  
###项目启动顺序
EurekaServerDemo1,ConfigService,service2,本项目Gateway最后启动

### 启动后项目测试
* 测试微服务1转发，ip方式:http://localhost:8081/a/test/t1
* 测试微服务2转发，ip方式:http://localhost:8081/b/test/t1
* 测试服务名,微服务1转发 http://localhost:8081/a1/test/t1
* 测试负载均衡<br />
  注意需把微服务1，微服务2服务名改成一样再测试:http://localhost:8081/a1/test/t1