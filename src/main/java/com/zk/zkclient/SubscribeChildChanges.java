package com.zk.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 订阅子节点列表变化
 * @date created on 2019/8/2
 */
public class SubscribeChildChanges {

    public static class ZkListener implements IZkChildListener{

        /*
        * 监听节点的子节点， 自身（创建，删除）也会受到通知
        * */
        @Override public void handleChildChange(String parentPath,
                                      List<String> currentChilds) throws Exception {
            System.out.println("Node Change");
            System.out.println(parentPath);
            System.out.println(currentChilds.toString());

        }

    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zc = new ZkClient("172.20.13.101:2181",10000,10000,new SerializableSerializer());
        System.out.println("Connected!");

        // 除子节点变化外，节点本身创建和删除也会收到通知
        zc.subscribeChildChanges("/servers", new ZkListener());
        Thread.sleep(Integer.MAX_VALUE);

    }
}
