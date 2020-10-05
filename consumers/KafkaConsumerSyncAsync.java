package com.tekcrux.kafka.consumers;

import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class KafkaConsumerSyncAsync {
	public static void main(String args[]) throws Exception{
		
		String topicName = "demo-3";
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");   //"localhost:9092,localhost:9093
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		Consumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(topicName));
		
		try { 
			while (true) { 
				ConsumerRecords<String, String> records = consumer.poll(100); 

				// if this section raises an exception after 200 records
				for (ConsumerRecord<String, String> record : records) { 
				   					
					System.out.printf(
				    	"topic = %s, partition = %s, offset = %d, key = %s, value = %s\n",
				    	record.topic(), 
				    	record.partition(), 
				    	record.offset(),
				    	record.key(), 
				    	record.value());
					
				} 				
				consumer.commitAsync();  // no guarantee...
			} 
		} 
		catch (Exception e) { 
			System.out.println("Unexpected error"+" "+ e); 
		}
		finally
		{ 
			try { 
				consumer.commitSync(); 
			}
			catch (Exception e){ 
				// do soething else
			}
			finally { 
				consumer.close();  
			} 
		} 
	}
}
