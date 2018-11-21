package com.src.retail.service;

import java.util.List;

import com.src.retail.entity.Bill;
import com.src.retail.rest.controller.BillUpdateInfo;

public interface BillService {

	public List<Bill> getallbills();
	
	public Bill getbill(long billid);
	
	public void addBill(Bill newbill);
	
	public Bill updateBill(BillUpdateInfo billinfo);
	
	public void deleteBill(long theId);
	
	
}
