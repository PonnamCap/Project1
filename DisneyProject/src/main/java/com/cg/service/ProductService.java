package com.cg.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.ProductAlreadyExists;
import com.cg.exception.ProductIdNotFoundException;
import com.cg.model.Product;
import com.cg.model.ProductPrice;
import com.cg.repository.ProductPriceRepository;
import com.cg.repository.ProductRepository;

@Service
public class ProductService {
	
	static Logger log = LoggerFactory.getLogger(ProductService.class.getName());
	@Autowired
	public ProductRepository productRepo;
	
	@Autowired
	public ProductPriceRepository productPriceRepo;
	
	//public Product addProduct(Product product) throws ProductAlreadyExists {
	//	productRepo.save(product);
	//	return product;
	//}
	
	
	public void addProductAndPrice(Product product) throws ProductAlreadyExists{
		if(!productRepo.existById(product.getCode())) {
			//Insert Product
			productRepo.save(product);
		}
		else {
			log.error("Failed to Add product but inserting Product Price");
			addProductPrices(product.getProductPrice());
			throw new ProductAlreadyExists();

		}
		log.info("Entered Product Price ..");
		addProductPrices(product.getProductPrice());
		
	}
	
	public Product updateProductAndPrice(Product product, int code) throws ProductAlreadyExists{
		if(productRepo.existById(code)) {
			//Insert Product
			productRepo.update(product);
			
			List<ProductPrice> productPriceList = new ArrayList<>();
			productPriceList = product.getProductPrice();
			for(ProductPrice i:productPriceList) {
				productPriceRepo.updateProductPrice(i);
			}
			log.info("Updated the product and product details of Product code:" + product.getCode());
			addProductPrices(product.getProductPrice());

		}
		else
			throw new ProductAlreadyExists("Product Code Does not Exist..");
		return product;
		
		
	}
	
	public Product updateProductDetails (int code, String description, boolean active) throws ProductIdNotFoundException {
		if(productRepo.existById(code)) {
			Product product = productRepo.getById(code);
			product.setDescription(description);
			product.setActive(active);
			return productRepo.update(product);
		}
		else 
			throw new ProductIdNotFoundException("Product code dcoes not exist..");
	}

	
	public Product addProduct(Product product) throws ProductAlreadyExists {
		// TODO Auto-generated method stub
		if(productRepo.existById(product.getCode())) {
			throw new ProductAlreadyExists();
		}
		Product savedProduct = productRepo.save(product);
		return savedProduct;
	}
	
	public void addProducts(List<Product> products) {
		productRepo.saveProducts(products);
	}
	
	public void addProductPrices(List<ProductPrice> productPrices) {
		productPriceRepo.saveProductPrices(productPrices);
	}

	
	public ProductPrice addProductPrice(ProductPrice productPrice) throws ProductAlreadyExists {
		// TODO Auto-generated method stub
		if(productPriceRepo.existById(productPrice.getCode())) {
			throw new ProductAlreadyExists();
		}
		ProductPrice savedProductPrice = productPriceRepo.save(productPrice);
		return savedProductPrice;
	}

	//public Product addProductPrice(ProductPrice productPrice) {
		//productPriceRepo.save(productPrice);
	//}
	
	public List<Product> getAllProduct() {
		return productRepo.getAll();
	}
	
	public List<ProductPrice> getAllProductPrice() {
		return productPriceRepo.getAll();
	}
	
	public Product getById(int code) {
		return productRepo.getById(code);
	}
	
	public List<ProductPrice> getByProductCode(int code) {
		return productPriceRepo.getAllByCode(code);
	}
	
	public void updateProduct(Product product, int code) throws ProductIdNotFoundException {
		if(productRepo.existById(code))
			productRepo.update(product);
		else 
			throw new ProductIdNotFoundException("Product Id Not Found");
	}
	
	public void updateProductPrice(ProductPrice productPrice, int code) throws ProductIdNotFoundException {
		if(productRepo.existById(code))
			productPriceRepo.updateProductPrice(productPrice);
		else 
			throw new ProductIdNotFoundException("Product Id Not Found");
	}

	
	public void deleteProductById ( int code) throws ProductIdNotFoundException{
		productRepo.deleteProductById(code);
	}
	
	public void deleteProductPriceById ( int code) throws ProductIdNotFoundException{
		productRepo.deleteProductPriceById(code);
	}

	public List<Product> getAllProductWithPrice() {

		return productRepo.getAllProductWithPrice();
	}
	
	public List<Product> getProductWithPriceByDate(Date date) throws  ProductIdNotFoundException {

		if(productRepo.getProductWithPriceByDate(date)==null) {
			log.error("No Product details available present at date :" + date);
			throw new ProductIdNotFoundException("No product at that date..");
		}
		return productRepo.getProductWithPriceByDate(date);
	}

	public List<Product> getAllProductWithPriceByCode(int code) {
		
		return productRepo.getAllProductWithPriceByCode(code);
	}




}
