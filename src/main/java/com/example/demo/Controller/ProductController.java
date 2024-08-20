package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Product;
import com.example.demo.Services.ProductService;
import com.example.demo.Utils.Response;


@RestController
public class ProductController {

	
	@Autowired
	private ProductService productServices;
	
	@PostMapping("/saveProduct")
	public ResponseEntity<Response<?>> createProduct (@RequestBody Product product)
	{
		Response<?> response=new Response<>();
		Product pro=    productServices.saveProduct(product);
		if (pro!=null) {
			response.setResultCode("200");
			response.setMessage("Product Saved Successfully");
			response.setData(pro);
		}
		else
		{
			response.setResultCode("500");
			response.setMessage("Product Not Saved Successfully");
		}
		return ResponseEntity.ok(response);
		
	}
	

    @GetMapping("/products/user/{userId}")
    public List<Product> getProductsByUserId(@PathVariable long userId) {
        return productServices.getProductsByUserId(userId);
    }
}
