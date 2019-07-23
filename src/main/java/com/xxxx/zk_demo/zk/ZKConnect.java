package com.xxxx.zk_demo.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-07-22 15:37
 */
public class ZKConnect implements Watcher {

    final static Logger log = LoggerFactory.getLogger(ZKConnect.class);

    public static final String zkServerPath = "106.14.148.237:2181";
//    public static final String zkServerPath = "106.14.148.237:2181,106.14.148.237:2182,106.14.148.237:2183";

    public static final Integer timeout = 5000;

    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * 客户端和zk1服务端连接是一个异步的过程
         * 当连接成功之后，客户端会收到一个watch通知
         *
         * 参数：
         * connectString： 连接服务器的ip字符串
         * sessionTimeout：超时时间，心跳收不到了，那就超时
         * watcher：通知事件，如果有对应的事件触发，则会收到一个通知：如果不需要，那就设置为null
         * canBeReadOnly：可读，当这个物理机节点断开后，还是可以读到数据的，只是不能写，
         *                  此时数据被读取到的可能是旧数据，此处建议设置为false，不推荐使用
         * sessionId：会话的id
         * sessionPasswd：会话密码   当会话丢失后，可以依据sessionId和sessionPasswd重新获取会话
         */
        ZooKeeper zk = new ZooKeeper(zkServerPath, timeout, new ZKConnect());

        long sessionId = zk.getSessionId();
        byte[] sessionPasswd = zk.getSessionPasswd();

        log.warn("客户端开始连接zookeeper服务器......");
        log.warn("连接状态: {}", zk.getState());

        TimeUnit.SECONDS.sleep(1);

        log.warn("连接状态: {}", zk.getState());

        TimeUnit.MILLISECONDS.sleep(200);

        // 开始会话重连
        log.warn("开始会话重连......");

        ZooKeeper zkSession = new ZooKeeper(zkServerPath, timeout, new ZKConnect(), sessionId, sessionPasswd);

        log.warn("重新连接状态zkSession: {}", zkSession.getState());
        TimeUnit.SECONDS.sleep(1);
        log.warn("重新连接状态zkSession: {}", zkSession.getState());
    }

    public void process(WatchedEvent watchedEvent) {
        log.warn("接收到watch通知: {}", watchedEvent);
    }
}
