package com.snowalker.lock;

/**
 * @author snowalker
 * @date 2018-7-9
 * @desc Redis分布式锁接口声明
 */
public interface DistributedLock {

    /**
     * 加锁
     * @param lockName
     * @return
     */
    boolean lock(String lockName);

    /**
     * 解锁
     * @param lockName
     */
    boolean release(String lockName);
}
