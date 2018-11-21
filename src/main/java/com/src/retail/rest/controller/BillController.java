package com.src.retail.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.src.retail.Dao.BillDao;
import com.src.retail.Dao.CarItemDao;
import com.src.retail.Dao.CartitemDaoImpl;
import com.src.retail.Dao.ProductDao;
import com.src.retail.entity.Bill;
import com.src.retail.entity.BillStatus;
import com.src.retail.entity.CartItem;
import com.src.retail.entity.Product;
import com.src.retail.entity.ProductCategory;
import com.src.retail.exception.BillNotFound;
import com.src.retail.service.BillService;

@RestController
public class BillController {

	@Autowired
	private BillService billservice;
	
	@Autowired
	private ProductDao productdao;
	
	@Autowired
	private CarItemDao cartitemdao;
	
	
	@GetMapping("/bills")
	public List<Bill> getAllBills(){
		List<Bill> tempbills=billservice.getallbills();
		return tempbills;
	}
	
	
	@GetMapping("/bills/{billid}")
	public Bill getBill(@PathVariable long billid){
		Bill singleBill=billservice.getbill(billid);
		
		if(singleBill==null|| singleBill.getId()==0)
			throw new BillNotFound("Bill Not Found");
		
		return singleBill;
	}
	
	@PostMapping("/bills")
	public Bill addBill(){
		
		Bill newbill=new Bill(0,0,BillStatus.INPROGRESS);
		
		billservice.addBill(newbill);
		return newbill;
	}
	
	
	@PutMapping("/bills")
	public Bill updateBill(@RequestBody BillUpdateInfo billinfo) {
		Bill updatebill=billservice.updateBill(billinfo);	
		return updatebill;
	}
	
	@DeleteMapping("/bills/{billid}")
	public Bill deleteBill(@PathVariable long billid) {
		Bill deletedbill=billservice.getbill(billid);
		if(deletedbill==null|| deletedbill.getId()==0)
			throw new BillNotFound("Bill Not Found");
		
		billservice.deleteBill(billid);	
		return deletedbill;
	}
		
}
