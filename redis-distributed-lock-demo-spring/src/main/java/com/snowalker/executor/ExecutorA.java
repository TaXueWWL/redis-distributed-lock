package com.snowalker.executor;

import com.snowalker.annotation.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-7-9
 * @desc 分布式锁注解支持
 */
@Component
public class ExecutorA {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorA.class);

    @Scheduled(cron = "${redis.lock.cron}")
    @RedisLock(value = "executor")
    public void execute() throws InterruptedException {
        LOGGER.info("[ExecutorA]--执行定时任务开始，休眠三秒");
        Thread.sleep(3000);
        System.out.println("====================================================");
        LOGGER.info("[ExecutorA]--执行定时任务结束，休眠三秒");
    }

}
