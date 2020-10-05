package assignment_orderlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.google.gson.Gson;

public class OrderInfoProducerJSONSer {
	
	public static void main(String[] args) throws Exception {
		
		final String topic = OrderLogConstants.TOPIC_ORDER_LOG_GSON;
		final String logFilePath = OrderLogConstants.TEST_DATA_FILE;
		Gson gson = new Gson();
		
		Properties kafkaProps = new Properties();
	    kafkaProps.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");         
	    kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    kafkaProps.put("partitioner.class", "assignment_orderlog.OrderInfoPartitioner");
	    
	    Producer<Integer, String> producer = new KafkaProducer <Integer, String>(kafkaProps);
	      
		try {
		      File logFileObj = new File(logFilePath);
		      Scanner logFileReader = new Scanner(logFileObj);
		      
		      while (logFileReader.hasNextLine()) {
		    	  
		    	  String data = logFileReader.nextLine();
		    	  String[] dataArr = data.split(",");  
		        	
		    	  try {
			    	  int orderId = Integer.parseInt( dataArr[0] );
			    	  String orderDate = dataArr[1];
			    	  double orderAmount = Double.parseDouble( dataArr[2] );
			    	  int storeId = Integer.parseInt( dataArr[3] );
			    	  int zoneId = Integer.parseInt( dataArr[4] ); 
			    	  
			    	  if (zoneId > 0) {			    		  
			    		  OrderInfo order = new OrderInfo(orderId, orderDate, orderAmount, storeId, zoneId);
				    	  int key = order.getZoneId();
				    	  String value = order.getOrderInfo();
				    	  		
				    	  ProducerRecord<Integer,String> record = new ProducerRecord<>(topic, key, gson.toJson(order));
			    		  producer.send(record);
			    		  System.out.printf("\nSent message: key: %s, value: %s", key, value);
			    	  }			    	  
		    	  }
		    	  catch (Exception e) {
		    		  continue;
		    	  }
		    	  
		      }
		      logFileReader.close();
		 } 
		 catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		 }
		 catch (Exception e) { 
			  e.printStackTrace();
		 } 		  
		 
		 producer.close();
	}

}
