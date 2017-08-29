package com.cache.common;

import java.io.Serializable;
import java.util.List;
/**
 * 公用数据列表与总记录数
 * @author Administrator
 *
 * @param <T>
 */
public class BaseLst<T> implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	private List<T> lst;
	private long total;
	public List<T> getLst() {
		return lst;
	}
	public void setLst(List<T> lst) {
		this.lst = lst;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
}
