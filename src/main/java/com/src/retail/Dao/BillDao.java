package com.src.retail.Dao;

import java.util.List;

import com.src.retail.entity.Bill;

public interface BillDao {

	
	public List<Bill> getallbills();
	
	public Bill getbill(long billid);
	
	public void addBill(Bill bill);
	
	public void deleteBill(long theId) ;
}
