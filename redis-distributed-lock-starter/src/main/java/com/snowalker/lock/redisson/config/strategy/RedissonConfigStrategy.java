package com.snowalker.lock.redisson.config.strategy;

import com.snowalker.lock.redisson.config.RedissonProperties;
import org.redisson.config.Config;

/**
 * @author snowalker
 * @date 2018/7/12
 * @desc Redisson配置构建接口
 */
public interface RedissonConfigStrategy {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     * @param redissonProperties
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
