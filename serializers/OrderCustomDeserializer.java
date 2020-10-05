package com.tekcrux.kafka.serializers;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.errors.SerializationException; 
import java.nio.ByteBuffer; 
import java.util.Map;
import com.tekcrux.kafka.Order;

public class OrderCustomDeserializer implements Deserializer<Order> {
	@Override 
	public void configure(Map configs, boolean isKey) { 
		// nothing to configure 
	} 
	
	@Override 
	public Order deserialize(String topic, byte[] data) { 
		String orderStr, custStr; 
		int orderSize, custSize;
		
		try { 
			if (data == null) return null; 
			
			if (data.length < 8) {
			    throw new SerializationException("Size of data received by IntegerDeserializer is shorter than expected"); 
			}
			
			ByteBuffer buffer = ByteBuffer.wrap(data); 
						
			orderSize = buffer.getInt();     		
			byte[] orderBytes = new byte[orderSize];			
			buffer.get(orderBytes); 
			orderStr = new String(orderBytes, "UTF-8"); 
			
			custSize = buffer.getInt(); 		
			byte[] custBytes = new byte[custSize]; 			
			buffer.get(custBytes); 
			custStr = new String(custBytes, "UTF-8"); 
			
			return new Order(orderStr, custStr); 
		} 
		catch (Exception e) { 
			throw new SerializationException("Error when serializing Order to byte[] " + e); 
		} 
	} 
	@Override 
	public void close() { 
		// nothing to close } 
	} 
}
