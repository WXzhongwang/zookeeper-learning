package com.zk.loadbalance.client;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description BalanceProvider是接口，它有2个实现类，一个是抽象的实现AbstractBalanceProvider，一个是默认的实现DefaultBalanceProvider。
 * @date created on 2019/8/5
 */
public interface BalanceProvider<T> {

    T getBalanceItem();

}