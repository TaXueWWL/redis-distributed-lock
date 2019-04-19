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
 * @desc 哨兵集群方式Redis连接配置
 */
public class SentinelRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentinelRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            /**设置redis配置文件sentinel.conf配置的sentinel别名*/
            config.useSentinelServers()
                    .setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            /**设置sentinel节点的服务IP和端口*/
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrTokens[i]);
            }
            LOGGER.info("初始化[sentinel]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("sentinel Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}
