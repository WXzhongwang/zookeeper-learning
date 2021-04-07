package com.zk.loadbalance.server;

import java.io.Serializable;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 服务端代码
 * @date created on 2019/8/5
 */
public class ServerData implements Serializable,Comparable<ServerData> {

    private static final long serialVersionUID = -8892569870391530906L;


    private Integer balance;
    private String host;
    private Integer port;


    public Integer getBalance() {
        return balance;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }


    @Override
    public String toString() {
        return "ServerData [balance=" + balance + ", host=" + host + ", port="
                + port + "]";
    }

    @Override
    public int compareTo(ServerData o) {
        return this.getBalance().compareTo(o.getBalance());
    }

}