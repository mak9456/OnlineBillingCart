package com.src.retail.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.retail.Dao.BillDao;
import com.src.retail.Dao.BillDaoImpl;
import com.src.retail.Dao.CarItemDao;
import com.src.retail.Dao.ProductDao;
import com.src.retail.entity.Bill;
import com.src.retail.entity.BillStatus;
import com.src.retail.entity.CartItem;
import com.src.retail.entity.Product;
import com.src.retail.rest.controller.BillUpdateInfo;

@Service
public  class BillServiceImpl implements BillService{
	
	@Autowired
	private BillDao billdao;

	@Autowired
	private ProductDao productdao;
	
	@Autowired
	private CarItemDao cartitemdao;
	
	@Override
	@Transactional
	public List<Bill> getallbills() {
		// TODO Auto-generated method stub
		List<Bill> tempbills=billdao.getallbills();
		
		
		return tempbills;
	}

	@Override
	@Transactional
	public Bill getbill(long billid) {
		// TODO Auto-generated method stub
		Bill singleBill=billdao.getbill(billid);
		return singleBill;
	}

	@Override
	@Transactional
	public void addBill(Bill newbill) {
		// TODO Auto-generated method stub
		System.out.println("In service addbill");
		billdao.addBill(newbill);
	}

	@Override
	@Transactional
	public Bill updateBill(BillUpdateInfo billinfo) {
		
		double taxamount=0;
		CartItem cartitemtoAdd=null;
		Bill tempbill=billdao.getbill(billinfo.getId());
		//fetch product by product barcode
		Product producttoadd=productdao.getProduct(billinfo.getBarcode());
		for (CartItem cart:tempbill.getCartitem())
		{
			
			if (cart.getProduct().getBarCodeId().equals(producttoadd.getBarCodeId()))
			{	
				System.out.println("In for loop"+cart.getProduct().getBarCodeId());
				cartitemtoAdd=cartitemdao.getCartItem(cart.getId()); 
				
			}
			
		}
		
		System.out.println("tempbill:"+tempbill.getBillStatus().name());
		if(billinfo.getBillstatus().equals(BillStatus.INPROGRESS) && tempbill.getBillStatus().name().equals(BillStatus.INPROGRESS) ) {
			System.out.println("In progeess");
			tempbill.setBillStatus(BillStatus.INPROGRESS);
			if(billinfo.getOperation().equals("ADD")) {
				
				//Add  product in Lineitem substract stcockitem
				if (cartitemtoAdd==null)
				cartitemtoAdd=new CartItem();
				else 
					tempbill.removeItem(cartitemtoAdd);
				
				cartitemtoAdd.setProduct(producttoadd);
				cartitemtoAdd.setQuantity(cartitemtoAdd.getQuantity()+billinfo.getQuantity());
				
				//Add Lineitem in Bill || Update no of items || total cost||  total tax || total  value
				tempbill.addItem(cartitemtoAdd);
				tempbill.setNoOfItems(tempbill.getNoOfItems()+billinfo.getQuantity());
				tempbill.setTotalCost(tempbill.getTotalCost()+ billinfo.getQuantity()*producttoadd.getRate());
				
				if (producttoadd.getProductCategory().name().equals("A"))
				taxamount=billinfo.getQuantity()*producttoadd.getRate()*0.1;	
				else if (producttoadd.getProductCategory().name().equals("B"))
				taxamount=billinfo.getQuantity()*producttoadd.getRate()*0.2;		
									
				
				tempbill.setTotalTax(tempbill.getTotalTax() + taxamount);
				tempbill.setTotalValue(tempbill.getTotalCost()+tempbill.getTotalTax());
				
				System.out.println("CartItem Save");
				cartitemdao.saveCartItem(cartitemtoAdd);
				System.out.println("Bill Update");
				billdao.addBill(tempbill);
			}
			else if(billinfo.getOperation().equals("REMOVE")) {
			
				tempbill.removeItem(cartitemtoAdd);
				
				cartitemtoAdd.setProduct(producttoadd);
				cartitemtoAdd.setQuantity(cartitemtoAdd.getQuantity()-billinfo.getQuantity());
				
				//Add Lineitem in Bill || Update no of items || total cost||  total tax || total  value
				tempbill.addItem(cartitemtoAdd);
				tempbill.setNoOfItems(tempbill.getNoOfItems()-billinfo.getQuantity());
				tempbill.setTotalCost(tempbill.getTotalCost()-billinfo.getQuantity()*producttoadd.getRate());
				
				if (producttoadd.getProductCategory().values().equals("A"))
				taxamount=tempbill.getTotalCost()*0.1;	
				else if (producttoadd.getProductCategory().values().equals("B"))
				taxamount=tempbill.getTotalCost()*0.2;		
									
				
				tempbill.setTotalTax(tempbill.getTotalTax() - taxamount);
				tempbill.setTotalValue(tempbill.getTotalCost()+tempbill.getTotalTax());
				
				cartitemdao.saveCartItem(cartitemtoAdd);
				
				billdao.addBill(tempbill);
				
			}
		}
		else {
			tempbill.setBillStatus(BillStatus.COMPLETED);
		}
		
		
		return tempbill;
	}
	
	@Override
	@Transactional
	public void deleteBill(long billId) {
		// TODO Auto-generated method stub
		billdao.deleteBill(billId);
	}
}
