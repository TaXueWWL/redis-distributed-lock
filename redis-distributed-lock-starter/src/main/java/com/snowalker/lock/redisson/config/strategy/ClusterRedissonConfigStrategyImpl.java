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
 * @desc 集群方式Redisson配置
 * 地址格式：
 *      cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
 *      格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384
 */
public class ClusterRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            /**设置cluster节点的服务IP和端口*/
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers()
                        .addNodeAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrTokens[i]);
                if (StringUtils.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            LOGGER.info("初始化[cluster]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("cluster Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}
