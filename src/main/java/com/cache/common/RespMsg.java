package com.cache.common;

import org.apache.log4j.Logger;

/**
 * 请求结果
 * @author arlen.yeh
 */
public class RespMsg<T> {
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean success;
	private int code;
	private String desc;
	private T cont;
	public RespMsg(){
	}
	public RespMsg(String msg){
		logger.debug("msg=" + msg);
		this.success = true;
		this.code = 0;
		this.desc = msg;
		this.cont = null;
	}
	public RespMsg(String msg, boolean success){
		logger.debug("msg=" + msg);
		this.success = success;
		this.code = (success?0:-1);
		this.desc = msg;
		this.cont = null;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public T getItems() {
		return cont;
	}
	public void setCont(T cont) {
		this.cont = cont;
	}
}
