package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 更新节点数据
 * @date created on 2019/8/2
 */
public class UpdateData {

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

        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/dick");

        client.setData().withVersion(stat.getVersion()).forPath("/dick", "dccc".getBytes());


    }

}