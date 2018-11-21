package com.src.retail.Dao;

import java.util.List;

import com.src.retail.entity.CartItem;
import com.src.retail.entity.Product;

public interface CarItemDao {

	public List<CartItem> getCartItems();

	public void saveCartItem(CartItem cartitem);

	public CartItem getCartItem(long theId);

	public void deleteCartItem(long theId);
	
	
}
