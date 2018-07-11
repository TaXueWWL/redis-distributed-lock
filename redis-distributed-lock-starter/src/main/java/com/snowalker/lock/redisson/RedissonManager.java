package com.snowalker.lock.redisson;

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

    public RedissonManager (String connectionType, String address) {
        try {
            config = RedissonConfigFactory.getInstance().createConfig(connectionType, address);
            redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            LOGGER.error("Redisson init error", e);
            e.printStackTrace();
        }
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

        private static final String REDIS_CONNECTION_PREFIX = "redis://";

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
         * 根据连接类型及连接地址参数获取对应连接方式的配置
         * @param connectionType
         * @param address
         * @return Config
         */
        Config createConfig(String connectionType, String address) {
            if (connectionType.equals(RedisConnectionType.STANDALONE.getConnection_type())) {
                try {
                    String redisAddr = REDIS_CONNECTION_PREFIX + address;
                    config.useSingleServer().setAddress(redisAddr);
                    LOGGER.info("初始化standalone方式Config,redisAddress:" + redisAddr);
                } catch (Exception e) {
                    LOGGER.error("standalone Redisson init error", e);
                    e.printStackTrace();
                }
                return config;
            } else if (connectionType.equals(RedisConnectionType.SENTINEL.getConnection_type())) {
                return null;
            }
            throw new RuntimeException("创建Redisson连接Config失败！当前连接方式:" + connectionType);
        }
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        Redisson redisson = (Redisson) Redisson.create(config);
    }
}


