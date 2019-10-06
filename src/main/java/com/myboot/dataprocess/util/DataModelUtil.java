package com.myboot.dataprocess.util;

import java.util.HashMap;
import java.util.Map;

import com.myboot.dataprocess.enums.AlarmCode;
import com.myboot.dataprocess.enums.ApprovalResult;
import com.myboot.dataprocess.enums.ResearchConclusion;
import com.myboot.dataprocess.enums.Sex;
import com.myboot.dataprocess.model.HBaseApplyCardEntity;

public class DataModelUtil {
	
	/**
	 * 申请编号自增长序列的起始值，11位
	 */
	private static long sequence = 10000000000L;
	
	public  static Map<String,Object> assembleData(Integer count,String currentDay) {
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0;i<count;i++) {
			sequence++;
			String rowkey = getRowkey();
			HBaseApplyCardEntity entity = new HBaseApplyCardEntity();
			//申请编号
		    entity.setApplicationNumber(currentDay.replaceAll("-", "")+sequence);
		    //获取数据日期	Date
		    entity.setCaptureDate(CommonUtil.getCurrentDate());
		    //获取数据时间	Numeric
		    entity.setCaptureTime(CommonUtil.getCurrentTime());
		    //中止日期	Date
		    entity.setExpiryDate(CommonUtil.getCurrentDate());
		    //申请日	Date
		    entity.setApplicationDate(currentDay);
		    //申请类型 Text
		    entity.setApplicationType("CARD");
		    //信用额度	Numeric
		    entity.setAmount(RandomNumberBuilder.getRangeRandom2Str(3000,100000));
		    //分行号	Text
		    entity.setBranch(RandomNumberBuilder.getRangeRandom2Str(10,100));
		    //处理结果	Text
		    String decision = RandomNumberBuilder.getRangeRandom2Str(1,4);
		    entity.setDecision(decision);
		    //处理原因	Text
		    if("3".equals(decision)) {
		    	entity.setDecisionReason(RandomCharacterBuilder.getRandomStr(10));
		    }
		    //处理日期	Date
		    if("3".equals(decision)) {
		    	 entity.setDecisionDate(CommonUtil.addDay(currentDay,RandomNumberBuilder.getRangeRandom2Int(1,4)));
		    }
		    //Id Num 证件号码	Text
		    String certNo = RandomCertNoBuilder.getRandomCertNo();
		    entity.setCertificateID(certNo);
		    //Id Type 证件种类	Text
		    entity.setCertificateType("10");
		    //Full Name 主卡姓名	Text
		    entity.setSurname(RandomNameBuilder.build());
		    //NameonCard 压花名	Text
		    entity.setFirstName(RandomNameBuilder.getFirstName());
		    //中间名	Text
		    entity.setMiddleName(RandomNameBuilder.getMiddleName());
		    //Sex 性别	Text
		    entity.setSex(Sex.randomType().toString());
		    //Date of Birth 出生日期	Date
		    entity.setDateofBirth(CommonUtil.getBirthByCertNo(certNo));
		    //Hm Addr1 住宅地址1	Text
		    entity.setHomeAddress("");
		    //Hm City 住宅城市	Text
		    entity.setInhabitCity("");  
		    //HmPcode 住宅邮编	Text
		    entity.setHomePostcode("");
		    //HmPhNum 住宅电话	Text
		    entity.setHomePhoneNumber(RandomPhoneBuilder.getRandomTel());
		    //Mobile 手机号码	Text	32
		    entity.setMobilePhoneNumber(RandomPhoneBuilder.getRandomTel());
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
		    entity.setSalesCode(RandomNumberBuilder.getRangeRandom2Str(1000,9999));
		    //Src of App 申请来源	Text	21
		    entity.setSourceCode(RandomNumberBuilder.getRangeRandomHexStr());
		    //Trackcode	Text	70
		    entity.setTrackcode(RandomNumberBuilder.getRangeRandom2Str(1000,9999));
		    //CIM码	Text	40
		    entity.setCimCode(RandomNumberBuilder.getRandomGuid(16));
		    //COOKIE地址	Text	40
		    entity.setCookie(RandomIpBuilder.getRandomIp());
		    //证件号码(公安)	Text	25
		    entity.setIdvalue(certNo);
		    //证件类型(公安)	Text	25
		    entity.setIdtype(RandomNumberBuilder.getRangeRandom2Str(10,30));
		    //配偶证件号码	Text	25
		    entity.setSpouseidnumber(RandomCertNoBuilder.getRandomCertNo());
		    //app设备Id	Text	70
		    entity.setAppID(RandomNumberBuilder.getRandomGuid(17));
		    //app手机设备标示码	Text	70
		    entity.setAppCode(RandomNumberBuilder.getRandomGuid(14));
		    //app渠道uuid	Text	70
		    entity.setApp_uuid(RandomNumberBuilder.getRandomGuid(16));
		    //IP地址	Text	40
		    entity.setIp(RandomIpBuilder.getRandomIp());
		    //审批结果	Text	10	通过，不通过
		    entity.setApprovalResult(ApprovalResult.randomType().toString());
		    //调查结论	Text	2	值：K,F,S,空
		    entity.setResearchConclusion(ResearchConclusion.randomType().toString());
		    //报警代码	Text	2	值：H,S,C
		    entity.setAlarmCode(AlarmCode.randomType().toString());		
			
		    map.put(rowkey, entity);
		}
		return map;
	}
	
	public static String getRowkey() {
		long timestamp = System.currentTimeMillis();
		return String.valueOf(sequence)+String.valueOf(timestamp);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getRowkey());
	}

}
