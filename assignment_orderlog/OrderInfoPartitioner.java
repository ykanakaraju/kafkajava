package assignment_orderlog;

import java.util.Objects;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

public class OrderInfoPartitioner extends DefaultPartitioner {
	@Override
	public int partition(String topic, 
			  Object key, byte[] keyBytes, 
			  Object value, byte[] valueBytes,
			  Cluster cluster) {		
		
		int partitionCount = cluster.availablePartitionsForTopic(topic).size();
				
	    if (Objects.nonNull(key)) {	    	
	      try {
	    	  int logKey = Integer.parseInt(key.toString()) ;
	    	  return logKey % partitionCount;
	      }
	      catch (Exception e) {
	    	  return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
	      }		      
	    }
	    else {
	    	return super.partition(topic, key, keyBytes, value, valueBytes, cluster);	
	    }		
    }
}