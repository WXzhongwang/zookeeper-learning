package com.zk.loadbalance.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description Client需要ClientHandler来处理与服务器之前的通讯，同时它需要BalanceProvider为它提供负载均衡的算法。
 * @date created on 2019/8/5
 */
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}