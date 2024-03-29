package com.myboot.dataprocess.hbase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myboot.dataprocess.DataprocessApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataprocessApplication.class)
public class HbaseProcessServiceImplTest {
	
	@Autowired
    private HbaseProcessServiceImpl service;
	
	@Test
    public void testSave() throws Exception {
		int total = 10000000;
		String startDate = "2019-04-01";
		String endDate = "2019-10-08";
		service.save(total, startDate, endDate);
		//service.other();//第一步先测试通不通
		//System.out.println("--------------测试----------");
	}
	
	@Test
    public void testSelect() throws Exception {
		//service.select("hbs_trans_log_act", "201910061000995|1570384394150");
		//service.other();//第一步先测试通不通
		//System.out.println("--------------测试----------");
	}

}
