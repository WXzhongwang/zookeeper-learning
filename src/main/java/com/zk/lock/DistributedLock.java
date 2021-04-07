package com.zk.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 分布式锁接口
 * @date created on 2019/8/6
 */
public interface DistributedLock {

    /**
     * 获取锁，如果没有得到就等待
     * @throws Exception
     */
    void acquire() throws Exception;


    /**
     * 获取锁，直到超时
     * @param time 时间long
     * @param unit 单位
     * @return 是否拿到锁
     * @throws Exception
     */
    boolean acquire(long time, TimeUnit unit) throws Exception;


    /**
     * 释放锁
     * @throws Exception
     */
    void release() throws Exception;

}