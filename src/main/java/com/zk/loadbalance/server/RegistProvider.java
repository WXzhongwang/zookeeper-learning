package com.zk.loadbalance.server;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 把服务端启动时的注册过程抽出为一个接口RegistProvider，并给予一个默认实现DefaultRegistProvider
 * @date created on 2019/8/5
 */
public interface RegistProvider {

    void regist(Object context) throws Exception;

    void unRegist(Object context) throws Exception;

}