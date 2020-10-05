package com.tekcrux.kafka.producers;

import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import com.tekcrux.kafka.*;

public class KafkaProducerOrders {
	public static void main(String[] args) throws Exception{
 	   
		  String topicName = "cts_topic3p3r";
		  
	      Properties kafkaProps = new Properties();
	      kafkaProps.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	      kafkaProps.put("key.serializer","com.tekcrux.kafka.serializers.OrderCustomSerializer"); 	      
	      kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	      kafkaProps.put("partitioner.class", "com.tekcrux.custom_partitioner.OrderPartitioner");
	      kafkaProps.put("batch.size", 20480);
	      kafkaProps.put("linger.ms", 10000);                                                                                                     
	      
	      Producer<Order, String> producer = new KafkaProducer <Order, String>(kafkaProps);				  		   
		  
	      System.out.println("Starting KafkaProducerOrders ...");
	      
		  try { 
			  for (int i = 0; i < 9; i++) {
				  int customerId = (i < 3) ? 0 : ((i < 6) ? 1 : 2);
				  Order order = new Order("Order" + i, "C" + customerId);
				  producer.send(
					  new ProducerRecord<Order, String>(topicName, order, "Message " + i),
					  new KafkaProducerOrdersCallback()
			      ); 				  
			  }
		  }
		  catch (Exception e) { 
			  e.printStackTrace();
		  } 		  
		  producer.close();
	 }
}

class KafkaProducerOrdersCallback implements Callback {

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
