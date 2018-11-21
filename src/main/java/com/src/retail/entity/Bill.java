package com.src.retail.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.print.DocFlavor.STRING;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="BILLS")
public class Bill {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bill_id")
	private long id;
	
	private int noOfItems;
	private double totalCost;

	private double totalTax;

	private double totalValue;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BillStatus billStatus;

	@OneToMany(cascade=CascadeType.REMOVE,fetch = FetchType.EAGER)
	@JoinColumn(name="bill_id")
	private List<CartItem> cartitem;

	public Bill(int noOfItems, double totalValue, BillStatus billStatus) {
		super();
		this.noOfItems = noOfItems;
		this.totalValue = totalValue;
		this.billStatus = billStatus;
		cartitem=new CopyOnWriteArrayList();
	}
	
	public void addItem(CartItem newitem) {
		cartitem.add(newitem);
	}
	
	public void removeItem(CartItem newitem) {
		cartitem.remove(newitem);
	}


	public Bill() {
		super();
	
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
	}

	public List<CartItem> getCartitem() {
		return cartitem;
	}

	public void setCartitem(List<CartItem> cartitem) {
		this.cartitem = cartitem;
	}
	
	


	
	
}
