package com.example.demo.Services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Bean.OrderBean;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Utils.Response;

import jakarta.persistence.criteria.Order;

@Service
public class ProductServiceImpl implements ProductService{
	
	 @Autowired
	    private OrderClient orderClient;
	private final RestTemplate restTemplate;
	@Autowired
    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBean> getOrdersList(long productId) {
		
		

        String url = "http://localhost:8082/getOrderListByProduct/" + productId;
        
        	@SuppressWarnings("rawtypes")
			ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
        Response<OrderBean> apiResponse = response.getBody();
        if (apiResponse!=null) {
			return (List<OrderBean>) apiResponse.getData();
		}
        else {
            return Collections.emptyList();
        }
        
      
        
       
        
        
        
       
    
	}

	@Override
	public OrderBean getOrderById(int orderId) {
		OrderBean result=orderClient.getProductById(orderId);
		System.out.println(result);
		return result;
	}

	
}
