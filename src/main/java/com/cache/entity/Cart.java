package com.cache.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cache.common.JsonYmdhmsSerializer;


/**
 * 购物车
 * @author liyc
 *
 */
public class Cart implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;//用户id
	private String shelId;//销售id
	private int num;//数量
	private int state;//状态
	private String goodsId;//商品id
	private boolean isValid;//是否有效
	private Date time;//时间
	
	private String uname;//用户名
	private String cityId;//城市id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getShelId() {
		return shelId;
	}
	public void setShelId(String shelId) {
		this.shelId = shelId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	@JsonSerialize(using = JsonYmdhmsSerializer.class)
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}
