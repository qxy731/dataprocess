package com.myboot.dataprocess.hbase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Configuration
@EnableConfigurationProperties({MyHBaseProperties.class})
public class MyHBaseConfiguration {
    
    @SuppressWarnings("unused")
	private final MyHBaseProperties myHBaseProperties;
    private final Map<String, String> config;
    private final Map<String, String> other;
    
    public MyHBaseConfiguration(MyHBaseProperties myHBaseProperties) {
        this.myHBaseProperties = myHBaseProperties;
        if(myHBaseProperties != null) {
        	this.config = myHBaseProperties.getConfig();
        	this.other = myHBaseProperties.getOther();
        }else {
        	this.config = new HashMap<String,String>();
        	this.other = new HashMap<String,String>();
        }
    }
    
    /**
     * 获取HBASE配置
     * @return
     */
    public org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        Set<String> keySet = config.keySet();
        for (String key : keySet) {
            configuration.set(key, config.get(key));
            log.info(key + ":" + config.get(key));
        }
        return configuration;
    }
    
    public String getOtherParameter(String key) {
    	return other.get(key);
    }
    
    public String getConfigParameter(String key) {
    	return other.get(key);
    }
}