package com.src.retail.Dao;

import java.util.List;

import javax.management.Query;
import javax.persistence.criteria.Selection;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.src.retail.entity.CartItem;

@Repository
@Transactional
public class CartitemDaoImpl implements CarItemDao {


	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public List<CartItem> getCartItems() {
		Session currentsession=sessionfactory.getCurrentSession();
		
		org.hibernate.Query<CartItem> thequery=currentsession.createQuery("from CartItem order by 2 ",CartItem.class);
		
		List<CartItem> tempcartitems=thequery.getResultList();
		
		return tempcartitems;
	}

	@Override
	public void saveCartItem(CartItem cartitem) {
		// TODO Auto-generated method stub
		System.out.println("In Saving cart");
		Session currentsession=sessionfactory.getCurrentSession();
		
		currentsession.saveOrUpdate(cartitem);
		
	}

	@Override
	public CartItem getCartItem(long theId) {
		// TODO Auto-generated method stub
		CartItem cartitem;
		Session currentsession=sessionfactory.getCurrentSession();
		
		cartitem=currentsession.get(CartItem.class, theId);
		
		return cartitem;
	}

	@Override
	public void deleteCartItem(long theId) {
		// TODO Auto-generated method stub
		Session currentsession=sessionfactory.getCurrentSession();
		
		org.hibernate.Query<CartItem> thequery=currentsession.createQuery("from CartItem where id=? ",CartItem.class);
		thequery.setParameter(0, theId);
		thequery.executeUpdate();
		
		
	}

}
