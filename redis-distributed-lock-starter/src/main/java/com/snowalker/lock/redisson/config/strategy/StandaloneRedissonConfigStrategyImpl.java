package com.snowalker.lock.redisson.config.strategy;

import com.snowalker.lock.redisson.config.RedissonProperties;
import com.snowalker.lock.redisson.constant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
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
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            LOGGER.info("初始化[standalone]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("standalone Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}
