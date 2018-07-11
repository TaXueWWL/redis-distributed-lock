# 开发一个spring-boot-starter的步骤
1. 新建一个maven项目
2. 需要一个配置类RedisAutoConfiguration，在配置类里面装配好需要提供出去的类
3. 使用@Enable配合@Import导入需要装配的类  或者  META-INF/spring.factories中配置
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.snowalker.demo.redis.RedisAutoConfiguration

## redis分布式锁starter--基于Redisson
### 配置文件
        redisson.lock.server.address=127.0.0.1:6379
        redisson.lock.server.type=standalone
### 更新记录
1. 改变配置方式，增加对不同Redis连接方式的支持
<br/>去除以下方法RedissonManager(String redisIp, String redisPort)

            public RedissonManager (String redisIp, String redisPort) {
                try {
                    String redisAddr = new StringBuilder("redis://")
                            .append(redisIp).append(":").append(redisPort)
                            .toString();
                    config.useSingleServer().setAddress(redisAddr);
                    redisson = (Redisson) Redisson.create(config);
                    LOGGER.info("初始化Redisson结束,redisAddress:" + redisAddr);
                } catch (Exception e) {
                    LOGGER.error("Redisson init error", e);
                    e.printStackTrace();
                }
            }
