package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository prorepo;
	
	
	
	@Override
	public Product saveProduct(Product product) {
		
		Product save = prorepo.save(product);
		return save;
	}

	@Override
	public List<Product> getProductsByUserId(long userId) {
		List<Product> res=	prorepo.getProductsByUserId(userId);
		
		return res;
	}

	
}
