springcloud微服务
A. 项目采用SpringBoot2.x+SpringCloud2.x构建微服务电商项目
1.使用SpringCloudEureka作为注册中心，实现服务治理
2.使用Zuul网关框架管理服务请求入口
3.使用Ribbon实现本地负载均衡器和FeginHttp客户端调用工具
4.使用Hystrix服务保护框架(服务降级、隔离、熔断、限流)
5.使用消息总线Stream RabbitMQ和Kafka
6.微服务API接口安全控制与单点登陆系统CAS+JWT+Oauth2.0


B. 分布式基础设施环境构建
1.分布式任务调度平台XXL-Job
2.分布式日志采集系统ELK +kafka
3.分布式事务解决方案LCN （MQ）
4.分布式锁解决方案Zookeeper、Redis
5.分布式配置中心携程阿波罗 
6.高并发分布式全局ID生成雪花算法
7.分布式Session框架Spring-Session
8.分布式服务追踪与调用链ZipKin  



C.项目运营与部署环境
1.分布式设施环境，统一采用docker安装
2.使用jenkins+docker+k8s实现自动部署 
3.微服务API管理ApiSwagger
4.使用GitLab代码管理 
5.统一采用第三方云数据库
6.使用七牛云服务器对静态资源实现加速
7.构建企业级Maven私服
