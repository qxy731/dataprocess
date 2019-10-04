package com.myboot.dataprocess.kafka;

import org.springframework.stereotype.Component;

/** 
*
* @ClassName ：MyKafkaCustermer 
* @Description ： 消费者处理类
* @author ：PeterQi
*
*/
@Component
public class MyKafkaCustermer {
	
	/*@KafkaListener(topics = {"test"})*/
    public void listener(String content) {
        System.out.println(content);
    }

}