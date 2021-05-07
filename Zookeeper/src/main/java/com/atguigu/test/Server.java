package com.atguigu.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String str = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
        int sessionTime=4000;
        ZooKeeper zk =new ZooKeeper(str, sessionTime, new Watcher() {
            public void process(WatchedEvent event) {

            }
        });
        Stat exists = zk.exists("/parent", false);
        if (exists == null){
            zk.create("/parent","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

            zk.create("/parent/"+args[0],args[1].getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            Thread.sleep(Long.MAX_VALUE);
    }
}
