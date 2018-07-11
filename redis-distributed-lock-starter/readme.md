# 开发一个spring-boot-starter的步骤
1. 新建一个maven项目
2. 需要一个配置类RedisAutoConfiguration，在配置类里面装配好需要提供出去的类
3. 使用@Enable配合@Import导入需要装配的类  或者  META-INF/spring.factories中配置
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.snowalker.demo.redis.RedisAutoConfiguration

## redis分布式锁starter--基于Redisson
### 配置文件
    redisson.server.host=127.0.0.1
    redisson.server.ip=6379