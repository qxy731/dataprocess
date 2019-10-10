package com.myboot.dataprocess.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.myboot.dataprocess.kafka.common.KafkaDataModelProcess;
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
@Service
public class MyKafkaProducerServiceImpl implements MyKafkaProducerService {
	
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    
    @Autowired
    private KafkaDataModelProcess kafkaDataModelProcess;
    
    public void sendMessage(String topic, int count,String currentDate) {
        for(int i=0;i<count;i++) {
        	KafkaApplyCardEntity entity = kafkaDataModelProcess.assembleKafkaData(currentDate);
        	Gson gson = new Gson();
        	String jsonStr = gson.toJson(entity);
        	log.info(jsonStr);
            kafkaTemplate.send(topic,jsonStr);
        }
    }

}