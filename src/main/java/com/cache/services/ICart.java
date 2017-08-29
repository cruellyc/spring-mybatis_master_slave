package com.cache.services;


import com.cache.common.BaseLst;
import com.cache.entity.Cart;

/**
 * 购物车
 * @author liyc
 *
 */
public interface ICart {
	/**加载购物车列表*/
	BaseLst<Cart> loadCartList(String uid,Integer pNow,Integer pCount);
	/**修改状态*/
	int updateCart(String uid,int state);
	/**加入购物车*/
	int addCart(String uid);

}
