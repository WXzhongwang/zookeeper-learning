package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 子节点监听
 * @date created on 2019/8/2
 */
public class NodeChildrenListener {

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

        final PathChildrenCache cache = new PathChildrenCache(client,"/Mutex",true);
        cache.start();

        cache.getListenable().addListener(new PathChildrenCacheListener() {
            //监听子节点
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                    throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED:" + event.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED:" + event.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED:" + event.getData());
                        break;
                    default:
                        break;
                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);

    }

}