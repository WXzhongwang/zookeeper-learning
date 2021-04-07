package com.zk.loadbalance.client;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 客户端
 * @date created on 2019/8/5
 */
public interface Client {

    void connect() throws Exception;

    void disConnect() throws Exception;

}