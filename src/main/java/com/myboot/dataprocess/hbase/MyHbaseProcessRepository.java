package com.myboot.dataprocess.hbase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/** 
*
* @ClassName ：MyHbaseProcessTool 
* @Description ： Hbase DDL&DML处理实体类
* @author ：PeterQi
*
*/
@Component
@Slf4j
public class MyHbaseProcessRepository {
	
    @Autowired
    private MyHbaseConfiguration myHbaseConfiguration;
	
    private Connection connection = null;
    
    /**
     * 获取连接
     * @return
     * @throws IOException 
     */
    public Connection getConnection() throws IOException {
        if (connection == null || connection.isClosed()) {
        	connection = ConnectionFactory.createConnection(myHbaseConfiguration.configuration());
        }
        return connection;
    }
    
    /**
	 * 关闭连接
	 * @throws IOException
	 */
	public void closeConnection() throws IOException{
		if(null != connection && !connection.isClosed()){
			connection.close();
		}
	}
	
	/**
	 * 创建表
	 * @throws IOException
	 */
	public void create(String namespace,String tableName,String columnFamily) throws IOException{
		log.info("---------------创建表 START-----------------");
		Admin admin = connection.getAdmin();
		admin.createNamespace(NamespaceDescriptor.create(namespace).build());
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
			if(null != columnFamily && columnFamily.length()>0) {
				HColumnDescriptor f1 = new HColumnDescriptor(columnFamily);
				//在数据表中新建一个列族
				tbDescriptor.addFamily(f1);
			}
			//新建数据表
			admin.createTable(tbDescriptor);
			admin.close();
			log.info(tableName+"表创建成功！");
		}
		log.info("---------------创建表 END-----------------");
	}
    
	/**
	 * 删除表
	 * @throws IOException
	 */
	public void drop(String tableName) throws IOException{
		log.info("---------------删除表 START-----------------");
		Admin admin = connection.getAdmin();
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
		admin.close();
		log.info("---------------删除表 END-----------------");
	}
	
	/**
	 * 删除数据
	 * @throws IOException
	 */
	public void delete(String tableName,String rowkey,String columnFamily,String cloumn) throws IOException{
		log.info("---------------删除表数据 START-----------------");
		// 新建一个数据表表名对象
		Table table = connection.getTable(TableName.valueOf(tableName));
		//指定rowkey
		Delete delete = new Delete(Bytes.toBytes(rowkey));
		//指定列
		if(columnFamily != null && cloumn != null) {
			delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn));
		}
		//执行删除操作
		table.delete(delete);
		table.close();
		log.info("---------------删除表数据 END-----------------");
	}
	/**
	 * 如果无列簇下面方面怎么更方便编写
	 */
	/**
	 * 单条单字段插入或更新数据
	 * @throws IOException
	 */
	public void insert(String tableName,String rowkey,String columnFamily,String cloumn,String columnText) throws IOException{
		log.info("---------------插入表 START-----------------");
		Table table = connection.getTable(TableName.valueOf(tableName));
		//单条插入
		Put put = new Put(Bytes.toBytes(rowkey));
		put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn), Bytes.toBytes(columnText));
		table.put(put);
		table.close();
		log.info(tableName + " 插入成功！");
		log.info("---------------插入表 END-----------------");
	}
	
	/**
	 * 单条插入或更新数据
	 * @throws IOException
	 */
	public void insert(String tableName,String rowkey,String columnFamily,String[] cloumn,String[] columnText) throws IOException{
		log.info("---------------插入表 START-----------------");
		Table table = connection.getTable(TableName.valueOf(tableName));
		//单条插入
		Put put = new Put(Bytes.toBytes(rowkey));
		for(int i = 0; i < cloumn.length; i++) {
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn[i]), Bytes.toBytes(columnText[i]));
		}
		table.put(put);
		table.close();
		log.info(tableName + " 插入成功！");
		log.info("---------------插入表 END-----------------");
	}
	
	/**
	 * 单条插入或更新数据
	 * @throws IOException
	 */
	public void insert(String tableName,String rowkey,String[] columnFamily,String[] cloumn,String[] columnText) throws IOException{
		log.info("---------------插入表 START-----------------");
		Table table = connection.getTable(TableName.valueOf(tableName));
		//单条插入
		Put put = new Put(Bytes.toBytes(rowkey));
		for(int i = 0; i < cloumn.length; i++) {
			put.addColumn(Bytes.toBytes(columnFamily[i]), Bytes.toBytes(cloumn[i]), Bytes.toBytes(columnText[i]));
		}
		table.put(put);
		table.close();
		log.info(tableName + " 插入成功！");
		log.info("---------------插入表 END-----------------");
	}
	
	/**
	 * 批量插入或更新数据
	 * @throws IOException
	 */
/*	public void insert(String tableName,String[] rowkey,String[] columnFamily,String[] cloumn,String[] columnText) throws IOException{
		log.info("---------------插入表 START-----------------");
		Table table = connection.getTable(TableName.valueOf(tableName));
		//单条插入
		List<Put> list = new ArrayList<Put>();
		for(int i = 0; i < cloumn.length; i++) {
			Put put = new Put(Bytes.toBytes(rowkey[i]));
			put.addColumn(Bytes.toBytes(columnFamily[i]), Bytes.toBytes(cloumn[i]), Bytes.toBytes(columnText[i]));
			list.add(put);
		}
		table.put(list);
		table.close();
		log.info(tableName + " 插入成功！");
		log.info("---------------插入表 END-----------------");
	}*/
	
	/**
	 * 批量插入或更新数据
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void insert(String tableName,String columnFamily,Map<String,Object> map) throws IOException, IllegalArgumentException, IllegalAccessException{
		log.info("---------------插入表 START-----------------");
		Table table = connection.getTable(TableName.valueOf(tableName));
		//单条插入
		List<Put> list = new ArrayList<Put>();
		for (Map.Entry<String,Object> entry : map.entrySet()) {
			String rowkey = entry.getKey();
			Object object = entry.getValue();
			Put put = new Put(Bytes.toBytes(rowkey));
			//获取实体类的所有属性，返回Field数组
			Field[] fields = object.getClass().getDeclaredFields(); 
			  for(int i = 0;i < fields.length; i ++){
				   Field f = fields[i];
				   f.setAccessible(true);
				   //得到对应字段的属性名，
				   String column = f.getName();
				   column = column.substring(0, 1).toUpperCase()+column.substring(1);
				   //得到对应字段属性值
				   String columnText = (String)f.get(object);
				   //得到对应字段的类型
				   //Type type = f.getGenericType();
				   put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(columnText));
				   if(i == fields.length-1) {
					   list.add(put);
				   }
			  }
		}
		table.put(list);
		table.close();
		log.info(tableName + " 插入成功！");
		log.info("---------------插入表 END-----------------");
	}
	
	/**
	 * 查询数据
	 * @throws IOException
	 */
	public void select(String tableName,String rowkey) throws IOException{
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
	
}