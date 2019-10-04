package com.myboot.dataprocess.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/** 
*
* @ClassName ：MyKafkaConsumerConfig 
* @Description ： 消费者属性文件配置工具类
* @author ：PeterQi
*
*/
@Slf4j
@Component
@Configuration
@EnableKafka
@EnableConfigurationProperties({MyKafkaProperties.class})
public class MyKafkaConsumerConfig {
	
	@SuppressWarnings("unused")
	private final MyKafkaProperties myKafkaProperties;
	private final Map<String, String> common;
	private final Map<String, String> consumer;
	private final Map<String, String> other;
    
    public MyKafkaConsumerConfig(MyKafkaProperties myKafkaProperties) {
        this.myKafkaProperties = myKafkaProperties;
        if(myKafkaProperties != null) {
        	this.common = myKafkaProperties.getCommon();
        	this.consumer = myKafkaProperties.getConsumer();
        	this.other = myKafkaProperties.getOther();
        }else {
        	this.common = new HashMap<String,String>();
        	this.consumer = new HashMap<String,String>();
        	this.other = new HashMap<String,String>();
        }
    }
	
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //log.info(other.get("concurrency"));
        factory.setConcurrency(Integer.valueOf(other.get("concurrency")));
        //log.info(other.get("polltimeout"));
        factory.getContainerProperties().setPollTimeout(Long.valueOf(other.get("polltimeout")));
        return factory;
    }
    
    
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.putAll(common);
        propsMap.putAll(consumer);
        return propsMap;
    }
    
    public String getOtherParameter(String key) {
    	return other.get(key);
    }
    
    public String getConsumerParameter(String key) {
    	return consumer.get(key);
    }
    
    public String getCommonParameter(String key) {
    	return common.get(key);
    }

}