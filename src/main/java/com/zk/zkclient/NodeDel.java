package com.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description
 * @date created on 2019/8/2
 */
public class NodeDel {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("172.20.13.101:2181",10000,10000,new SerializableSerializer());
        System.out.println("Connected!");

        boolean e1 = zc.delete("/dick");
        boolean e2 = zc.deleteRecursive("/dick");
        System.out.println(e1);
        System.out.println(e2);
    }

}
