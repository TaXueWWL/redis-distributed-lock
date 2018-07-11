package com.snowalker.lock.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc Redisson配置映射类
 */
@ConfigurationProperties(prefix = "redisson.server")
public class RedissonProperties {

    /**单节点redis主机*/
    private String host;
    /**单节点redis端口*/
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
