package assignment_orderlog;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

public class OrderInfoStream {

	public static void main(String[] args) {
		
		final String topic_input = OrderLogConstants.TOPIC_ORDER_LOG_GSON;
		final String topic_zone_1 = OrderLogConstants.TOPIC_ORDER_LOG_ZONE_1;
		final String topic_zone_2 = OrderLogConstants.TOPIC_ORDER_LOG_ZONE_2;
		final String topic_zone_3 = OrderLogConstants.TOPIC_ORDER_LOG_ZONE_3;
		
		Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, topic_input);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,loaclhost:9093");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");   //earliest

        StreamsBuilder builder = new StreamsBuilder();
        
        KStream<Integer, String> source = builder.stream(
        		topic_input, 
        	    Consumed.with( Serdes.Integer(), Serdes.String() )
        );
        
        @SuppressWarnings("unchecked")
		KStream<Integer, String>[] branches = source.branch(
        	    (key, value) -> key == 1, 
        	    (key, value) -> key == 2, 
        	    (key, value) -> key == 3               
        	  );
        
        branches[0].to(topic_zone_1, Produced.with(Serdes.Integer(), Serdes.String()));
        branches[1].to(topic_zone_2, Produced.with(Serdes.Integer(), Serdes.String()));
        branches[2].to(topic_zone_3, Produced.with(Serdes.Integer(), Serdes.String()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props); 
        
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await(); 
        } 
        catch (Throwable e) {
            System.exit(1);
        }        
        
        System.exit(0);
	}
}
