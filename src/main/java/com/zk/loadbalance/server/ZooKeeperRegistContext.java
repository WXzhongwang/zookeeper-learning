package com.zk.loadbalance.server;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 上下文的类ZooKeeperRegistContext
 * @date created on 2019/8/5
 */
public class ZooKeeperRegistContext {

    private String path;
    private ZkClient zkClient;
    private Object data;

    public ZooKeeperRegistContext(String path, ZkClient zkClient, Object data) {
        super();
        this.path = path;
        this.zkClient = zkClient;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}