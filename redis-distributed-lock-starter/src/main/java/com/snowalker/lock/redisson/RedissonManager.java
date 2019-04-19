package com.snowalker.lock.redisson;

import com.google.common.base.Preconditions;
import com.snowalker.lock.redisson.config.RedissonProperties;
import com.snowalker.lock.redisson.config.strategy.*;
import com.snowalker.lock.redisson.constant.RedisConnectionType;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc Redisson核心配置，用于提供初始化的redisson实例
 */
public class RedissonManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Redisson.class);

    private Config config = new Config();

    private Redisson redisson = null;

    public RedissonManager() {}

    public RedissonManager(RedissonProperties redissonProperties) {
        try {
            config = RedissonConfigFactory.getInstance().createConfig(redissonProperties);
            redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            LOGGER.error("Redisson init error", e);
            throw new IllegalArgumentException("please input correct configurations," +
                    "connectionType must in standalone/sentinel/cluster/masterslave");        }
    }

    public Redisson getRedisson() {
        return redisson;
    }

    /**
     * Redisson连接方式配置工厂
     */
    static class RedissonConfigFactory {

        private RedissonConfigFactory() {}

        private static volatile RedissonConfigFactory factory = null;

        public static RedissonConfigFactory getInstance() {
            if (factory == null) {
                synchronized (RedissonConfigFactory.class) {
                    factory = new RedissonConfigFactory();
                }
            }
            return factory;
        }

        private Config config = new Config();

        /**
         * 根据连接类型获取对应连接方式的配置,基于策略模式
         * @param redissonProperties
         * @return Config
         */
        Config createConfig(RedissonProperties redissonProperties) {
            Preconditions.checkNotNull(redissonProperties);
            Preconditions.checkNotNull(redissonProperties.getAddress(), "redisson.lock.server.address cannot be NULL!");
            Preconditions.checkNotNull(redissonProperties.getType(), "redisson.lock.server.password cannot be NULL");
            Preconditions.checkNotNull(redissonProperties.getDatabase(), "redisson.lock.server.database cannot be NULL");
            Preconditions.checkNotNull(redissonProperties.getPassword(), "redisson.lock.server.type cannot be NULL");
            String connectionType = redissonProperties.getType();
            /**声明配置上下文*/
            RedissonConfigContext redissonConfigContext = null;
            if (connectionType.equals(RedisConnectionType.STANDALONE.getConnection_type())) {
                redissonConfigContext = new RedissonConfigContext(new StandaloneRedissonConfigStrategyImpl());
            } else if (connectionType.equals(RedisConnectionType.SENTINEL.getConnection_type())) {
                redissonConfigContext = new RedissonConfigContext(new SentinelRedissonConfigStrategyImpl());
            } else if (connectionType.equals(RedisConnectionType.CLUSTER.getConnection_type())) {
                redissonConfigContext = new RedissonConfigContext(new ClusterRedissonConfigStrategyImpl());
            } else if (connectionType.equals(RedisConnectionType.MASTERSLAVE.getConnection_type())) {
                redissonConfigContext = new RedissonConfigContext(new MasterslaveRedissonConfigStrategyImpl());
            } else {
                throw new IllegalArgumentException("创建Redisson连接Config失败！当前连接方式:" + connectionType);
            }
            return redissonConfigContext.createRedissonConfig(redissonProperties);
        }
    }

}


