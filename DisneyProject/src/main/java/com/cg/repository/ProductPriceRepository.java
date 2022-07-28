package com.cg.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.model.Product;
import com.cg.model.ProductPrice;

@Repository
public class ProductPriceRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public ProductPrice save(ProductPrice productPrice) {
		String sqlQuery = "insert into product_price(code, validFromDate, validToDate, price) " + "values (?,?,?,?)";
		
		jdbcTemplate.update(sqlQuery,
				productPrice.getCode(),
				productPrice.getValidFromDate(),
				productPrice.getValidToDate(),
		        productPrice.getPrice());
		return productPrice;
		
   
	}
	
	public void saveProductPrices(List<ProductPrice> productPrice) {
		String sqlQuery = "insert into product_price(pid, validFromDate, validToDate, price, code) "+" values (?,?,?,?,?)";
		
		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, productPrice.get(i).getPid());
				ps.setDate(2, productPrice.get(i).getValidFromDate());
				ps.setDate(3, productPrice.get(i).getValidToDate());
				ps.setInt(4, productPrice.get(i).getPrice());
				ps.setInt(5, productPrice.get(i).getCode());
				
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return productPrice.size();
			}
		});
	}
	
	public List<ProductPrice> getAll() {
		return jdbcTemplate.query("select * from product_price", this::mapRowToProductPrice);
		
	}
	
	public List<ProductPrice> getAllByCode(int code) {
		String sqlQuery = "select * from product where code = ?";
		return jdbcTemplate.query("select * from product_price where code=?", this::mapRowToProductPrice, code);

	}
	
	public void updateProductPrice(ProductPrice productPrice) {
		String sqlQuery = "update product_price set code = ?, validFromDate = ? , where validToDate = ?, price = ?, where pid = ?";
		jdbcTemplate.update(sqlQuery,
				productPrice.getCode(),
				productPrice.getValidFromDate(),
				productPrice.getValidToDate(),
		        productPrice.getPrice(),
		        productPrice.getPid());
	}
	
	public void deleteProductByCode(int code) {
		jdbcTemplate.update("delete from product where code = ? ", code);
	}
	
	public void deleteProductPriceById(int pid) {
		jdbcTemplate.update("delete from product_price where pid = ? ", pid);
	}

	
	private ProductPrice mapRowToProductPrice(ResultSet resultSet, int rowNum) throws SQLException {
		return ProductPrice.builder()
				.pid(resultSet.getInt("pid"))
				.code(resultSet.getInt("code"))
				.validFromDate(resultSet.getDate("validFromDate"))
				.validToDate(resultSet.getDate("validToDate"))
				.price(resultSet.getInt("price"))
				.build();
	}

	public boolean existById(int code) {
		// TODO Auto-generated method stub
		return false;
	}

}
