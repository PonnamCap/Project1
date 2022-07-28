package com.cg.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.ProductAlreadyExists;
import com.cg.exception.ProductIdNotFoundException;
import com.cg.model.Product;
import com.cg.model.ProductPrice;
import com.cg.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ProductController {
	
	@Autowired
	public ProductService productService;
	
	@PostMapping("/addProductAndPrice")
	public void addProductAndPrice(@RequestBody Product product) throws ProductAlreadyExists{
		productService.addProductAndPrice(product);
	}
	

	@PostMapping("/addProduct")
	@ApiOperation(value = "Adding products", notes = "Here you can add the products to the product table", response = Product.class)
	public ResponseEntity<Object> addProduct(@RequestBody Product product) throws ProductAlreadyExists {
		Product savedproduct = productService.addProduct(product);
		return new ResponseEntity<Object>(savedproduct, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/addProducts")
	public void addProducts(@RequestBody List<Product> products) {
		productService.addProducts(products);
	}
	
	@PostMapping("/addProductPrices")
	public void addProductPrices(@RequestBody List<ProductPrice> productPrices) {
		productService.addProductPrices(productPrices);
	}
	
	
	@PostMapping("/addProductPrice")
	public ResponseEntity<Object> addProductPrice(@RequestBody ProductPrice productPrice) throws ProductAlreadyExists {
		ProductPrice savedproductPrice = productService.addProductPrice(productPrice);
		return new ResponseEntity<Object>(savedproductPrice, HttpStatus.CREATED);
		
	}
	
	//@PostMapping("/addProduct")
	//public void addProduct(@RequestBody Product product) {
	//	productService.addProduct(product);
	//}
	
	//@PostMapping("/addProductPrice")
	//public void addProductPrice(@RequestBody ProductPrice productPrice) {
	//	productService.addProductPrice(productPrice);
	//}
	
	@GetMapping("/findAllProduct")
	public List<Product> getAllProduct() {
		return productService.getAllProduct();
	}
	
	 @GetMapping("/findAllProductWithPrice")
	    public List<Product> getAllProductWithPrice(){
	        return productService.getAllProductWithPrice();
	    }

	    @GetMapping("/findAllProductWithPriceByCode/{code}")
	    public List<Product> getAllProductWithPriceByCode(@PathVariable int code){
	        return productService.getAllProductWithPriceByCode(code);
	    }
	    
	    @GetMapping("/findAllProductWithPriceByDate/{date}")
	    public List<Product> getProductWithPriceByDate(@PathVariable Date date) throws ProductIdNotFoundException{
	        return productService.getProductWithPriceByDate(date);
	    }


	    @GetMapping("/findAllProductPrice")
	    public List<ProductPrice> getAllProductPrice(){
	        return productService.getAllProductPrice();
	    }

	    @GetMapping("/findAllProductPriceByCode/{code}")
	    public List<ProductPrice> getAllProductPriceByCode(@PathVariable int code){
	        return productService.getByProductCode(code);
	    }

	    @GetMapping("/findProductById/{code}")
	    public Product getById(@PathVariable int code) {
	        return productService.getById(code);
	    }

	    @PutMapping("/updateProduct/{code}")
	    public void updateProduct(@RequestBody Product product, @PathVariable int code) throws ProductIdNotFoundException {
	    	productService.updateProduct(product, code);
	    }
	    
	    @PutMapping("/updateProductPrice/{code}")
	    public void updateProductPrice(@RequestBody ProductPrice productPrice, @PathVariable int code) throws ProductIdNotFoundException {
	    	productService.updateProductPrice(productPrice, code);
	    }
	    
	    @PutMapping("/updateProductAndPrice/{code}")
	    public void updateProductAndPrice(@RequestBody Product product, @PathVariable int code) throws  ProductAlreadyExists {
	    	productService.updateProductAndPrice(product, code);
	    }
	    
	    @PatchMapping("/updateProduct/{code}/{description}/{active}")
	    public void updateProductDetails(@PathVariable int code,@PathVariable String description,@PathVariable boolean active) throws ProductIdNotFoundException {
	    	productService.updateProductDetails(code,description,active);
	    }


	    @DeleteMapping("/delete/{code}")
	    public void deleteById(@PathVariable int code)  throws ProductIdNotFoundException{
	    	productService.deleteProductById(code);
	    }
	    
	    @DeleteMapping("/delet/{code}")
	    public void deleteByCode(@PathVariable int code) throws ProductIdNotFoundException {
	    	productService.deleteProductPriceById(code);
	    }

	}
