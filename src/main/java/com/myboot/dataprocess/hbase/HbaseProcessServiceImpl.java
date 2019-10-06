package com.myboot.dataprocess.hbase;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myboot.dataprocess.util.CommonUtil;
import com.myboot.dataprocess.util.DataModelUtil;

import lombok.extern.slf4j.Slf4j;

/** 
*
* @ClassName ：HbaseProcessService 
* @Description ： 业务逻辑实现类
* @author ：PeterQi
*
*/
@Service
@Slf4j
public class HbaseProcessServiceImpl implements HbaseProcessService {
	
	@Autowired
	private MyHbaseProcessRepository hbaseRepository;
	
	@Autowired
    private MyHbaseConfiguration myHbaseConfiguration;
	
	/**
	 * 创建表
	 */
	@Override
	public void create() throws IOException {
		//空间名称
		String namespace = myHbaseConfiguration.getOtherParameter("namespace");
		//数据表表名
		String tableName =  myHbaseConfiguration.getOtherParameter("tablename");
		//列簇名称
		String columnFamily = myHbaseConfiguration.getOtherParameter("columnFamily");
		hbaseRepository.create(namespace, tableName,columnFamily);
	}
	
	/**
	 * 创建表
	 */
	@Override
	public void create(String namespace, String tableName, String columnFamily) throws Exception {
		hbaseRepository.create(namespace, tableName,columnFamily);
	}
	
	/**
	 * 删除表
	 */
	@Override
	public void drop(String tableName) throws Exception {
		hbaseRepository.drop(tableName);
	}
	
	/**
	 * 插入或更新表数据
	 */
	@Override
	public void save(int total, String startDate, String endDate) throws Exception {
		String tableName =  myHbaseConfiguration.getOtherParameter("tablename");
		String columnFamily = myHbaseConfiguration.getOtherParameter("columnFamily");
		int days = CommonUtil.betweenDay(startDate, endDate);
		int dayTotal = total/days;
		int modTotal = total%days;
		String currentDay = startDate;
		for(int d = 0; d < days; d++) {
			if(d == days - 1 ) {
				if(modTotal != 0 ) {
					dayTotal = dayTotal + modTotal;
				}
			}
			int count = 10000;
			int length = dayTotal/count;
			int mod = dayTotal % count;
			if(mod != 0 ) {
				length = length+1;
			}
			for(int i=0;i<length;i++) {
				if(i == length-1 && mod != 0) {
					count = mod;
				}
				Map<String,Object> map = DataModelUtil.assembleData(count,currentDay);
				hbaseRepository.insert(tableName,columnFamily,map);
			}
			currentDay = CommonUtil.addDay(currentDay,1);
		}
	}
	
	@Override
	public void delete(String tableName, String rowkey) throws Exception {
		
	}


	@Override
	public void select(String tableName, Map<String, Object> params) throws Exception {
		
	}
	
	public void other() throws Exception {
		log.info("===================================================== method is start ===================================================== ");
		try {
			hbaseRepository.getConnection();
			//空间名称
			String namespace = myHbaseConfiguration.getOtherParameter("namespace");
			log.info("===================="+namespace+"====================");
			//数据表表名
			String tableName =  myHbaseConfiguration.getOtherParameter("tablename");
			log.info("===================="+tableName+"====================");
			//列簇名称
			String columnFamily = myHbaseConfiguration.getOtherParameter("columnfamily");
			log.info("===================="+columnFamily+"====================");
			hbaseRepository.create(namespace, tableName, columnFamily);
			String rowkey = "1234567890";
			String cloumn= "testcloumn";
			String columnText = "这是测试列字符";
			hbaseRepository.insert(tableName, rowkey, columnFamily, cloumn, columnText);
			hbaseRepository.select(tableName, rowkey);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}finally {
			if(null != hbaseRepository) {
				try {
					hbaseRepository.closeConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		log.info("===================================================== method is end ===================================================== ");
	}
	
}