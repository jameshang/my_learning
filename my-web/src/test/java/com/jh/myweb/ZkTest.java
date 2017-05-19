package com.jh.myweb;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jh.myweb.zk.ZkWatcher;

@Ignore
public class ZkTest {

	private static Logger log = LoggerFactory.getLogger(ZkTest.class);

	@Test
	public void testFoo1() {
		ZooKeeper zk = null;
		try {
			Watcher w = new ZkWatcher();
			zk = new ZooKeeper("192.168.1.190:2183,192.168.1.190:2182,192.168.1.190:2183", 3600000, w);
			if (zk.exists("/test1", false) == null) {
				zk.create("/test1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			} else {
				log.info("Node exists!");
			}
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zk != null && zk.getState().isConnected()) {
				try {
					zk.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testFoo2() {
		ZooKeeper zk = null;
		try {
			Watcher w = new ZkWatcher();
			zk = new ZooKeeper("192.168.1.190:2181, 192.168.1.190:2182, 192.168.1.190:2183/test1", 3600000, w);
			zk.create("/prop1", "value1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			zk.create("/prop2", "value2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			zk.create("/temp1", "tempValue1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			zk.create("/temp2", "tempValue2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.in.read();
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zk != null && zk.getState().isConnected()) {
				try {
					zk.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
