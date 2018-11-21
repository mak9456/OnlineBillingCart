package com.src.retail.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.src.retail.entity.Bill;

@Repository
public class BillDaoImpl implements BillDao {
	
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public List<Bill> getallbills() {
		
		return null;
	}

	@Override
	public Bill getbill(long billid) {
		Bill tempbill;
		Session currentsession =sessionfactory.getCurrentSession();
		System.out.println("Getting single bill In dao class");
		tempbill=currentsession.get(Bill.class,billid);
		return tempbill;
	}

	@Override
	public void addBill(Bill bill) {
		
		System.out.println("Adding bill In dao class");
		Session currentsession=sessionfactory.getCurrentSession();
		System.out.println("Adding bill via dao class");
		currentsession.saveOrUpdate(bill);
		
	}
	
	@Override
	public void deleteBill(long theId) {
	
		Session currentSession = sessionfactory.getCurrentSession();
		System.out.println("Delete after session bill In dao class");
		
		Bill entity = currentSession.load(Bill.class,theId);
		currentSession.delete(entity);
		
		
	}
	
	
	
	
	
	
}
