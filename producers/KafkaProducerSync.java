package com.tekcrux.kafka.producers;

import java.util.*;
import org.apache.kafka.clients.producer.*;

public class KafkaProducerSync {
	public static void main(String[] args) throws Exception{
        
		  String topicName = "cts_topic1";
	     
	      Properties kafkaProps = new Properties();
	      kafkaProps.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	      kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");         
	      kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	      kafkaProps.put("buffer.memory", 104857600);
	      kafkaProps.put("max.block.ms", 30000);
	      kafkaProps.put("batch.size", 20480);  
	      kafkaProps.put("linger.ms", 10000);   
	      	      
	      Producer<String, String> producer = new KafkaProducer <>(kafkaProps);
				  		  
		  try { 
			  for (int i = 0; i < 10; i++) {	
				  
				  RecordMetadata metaData = producer
					.send(new ProducerRecord<>(topicName,"Key " + i, "Java KafkaProducerSync Value " + i))
					.get();
				  
				  String strMetaData = "partition: " + metaData.partition() +
						  			     "; topic: " + metaData.topic() + 
						  			    "; offset: " + metaData.offset() +
						  			  "; hashCode: " + metaData.hashCode() + 
						  			 "; timestamp: " + metaData.timestamp();
						  			
				  System.out.println(strMetaData);				  
			  }			  
		  } 
          catch (Exception e) { 
			  e.printStackTrace(); 
		  } 

		  producer.close();
		  
		  System.out.println("KafkaProducerSync Completed.");
	   }
}
