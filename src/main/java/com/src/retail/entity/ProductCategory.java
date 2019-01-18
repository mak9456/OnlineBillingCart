package com.src.retail.entity;

public enum ProductCategory {
	A(10,"A"), B(20,"B"), C(0,"C");
	
	private final double value;
	private final String productType;
	
	
	private ProductCategory(int value, String productType) {
		this.value = value;
		this.productType = productType;
	}
	
	
	public double value() {
		return this.value;
	}
	
	
}
