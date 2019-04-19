package com.snowalker.lock.redisson.config.strategy;

import com.snowalker.lock.redisson.config.RedissonProperties;
import com.snowalker.lock.redisson.constant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author snowalker
 * @date 2018/7/12
 * @desc 主从方式Redisson配置
 * 连接方式：主节点,子节点,子节点
 *          格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381
 */
public class MasterslaveRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            /**设置主节点ip*/
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StringUtils.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            /**设置从节点，移除第一个节点，默认第一个为主节点*/
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrToken);
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

}
