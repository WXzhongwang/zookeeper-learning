package com.zk.lock;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 简单的分布式互斥锁
 * @date created on 2019/8/6
 */
public class SimpleDistributedLockMutex extends BaseDistributedLock implements
        DistributedLock {

    /**
     * 锁名称前缀
     * 顺序节点如lock-0000000000,lock-0000000001,...
     */
    private static final String LOCK_NAME = "lock-";


    /**
     * zookeeper中locker节点的路径
     */
    private final String basePath;


    /**
     * 获取锁以后自己创建的那个顺序节点的路径
     */
    private String ourLockPath;


    public SimpleDistributedLockMutex(ZkClientExt client, String basePath) {
        super(client, basePath, LOCK_NAME);
        this.basePath = basePath;

    }


    private boolean internalLock(long time, TimeUnit unit) throws Exception {
        ourLockPath = attemptLock(time, unit);
        return ourLockPath != null;
    }

    /**
     * 获取锁
     */
    @Override
    public void acquire() throws Exception {
        if (!internalLock(-1, null)) {
            throw new IOException("连接丢失!在路径:'" + basePath + "'下不能获取锁!");
        }
    }

    /**
     * 获取锁，可以超时
     */
    @Override
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        return internalLock(time, unit);
    }

    /**
     * 释放锁
     */
    @Override
    public void release() throws Exception {
        releaseLock(ourLockPath);
    }


}