package com.myboot.dataprocess.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.myboot.dataprocess.model.KafkaApplyCardEntity;

import lombok.extern.slf4j.Slf4j;

/** 
*
* @ClassName ：MyKafkaProducer 
* @Description ：生产者处理类
* @author ：PeterQi
*
*/
@Slf4j
@Component
public class MyKafkaProducerServiceImpl implements MyKafkaProducerService {
	
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    
    public void sendMessage(String topic, int count,String currentDate) {
        for(int i=0;i<count;i++) {
        	KafkaApplyCardEntity entity = KafkaDataModelProcess.assembleKafkaData(currentDate);
        	Gson gson = new Gson();
        	String jsonStr = gson.toJson(entity);
        	log.info(jsonStr);
            kafkaTemplate.send(topic,jsonStr);
        }
    }

  
    public static void main(String[] args) {
    	MyKafkaProducerServiceImpl producer = new MyKafkaProducerServiceImpl();
    	producer.sendMessage("", 10,"2019-10-01");
    }

}
