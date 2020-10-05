package com.tekcrux.kafka.producers;

import java.util.*;
import org.apache.kafka.clients.producer.*;

public class KafkaProducerAsync {
	public static void main(String[] args) throws Exception{
		
		  String topicName = "cts_topic1";
		
	      Properties props = new Properties();
	      props.put("bootstrap.servers", "localhost:9092,localhost:9093");
	      props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
	      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	      props.put("batch.size", 20480);
	      props.put("linger.ms", 1000);
	      
	      Producer<String, String> producer = new KafkaProducer <>(props);

	      System.out.println("Started writing messages.. ");
	      	      
	      // File Reader
	      /*
	       *  file reader
	       *  read from the file line by line
	       *  parse line and create key and value
	       *  create producerRecord object from the key & value
	       *  send that data to kafka
	       *  
	       */	      
	      	      
	      for (int i = 0; i < 10; i++) {		      	
		      producer.send( 
		         new ProducerRecord<>(topicName,"Key " + i, "Java KafkaProducerAsync Value " + i), 
		    	 new KafkaProducerAsyncCallback()
		      );
		     
		      System.out.println("Writing message to topic '" + topicName + "' >> - Java KafkaProducerAsync Value " + i);
	      }
	      System.out.println("AsynchronousProducer call completed");
	      producer.close();
	}
}

class KafkaProducerAsyncCallback implements Callback {
		
    @Override
    public  void onCompletion(RecordMetadata metadata, Exception e) {
    	if (e != null) {
    		System.out.println("KafkaProducerAsync failed with an exception");    		
    	}
        else {
        	System.out.println("Calling KafkaProducerAsyncCallback.onCompletion method");
        	
        	String strMetaData = "partition: " + metadata.partition() +
		  			"; topic: " + metadata.topic() + 
		  			"; offset: " + metadata.offset() +
		  			"; hashCode: " + metadata.hashCode() + 
		  			"; timestamp: " + metadata.timestamp();
		  			
        	System.out.println(strMetaData);            
        }
    }
}
