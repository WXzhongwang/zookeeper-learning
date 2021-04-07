package com.zk.loadbalance.server;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 每个服务端对应一个Server接口，ServiceImpl是服务端的实现类。
 * @date created on 2019/8/5
 */
public interface Server {

    public void bind();

}
