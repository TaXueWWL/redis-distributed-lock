package com.snowalker.lock.redisson.config.strategy;

import com.snowalker.lock.redisson.constant.GlobalConstant;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author snowalker
 * @date 2018/7/12
 * @desc 单机方式Redisson配置
 */
public class StandaloneRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(String address) {
        Config config = new Config();
        try {
            String redisAddr = GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + address;
            config.useSingleServer().setAddress(redisAddr);
            LOGGER.info("初始化[standalone]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("standalone Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}
