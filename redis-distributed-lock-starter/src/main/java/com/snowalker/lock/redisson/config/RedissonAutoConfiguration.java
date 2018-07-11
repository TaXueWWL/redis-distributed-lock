package com.snowalker.lock.redisson.config;

import com.snowalker.lock.redisson.RedissonLock;
import com.snowalker.lock.redisson.RedissonManager;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc Redisson自动化配置
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 2)
    public RedissonLock redissonLock(RedissonManager redissonManager) {
        RedissonLock redissonLock = new RedissonLock();
        redissonLock.setRedissonManager(redissonManager);
        LOGGER.info("[RedissonLock]组装完毕");
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 1)
    public RedissonManager redissonManager(RedissonProperties redissonProperties) {
        RedissonManager redissonManager =
                new RedissonManager(redissonProperties.getHost(), redissonProperties.getPort());
        LOGGER.info("[RedissonManager]组装完毕");
        return redissonManager;
    }
}

