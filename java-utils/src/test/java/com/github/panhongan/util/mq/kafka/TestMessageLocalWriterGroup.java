package com.github.panhongan.util.mq.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.mq.kafka.AbstractMessageProcessor;
import com.github.panhongan.util.mq.kafka.HighLevelConsumerGroup;


public class TestMessageLocalWriterGroup {
	
	private static final Logger logger = LoggerFactory.getLogger(TestMessageLocalWriterGroup.class);
	
	public static void main(String [] args) {
		// local.data.dir, zk.list, kafka.consumer.group
		String conf_file = "../conf/kafka.properties";
		Config config = new Config();
		if (!config.parse(conf_file)) {
			logger.warn("parse conf file failed : {}", conf_file);
			return;
		}
		
		logger.info(config.toString());
		
		String topic = "test";
		int partitions = 4;
		
		List<AbstractMessageProcessor> processors = new ArrayList<AbstractMessageProcessor>();
		for (int i = 0; i < partitions; ++i) {
			MessageLocalWriter local_writer = new MessageLocalWriter(config.getString("local.data.dir"));
			if (local_writer.init()) {
				processors.add(local_writer);
			}
		}
		
		HighLevelConsumerGroup group = new HighLevelConsumerGroup(config.getString("zk.list"), 
				config.getString("kafka.consumer.group"),
				topic, partitions, processors);
		
		if (group.init()) {
			logger.info("HighLevelConsumerGroup init ok");
		} else {
			logger.warn("HighLevelConsumerGroup init failed");
		}
		
		try {
			Thread.sleep(5 * 60 * 1000);
		} catch (Exception e) {
		}
		
		// uninit
		group.uninit();
		
		for (AbstractMessageProcessor processor : processors) {
			processor.uninit();
		}
	}

}
