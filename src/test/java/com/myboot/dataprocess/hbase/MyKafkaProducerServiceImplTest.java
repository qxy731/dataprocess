package com.myboot.dataprocess.hbase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myboot.dataprocess.DataprocessApplication;
import com.myboot.dataprocess.kafka.MyKafkaProducerServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataprocessApplication.class)
public class MyKafkaProducerServiceImplTest {
	
	@Autowired
    private MyKafkaProducerServiceImpl service;
	
	@Test
    public void testSendMessage() throws Exception {
		int count = 100000;
		String topic = "datacenter.sfz.ins.opaincome";
		String currentDate = "2019-10-07";
		service.sendMessage(topic, count, currentDate);
		//service.other();//第一步先测试通不通
		//System.out.println("--------------测试----------");
	}
	

}
