package com.zk.publishsubscribe;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 服务器基本信息
 * @date created on 2019/8/2
 */
public class ServerData {

    private String address;
    private Integer id;
    private String name;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerData [address=" + address + ", id=" + id + ", name="
                + name + "]";
    }
}
