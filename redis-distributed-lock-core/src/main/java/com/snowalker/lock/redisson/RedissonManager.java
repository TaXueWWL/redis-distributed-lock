package com.snowalker.lock.redisson;

import com.snowalker.util.PropertiesUtil;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc
 */
@Component
public class RedissonManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Redisson.class);

    private Config config = new Config();

    private Redisson redisson = null;

    private static String redisIp =
            PropertiesUtil.getProperty("redis.ip", "127.0.0.1");

    private static String redisPort =
            PropertiesUtil.getProperty("redis.port", "6379");

    @PostConstruct
    private void init() {
        try {
            config.useSingleServer().setAddress(
                            new StringBuilder("redis://")
                                    .append(redisIp).append(":").append(redisPort).toString());
            redisson = (Redisson) Redisson.create(config);
            LOGGER.info("初始化Redisson结束");
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
