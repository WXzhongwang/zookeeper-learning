package com.zk.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 订阅数据内容发生变化
 * @date created on 2019/8/2
 */
public class SubscribeDataChanges {
    private static class ZkDataListener  implements IZkDataListener {

        @Override public void handleDataChange(String dataPath, Object data)
                throws Exception {
            System.out.println("节点数据被改变");
            System.out.println(dataPath + ":" + data.toString());
        }

        @Override public void handleDataDeleted(String dataPath) throws Exception {
            System.out.println("节点被删除");
            System.out.println(dataPath);
        }


    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zc = new ZkClient("172.20.13.101:2181",10000,10000,new SerializableSerializer());
        System.out.println("Connected!");


        // 除子节点变化外，节点本身创建和删除也会收到通知
        zc.subscribeDataChanges("/dick", new ZkDataListener());
        Thread.sleep(Integer.MAX_VALUE);

    }
}
