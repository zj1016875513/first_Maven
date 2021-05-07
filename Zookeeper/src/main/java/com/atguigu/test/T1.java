package com.atguigu.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class T1 {
    private ZooKeeper zk;
    @Before
    public void Befor() throws IOException {
        String cluster = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
        int sessionTimeout = 4000;
         zk = new ZooKeeper(cluster, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {

            }
        });
    }
    @After
    public void After(){
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void T01() throws KeeperException, InterruptedException {
        zk.create("/shuihu","abc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void T02() throws KeeperException, InterruptedException {
        lisening();
        Thread.sleep(Long.MAX_VALUE);
    }
    public void lisening() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/sanguo", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("节点发生变化了");
                try {
                    lisening();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (String child : children) {
            System.out.println(child);
        }
        }

        @Test
    public void T03() throws KeeperException, InterruptedException {
            Stat exists = zk.exists("/sanguo", false);
            System.out.println(exists!=null?"存在":"不存在");
        }

}
