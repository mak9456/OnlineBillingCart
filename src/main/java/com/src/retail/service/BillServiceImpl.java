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

	@Override
	@Transactional
	public Bill updateBill(BillUpdateInfo billinfo) {
		
		double taxamount=0;
		CartItem cartitemtoAdd=null;
		Bill tempbill=billdao.getbill(billinfo.getId());
		
		Product producttoadd=productdao.getProduct(billinfo.getBarcode());
		for (CartItem cart:tempbill.getCartitem())
		{
			
			if (cart.getProduct().getBarCodeId().equals(producttoadd.getBarCodeId()))
			{	
				
				cartitemtoAdd=cartitemdao.getCartItem(cart.getId()); 
				
			}
			
		}
		
		
		if(billinfo.getBillstatus().equals(BillStatus.INPROGRESS) && tempbill.getBillStatus().name().equals(BillStatus.INPROGRESS.name()) ) {
			
			tempbill.setBillStatus(BillStatus.INPROGRESS);
			if(billinfo.getOperation().equals("ADD")) {
				
				//Add  product in Cartitem substract stcockitem
				if (cartitemtoAdd==null)
				cartitemtoAdd=new CartItem();
				else 
					tempbill.removeItem(cartitemtoAdd);
				
				cartitemtoAdd.setProduct(producttoadd);
				cartitemtoAdd.setQuantity(cartitemtoAdd.getQuantity()+billinfo.getQuantity());
				
				//Add Cartitem in Bill || Update no of items || total cost||  total tax || total  value
				tempbill.addItem(cartitemtoAdd);
				tempbill.setNoOfItems(tempbill.getNoOfItems()+billinfo.getQuantity());
				tempbill.setTotalCost(tempbill.getTotalCost()+ billinfo.getQuantity()*producttoadd.getRate());
				
				if (producttoadd.getProductCategory().name().equals("A"))
				taxamount=billinfo.getQuantity()*producttoadd.getRate()*TAXTYPE_A/100;	
				else if (producttoadd.getProductCategory().name().equals("B"))
				taxamount=billinfo.getQuantity()*producttoadd.getRate()*TAXTYPE_B/100;		
									
				
				tempbill.setTotalTax(tempbill.getTotalTax() + taxamount);
				tempbill.setTotalValue(tempbill.getTotalCost()+tempbill.getTotalTax());
				
				
				cartitemdao.saveCartItem(cartitemtoAdd);
				billdao.addBill(tempbill);
			}
			else if(billinfo.getOperation().equals("REMOVE")) {
			
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
				
			}
		}
		else if (billinfo.getBillstatus().equals(BillStatus.COMPLETED)) {
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
