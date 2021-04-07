package com.zk.loadbalance.server;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 负载更新接口
 * @date created on 2019/8/5
 */
public interface BalanceUpdateProvider {

    boolean addBalance(Integer step);

    boolean reduceBalance(Integer step);
}
