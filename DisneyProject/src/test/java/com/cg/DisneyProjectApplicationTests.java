package com.cg;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.model.Product;
import com.cg.repository.ProductRepository;
import com.cg.service.ProductService;

@SpringBootTest
class DisneyProjectApplicationTests {

	@Test
	void contextLoads() {
	}
	
	 @Autowired
	    private ProductService service;

	    @Autowired
	    private ProductRepository pRepo;
	    
	    @Test
	    void readAllProuct() {
	        List<Product> list = service.getAllProduct();
	        assertThat(list).size().isGreaterThan(0);
	    }
	
	

}
