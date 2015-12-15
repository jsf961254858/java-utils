package pha.java.util.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ThreadManager {
	
	private static Logger logger = Logger.getLogger(ThreadManager.class);
	
	private List<Thread> threads = new ArrayList<Thread>();
	
	public void addThread(Thread t) {
		if (t != null) {
			threads.add(t);
			t.start();
		}
	}
	
	public void clear() {
		threads.clear();
	}
	
	public List<Thread> getThreads() {
		return threads;
	}
	
	public void waitForCompletion() {
		for (Thread t : threads) {
			try {
				t.join();
				logger.info("thread finished : " + t.getName());
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}

}
