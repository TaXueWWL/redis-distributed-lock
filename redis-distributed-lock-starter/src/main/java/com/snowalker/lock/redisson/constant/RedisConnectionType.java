package com.snowalker.lock.redisson.constant;

/**
 * @author snowalker
 * @date 2018/7/11
 * @desc Redis连接方式
 *          包含:standalone-单节点部署方式
 *              sentinel-哨兵部署方式
 *              cluster-集群方式
 *              masterslave-主从部署方式
 */
public enum RedisConnectionType {

    STANDALONE("standalone", "单节点部署方式"),
    SENTINEL("sentinel", "哨兵部署方式"),
    CLUSTER("cluster", "集群方式"),
    MASTERSLAVE("masterslave", "主从部署方式");

    private final String connection_type;
    private final String connection_desc;

    private RedisConnectionType(String connection_type, String connection_desc) {
        this.connection_type = connection_type;
        this.connection_desc = connection_desc;
    }

    public String getConnection_type() {
        return connection_type;
    }

    public String getConnection_desc() {
        return connection_desc;
    }
}
