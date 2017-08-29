package com.cache.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户profile，用于session保存用户信息
 * @author arlen.yeh
 */
public class Profile {
	private String id;
	private String openid;
	private String scid;
	private String cmmyId;
	private String buildingId;
	private String houseId;
	private String mobile;
	private String smcId;
	private Map<String, String> map;
	private String cityId;
	private String smcOpenid;
	private String suprId;
	private int system;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	
	public String getScid() {
		return scid;
	}
	public void setScid(String scid) {
		this.scid = scid;
	}
	public String getCmmyId() {
		return cmmyId;
	}
	public void setCmmyId(String cmmyId) {
		this.cmmyId = cmmyId;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getSystem() {
		return system;
	}
	public void setSystem(int system) {
		this.system = system;
	}
	public void logout(){
		this.setId(null);
		this.setScid(null);
		this.setMobile(null);
		this.setCmmyId(null);
		this.setBuildingId(null);
		this.setHouseId(null);
		this.setCityId(null);
	}
	public void Smclogout(){
		this.setSmcId(null);
		this.setSmcOpenid(null);
		this.setSuprId(null);
	}
	public Map<String, String> getMap() {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;
	}
	public String getRegCode() {
		return getMap().get("regCode");
	}
	public void setRegCode(String regCode) {
		getMap().put("regCode", regCode);
	}
	public void setAttribute(String key, String value) {
		getMap().put(key, value);
	}
	public String getAttribute(String key) {
		return getMap().get(key);
	}
	public String getSmcId() {
		return smcId;
	}
	public void setSmcId(String smcId) {
		this.smcId = smcId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getSmcOpenid() {
		return smcOpenid;
	}
	public void setSmcOpenid(String smcOpenid) {
		this.smcOpenid = smcOpenid;
	}
	public String getSuprId() {
		return suprId;
	}
	public void setSuprId(String suprId) {
		this.suprId = suprId;
	}
	
}
