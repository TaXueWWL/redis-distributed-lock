package com.snowalker.annotation;

import java.lang.annotation.*;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-7-9
 * @desc
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RedisLock {

    String value() default "redis-lock";
}
