package com.jh.myweb.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkWatcher implements Watcher {

	private static Logger log = LoggerFactory.getLogger(ZkWatcher.class);

	@Override
	public void process(WatchedEvent event) {
		log.info(event.getType() + ", " + event.getState() + ", " + event.getPath());
	}

}
