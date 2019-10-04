package com.myboot.dataprocess.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;
/** 
*
* @ClassName ：MyKafkaProducerConfig 
* @Description ： 生产者属性文件配置工具类
* @author ：PeterQi
*
*/
@Component
@Configuration
@EnableKafka
@EnableConfigurationProperties({MyKafkaProperties.class})
public class MyKafkaProducerConfig {
	
	@SuppressWarnings("unused")
	private final MyKafkaProperties myKafkaProperties;
	private final Map<String, String> common;
	private final Map<String, String> producer;
	private final Map<String, String> other;
    
    public MyKafkaProducerConfig(MyKafkaProperties myKafkaProperties) {
        this.myKafkaProperties = myKafkaProperties;
        if(myKafkaProperties != null) {
        	this.common = myKafkaProperties.getCommon();
        	this.producer = myKafkaProperties.getProducer();
        	this.other = myKafkaProperties.getOther();
        }else {
        	this.common = new HashMap<String,String>();
        	this.producer = new HashMap<String,String>();
        	this.other = new HashMap<String,String>();
        }
    }
	
	@Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String,String>(producerFactory());
    }
	
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.putAll(common);
        props.putAll(producer);
        /*props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
      	props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);*/
        return props;
    }
    
    public String getOtherParameter(String key) {
    	return other.get(key);
    }
    
    public String getProducerParameter(String key) {
    	return producer.get(key);
    }
    
    public String getCommonParameter(String key) {
    	return common.get(key);
    }

}