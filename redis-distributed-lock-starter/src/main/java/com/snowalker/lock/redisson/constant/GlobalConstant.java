package com.snowalker.lock.redisson.constant;

/**
 * @author snowalker
 * @date 2018/7/12
 * @desc 全局常量枚举
 */
public enum GlobalConstant {

    REDIS_CONNECTION_PREFIX("redis://", "Redis地址配置前缀");

    private final String constant_value;
    private final String constant_desc;

    private GlobalConstant(String constant_value, String constant_desc) {
        this.constant_value = constant_value;
        this.constant_desc = constant_desc;
    }

    public String getConstant_value() {
        return constant_value;
    }

    public String getConstant_desc() {
        return constant_desc;
    }
}
