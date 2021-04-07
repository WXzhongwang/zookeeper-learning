package com.zk.queue;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 阻塞型分布式队列
 * @date created on 2019/8/6
 */
public class DistributedBlockingQueue<T> extends DistributedSimpleQueue<T> {

    public DistributedBlockingQueue(ZkClient zkClient, String root) {
        super(zkClient, root);
    }

    @Override
    public T poll() throws Exception {
        while (true) {
            // 结束在latch上的等待后，再来一次
            final CountDownLatch latch = new CountDownLatch(1);
            final IZkChildListener childListener = new IZkChildListener() {
                @Override
                public void handleChildChange(String s, List<String> list) throws Exception {
                    // 队列有变化，结束latch上的等待
                    latch.countDown();
                }

            };
            zkClient.subscribeChildChanges(root, childListener);
            try {
                // 获取队列数据
                T node = super.poll();

                if ( node != null ){
                    return node;
                } else {
                    // 拿不到队列数据，则在latch上await
                    latch.await();
                }
            } finally {
                zkClient.unsubscribeChildChanges(root, childListener);
            }
        }
    }
}
