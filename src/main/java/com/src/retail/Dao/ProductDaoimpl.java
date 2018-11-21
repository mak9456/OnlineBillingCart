package com.src.retail.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.src.retail.entity.CartItem;
import com.src.retail.entity.Product;

@Repository
public class ProductDaoimpl implements ProductDao {

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		Session currentsession=sessionfactory.getCurrentSession();
		
		Query<Product> thequery=currentsession.createQuery("from Product order by 2 ",Product.class);
		
		List<Product> tempproducts=thequery.getResultList();
		
		return tempproducts;
	}

	
	@Override
	@Transactional
	public void saveProduct(Product product) {
		// TODO Auto-generated method stub
		System.out.println("In Save product Dao");
		Session currentsession;
			try {
				 currentsession=sessionfactory.getCurrentSession();
			} catch (Exception e) {
				// TODO: handle exception
				 currentsession=sessionfactory.openSession();
			}
		
		
		currentsession.saveOrUpdate(product);
		
	}

	@Override
	public Product getProduct(String theId) {
		// TODO Auto-generated method stub
		Product product;
		Session currentsession=sessionfactory.getCurrentSession();
		org.hibernate.Query<Product> thequery=currentsession.createQuery("from Product where barCodeId=? ",Product.class);
		thequery.setParameter(0,theId);
		product= thequery.getSingleResult();
		
		return product;
	}

	@Override
	public void deleteProduct(long theId) {
		// TODO Auto-generated method stub
		Session currentsession=sessionfactory.getCurrentSession();
		
		org.hibernate.Query<Product> thequery=currentsession.createQuery("from Product where id=? ",Product.class);
		thequery.setParameter(0, theId);
		thequery.executeUpdate();
	}

	
	
	
}
