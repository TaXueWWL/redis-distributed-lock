package com.snowalker.executor;

import com.snowalker.annotation.RedisLock;
import com.snowalker.lock.RedisDistributedLock;
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
public class ExecutorB {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorB.class);

    @Scheduled(cron = "${redis.lock.cron}")
    public void execute() throws InterruptedException {
        RedisDistributedLock lock = RedisDistributedLock.getInstance();
        if (lock.lock("AAAAAAA")) {
            LOGGER.info("[ExecutorB]--执行定时任务开始，休眠三秒");
            Thread.sleep(3000);
            System.out.println("=======================业务逻辑=============================");
            LOGGER.info("[ExecutorB]--执行定时任务结束，休眠三秒");
            lock.release("AAAAAAA");
        } else {
            LOGGER.info("【ExecutorB】获取锁失败");
        }

    }

}
