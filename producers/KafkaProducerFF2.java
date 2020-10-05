package com.tekcrux.kafka.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerFF2 {
	public static void main(String[] args) throws Exception{
 	   
		  String topicName = "cts_topic3p3r";
		  
	      Properties kafkaProps = new Properties();
	      kafkaProps.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	      kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");         
	      kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	            
	      //more optional properties....
	      //kafkaProps.put("acks", "all");   //"0" -No ack, "1" only Leader ,"all" ALL
	      //kafkaProps.put("retries", 0);    // "0" doesn't retry ; positive value will retry
	      //kafkaProps.put("buffer.memory", 104857600);
	      //kafkaProps.put("max.block.ms", 30000);
	      //kafkaProps.put("batch.size", 16384);
	      //kafkaProps.put("linger.ms", 5000);
		        
	      Producer<Integer, String> producer = new KafkaProducer <>(kafkaProps);				  		   
		  
	      System.out.println("Starting KafkaProducerFF ...");
	      		   
		  Integer key = 0;
		  String msg = "";
		  for (int i = 0; i < 40; i++) {
			  try {
				  key = i % 3; 
				  msg = "Message " + i + " from KafkaProducerFF with key: " + key;
				  producer.send(new ProducerRecord<>(topicName, key, msg)); 
				  System.out.println("Sent message >> " + msg);
			  }
			  catch (Exception e) { 
				  e.printStackTrace();
			  }
			  Thread.sleep(1500);
		  }
		  		  
		  producer.close();
	 }
}
