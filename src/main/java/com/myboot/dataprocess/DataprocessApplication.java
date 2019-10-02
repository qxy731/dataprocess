package com.myboot.dataprocess;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.myboot.dataprocess.hbase.MyHBaseProperties;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
//@EnableConfigurationProperties
public class DataprocessApplication{
	
	

	public static void main(String[] args) {
		//SpringApplication.run(DataprocessApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(DataprocessApplication.class, args);
		MyHBaseProperties myHBaseProperties = context.getBean(MyHBaseProperties.class);
		if(myHBaseProperties == null) {
			log.info("myHBaseProperties is null....");
		}
		log.info(myHBaseProperties);
		Map<String,String> config  = myHBaseProperties.getConfig();
		if(config == null) {
			log.info("config is null....");
		}
		Set<String> configKeySet = config.keySet();
        for (String key : configKeySet) {
        	log.info(key + ":" + config.get(key));
        }
        Map<String,String> other  = myHBaseProperties.getOther();
		if(other == null) {
			log.info("other is null....");
		}
		Set<String> otherKeySet = other.keySet();
        for (String key : otherKeySet) {
        	log.info(key + ":" + other.get(key));
        }
	}
	
}