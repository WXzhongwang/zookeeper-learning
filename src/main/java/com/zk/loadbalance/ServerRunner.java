package com.zk.loadbalance;

import com.zk.loadbalance.server.Server;
import com.zk.loadbalance.server.ServerData;
import com.zk.loadbalance.server.ServerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 用于测试, 负责启动Work Server
 * @date created on 2019/8/5
 */
public class ServerRunner {

    private static final int SERVER_QTY = 5;
    private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";
    private static final String SERVERS_PATH = "/servers";

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < SERVER_QTY; i++) {

            final Integer count = i;
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    ServerData sd = new ServerData();
                    sd.setBalance(0);
                    sd.setHost("127.0.0.1");
                    sd.setPort(6000 + count);
                    Server server = new ServerImpl(ZOOKEEPER_SERVER, SERVERS_PATH, sd);
                    server.bind();
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for (int i = 0; i < threadList.size(); i++) {
            try {
                threadList.get(i).join();
            } catch (InterruptedException ignore) {
                //
            }

        }
    }

}