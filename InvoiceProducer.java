package com.tekcrux.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.google.gson.Gson;

public class InvoiceProducer {
	public static void main(String[] args) throws InterruptedException
    {
    	String topic = "invoices";
    	
        Gson gson = new Gson();
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");  //"0" -No ack, "1" only Leader ,"all" ALL
        props.put("retries", 0);  // "0" doesn't re try ; positive value will retry
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        
        try
        {
            Invoice invoice1 = new Invoice(102, "Raju", 5000, "DEL");
            Invoice invoice2 = new Invoice(102, "Shiva", 500, "BLR");
            Invoice invoice3 = new Invoice(102, "Veer", 1500, "DEL");
            Invoice invoice4 = new Invoice(102, "Harsha", 3000, "BLR");
            Invoice invoice5 = new Invoice(102, "Aditya", 150, "BLR");
            Invoice invoice6 = new Invoice(102, "Amrita", 750, "MUM");
            
            //String jsonString = gson.toJson(invoice);
            ProducerRecord<String, String> record1 = new ProducerRecord<>(topic, invoice1.getCityCode(), gson.toJson(invoice1));
            ProducerRecord<String, String> record2 = new ProducerRecord<>(topic, invoice2.getCityCode(), gson.toJson(invoice2));
            ProducerRecord<String, String> record3 = new ProducerRecord<>(topic, invoice3.getCityCode(), gson.toJson(invoice3));
            ProducerRecord<String, String> record4 = new ProducerRecord<>(topic, invoice4.getCityCode(), gson.toJson(invoice4));
            ProducerRecord<String, String> record5 = new ProducerRecord<>(topic, invoice5.getCityCode(), gson.toJson(invoice5));
            ProducerRecord<String, String> record6 = new ProducerRecord<>(topic, invoice6.getCityCode(), gson.toJson(invoice6));
           
            producer.send(record1);
            producer.send(record2);
            producer.send(record3);
            producer.send(record4);
            producer.send(record5);
            producer.send(record6);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        producer.close();
        
        System.out.println("message published");
    }
}
