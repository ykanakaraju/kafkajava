package com.tekcrux.kafka.producers;

import java.util.*;
import org.apache.kafka.clients.producer.*;

public class KafkaProducerFF {
	
	public static void main(String[] args) throws Exception {
        	   
		  String topicName = "t1";  //"cts_topic1";
		  
	      Properties kafkaProps = new Properties();
	      kafkaProps.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	      kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");         
	      kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	     		        
	      Producer<String, String> producer = new KafkaProducer <String, String>(kafkaProps);				  		   
		  
	      System.out.println("Starting KafkaProducerFF ...");
	      
		  try { 
			  for (int i = 0; i < 20; i++) {
				  
				  producer.send(new ProducerRecord<>(topicName, "MyKey-" + i, "Test Java Message " + i));
				  System.out.println("sent message - " + i);			  
			  }
		  }
		  catch (Exception e) { 
			  e.printStackTrace();			  
		  } 
		  
		  producer.close();
		  
		  System.out.println("--- done ---- ");
	 }
}

/*
	ProducerRecord(String topic, V value)
	ProducerRecord(String topic, K key, V value)
	ProducerRecord(String topic, Integer partition, K key, V value)
	ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value)
*/











