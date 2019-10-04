package com.myboot.dataprocess.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
public class MyKafkaProducer {
	
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    
    public void sendMessage(String topic, String message) {
        log.info("on message:{}", message);
        kafkaTemplate.send(topic,message);
    }

    /*public void run() {
        int messageNo = 1;
        while (true) {
            String messageStr = "Message_" + messageNo;
            long startTime = System.currentTimeMillis();
            if (isAsync) {
                producer.send(new ProducerRecord<Integer, String>(topic, messageNo, messageStr), new DemoCallback(startTime, messageNo, messageStr));
            } else {
                try {
                    producer.send(new ProducerRecord<Integer, String>(topic, messageNo, messageStr)).get();
                    System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            ++messageNo;
        }
    }*/
    public static void main(String[] args) {

    }

}
