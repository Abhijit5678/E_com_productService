package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(nativeQuery = true,value = "SELECT * FROM  product where user_id=?1")
	List<Product> getProductsByUserId(long userId);

}
