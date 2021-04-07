package com.zk.loadbalance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 默认BalanceUpdateProvider
 * @date created on 2019/8/5
 */
public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {

    private String serverPath;
    private ZkClient zc;

    public DefaultBalanceUpdateProvider(String serverPath, ZkClient zkClient) {
        this.serverPath = serverPath;
        this.zc = zkClient;
    }

    @Override
    public boolean addBalance(Integer step) {
        Stat stat = new Stat();
        ServerData sd;

        // 增加负载：读取服务器的信息ServerData，增加负载，并写回zookeeper
        while (true) {
            try {
                sd = zc.readData(this.serverPath, stat);
                sd.setBalance(sd.getBalance() + step);
                // 带上版本
                zc.writeData(this.serverPath, sd, stat.getVersion());
                return true;
            } catch (ZkBadVersionException e) {
                // ignore
            } catch (Exception e) {
                return false;
            }
        }

    }

    @Override
    public boolean reduceBalance(Integer step) {
        Stat stat = new Stat();
        ServerData sd;

        while (true) {
            try {
                sd = zc.readData(this.serverPath, stat);
                final Integer currBalance = sd.getBalance();
                sd.setBalance(currBalance>step?currBalance-step:0);
                zc.writeData(this.serverPath, sd, stat.getVersion());
                return true;
            } catch (ZkBadVersionException e) {
                // ignore
            } catch (Exception e) {
                return false;
            }
        }
    }

}