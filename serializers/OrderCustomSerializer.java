package com.tekcrux.kafka.serializers;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.errors.SerializationException;
import java.nio.ByteBuffer;
import java.util.Map;
import com.tekcrux.kafka.Order;

public class OrderCustomSerializer implements Serializer<Order> {
	
	  @Override	
	  public void configure(Map configs, boolean isKey) {
	  	// nothing to configure
	  }

	  @Override
	  public byte[] serialize(String topic, Order data) {
	  	try {
			  byte[] orderBytes, custBytes;
			  int orderSize, custSize;
	      
			  if (data == null) return null;
			  else {
				  
				    if (data.getOrderId() != null) {
				    	orderBytes = data.getOrderId().getBytes("UTF-8");
						orderSize = orderBytes.length;
				    }
				    else {
				    	orderBytes = new byte[0];
						orderSize = 0;
				    }
				  
				  
					if (data.getCustomerId() != null) {	
						custBytes = data.getCustomerId().getBytes("UTF-8");
						custSize = custBytes.length;
					} 
					else {						
						custBytes = new byte[0];
						custSize = 0;
					}
			  }
			  
			  ByteBuffer buffer = ByteBuffer.allocate(4 + orderSize + 4 + custSize);
			  buffer.putInt(orderSize);
			  buffer.put(orderBytes);
			  buffer.putInt(custSize);
			  buffer.put(custBytes);			  
			  
			  return buffer.array();
		  } 
	  	  catch (Exception e) {
	  		  throw new SerializationException("Error when serializing Order to byte[] " + e);
	      }
	  }

	  @Override
	  public void close() {
		  // nothing to close
	  }
}
