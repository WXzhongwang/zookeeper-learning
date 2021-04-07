package com.zk.loadbalance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 默认注册Provider
 * @date created on 2019/8/5
 */
public class DefaultRegistProvider implements RegistProvider {


    @Override
    public void regist(Object context) throws Exception {
        // 在zookeeper中创建临时节点并写入信息
        // Server在zookeeper中注册自己，需要在zookeeper的目标节点上创建临时节点并写入自己
        // 将需要的以下3个信息包装成上下文传入
        // 1:path
        // 2:zkClient
        // 3:serverData

        ZooKeeperRegistContext registContext = (ZooKeeperRegistContext) context;
        String path = registContext.getPath();
        ZkClient zc = registContext.getZkClient();

        try {
            zc.createEphemeral(path, registContext.getData());
        } catch (ZkNoNodeException e) {
            String parentDir = path.substring(0, path.lastIndexOf('/'));
            zc.createPersistent(parentDir, true);
            regist(registContext);
        }
    }

    @Override
    public void unRegist(Object context) throws Exception {
        return;
    }

}