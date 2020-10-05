package com.tekcrux.kafka.serializers;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.errors.SerializationException; 
import java.nio.ByteBuffer; 
import java.util.Map;

import com.tekcrux.kafka.Student;

public class CustomDeserializer implements Deserializer<Student> {
	
	@Override 
	public void configure(Map configs, boolean isKey) { 
		// nothing to configure 
	} 
	
	@Override 
	public Student deserialize(String topic, byte[] data) { 
		int id; 
		int nameSize; 
		String name; 
		try { 
			if (data == null) return null; 
			
			if (data.length < 8) {
			    throw new SerializationException("Size of data received by IntegerDeserializer is shorter than expected"); 
			}
			
			ByteBuffer buffer = ByteBuffer.wrap(data); 
			id = buffer.getInt(); 
			
			int nameSize1 = buffer.getInt(); 		
			byte[] nameBytes = new byte[nameSize1]; 
			
			buffer.get(nameBytes); 
			name = new String(nameBytes, "UTF-8"); 
			
			return new Student(id, name); 
		} 
		catch (Exception e) { 
			throw new SerializationException("Error when serializing Student to byte[] " + e); 
		} 
	} 
	@Override 
	public void close() { 
		// nothing to close } 
	} 
}
