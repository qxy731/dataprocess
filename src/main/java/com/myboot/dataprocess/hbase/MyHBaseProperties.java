package com.myboot.dataprocess.hbase;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.myboot.dataprocess.DataprocessApplication;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Primary
@Component
@Log4j2
@ConfigurationProperties(prefix = "hbase",ignoreUnknownFields=true,ignoreInvalidFields=true)
@PropertySource(value={"classpath:config/hbase.properties"},ignoreResourceNotFound=false,encoding="UTF-8",name="MyHBaseProperties")
public class MyHBaseProperties {
	
	private Map<String,String> config;
	
	private Map<String,String> other;
	
	/*@Value("${hbase.zookeeper.quorum}")
    private String hbaseZookeeperQuorum ;
    
    @Value("${hbase.zookeeper.property.clientPort}")
    private String hbaseZookeeperPropertyClientPort;
    
    @Value("${hbase.zookeeper.znode.parent}")
    private String hbaseZookeeperZnodeParent;*/
	
	 public static void main(String[] args) {
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