package com.tekcrux.kafka.producers;

import java.util.*;
import org.apache.kafka.clients.producer.*;
import com.tekcrux.kafka.Student;

public class KafkaProducerCustomSer {

	public static void main(String[] args) throws Exception{
		
		String topicName="student-details";
		
		Properties props= new Properties();
		props.put("bootstrap.servers","localhost:9092,localhost:9093");
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");		
		props.put("value.serializer","com.tekcrux.kafka.serializers.CustomSerializer");
		
		Producer<String, Student> producer=new KafkaProducer<>(props);
		
		Student stud1 = new Student(100,"Raju");
		Student stud2 = new Student(101,"Ram");
		
		producer.send(new ProducerRecord<String,Student>(topicName,"key1",stud1)).get();
		producer.send(new ProducerRecord<String,Student>(topicName,"key2",stud2)).get();
		
		producer.close();
		
		System.out.println("Completed");
	}
}
