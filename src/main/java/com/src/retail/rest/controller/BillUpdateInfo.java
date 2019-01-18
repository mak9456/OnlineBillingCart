package com.src.retail.rest.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.src.retail.entity.BillStatus;

public class BillUpdateInfo {

	@NotNull
	private long id;
	
	
	private String operation;
	
	@Size(min=4 , max=30)
	private String barcode;
	
	private int quantity;
	
	private BillStatus billstatus;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quanitity) {
		this.quantity = quanitity;
	}

	public BillStatus getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(BillStatus billstatus) {
		this.billstatus = billstatus;
	}
	
	
	
	
}
