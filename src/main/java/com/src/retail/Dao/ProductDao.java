package com.src.retail.Dao;

import java.util.List;

import com.src.retail.entity.Product;

public interface ProductDao {

	public List<Product> getProducts();

	public void saveProduct(Product product);

	public Product getProduct(String theId);

	public void deleteProduct(long theId);
}
