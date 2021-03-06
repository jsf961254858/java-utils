package com.github.panhongan.util.zookeeper;

import org.apache.zookeeper.WatchedEvent;

public interface ZKEventPrcoessable {
	
	public void handleCreateEvent(WatchedEvent event);
	
	public void handleDeleteEvent(WatchedEvent event);
	
	public void handleUpdateEvent(WatchedEvent event);

}
