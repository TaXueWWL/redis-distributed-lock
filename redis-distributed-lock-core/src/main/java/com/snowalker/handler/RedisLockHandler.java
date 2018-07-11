package com.snowalker.handler;

import com.snowalker.annotation.RedisLock;
import com.snowalker.lock.RedisDistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-7-9
 * @desc Redis分布式锁注解解析器
 */
@Aspect
@Component
public class  RedisLockHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockHandler.class);

    @Pointcut("@annotation(com.snowalker.annotation.RedisLock)")
    public void redisLock() {}

    @Around("@annotation(redisLock)")
    public void around(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        LOGGER.info("[开始]执行RedisLock环绕通知,获取Redis分布式锁开始");
        String lockName = redisLock.value();
        RedisDistributedLock redisDistributedLock = RedisDistributedLock.getInstance();
        if (redisDistributedLock.lock(lockName)) {
            try {
                LOGGER.info("获取Redis分布式锁[成功]");
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            LOGGER.info("释放Redis分布式锁[成功]");
            redisDistributedLock.release(lockName);
        } else {
            LOGGER.error("获取Redis分布式锁[失败]");
        }
        LOGGER.error("[结束]执行RedisLock环绕通知");
    }
}
