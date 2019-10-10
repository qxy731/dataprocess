package com.myboot.dataprocess.kafka.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myboot.dataprocess.model.ApplyCardEntity;
import com.myboot.dataprocess.model.KafkaApplyCardEntity;
import com.myboot.dataprocess.model.ProtocolEntity;
import com.myboot.dataprocess.model.SchemaEntity;
import com.myboot.dataprocess.util.RandomDataModelBuilder;
import com.myboot.dataprocess.util.RowkeyGenerator;

@Component
public class KafkaDataModelProcess {
	
	@Autowired
	private MyKafkaConfiguration myKafkaProducerConfig;
	
	public KafkaApplyCardEntity assembleKafkaData(String currentDate) {
		KafkaApplyCardEntity apply  = new KafkaApplyCardEntity();
		ProtocolEntity protocol = new ProtocolEntity();
		protocol.setType(myKafkaProducerConfig.getOtherParameter("protocol.type"));
		protocol.setVersion(myKafkaProducerConfig.getOtherParameter("protocol.version"));
		SchemaEntity schema = new SchemaEntity();
		schema.setNamespace(myKafkaProducerConfig.getOtherParameter("topic"));
		schema.setTableName(myKafkaProducerConfig.getOtherParameter("tablename"));
		apply.setProtocol(protocol);
		apply.setSchema(schema);
		apply.setTimestamp(System.currentTimeMillis());
		RowkeyGenerator generator = RowkeyGenerator.getInstance(currentDate);
		long sequence = generator.getSequence();
		ApplyCardEntity data = RandomDataModelBuilder.getRandomDataModel(sequence, currentDate);
		apply.setData(data);
		return apply;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
