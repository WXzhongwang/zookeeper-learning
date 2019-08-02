package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 创建节点
 * @date created on 2019/8/2
 */
public class CreateNode {

    public static void main(String[] args) {
        try {
            RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
            CuratorFramework client = CuratorFrameworkFactory
                    .builder()
                    .connectString("172.20.13.101:2181")
                    .sessionTimeoutMs(5000)
                    .connectionTimeoutMs(5000)
                    .retryPolicy(retryPolicy)
                    .build();

            client.start();

            System.out.println("创建节点");
            String path = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/dick/2", "123".getBytes());

            System.out.println("创建节点成功" + path);

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception ex) {

        }
    }
}
