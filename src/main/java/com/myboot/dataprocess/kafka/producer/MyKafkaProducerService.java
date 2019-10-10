package com.myboot.dataprocess.kafka.producer;

public interface MyKafkaProducerService {
	public void sendMessage(String topic, int count,String currentDate) throws Exception;
}
