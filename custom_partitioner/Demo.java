package com.tekcrux.custom_partitioner;

import java.util.List;
import java.util.Objects;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

public class Demo extends DefaultPartitioner {
	 @Override
	  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
	      Cluster cluster) {
    	    
	    List<PartitionInfo> partitonList = cluster.availablePartitionsForTopic(topic);
	    int numberOfPartitons = partitonList.size();
	    
	    if (numberOfPartitons > 2) {
		    if (key.toString() == "USA") return 0;
		    else if (key.toString() == "India") return 1;
		    else return 2 + ( key.hashCode() % (numberOfPartitons - 2) );   
	    }
	    else {
	    	return super.partition(topic, key, keyBytes, value, valueBytes, cluster);	
	    }    
	 }
}
