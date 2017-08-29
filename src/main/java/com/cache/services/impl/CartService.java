package com.cache.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cache.IDao.CartDao;
import com.cache.common.BaseLst;
import com.cache.entity.Cart;
import com.cache.services.ICart;


/**
 * 购物车
 * @author liyc
 *
 */
@Service
public class CartService implements ICart {
	@Autowired
	private CartDao cartDao;
	private Logger logger = Logger.getLogger(this.getClass());
	/**加载购物车列表*/
	@Cacheable(value="userCache",key="#uid")//缓存
	public BaseLst<Cart> loadCartList(String uid,Integer pNow, Integer pCount) {
		if(pNow==null){
			pNow=0;
			pCount=0;
		}
		if(pNow>0&&pCount>0){
			pNow=(pNow-1)*pCount;
		}
		
		BaseLst<Cart> lst=new BaseLst<>();
		int[] state={1,2};
		List<Cart> list=cartDao.loadCartList(uid, state, pNow, pCount);
		lst.setLst(list);
		lst.setTotal(cartDao.getTotal(uid, state));
		
		logger.info("*****"+new Date().getTime());
		return lst;
	}
	@Override
	public int updateCart(String uid, int state) {
		int n=cartDao.updateCartState(uid, state);
		return n;
	}
	@Override
	@CacheEvict(value="userCache",key="#uid",beforeInvocation=true)//清除缓存
	public int addCart(String uid) {
		Cart cart=new Cart();
		cart.setId(UUID.randomUUID().toString());
		cart.setCityId("1");
		cart.setGoodsId("7");
		cart.setShelId("6");
		cart.setNum(1);
		cart.setState(1);
		cart.setTime(new Date());
		cart.setUid(uid);
		cart.setValid(true);
		int n=cartDao.addCart(cart);
		return n;
	}
	

}
