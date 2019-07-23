package com.xxxx.zk_demo.service;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-07-23 16:52
 */
@Component
public class ZKCustor {
    private CuratorFramework client = null;

    final static Logger log = LoggerFactory.getLogger(ZKCustor.class);

    public static final  String ZOOKEEPER_SERVER = "192.168.1.123:2181";

    public void init() {
        if (client != null) {
            return;
        }

        //创建重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

        //创建zookeeper客户端
        client = CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_SERVER)
                .sessionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .namespace("admin")
                .build();

        client.start();

        try {
            if (client.checkExists().forPath("/bgm") == null) {
                /**
                 * @Description: zk有两种节点
                 * @date 2019/1/18 14:07
                 * @params  * @param 持久节点,创建之后 节点会永远存在 除非你手动删除
                 *                   临时节点 会话断开 自动删除
                 */
                client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath("/bgm");
                log.info("zookeeper初始化成功");
            }
        } catch (Exception e) {
            log.error("zookeeper初始化失败");
            e.printStackTrace();
        }
    }
}
