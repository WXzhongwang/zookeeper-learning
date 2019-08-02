package com.zk.zkclient;

import com.alibaba.fastjson.JSON;
import com.zk.publishsubscribe.MyZkSerializer;
import com.zk.publishsubscribe.ServerConfig;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description
 * @date created on 2019/8/2
 */
public class GetData {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("172.20.13.101:2181",10000,10000,new MyZkSerializer());
        System.out.println("Connected!");

        Stat stat = new Stat();
        ServerConfig serverConfig = JSON.parseObject(zc.readData("/config", stat).toString(), ServerConfig.class);
        System.out.println(zc.readData("/config", stat));
        System.out.println(serverConfig.toString());
        System.out.println(stat);
    }

}