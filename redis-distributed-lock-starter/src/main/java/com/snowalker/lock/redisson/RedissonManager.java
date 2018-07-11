package com.snowalker.lock.redisson;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc
 */
public class RedissonManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Redisson.class);

    private Config config = new Config();

    private Redisson redisson = null;

    public RedissonManager (String redisIp, String redisPort) {
        try {
            String redisAddr = new StringBuilder("redis://")
                    .append(redisIp).append(":").append(redisPort)
                    .toString();
            config.useSingleServer().setAddress(redisAddr);
            redisson = (Redisson) Redisson.create(config);
            LOGGER.info("初始化Redisson结束,redisAddress:" + redisAddr);
        } catch (Exception e) {
            LOGGER.error("Redisson init error", e);
            e.printStackTrace();
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }


    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        Redisson redisson = (Redisson) Redisson.create(config);
    }
}
