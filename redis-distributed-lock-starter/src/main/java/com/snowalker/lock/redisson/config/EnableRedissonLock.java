package com.snowalker.lock.redisson.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author snowalker
 * @date 2018/7/10
 * @desc 开启Redisson注解支持
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedissonAutoConfiguration.class)
public @interface EnableRedissonLock {
}
