package com.snowalker.executor;

import com.snowalker.lock.redisson.annotation.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-7-9
 * @desc 纯java调用
 */
@Component
public class ExecutorRedissonAnnotation {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorRedissonAnnotation.class);

    @Scheduled(cron = "${redis.lock.cron}")
    @DistributedLock(value = "redis-lock", expireSeconds = 11)
    public void execute() throws InterruptedException {
        LOGGER.info("[ExecutorRedisson]--执行定时任务开始，休眠三秒");
        Thread.sleep(3000);
        System.out.println("=======================业务逻辑=============================");
        LOGGER.info("[ExecutorRedisson]--执行定时任务结束，休眠三秒");
    }
}
