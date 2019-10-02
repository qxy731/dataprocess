package com.myboot.dataprocess.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.myboot.dataprocess.DataprocessApplication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class MyHBaseCRUD {
	
    @Autowired
    private MyHBaseConfiguration myHBaseConfiguration;
	
    private static Connection connection = null;
    
    // 数据库元数据操作对象
    private static Admin admin;
    
    /**
     * 获取连接
     * @return
     * @throws IOException 
     */
    public Connection getHBaseConnection() throws IOException {
        if (connection == null || connection.isClosed()) {
        	connection = ConnectionFactory.createConnection(myHBaseConfiguration.configuration());
        }
        return connection;
    }
    
    /**
	 * 关闭连接
	 * @throws Exception
	 */
	public static void closeConnection() throws Exception{
		if(null != connection && !connection.isClosed()){
			connection.close();
		}
	}
	
	/**
	 * 创建表
	 * @throws Exception
	 */
	public void createTable(String namespace,String tableName)throws Exception{
		log.info("---------------创建表 START-----------------");
		//创建namespace
		//String namespace = myHBaseConfiguration.getOtherParameter("namespace");
		admin.createNamespace(NamespaceDescriptor.create(namespace).build());
		//数据表表名
		//String tableName = myHBaseConfiguration.getOtherParameter("tablename");
		//新建一个数据表表名对象
		TableName tn = TableName.valueOf(tableName);
		//判断表是否存在
		if(admin.tableExists(tn)){
			log.info(tableName+"表已经存在！");
		}else{
			log.info(tableName+"表不存在，开始创建！");
			//数据表描述对象
			HTableDescriptor tbDescriptor=new HTableDescriptor(tn);
			//列族描述对象
			String columnFamily = myHBaseConfiguration.getOtherParameter("columnfamily");
			HColumnDescriptor f1 = new HColumnDescriptor(columnFamily);
			//在数据表中新建一个列族
			tbDescriptor.addFamily(f1);
			// 新建数据表
			admin.createTable(tbDescriptor);
			log.info(tableName+"表创建成功！");
		}
		log.info("---------------创建表 END-----------------");
	}
    
	/**
	 * 删除表
	 * @throws Exception
	 */
	public static void deleteTable(String tableName)throws Exception{
		log.info("---------------删除表 START-----------------");
		// 数据表表名
		//String tableName = myHBaseConfiguration.getOtherParameter("tablename");
		// 新建一个数据表表名对象
		TableName tn = TableName.valueOf(tableName);
		//判断表是否存在
		if(admin.tableExists(tn)){
			admin.disableTable(tn);
			admin.deleteTable(tn);
			log.info(tableName+"表已经删除！");
		}else{
			log.info(tableName+"表不存在！");
		}
		log.info("---------------删除表 END-----------------");
	}
	
	/**
	 * 删除数据
	 * @throws Exception
	 */
	public static void delete(String tableName,String rowkey,String columnFamily,String cloumn)throws Exception{
		log.info("---------------删除表数据 START-----------------");
		// 数据表表名
		//String tableName = myHBaseConfiguration.getOtherParameter("tablename");
		// 新建一个数据表表名对象
		Table table = connection.getTable(TableName.valueOf(tableName));
		//指定rowkey
		Delete del = new Delete(Bytes.toBytes(rowkey));
		//指定列
		if(columnFamily != null && cloumn != null) {
			del.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn));
		}
		//执行删除操作
		table.delete(del);
		log.info("---------------删除表数据 END-----------------");
	}
	
	/**
	 * 插入数据
	 * @throws Exception
	 */
	public static void insert(String tableName,String rowkey,String columnFamily,String[] cloumn,String[] columnText)throws Exception{
		log.info("---------------插入表 START-----------------");
		Table table = connection.getTable(TableName.valueOf(tableName));
		//单条插入
		Put put = new Put(Bytes.toBytes(rowkey));
		for(int i = 0; i < cloumn.length; i++) {
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn[i]), Bytes.toBytes(columnText[i]));
		}
		table.put(put);
		//批量插入
		/*List<Put> list = new ArrayList<Put>();
		Put put = new Put(Bytes.toBytes("1001"));
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("测试1"));
		list.add(put);
		put = new Put(Bytes.toBytes("1002"));
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("测试2"));
		list.add(put);
		table.put(list);*/
		
		log.info(tableName + " 插入成功！");
		log.info("---------------插入表 END-----------------");
	}
	
	/**
	 * 更新数据
	 * @throws Exception
	 */
	public static void update(String tableName,String rowkey,String columnFamily,String[] cloumn,String[] columnText)throws Exception{
		log.info("---------------更新表 START-----------------");
		Table table =connection.getTable(TableName.valueOf(tableName));
		//更新 - 同插入操作,覆盖的意思
		Put put = new Put(Bytes.toBytes(rowkey));
		for(int i = 0; i < cloumn.length; i++) {
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn[i]), Bytes.toBytes(columnText[i]));
		}
		table.put(put);
		log.info("---------------更新表 END-----------------");
	}
	
	/**
	 * 查询数据
	 * @throws Exception
	 */
	public static void select(String tableName,String rowkey)throws Exception{
		log.info("---------------查询 START-----------------");
		//根据rowkey查询
		Table table =connection.getTable(TableName.valueOf(tableName));
		Get get = new Get(Bytes.toBytes(rowkey));
		//通过列族查询
//		get.addFamily(Bytes.toBytes("f1"));
		//通过列查询
//		get.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));
		Result result = table.get(get);
		if(null != result){
			for (Cell cell:result.rawCells()) {
				log.info("列族:" + new String(CellUtil.cloneFamily(cell)) + " ");
				log.info("列:" + new String(CellUtil.cloneQualifier(cell)) + " ");
				log.info("值:" + new String(CellUtil.cloneValue(cell)) + " ");
				log.info("时间戳:" + cell.getTimestamp());
				log.info("---------------字段分隔符--------------");
			}
		}else{
			log.info("数据为空!!!!");
		}
		
		/*//根据rowkey进行范围查询
		String talbeName="WHG:TB1";
		String startRowKey="1001";//包含
		String endRowKey=  "1005";//不包含
		
		Table table =connection.getTable(TableName.valueOf(talbeName));
		Scan scan = new Scan();
		
		//设置取值范围
        scan.setStartRow(startRowKey.getBytes());//开始的key
        scan.setStopRow(endRowKey.getBytes());//结束的key
        ResultScanner scanner = table.getScanner(scan) ;
        List<Result> list = null;
        list = new ArrayList<Result>() ;
        for (Result rs : scanner) {
            list.add(rs) ;
            for (Cell cell : rs.listCells()) {
                log.info("列族:" + new String(CellUtil.cloneFamily(cell)) + " ");
                log.info("列:" + new String(CellUtil.cloneQualifier(cell)) + " ");
                log.info("值:" + new String(CellUtil.cloneValue(cell)) + " ");
                log.info("时间戳:" + cell.getTimestamp());
                log.info("---------------我是光荣的分隔符--------------");
            }
        }
        log.info("----------------记录数："+list.size());*/
		log.info("---------------查询 END-----------------");
	}
	
    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DataprocessApplication.class, args);
		try {
			MyHBaseCRUD hbaseConnection = context.getBean(MyHBaseCRUD.class);
			Connection connection = hbaseConnection.getHBaseConnection();
			log.info(connection.getAdmin());
			connection.close();
		} catch (Exception e) {
			log.error(e);
		}
	}
}