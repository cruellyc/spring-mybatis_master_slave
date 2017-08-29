package com.cache.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cache.common.BaseLst;
import com.cache.common.JSONP;
import com.cache.entity.Cart;
import com.cache.services.ICart;


/**
 * 购物车
 * @author liyc
 *
 */
@Controller
@RequestMapping("cart")
public class CartCtl {
	@Autowired
	private ICart cartService;
	private Logger logger = Logger.getLogger(this.getClass());
	/**加载购物车列表*/
	@ResponseBody
	@RequestMapping("loadCart")
	public JSONP<BaseLst<Cart>> loadCart(@RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pCount", required = false) Integer pCount,
			@RequestParam(value = "uid", required = false) String uid,
			@RequestParam(value = "pNow", required = false) Integer pNow){
		logger.info("loadCart() start");

		try {
			BaseLst<Cart> lst=cartService.loadCartList(uid, pNow, pCount);
			JSONP<BaseLst<Cart>> jsonp=new JSONP<BaseLst<Cart>>(callback,"查询成功！",true);
			jsonp.setCont(lst);
			logger.info("loadCart() end");
			return jsonp;
		} catch (Exception e) {
			logger.error("loadCart() error:"+e.getMessage());
			return new JSONP<BaseLst<Cart>>(callback,"加载失败",false);
		}
	}
	/**加入购物车*/
	@ResponseBody
	@RequestMapping("addCart")
	public JSONP<String> addCart(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam(value = "uid", required = true) String uid){
		logger.info("addCart() start");
		try {
			int n=cartService.addCart(uid);
			logger.info("addCart() end");
			if(n>0){
				return new JSONP<String>(callback,"添加成功！",true);
			}else{
				return new JSONP<String>(callback,"添加失败！",false);
			}
		} catch (Exception e) {
			logger.error("addCart() error:"+e.getMessage());
			return new JSONP<String>(callback,"添加失败",false);
		}
	}
	/**修改购物车*/
	@ResponseBody
	@RequestMapping("updateCart")
	public JSONP<String> updateCart(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam(value = "state", required = true) int state,
			@RequestParam(value = "uid", required = true) String uid){
		logger.info("updateCart() start");
		try {
			int n=cartService.updateCart(uid, state);
			logger.info("updateCart() end");
			if(n>0){
				return new JSONP<String>(callback,"修改成功！",true);
			}else{
				return new JSONP<String>(callback,"修改失败！",false);
			}
		} catch (Exception e) {
			logger.error("updateCart() error:"+e.getMessage());
			return new JSONP<String>(callback,"修改失败",false);
		}
	}
}
