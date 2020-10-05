package com.tekcrux.kafka.consumers;

import java.util.*;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class KafkaConsumerCommitAsync {
	public static void main(String args[]) throws Exception{
		
		String topicName = "cts_topic3p3r";
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");		
		props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		props.put("group.id", "mygroup");
		props.put("enable.auto.commit", "false");
		
		// create a KafkaConsumer instance with the specified configuration
		Consumer<Integer, String> consumer = new KafkaConsumer<>(props);
		
		// subscribe to the topic
		consumer.subscribe(Arrays.asList(topicName));
		
		System.out.println("Starting KafkaConsumerCommitAsync");
		
		try {
			while (true) {    
			    ConsumerRecords<Integer, String> records = consumer.poll(100);	
			    
				for (ConsumerRecord<Integer, String> record : records) {
					System.out.printf(
					  "topic = %s, partition = %s,offset = %d, key = %s, value = %s\n", 
					   record.topic(), 
					   record.partition(), 
					   record.offset(), 
					   record.key(), 
					   record.value()); 
				} 			
				consumer.commitAsync();   // non-blocking call				
			} 
		}
		catch (Exception e) {			
			consumer.close();    // gracefully closing the consumer
		}
	}
}
