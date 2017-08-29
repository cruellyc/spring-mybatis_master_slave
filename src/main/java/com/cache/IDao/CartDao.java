package com.cache.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cache.dataSource.DataSource;
import com.cache.entity.Cart;


/**
 * 购物车
 * @author liyc
 *
 */
@Repository
public interface CartDao {
	/**加载购物车列表*/
	@DataSource("slave")
	public List<Cart> loadCartList(@Param("uid")String uid,@Param("state")int[] state,
			@Param("pNow")int pNow,@Param("pCount")int pCount);
	/**购物车记录条数*/
	@DataSource("slave")
	public int getTotal(@Param("uid")String uid,@Param("state")int[] state);
	
	/**修改购物车状态*/
	@DataSource("master")
	public int updateCartState(@Param("id")String id,@Param("state")int state);
	/**加入购物车*/
	@DataSource("master")
	public int addCart(Cart cart);
}
