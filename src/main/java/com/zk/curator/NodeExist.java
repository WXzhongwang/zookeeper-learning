package com.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.*;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 判断节点是否存在
 * @date created on 2019/8/2
 */
public class NodeExist {

    public static void main(String[] args) throws Exception {
        ExecutorService es =
                new ThreadPoolExecutor(6 ,12, 5L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(10),new ThreadPoolExecutor.CallerRunsPolicy());

        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("172.20.13.101:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        client.checkExists().inBackground(new BackgroundCallback() {

            public void processResult(CuratorFramework arg0, CuratorEvent arg1)
                    throws Exception {

                Stat stat = arg1.getStat();
                System.out.println(stat);
                System.out.println(arg1.getContext());

            }
        },"123", es).forPath("/dick");

        Thread.sleep(Integer.MAX_VALUE);

    }

}