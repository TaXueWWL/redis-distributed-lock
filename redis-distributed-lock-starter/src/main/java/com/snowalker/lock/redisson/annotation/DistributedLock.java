package com.snowalker.lock.redisson.annotation;

import java.lang.annotation.*;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc Redisson分布式锁注解
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DistributedLock {

    /**分布式锁名称*/
    String value() default "distributed-lock-redisson";
    /**锁超时时间,默认十秒*/
    int expireSeconds() default 10;
}


