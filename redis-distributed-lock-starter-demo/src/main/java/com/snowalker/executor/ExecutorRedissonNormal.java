//package com.snowalker.executor;
//
//import com.snowalker.lock.redisson.RedissonLock;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * @author wuwl@19pay.com.cn
// * @date 2018-7-9
// * @desc 纯java调用
// */
//@Component
//public class ExecutorRedissonNormal {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorRedissonNormal.class);
//
//    @Autowired
//    RedissonLock redissonLock;
//
//    @Scheduled(cron = "${redis.lock.cron}")
//    public void execute() throws InterruptedException {
//        if (redissonLock.lock("redisson", 10)) {
//            LOGGER.info("[ExecutorRedisson]--执行定时任务开始，休眠三秒");
//            Thread.sleep(3000);
//            System.out.println("=======================业务逻辑=============================");
//            LOGGER.info("[ExecutorRedisson]--执行定时任务结束，休眠三秒");
//            redissonLock.release("redisson");
//        } else {
//            LOGGER.info("[ExecutorRedisson]获取锁失败");
//        }
//
//    }
//
//}
