package com.example.demo.Services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Bean.OrderBean;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Utils.Response;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private OrderClient orderClient;
	private final RestTemplate restTemplate;

	@Autowired
	public ProductServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Autowired
	private ProductRepository prorepo;

	@Value("${order.service.url}")
	private String orderServiceUrl;

	@Override
	public Product saveProduct(Product product) {

		return prorepo.save(product);
	}

	@Override
	public List<Product> getProductsByUserId(long userId) {
		List<Product> res = prorepo.getProductsByUserId(userId);

		return res;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	@CircuitBreaker(name = "productServiceCircuitBreaker", fallbackMethod = "productFallback")
//	public List<OrderBean> getOrdersList(long productId) {
//
//		String url = orderServiceUrl + "/getOrderListByProduct/" + productId;
//
//		@SuppressWarnings("rawtypes")
//		ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
//		Response<OrderBean> apiResponse = response.getBody();
//		if (apiResponse != null) {
//			return (List<OrderBean>) apiResponse.getData();
//		} else {
//			return Collections.emptyList();
//		}
//
//	}

	@Override
	public OrderBean getOrderById(int orderId) {
		OrderBean result = orderClient.getProductById(orderId);
		System.out.println(result);
		return result;
	}

	// Circuit breaker configuration
	@CircuitBreaker(name = "testCircuitBreaker", fallbackMethod = "fallback")
	public String unreliableService() {
		// Simulate an external service call that may fail
		simulateFailure();
		return "Service response";
	}

	// Simulated external failure
	private void simulateFailure() {
		// This method always throws an exception to simulate a failure
		throw new RuntimeException("Simulated external service failure sdfgdsgds");
	}

	// Fallback method
	public String fallback(Throwable throwable) {
		// Handle the fallback response
		return "Fallback response due to: " + throwable.getMessage();
	}

	@Override
	@CircuitBreaker(name = "productServiceCircuitBreaker", fallbackMethod = "productFallback")
	public List<OrderBean> getOrdersList(long productId) {
		String url = orderServiceUrl + "/getOrderListByProduct/" + productId;

		try {
			// Make the API call using RestTemplate
			ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);

			// Check if the response body is not null
			if (response.getBody() != null) {
				Response<OrderBean> apiResponse = response.getBody();
				return apiResponse != null ? (List<OrderBean>) apiResponse.getData() : Collections.emptyList();
			} else {
				return Collections.emptyList(); // Return empty list if response body is null
			}
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			// Handle client/server errors (e.g., 4xx and 5xx responses)
			throw new RuntimeException("Error occurred while fetching order list: " + e.getMessage(), e);
		} catch (Exception e) {
			// Handle any other exceptions that may occur
			throw new RuntimeException("An unexpected error occurred: " + e.getMessage(), e);
		}

	}

	public List<OrderBean> productFallback(long productId, Throwable throwable) {
		// Handle fallback logic, e.g., returning a default value or logging the error
		System.out.println("Fallback method called due to: " + throwable.getMessage());

		// Return an empty list or a default response
		OrderBean defaultOrder = new OrderBean();
		defaultOrder.setOrderStatus("Service Down");
		return Collections.singletonList(defaultOrder);
	}

}
