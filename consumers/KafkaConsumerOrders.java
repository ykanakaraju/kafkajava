package com.tekcrux.kafka.consumers;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.tekcrux.kafka.Order;

public class KafkaConsumerOrders {

	public static void main(String args[]) throws Exception {
		
		String topicName = "cts_topic3p3r";
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("group.id", "Orders");
		//props.put("id.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("key.deserializer","com.tekcrux.kafka.serializers.OrderCustomDeserializer");
		props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		
		KafkaConsumer<Order, String> consumer = new KafkaConsumer<Order, String>(props);
		consumer.subscribe(Arrays.asList(topicName));

		System.out.println("Starting Order Consumer with Custom Deserializer");
		
		while (true){
			
		    ConsumerRecords<Order, String> records = consumer.poll(100);
		    
		    for (ConsumerRecord<Order, String> record : records){
		        System.out.println("OrderId:" + record.key().getOrderId() 
		        				+ ", CustomerId:" + record.key().getCustomerId()
		        				+ ", Partition:" + record.partition());
		    }
		}
		
	}

}
