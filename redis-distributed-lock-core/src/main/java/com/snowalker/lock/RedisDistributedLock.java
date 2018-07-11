package com.snowalker.lock;

import com.snowalker.config.RedisPoolUtil;
import com.snowalker.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-7-9
 * @desc
 */
public class RedisDistributedLock implements DistributedLock {

    /**默认锁超时时间为10S*/
    private static final int EXPIRE_SECONDS = 50;
    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);

    private RedisDistributedLock() {
    }

    private volatile static RedisDistributedLock redisDistributedLock;

    public static RedisDistributedLock getInstance() {
        if (redisDistributedLock == null) {
            synchronized (RedisDistributedLock.class) {
                redisDistributedLock = new RedisDistributedLock();
            }
        }
        return redisDistributedLock;
    }

    /**
     * 加锁
     *
     * @param lockName
     * @return 返回true表示加锁成功，执行业务逻辑，执行完毕需要主动释放锁，否则就需要等待锁超时重新争抢
     * 返回false标识加锁失败，阻塞并继续尝试获取锁
     */
    @Override
    public boolean lock(String lockName) {
        /**1.使用setNx开始加锁*/
        log.info("开始获取Redis分布式锁流程,lockName={},CurrentThreadName={}", lockName, Thread.currentThread().getName());
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("redis.lock.timeout", "5"));
        /**redis中锁的值为:当前时间+超时时间*/
        Long lockResult = RedisPoolUtil.setnx(lockName, String.valueOf(System.currentTimeMillis() + lockTimeout));

        if (lockResult != null && lockResult.intValue() == 1) {
            log.info("setNx获取分布式锁[成功],threadName={}", Thread.currentThread().getName());
            RedisPoolUtil.expire(lockName, EXPIRE_SECONDS);
            return true;
        } else {
            log.info("setNx获取分布式锁[失败],threadName={}", Thread.currentThread().getName());
//            return tryLock(lockName, lockTimeout);
            return false;
        }
    }

    private boolean tryLock(String lockName, long lockTimeout) {
        /**
         * 2.加锁失败后再次尝试
         * 2.1获取锁失败，继续判断，判断时间戳，看是否可以重置并获取到锁
         *    setNx结果小于当前时间，表明锁已过期，可以再次尝试加锁
         */
        String lockValueStr = RedisPoolUtil.get(lockName);
        Long lockValueATime = Long.parseLong(lockValueStr);
        log.info("lockValueATime为:" + lockValueATime);
        if (lockValueStr != null && lockValueATime < System.currentTimeMillis()) {

            /**2.2再次用当前时间戳getset--->将给定 key 的值设为 value，并返回 key 的旧值(old value)
             * 通过getset重设锁对应的值: 新的当前时间+超时时间，并返回旧的锁对应值
             */
            String getSetResult = RedisPoolUtil.getSet(lockName, String.valueOf(System.currentTimeMillis() + lockTimeout));
            log.info("lockValueBTime为:" + Long.parseLong(getSetResult));
            if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
                /**
                 *2.3旧值判断，是否可以获取锁
                 *当key没有旧值时，即key不存在时，返回nil ->获取锁，设置锁过期时间
                 */
                log.info("获取Redis分布式锁[成功],lockName={},CurrentThreadName={}",
                        lockName, Thread.currentThread().getName());
                RedisPoolUtil.expire(lockName, EXPIRE_SECONDS);
                return true;
            } else {
                log.info("获取锁失败,lockName={},CurrentThreadName={}",
                        lockName, Thread.currentThread().getName());
                return false;
            }
        } else {
            /**3.锁未超时，获取锁失败*/
            log.info("当前锁未失效！！！！，竞争失败，继续持有之前的锁,lockName={},CurrentThreadName={}",
                    lockName, Thread.currentThread().getName());
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param lockName
     */
    @Override
    public boolean release(String lockName) {
        Long result = RedisPoolUtil.del(lockName);
        if (result != null && result.intValue() == 1) {
            log.info("删除Redis分布式锁成功，锁已释放, key= :{}", lockName);
            return true;
        }
        log.info("删除Redis分布式锁失败，锁未释放, key= :{}", lockName);
        return false;
    }
}
