package com.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description
 * @date created on 2019/8/2
 */
public class NodeExists {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("172.20.13.101:2181",10000,10000,new SerializableSerializer());
        System.out.println("Connected!");


        boolean e = zc.exists("/dick");
        System.out.println(e);
    }

}