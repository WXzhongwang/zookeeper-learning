package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

import java.util.List;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 获取子节点
 * @date created on 2019/8/2
 */
public class GetChildren {

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

        List<String> cList = client.getChildren().forPath("/dick");

        System.out.println(cList.toString());

    }

}
