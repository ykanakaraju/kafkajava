package com.tekcrux.kafka.serializers;

import org.apache.kafka.common.serialization.Serializer;

import com.tekcrux.kafka.Student;

import org.apache.kafka.common.errors.SerializationException;
import java.nio.ByteBuffer;
import java.util.Map;

public class CustomSerializer implements Serializer<Student> {

  @Override	
  public void configure(Map configs, boolean isKey) {
  	// nothing to configure
  }

  @Override
  /*
  We are serializing Student as:
  4 byte int representing studentId
  4 byte int representing length of studentName in UTF-8 bytes (0 if name is Null)
  N bytes representing studentName in UTF-8
  */
  public byte[] serialize(String topic, Student data) {
  	try {
		  byte[] serializedName;
		  int stringSize;
      
		  if (data == null)   return null;
		  else {
				if (data.getStudentName() != null) {
					serializedName = data.getStudentName().getBytes("UTF-8");
					stringSize = serializedName.length;
				} 
				else {
					serializedName = new byte[0];
					stringSize = 0;
				}
		  }
		  
		  ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
		  buffer.putInt(data.getStudentId());
		  buffer.putInt(stringSize);
		  buffer.put(serializedName);

		  return buffer.array();
	  } 
  	  catch (Exception e) {
  		  throw new SerializationException("Error when serializing Student to byte[] " + e);
      }
  }

  @Override
  public void close() {
	  // nothing to close
  }
}
