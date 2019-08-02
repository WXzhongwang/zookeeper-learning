package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 删除节点
 * @date created on 2019/8/2
 */
public class DelNode {

    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("172.20.13.101:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        client.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .withVersion(-1)
                .forPath("/dick/1");

        Thread.sleep(Integer.MAX_VALUE);

    }

}