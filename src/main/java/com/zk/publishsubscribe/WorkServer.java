package com.zk.publishsubscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 工作服务器
 * @date created on 2019/8/2
 */
public class WorkServer {

    private ZkClient zkClient;

    private String configPath;

    private String serversPath;

    private ServerData serverData;

    private ServerConfig serverConfig;

    private IZkDataListener dataListener;

    public WorkServer(String configPath, String serversPath,
                      ServerData serverData, ZkClient zkClient, ServerConfig initConfig) {
        this.zkClient = zkClient;
        this.serversPath = serversPath;
        this.configPath = configPath;
        this.serverConfig = initConfig;
        this.serverData = serverData;

        //监听配置变更
        this.dataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }

            @Override
            public void handleDataChange(String dataPath, Object data)
                    throws Exception {
                String retJson = new String((byte[]) data);
                ServerConfig serverConfigLocal = (ServerConfig) JSON.parseObject(retJson, ServerConfig.class);
                updateConfig(serverConfigLocal);
                System.out.println("配置发生更改:" + serverConfig.toString());

            }
        };

    }

    public void start() {
        System.out.println("work server start...");
        initRunning();
    }

    public void stop() {
        System.out.println("work server stop...");
        zkClient.unsubscribeDataChanges(configPath, dataListener);
        // 取消监听config节点
    }


    private void initRunning() {
        // 注册自己
        registMe();
        zkClient.subscribeDataChanges(configPath, dataListener);
        // 订阅config节点的改变事件
    }


    private void registMe() {
        String mePath = serversPath.concat("/").concat(serverData.getAddress());

        try {
            System.out.println("zk注册：" + serverData.toString());
            zkClient.createEphemeral(mePath, JSON.toJSONString(serverData)
                    .getBytes());
        } catch (ZkNoNodeException e) {
            zkClient.createPersistent(serversPath, true);
            registMe();
        }
    }

    private void updateConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

}
