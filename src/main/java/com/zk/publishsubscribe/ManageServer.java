package com.zk.publishsubscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.List;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 管理服务器
 * @date created on 2019/8/2
 */
public class ManageServer {

    // zookeeper的servers节点路径
    private String serversPath;

    // zookeeper的command节点路径
    private String commandPath;

    // zookeeper的config节点路径
    private String configPath;

    private ZkClient zkClient;

    private ServerConfig config;

    // 用于监听servers节点的子节点列表的变化
    private IZkChildListener childListener;

    // 用于监听command节点数据内容的变化
    private IZkDataListener dataListener;

    // 工作服务器的列表
    private List<String> workServerList;


    public ManageServer(String serversPath, String commandPath,
                        String configPath, ZkClient zkClient, ServerConfig config) {
        this.serversPath = serversPath;
        this.commandPath = commandPath;
        this.zkClient = zkClient;
        this.config = config;
        this.configPath = configPath;

        //监听子节点（服务器节点数量的变更）
        this.childListener = new IZkChildListener() {

            public void handleChildChange(String parentPath,
                                          List<String> currentChilds) throws Exception {
                // TODO Auto-generated method stub
                workServerList = currentChilds; // 更新内存中工作服务器列表

                System.out.println("工作节点列表变更，新的节点是：");
                execList();

            }
        };

        //监听配置节点
        this.dataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // TODO Auto-generated method stub
                // ignore;
            }

            @Override
            public void handleDataChange(String dataPath, Object data)
                    throws Exception {
                // TODO Auto-generated method stub
                String cmd = new String((byte[]) data);
                System.out.println("cmd:" + cmd);
                exeCmd(cmd);
                // 执行命令

            }
        };

    }

    private void initRunning() {
        //监听数据变更
        zkClient.subscribeDataChanges(commandPath, dataListener);
        //监听子节点列表
        zkClient.subscribeChildChanges(serversPath, childListener);
    }


    private void exeCmd(String cmdType) {
        if ("list".equals(cmdType)) {
            execList();

        } else if ("create".equals(cmdType)) {
            execCreate();
        } else if ("modify".equals(cmdType)) {
            execModify();
        } else {
            System.out.println("error command!" + cmdType);
        }

    }

    private void execList() {
        System.out.println(workServerList.toString());
    }

    private void execCreate() {
        if (!zkClient.exists(configPath)) {
            try {
                zkClient.createPersistent(configPath, JSON.toJSONString(config)
                        .getBytes());
            } catch (ZkNodeExistsException e) {
                zkClient.writeData(configPath, JSON.toJSONString(config)
                        .getBytes());
                // config节点已经存在，则写入内容就可以了
            } catch (ZkNoNodeException e) {
                String parentDir = configPath.substring(0,
                        configPath.lastIndexOf('/'));
                zkClient.createPersistent(parentDir, true);
                execCreate();
            }
        }
    }

    private void execModify() {
        // 我们随意修改config的一个属性就可以了
        config.setDbUser(config.getDbUser() + "_modify");

        try {
            zkClient.writeData(configPath, JSON.toJSONString(config).getBytes());
        } catch (ZkNoNodeException e) {
            execCreate();
            // 写入时config节点还未存在，则创建它
        }
    }

    public void start() {
        initRunning();
    }

    public void stop() {
        zkClient.unsubscribeChildChanges(serversPath, childListener);
        zkClient.unsubscribeDataChanges(commandPath, dataListener);
    }

}