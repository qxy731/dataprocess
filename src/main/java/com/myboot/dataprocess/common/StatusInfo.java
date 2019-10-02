package com.myboot.dataprocess.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "状态消息信息", description = "状态消息信息")
public class StatusInfo<T> {
	
	@ApiModelProperty(value = "成功或异常消息描述", required = true)
    private String message;

    @ApiModelProperty(value = "请求或任务执行状态", required = true)
    private String status;
    
    @ApiModelProperty(value = "对象数据", required = false)
	private T data;
    
    public StatusInfo() {
        this.status = ErrorMessage.m0.getMsgCode();
        this.message = ErrorMessage.m0.getMsg();
    }
    
    public StatusInfo(String message,String status) {
    	this.status = status;
        this.message = message;
        this.data = null;
    }
    
	public StatusInfo(ErrorMessage errMsg) {
		this.message = errMsg.getMsg();
		this.status = errMsg.getMsgCode();
		this.data = null;
	}

    public StatusInfo(T data) {
    	this.message = ErrorMessage.m0.getMsg();
    	this.status = ErrorMessage.m0.getMsgCode();
        this.data = data;
    }
    
    public StatusInfo(String message,String status,T data) {
    	this.message = message;
    	this.status = status;
        this.data = data;
    }
    
}