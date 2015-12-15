package pha.java.util.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import pha.java.util.thread.ControllableThread;

public class PersistentWatcher extends ControllableThread implements SessionUpdatable {
	
	private static Logger logger = Logger.getLogger(PersistentWatcher.class);
	
	private ZooKeeper zk = null;
	
	private String path = null;
	
	private Watcher watcher = null;
	
	private int rewatch_inteval = 1000;
	
	public PersistentWatcher(ZooKeeper zk, String path, Watcher watcher) {
		this.zk = zk;
		this.path = path;
		this.watcher = watcher;
	}
	
	public void setRewatchInterval(int rewatch_interval) {
		if (rewatch_interval < 0) {
			throw new IllegalArgumentException();
		}
		
		this.rewatch_inteval = rewatch_interval;
		this.setSleepInterval(this.rewatch_inteval);
		logger.info("rewatch interval = " + this.rewatch_inteval);
	}
	
	@Override
	protected void work() {
		if (zk == null || path == null || watcher == null) {
			return;
		}

		try {
			zk.exists(path, watcher);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void set(ZooKeeper zk) {
		this.zk = zk;
	}
	
}
