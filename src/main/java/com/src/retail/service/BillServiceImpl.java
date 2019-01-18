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
import com.src.retail.entity.ProductCategory;
import com.src.retail.exception.ItemremoveException;
import com.src.retail.rest.controller.BillUpdateInfo;

@Service
public  class BillServiceImpl implements BillService{
	
	@Autowired
	private BillDao billdao;

	@Autowired
	private ProductDao productdao;
	
	@Autowired
	private CarItemDao cartitemdao;
	
	private static final int TAXTYPE_A=10;
	private static final int TAXTYPE_B=20;
	private static final int TAXTYPE_C=0;
	
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
	
		Bill singleBill=billdao.getbill(billid);
		return singleBill;
	}

	@Override
	@Transactional
	public void addBill(Bill newbill) {
		billdao.addBill(newbill);
	}

	
	private  Bill addtoCart(Bill bill,CartItem cartitemtoAdd,BillUpdateInfo billinfo,Product producttoadd) {
		
		double taxamount=0;
		//Add  product in Cartitem substract stcockitem
		if (cartitemtoAdd==null)
			cartitemtoAdd=new CartItem();
		else 
			bill.removeItem(cartitemtoAdd);
		
		cartitemtoAdd.setProduct(producttoadd);
		cartitemtoAdd.setQuantity(cartitemtoAdd.getQuantity()+billinfo.getQuantity());
		
		//Add Cartitem in Bill || Update no of items || total cost||  total tax || total  value
		bill.addItem(cartitemtoAdd);
		bill.setNoOfItems(bill.getNoOfItems()+billinfo.getQuantity());
		bill.setTotalCost(bill.getTotalCost()+ billinfo.getQuantity()*producttoadd.getRate());
		
		if (producttoadd.getProductCategory().name().equals(ProductCategory.A))
		taxamount=billinfo.getQuantity()*producttoadd.getRate()*(ProductCategory.A.value())/100;	
		
		else if (producttoadd.getProductCategory().name().equals(ProductCategory.A))
		taxamount=billinfo.getQuantity()*producttoadd.getRate()*ProductCategory.B.value()/100;		
							
		
		bill.setTotalTax(bill.getTotalTax() + taxamount);
		bill.setTotalValue(bill.getTotalCost()+bill.getTotalTax());
		
		
		cartitemdao.saveCartItem(cartitemtoAdd);
		billdao.addBill(bill);
		
		return bill;
	}
	
	private Bill removeCartItem(Bill tempbill,CartItem cartitemtoAdd,BillUpdateInfo billinfo,Product producttoadd){
		
		double taxamount=0;
		
		tempbill.removeItem(cartitemtoAdd);
		
		cartitemtoAdd.setProduct(producttoadd);
		cartitemtoAdd.setQuantity(cartitemtoAdd.getQuantity()-billinfo.getQuantity());
		
		//Add Cartitem in Bill || Update no of items || total cost||  total tax || total  value
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
	
		
		return tempbill;
	}
	
	@Override
	@Transactional
	public Bill updateBill(BillUpdateInfo billinfo)  {
		Bill tempbill;
	try {
			
		CartItem cartitemtoAdd=null;
		tempbill=billdao.getbill(billinfo.getId());
		
		Product producttoadd=productdao.getProduct(billinfo.getBarcode());
		
		for (CartItem cart:tempbill.getCartitem())
		{
			
			if (cart.getProduct().getBarCodeId().equals(producttoadd.getBarCodeId()))
			{	
				
				cartitemtoAdd=cartitemdao.getCartItem(cart.getId()); 
				
			}
			
		}
		
		
		if(tempbill.getBillStatus().name().equals(BillStatus.INPROGRESS.name()) ) {
			
			if(billinfo.getOperation().equals("ADD"))	
				tempbill=addtoCart(tempbill,cartitemtoAdd,billinfo,producttoadd);
			
			else if(billinfo.getOperation().equals("REMOVE")) {
				if (cartitemtoAdd==null) {
					System.out.println("In ItemremoveException if (cartitemtoAdd==null) ");
					throw new ItemremoveException("No item present to be removed");
				}
			tempbill=removeCartItem(tempbill,cartitemtoAdd,billinfo,producttoadd);
			}
				
			if (billinfo.getBillstatus().equals(BillStatus.COMPLETED))
				tempbill.setBillStatus(BillStatus.COMPLETED);
			else
				tempbill.setBillStatus(BillStatus.INPROGRESS);
			
			
			}
		}catch(Exception ex) {
			throw ex;
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
