package com.myboot.dataprocess.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

/** 
*
* @ClassName ：MyKafkaCustermer 
* @Description ： 消费者处理类
* @author ：PeterQi
*
*/
@Slf4j
@Service
public class MyKafkaCustermerServiceImpl  implements MyKafkaCustermerService {
	
	@SuppressWarnings("unchecked")
	/*@KafkaListener(topics = {"${kafka.other.topic}"})*/
    public void listener(String content) {
        log.info("==================MyKafkaCustermer listener start=========================");
        //System.out.println(content);
        Gson gson = new Gson();
        /*KafkaApplyCardEntity entity = gson.fromJson(content, KafkaApplyCardEntity.class);
        System.out.println(entity); */
        Map<String,Object> map = gson.fromJson(content, new TypeToken<HashMap<String,Object>>(){}.getType());
        if(map==null) {
        	return;
        }
        Map<String,String> dataObject = (Map<String,String>)map.get("data");
        if(dataObject==null) {
        	return;
        }
        Set<Map.Entry<String, String>> set = dataObject.entrySet();
        if(set==null) {
        	return;
        }
        for(Map.Entry<String, String> entry : set) {
        	String key = entry.getKey();
        	String value = entry.getValue();
        	System.out.println("key="+key+"&value="+value);
        }
        System.out.println(map);
        log.info("==================MyKafkaCustermer listener end =========================");
    }

}