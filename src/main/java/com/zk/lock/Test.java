package com.zk.lock;

import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 测试类
 * @date created on 2019/8/6
 */
public class Test {

    public static void main(String[] args) {

        final ZkClientExt zkClientExt1 = new ZkClientExt("127.0.0.1:2181", 5000, 5000, new BytesPushThroughSerializer());
        final SimpleDistributedLockMutex mutex1 = new SimpleDistributedLockMutex(zkClientExt1, "/Mutex");

        final ZkClientExt zkClientExt2 = new ZkClientExt("127.0.0.1:2181", 5000, 5000, new BytesPushThroughSerializer());
        final SimpleDistributedLockMutex mutex2 = new SimpleDistributedLockMutex(zkClientExt2, "/Mutex");

        try {
            mutex1.acquire();
            System.out.println("Client1 获取锁");
            Thread client2Thd = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        mutex2.acquire();
                        System.out.println("Client2 获取锁");
                        mutex2.release();
                        System.out.println("Client2 释放锁");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client2Thd.start();

            Thread.sleep(5000);
            mutex1.release();
            System.out.println("Client1 释放锁");

            client2Thd.join();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}