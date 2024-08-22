package com.example.demo.Services;

import java.util.List;

import com.example.demo.Bean.OrderBean;
import com.example.demo.Entity.Product;

public interface ProductService {

	Product saveProduct(Product product);

	List<Product> getProductsByUserId(long userId);

	List<OrderBean> getOrdersList(long productId);

}
