package com.snowalker.lock.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc Redisson配置映射类
 */
@ConfigurationProperties(prefix = "redisson.lock.server")
public class RedissonProperties {

    /**redis主机地址，ip：port，有多个用半角逗号分隔*/
    private String address;
    /**连接类型，支持standalone-单机节点，sentinel-哨兵，cluster-集群，masterslave-主从*/
    private String type;
    /**redis连接密码*/
    private String password;
    /**选取那个数据库*/
    private int database;

    public String getPassword() {
        return password;
    }

    public RedissonProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getDatabase() {
        return database;
    }

    public RedissonProperties setDatabase(int database) {
        this.database = database;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
