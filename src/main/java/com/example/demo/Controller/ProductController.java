package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Bean.OrderBean;
import com.example.demo.Entity.Product;
import com.example.demo.Services.ProductService;
import com.example.demo.Utils.Response;

@RestController
public class ProductController {

	@Autowired
	private ProductService productServices;

	@PostMapping("/saveProduct")
	public Response<Product> createProduct(@RequestBody Product product) {

		Product pro = productServices.saveProduct(product);
		if (pro != null) {

			return Response.<Product>builder().status(HttpStatus.OK).message("Data Saved Successfully").build();
		}

		return Response.<Product>builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("SomeThings Wents Wrong")
				.build();

	}

	@GetMapping("/products/user/{userId}")
	public List<Product> getProductsByUserId(@PathVariable long userId) {
		return productServices.getProductsByUserId(userId);
	}

	@GetMapping("/orders/{productId}")
	public List<OrderBean> getOrdersList(@PathVariable long productId) {
		return productServices.getOrdersList(productId);
	}

//    @GetMapping("/orders/{productId}")
//    public List<OrderBean> getOrdersListByUser(@PathVariable long productId) {
//        return productServices.getOrdersList(productId);
//    }

	@GetMapping("/product/user/{userId}")
	public List<List<OrderBean>> getProductsListByUserId(@PathVariable long userId) {
		List<Long> collect = new ArrayList<>();
		List<List<OrderBean>> listorder = new ArrayList<>();
		List<Product> productsByUserId = productServices.getProductsByUserId(userId);
		if (!productsByUserId.isEmpty()) {
			collect = productsByUserId.stream().map(Product::getProductId).collect(Collectors.toList());

		}
		for (Long product : collect) {
			List<OrderBean> ordersList = productServices.getOrdersList(product);
			listorder.add(ordersList);
		}
		return listorder;
	}

	@GetMapping("/getOrderList/{orderId}")
	public Response<OrderBean> getorderList(@PathVariable("orderId") int orderId) {
		OrderBean order = productServices.getOrderById(orderId);

		if (order != null) {
			return Response.<OrderBean>builder().status(HttpStatus.OK).data(order).build();
		}

		return Response.<OrderBean>builder().status(HttpStatus.INTERNAL_SERVER_ERROR).data(order)
				.message("Data Not There").build();
	}

}
