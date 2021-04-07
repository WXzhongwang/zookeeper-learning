package com.zk.loadbalance;

import com.zk.loadbalance.client.BalanceProvider;
import com.zk.loadbalance.client.Client;
import com.zk.loadbalance.client.ClientImpl;
import com.zk.loadbalance.client.DefaultBalanceProvider;
import com.zk.loadbalance.server.ServerData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 用于测试, 负责启动Mock, Client
 * @date created on 2019/8/5
 */
public class ClientRunner {

    private static final int CLIENT_QTY = 200;
    private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";
    private static final String SERVERS_PATH = "/servers";

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList<Thread>(CLIENT_QTY);
        final List<Client> clientList = new ArrayList<Client>();
        final BalanceProvider<ServerData> balanceProvider = new DefaultBalanceProvider(ZOOKEEPER_SERVER, SERVERS_PATH);

        try {

            for (int i = 0; i < CLIENT_QTY; i++) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Client client = new ClientImpl(balanceProvider);
                        clientList.add(client);
                        try {
                            client.connect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadList.add(thread);
                thread.start();
                //延时
                Thread.sleep(2000);

            }

            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();


        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            //关闭客户端
            for (int i = 0; i < clientList.size(); i++) {
                try {
                    clientList.get(i);
                    clientList.get(i).disConnect();
                } catch (Exception ignore) {
                    //ignore
                }
            }
            //关闭线程
            for (int i = 0; i < threadList.size(); i++) {
                threadList.get(i).interrupt();
                try {
                    threadList.get(i).join();
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }
}