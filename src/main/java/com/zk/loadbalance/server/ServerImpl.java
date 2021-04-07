package com.zk.loadbalance.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 每个服务端对应一个Server接口，ServiceImpl是服务端的实现类。
 * @date created on 2019/8/5
 */
public class ServerImpl implements Server {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ServerBootstrap bootStrap = new ServerBootstrap();

    private ChannelFuture cf;
    private String zkAddress;
    private String serversPath;
    private String currentServerPath;
    private ServerData sd;

    private volatile boolean binded = false;

    private final ZkClient zc;
    private final RegistProvider registProvider;

    private static final Integer SESSION_TIME_OUT = 10000;
    private static final Integer CONNECT_TIME_OUT = 10000;


    public String getCurrentServerPath() {
        return currentServerPath;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public String getServersPath() {
        return serversPath;
    }

    public ServerData getSd() {
        return sd;
    }

    public void setSd(ServerData sd) {
        this.sd = sd;
    }

    public ServerImpl(String zkAddress, String serversPath, ServerData sd) {
        this.zkAddress = zkAddress;
        this.serversPath = serversPath;
        this.zc = new ZkClient(this.zkAddress, SESSION_TIME_OUT, CONNECT_TIME_OUT, new SerializableSerializer());
        this.registProvider = new DefaultRegistProvider();
        this.sd = sd;
    }


    private void initRunning() throws Exception {
        //初始化服务端
        String mePath = serversPath.concat("/").concat(sd.getPort().toString());
        //注册到zookeeper
        registProvider.regist(new ZooKeeperRegistContext(mePath, zc, sd));
        currentServerPath = mePath;
    }


    @Override
    public void bind() {
        if (binded) {
            return;
        }

        System.out.println(sd.getPort() + ":绑定中");

        try {
            initRunning();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        bootStrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerHandler(new DefaultBalanceUpdateProvider(currentServerPath, zc)));
                    }
                });

        try {
            cf = bootStrap.bind(sd.getPort()).sync();
            binded = true;
            System.out.println(sd.getPort() + ": 绑定成功...");
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}