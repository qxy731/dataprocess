package com.myboot.dataprocess.kafka;

import com.myboot.dataprocess.model.ApplyCardEntity;
import com.myboot.dataprocess.model.KafkaApplyCardEntity;
import com.myboot.dataprocess.model.ProtocolEntity;
import com.myboot.dataprocess.model.SchemaEntity;
import com.myboot.dataprocess.util.RandomDataModelBuilder;
import com.myboot.dataprocess.util.RowkeyGenerator;

public class KafkaDataModelProcess {
	
	public static KafkaApplyCardEntity assembleKafkaData(String currentDate) {
		KafkaApplyCardEntity apply  = new KafkaApplyCardEntity();
		ProtocolEntity protocol = new ProtocolEntity();
		protocol.setType("data_increment_data");
		protocol.setVersion("1.0");
		SchemaEntity schema = new SchemaEntity();
		schema.setNamespace("datacenter.sfz.ins.opaincome");
		schema.setTableName("hbs_trans_log_act");
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
