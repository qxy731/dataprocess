package com.myboot.dataprocess.kafka;

public interface MyKafkaProducerService {
	public void sendMessage(String topic, int count,String currentDate) throws Exception;
}
