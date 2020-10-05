package com.tekcrux.kafka.consumers;

import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

public class KafkaConsumerCommitSync {
	public static void main(String args[]) throws Exception{
		
		String topicName = "ctstopic3p3r";
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");  
		//props.put("group.id", "mygroup");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.Integer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		Consumer<Integer, String> consumer = new KafkaConsumer<>(props);

		List<TopicPartition> partitions = new ArrayList<>(); 

		if (partitions != null) { 
			
			for (PartitionInfo partition : consumer.partitionsFor(topicName)) {
				partitions.add(new TopicPartition(topicName, partition.partition()));
			}				
			consumer.assign(partitions); 
			
			/*
			partitions.add(new TopicPartition(topicName, 0));
			partitions.add(new TopicPartition(topicName, 1));
			consumer.assign(partitions);   
			*/

			while (true) { 
				ConsumerRecords<Integer, String> records = consumer.poll(100); 
				for (ConsumerRecord<Integer, String> record: records) { 
					
					System.out.printf( 
							"topic = %s, partition = %s, offset = %d, key = %s, value = %s\n",
							record.topic(), 
							record.partition(), 
							record.offset(), 
							record.key(), 
							record.value()); 
				}		 
				consumer.commitSync();   // blocking calling

			}			
		}
		consumer.close();
	}
}
