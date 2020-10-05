package com.tekcrux.kafka.consumers;

import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.tekcrux.kafka.Student;

public class KafkaConsumerCustomDeser {

	public static void main(String args[]) throws Exception{
		
		String topicName ="student-details";
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("group.id", "StudentDetails");
		props.put("id.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer","com.tekcrux.kafka.serializers.CustomDeserializer");
		
		KafkaConsumer<String, Student> consumer = new KafkaConsumer<String, Student>(props);
		consumer.subscribe(Arrays.asList(topicName));

		System.out.println("Starting Consumer with Custom Deserializer");
		
		while (true){
			
		    ConsumerRecords<String, Student> records = consumer.poll(100);
		    
		    for (ConsumerRecord<String, Student> record : records){
		        System.out.println("Student id= " + String.valueOf(record.value().getStudentId()) 
		        				+ " Student  Name = " + record.value().getStudentName() );
		    }
		}
	}
}
