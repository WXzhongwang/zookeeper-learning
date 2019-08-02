package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 节点监听
 * @date created on 2019/8/2
 */
public class NodeListener {

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

        final NodeCache cache = new NodeCache(client,"/dick");
        cache.start();

        cache.getListenable().addListener(new NodeCacheListener() {

            //监听节点
            public void nodeChanged() throws Exception {
                byte[] ret = cache.getCurrentData().getData();
                System.out.println("new data:"+ new String(ret));
            }
        });

        Thread.sleep(Integer.MAX_VALUE);

    }

}