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
		Session currentsession=sessionfactory.getCurrentSession();
		
		Query<Bill> query=currentsession.createQuery("from Bill order by 1");
		
		List<Bill> bills=query.getResultList();
		
		return bills;
	}

	@Override
	public Bill getbill(long billid) {
		Bill tempbill;
		Session currentsession =sessionfactory.getCurrentSession();
		tempbill=currentsession.get(Bill.class,billid);
		return tempbill;
	}

	@Override
	public void addBill(Bill bill) {
		
		
		Session currentsession=sessionfactory.getCurrentSession();
		
		currentsession.saveOrUpdate(bill);
		
	}
	
	@Override
	public void deleteBill(long theId) {
	
		Session currentSession = sessionfactory.getCurrentSession();
		
		
		Bill entity = currentSession.load(Bill.class,theId);
		currentSession.delete(entity);
		
		
	}
	
	
	
	
	
	
}
