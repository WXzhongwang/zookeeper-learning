# zookeeper-learning
# Zookeeper分布式协调服务

分布式特点：
- 凡是分布式就有路由
- 凡是路由就有负载
- 凡是有负载就一定会有宕机
- 凡是分布式就有链接
- 凡是链接就有安全

分布式、开源、分布式应用程序协调服务。包括配置维护、域名服务、分布式同步、组服务等。

## 单机模式：
Mode: standalone
C/S架构
Znode 称为zk节点数据

## 对节点基本操作
create /monkey xxx 创建节点
set /monkey xxxxa  更新节点
get /zk_test     获取节点
delete /zk_test 一般删除（若里面不为空，无法删除）
rmr /zk_test 递归删除

节点类型：
- 持久节点
- 持久有序
- 临时节点
- 临时有序

## watcher(监听作用)
get /zk_test watch 监听节点数据，一旦发生改变，可以通知


## 源码分析
jps 查看进程号 ===》 查看方法名
```
C:\Users\Administrator>jps
1748 Bootstrap
4788 QuorumPeerMain
2424 ZooKeeperMain
3116 Jps
```

ZooKeeperMain客户端入口
QuorumPeerMain服务端源码

通过源码发现：
help 帮助
history 历史

create -s /monkey xxxxx


- 持久: 当客户端断开时，znode节点不会被删除
- 临时: 当客户端断开时，znode节点自动删除

利用Watch机制实现  》》》 分布式配置中心

利用临时有序节点（失效删除，自增等特点）  》》》分布式任务调度

》》》分布式服务注册与订阅


znode节点属性：
cZxid = 0x2
ctime = Tue Jun 04 23:21:32 CST 2019
mZxid = 0x2
mtime = Tue Jun 04 23:21:32 CST 2019
pZxid = 0xa
cversion = 1
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 0
numChildren = 1

## 集群模式 （选举）
zk一般部署奇数个

zoo.cfg文件修改，2888端口，添加集群机器
server.1=196.128.3.1:2888:3888
server.2=196.128.3.2:2888:3888
server.3=196.128.3.3:2888:3888

选举模式 ==> 过半选举（过半原则）
Mode: leader
Mode: follower


查看状态：
sh zkServer.sh status






