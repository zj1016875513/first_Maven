package com.atguigu.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class Client {
    public static ZooKeeper zk;
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String str ="hadoop102:2181,hadoop103:2181,hadoop104:2181";
        int time=4000;
         zk =new ZooKeeper(str, time, new Watcher() {
            public void process(WatchedEvent event) {

            }
        });
        Stat exists = zk.exists("/parent", false);
        if (exists == null){
            zk.create("/parent","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
        listen();
        Thread.sleep(Long.MAX_VALUE);
    }
    public static void listen() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/parent", new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    listen();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (String child : children) {
            System.out.println(child+"online");
        }
    }
}