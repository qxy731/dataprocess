package com.myboot.dataprocess.kafka;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.myboot.dataprocess.model.KafkaApplyCardEntity;

import jline.internal.Log;

/** 
*
* @ClassName ：MyKafkaCustermer 
* @Description ： 消费者处理类
* @author ：PeterQi
*
*/
@Component
public class MyKafkaCustermerServiceImpl  implements MyKafkaCustermerService {
	
	/*@KafkaListener(topics = {"datacenter.sfz.ins.opaincome"})*/
    public void listener(String content) {
        Log.info("==================MyKafkaCustermer listener start=========================");
        System.out.println(content);
        Gson gson = new Gson();
        KafkaApplyCardEntity entity = gson.fromJson(content, KafkaApplyCardEntity.class);
        System.out.println(entity);
        Log.info("==================MyKafkaCustermer listener end =========================");
    }

}