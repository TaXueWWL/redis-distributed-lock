package com.snowalker.lock.redisson;

import com.google.common.base.Preconditions;
import com.snowalker.lock.redisson.constant.RedisConnectionType;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
            Preconditions.checkNotNull(connectionType);
            Preconditions.checkNotNull(address);
            if (connectionType.equals(RedisConnectionType.STANDALONE.getConnection_type())) {
                return createStandaloneConfig(address);
            } else if (connectionType.equals(RedisConnectionType.SENTINEL.getConnection_type())) {
                return createSentinelConfig(address);
            } else if (connectionType.equals(RedisConnectionType.CLUSTER.getConnection_type())) {
                return createClusterConfig(address);
            } else if (connectionType.equals(RedisConnectionType.MASTERSLAVE.getConnection_type())) {
                return createMasterSlaveConfig(address);
            }
            throw new RuntimeException("创建Redisson连接Config失败！当前连接方式:" + connectionType);
        }

        /**
         * 主从方式配置
         * @param address
         * @return
         */
        private Config createMasterSlaveConfig(String address) {
            try {
                String[] addrTokens = address.split(",");
                String masterNodeAddr = addrTokens[0];
                /**设置主节点ip*/
                config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
                /**设置从节点，移除第一个节点，默认第一个为主节点*/
                List<String> slaveList = new ArrayList<>();
                for (String addrToken : addrTokens) {
                    slaveList.add(REDIS_CONNECTION_PREFIX + addrToken);
                }
                slaveList.remove(0);

                config.useMasterSlaveServers().addSlaveAddress((String[]) slaveList.toArray());
                LOGGER.info("初始化[MASTERSLAVE]方式Config,redisAddress:" + address);
            } catch (Exception e) {
                LOGGER.error("MASTERSLAVE Redisson init error", e);
                e.printStackTrace();
            }
            return config;
        }

        /**
         * 集群方式配置
         * @param address
         * @return
         */
        private Config createClusterConfig(String address) {
            try {
                String[] addrTokens = address.split(",");
                /**设置cluster节点的服务IP和端口*/
                for (int i = 0; i < addrTokens.length; i++) {
                    config.useClusterServers().addNodeAddress(REDIS_CONNECTION_PREFIX + addrTokens[i]);
                }
                LOGGER.info("初始化[cluster]方式Config,redisAddress:" + address);
            } catch (Exception e) {
                LOGGER.error("cluster Redisson init error", e);
                e.printStackTrace();
            }
            return config;
        }

        /**
         * 哨兵方式配置
         * @param address
         * @return
         */
        private Config createSentinelConfig(String address) {
            try {
                String[] addrTokens = address.split(",");
                String sentinelAliasName = addrTokens[0];
                /**设置redis配置文件sentinel.conf配置的sentinel别名*/
                config.useSentinelServers()
                        .setMasterName(sentinelAliasName);
                /**设置sentinel节点的服务IP和端口*/
                for (int i = 1; i < addrTokens.length; i++) {
                    config.useSentinelServers().addSentinelAddress(REDIS_CONNECTION_PREFIX + addrTokens[i]);
                }
                LOGGER.info("初始化[sentinel]方式Config,redisAddress:" + address);
            } catch (Exception e) {
                LOGGER.error("sentinel Redisson init error", e);
                e.printStackTrace();
            }
            return config;
        }

        /**
         * 单机方式配置
         * @param address
         * @return
         */
        private Config createStandaloneConfig(String address) {
            try {
                String redisAddr = REDIS_CONNECTION_PREFIX + address;
                config.useSingleServer().setAddress(redisAddr);
                LOGGER.info("初始化[standalone]方式Config,redisAddress:" + address);
            } catch (Exception e) {
                LOGGER.error("standalone Redisson init error", e);
                e.printStackTrace();
            }
            return config;
        }

    }

}


