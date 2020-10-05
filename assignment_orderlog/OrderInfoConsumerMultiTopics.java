package assignment_orderlog;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.gson.Gson;

public class OrderInfoConsumerMultiTopics {
	
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		String topic_zone_1 = OrderLogConstants.TOPIC_ORDER_LOG_ZONE_1;
		String topic_zone_2 = OrderLogConstants.TOPIC_ORDER_LOG_ZONE_2;
		String topic_zone_3 = OrderLogConstants.TOPIC_ORDER_LOG_ZONE_3;
		
		List<String> topicList = Arrays.asList(topic_zone_1, topic_zone_2, topic_zone_3); 
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		props.put("group.id", "order_log");
		props.put("enable.auto.commit", "false");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		// create a KafkaConsumer instance with the specified configuration
		Consumer<Integer, String> consumer = new KafkaConsumer<>(props);
		
		// subscribe to the topic
		consumer.subscribe(topicList);
		
		System.out.println("Starting OrderInfoConsumerMultiTopics..");
		
		try {
			while (true) {
			    ConsumerRecords<Integer, String> records = consumer.poll(100);		    
			    
				for (ConsumerRecord<Integer, String> record : records) { 
					
					OrderInfo orderInfo = gson.fromJson(record.value(), OrderInfo.class );
					
					System.out.printf(
					  "topic = %s, partition = %s,offset = %d, key = %s, value = %s, message = %s\n", 
					   record.topic(), 
					   record.partition(), 
					   record.offset(), 
					   record.key(), 
					   record.value(),
					   orderInfo.getOrderInfo()); 
				} 			
				consumer.commitAsync(); 
			}
		} 
		catch (Exception e){
			consumer.close();
		}
	}
}
