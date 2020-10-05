package assignment_orderlog;

import java.util.Arrays;
import java.util.Properties;
import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.gson.Gson;

public class OrderInfoConsumerJSONDeser {
	
	public static void main(String[] args) {
		
        String topicName = OrderLogConstants.TOPIC_ORDER_LOG_GSON;
		Gson gson = new Gson();
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
		props.put("group.id", "order_log");
		props.put("enable.auto.commit", "false");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		// create a KafkaConsumer instance with the specified configuration
		Consumer<Integer, String> consumer = new KafkaConsumer<>(props);
		
		// subscribe to the topic
		consumer.subscribe(Arrays.asList(topicName));
		
		System.out.println("Starting OrderInfoConsumerJSONDeser ..");
		
		try {
			while (true) {
			    ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));	
			    
				for (ConsumerRecord<Integer, String> record : records) { 
					
					OrderInfo orderInfo = gson.fromJson(record.value(), OrderInfo.class );
					
					System.out.printf(
					  "topic: %s, partition: %s, offset: %d, key: %s, value: %s\n", 
					   record.topic() 
					   , record.partition()
					   , record.offset()
					   , orderInfo.getZoneId()
					   , orderInfo.getOrderInfo()
					); 
				} 			
				consumer.commitAsync(); 
			}
		} 
		catch (Exception e){
			consumer.close();
		}
	}
}
