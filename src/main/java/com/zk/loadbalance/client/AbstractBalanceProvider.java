package com.zk.loadbalance.client;

import java.util.List;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 抽象负载Provider
 * @date created on 2019/8/5
 */
public abstract class AbstractBalanceProvider<T> implements BalanceProvider<T> {

    protected abstract T balanceAlgorithm(List<T> items);

    protected abstract List<T> getBalanceItems();

    @Override
    public T getBalanceItem() {
        return balanceAlgorithm(getBalanceItems());
    }

}