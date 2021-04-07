package com.zk.loadbalance.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 处理服务端与客户端之间的通信
 * @date created on 2019/8/5
 */
public class ServerHandler extends ChannelHandlerAdapter {

    private final BalanceUpdateProvider balanceUpdater;
    private static final Integer BALANCE_STEP = 1;


    public ServerHandler(BalanceUpdateProvider balanceUpdater){
        this.balanceUpdater = balanceUpdater;

    }

    public BalanceUpdateProvider getBalanceUpdater() {
        return balanceUpdater;
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client connect...");
        balanceUpdater.addBalance(BALANCE_STEP);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        balanceUpdater.reduceBalance(BALANCE_STEP);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}