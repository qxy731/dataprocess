package com.myboot.dataprocess.hbase;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myboot.dataprocess.common.ErrorMessage;
import com.myboot.dataprocess.common.StatusInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Api
@RestController
@RequestMapping("myboot/hbase")
@Log4j2
public class HBaseController {
	
	@ApiOperation(value="HBase造数据", notes="HBase造数据")
	@RequestMapping(value = "/save", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public StatusInfo<String> save(HttpServletRequest request) {
		StatusInfo<String> spi = null;
    	try {
    		spi = new StatusInfo<String>("");
    	}catch(Exception e) {
			spi = new StatusInfo<>(ErrorMessage.msg_opt_fail);
			log.info(ErrorMessage.msg_opt_fail.getMsg());
		}
    	return spi ;
	}

}
