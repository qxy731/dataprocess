package com.myboot.dataprocess.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.myboot.dataprocess.model.HBaseApplyCardEntity;

public class DataProcessUtil {
	
	/**
	 * 计算两个字符串日期之间的天数差
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer betweenDay(String startDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 自定义时间格式
        Calendar calendar_a = Calendar.getInstance();// 获取日历对象
        Calendar calendar_b = Calendar.getInstance();
        Date date_a = null;
        Date date_b = null;
        try {
            date_a = simpleDateFormat.parse(startDate);//字符串转Date
            date_b = simpleDateFormat.parse(endDate);
            calendar_a.setTime(date_a);// 设置日历
            calendar_b.setTime(date_b);
        } catch (ParseException e) {
            e.printStackTrace();//格式化异常
        }
        long time_a = calendar_a.getTimeInMillis();
        long time_b = calendar_b.getTimeInMillis();
        Long between_days = (time_b - time_a) / (1000 * 3600 * 24);//计算相差天数
        return between_days.intValue();
    }
	
	/**
	 * 字符串日期加1天
	 * @param startDate
	 * @return
	 */
	public static String addDay(String startDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 自定义时间格式
        Calendar calendar = Calendar.getInstance();// 获取日历对象
        Date date = null;
        try {
            date = simpleDateFormat.parse(startDate);//字符串转Date
            calendar.setTime(date);// 设置日历
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        } catch (ParseException e) {
            e.printStackTrace();//格式化异常
        }
        String y = calendar.get(Calendar.YEAR)+"";
        int month = calendar.get(Calendar.MONTH)+1;
        String m = String.valueOf(month);
        if(month < 10) {
        	m = "0" + m;
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String d = String.valueOf(day);
        if(day < 10) {
        	d = "0" + d;
        }
        String ret = y + "-" + m + "-" + d;
        return ret;
    }
	
	
	public  static Map<String,Object> assembleData(Integer count,String currentDay) {
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0;i<count;i++) {
			String rowkey = getRowkey();
			HBaseApplyCardEntity entity = new HBaseApplyCardEntity();
			//申请编号
		    entity.setApplicationNumber("");
		    //获取数据日期	Date
		    entity.setCaptureDate("");
		    //获取数据时间	Numeric
		    entity.setCaptureTime("");
		    //中止日期	Date
		    entity.setAxpiryDate("");
		    //申请日	Date
		    entity.setApplicationDate(currentDay);
		    //申请类型 Text
		    entity.setApplicationType("");
		    //信用额度	Numeric
		    entity.setAmount("");
		    //分行号	Text
		    entity.setBranch("");
		    //处理结果	Text
		    entity.setDecision("");
		    //处理原因	Text
		    entity.setDecisionReason("");
		    //处理日期	Date
		    entity.setDecisionDate("");
		    //Id Num 证件号码	Text
		    entity.setCertificateID("");
		    //Id Type 证件种类	Text
		    entity.setCertificateType("");
		    //Full Name 主卡姓名	Text
		    entity.setSurname("");
		    //NameonCard 压花名	Text
		    entity.setFirstName("");
		    //中间名	Text
		    entity.setMiddleName("");
		    //Sex 性别	Text
		    entity.setSex("");
		    //Date of Birth 出生日期	Date
		    entity.setDateofBirth("");
		    //Hm Addr1 住宅地址1	Text
		    entity.setHomeAddress("");
		    //Hm City 住宅城市	Text
		    entity.setInhabitCity("");  
		    //HmPcode 住宅邮编	Text
		    entity.setHomePostcode("");
		    //HmPhNum 住宅电话	Text
		    entity.setHomePhoneNumber("");
		    //Mobile 手机号码	Text	32
		    entity.setMobilePhoneNumber("");
		    //Co Name 单位名称	Text	70
		    entity.setCompanyName("");
		    //Co Addr1 单位地址1	Text	40
		    entity.setOfficeAddress1("");
		    //Co Addr2 单位地址2	Text	40
		    entity.setOfficeAddress2("");
		    //Co City 单位城市	Text	40
		    entity.setCompanyAddress3("");
		    //Co Pcode 单位邮编	Text	10
		    entity.setCompanyCity("");
		    //Co Ph Num 单位电话	Text	32
		    entity.setCompanyPhoneNumber("");
		    //Channel 营销代码	Text	21
		    entity.setSalesCode("");
		    //Src of App 申请来源	Text	21
		    entity.setSourceCode("");
		    //Trackcode	Text	70
		    entity.setTrackcode("");
		    //CIM码	Text	40
		    entity.setCimCode("");
		    //COOKIE地址	Text	40
		    entity.setCookie("");
		    //证件号码(公安)	Text	25
		    entity.setIdvalue("");
		    //证件类型(公安)	Text	25
		    entity.setIdtype("");
		    //配偶证件号码	Text	25
		    entity.setSpouseidnumber("");
		    //app设备Id	Text	70
		    entity.setAppID("");
		    //app手机设备标示码	Text	70
		    entity.setAppCode("");
		    //app渠道uuid	Text	70
		    entity.setApp_uuid("");
		    //IP地址	Text	40
		    entity.setIp("");
		    //审批结果	Text	10	通过，不通过
		    entity.setApprovalResult("");
		    //调查结论	Text	2	值：K,F,S,空
		    entity.setResearchConclusion("");
		    //报警代码	Text	2	值：H,S,C
		    entity.setAlarmCode("");		
		    
			map.put(rowkey, entity);
		}
		return map;
	}
	
	public static String getRowkey() {
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String day = DataProcessUtil.addDay("2019-02-28");
		System.out.println(day);
	}

}
