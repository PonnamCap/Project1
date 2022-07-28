
package com.cg.repository;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import com.cg.model.Product;
import com.cg.model.Product.ProductBuilder;
import com.cg.model.ProductPrice;

@Repository
public class ProductRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Product save(Product product) {
		String sqlQuery = "insert into product(code, description, active) " + "values (?,?,?)";
		
		jdbcTemplate.update(sqlQuery,
				product.getCode(),
		        product.getDescription(),
		        product.isActive());
		return product;
	}
	
	public void saveProducts(List<Product> product) {
		String sqlQuery = "insert into product(code, description, active) "+" values (?,?,?)";
		
		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, product.get(i).getCode());
				ps.setString(2, product.get(i).getDescription());
				ps.setBoolean(3, product.get(i).isActive());


				
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return product.size();
			}
		});
	}
	
	public List<Product> getAll() {
		return jdbcTemplate.query("select * from product", this::mapRowToProduct);
		
	}
	
	public Product getById(int code) throws DataAccessException {
		String sqlQuery = "select * from product where code = ?";
         
		Product product = null;
		
		try {
			product = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToProduct, code);
		} catch (DataAccessException e) {
			e.getMessage();
		}
		return product;
	}
	
	
	public  Product update(Product product) {
		String sqlQuery = "update product set description = ?, active = ? where code = ?";
		jdbcTemplate.update(sqlQuery,
		        product.getDescription(),
		        product.isActive(),
				product.getCode());
		
		return product;

	}
		
	
	public void deleteProductById(int code) {
		jdbcTemplate.update("delete from product where code = ? ", code);
	}
	
	public void deleteProductPriceById(int code) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("delete from product_price where code = ? ", code);
	}
	
	
	
	 public List<Product> getAllProductWithPriceByCode(int code){
	        String sql="select p.code, p.description, p.active, pp.pid, pp.validFromDate, pp.validToDate, pp.price from product p, product_price pp where p.code=pp.code and p.code=?";

	        List<Product> product =  new ArrayList<>();

	        product = jdbcTemplate.query(sql, RESULTSET_EXTRACTOR, code);

	        return product;
	    }
	 
	private Product mapRowToProduct(ResultSet resultSet, int rowNum) throws SQLException {
		return Product.builder()
				.code(resultSet.getInt("code"))
				.description(resultSet.getString("description"))
				.active(resultSet.getBoolean("active"))
				.build();
	}
	
	  public List<Product> getAllProductWithPrice(){
	        String sql="select p.code, p.description, p.active, pp.pid, pp.validFromDate, pp.validToDate, pp.price from product p, product_price pp where p.code=pp.code";

	        List<Product> productList =new ArrayList<>();

	        productList = jdbcTemplate.query(sql, RESULTSET_EXTRACTOR);

	        return productList;

	    }
	  
	  public List<Product> getProductWithPriceByDate(Date date){
		  String sql="select p.code, p.description, p.active, pp.pid, pp.validFromDate, pp.validToDate, pp.price from product p, product_price pp"
                  +" where p.code=pp.code and pp.validFromDate <= ? and pp.validToDate >= ?";
		  
	        List<Product> productList =jdbcTemplate.query(sql, RESULTSET_EXTRACTOR, date, date); 
	        
	        return productList;

	  }

	  
	  private static final ResultSetExtractor<List<Product>> RESULTSET_EXTRACTOR = rs -> {
	        Map<Integer, Product> productMap = new HashMap<>();

	        List<Product> productList = new ArrayList<>();

	        while (rs.next()) {
	            List<ProductPrice> productPriceList = new ArrayList<>();

	            int code = rs.getInt("code");

	            if (productMap.get(code) != null) {
	                Product product = productMap.get(code);

	                ProductPrice productPrice = ProductPrice.builder().pid(rs.getInt("pid"))
	                        .validFromDate(rs.getDate("validFromDate")).validToDate(rs.getDate("validToDate"))
	                        .price(rs.getInt("price")).code(code).build();

	                product.getProductPrice().add(productPrice);
	            } else {
	                ProductBuilder productBuilder = Product.builder().code(code).description(rs.getString("description"))
	                        .active(rs.getBoolean("active"));

	                ProductPrice productPrice = ProductPrice.builder().pid(rs.getInt("pid"))
	                        .validFromDate(rs.getDate("validFromDate")).validToDate(rs.getDate("validToDate"))
	                        .price(rs.getInt("price")).code(code).build();
	                productPriceList.add(productPrice);

	                productBuilder.productPrice(productPriceList);

	                Product product = productBuilder.build();

	                productMap.put(code, product);

	                productList.add(product);

	            }
	        }
	        return productList;
	    };

	   
		

		public boolean existById(int code) {
		Product p = getById(code);
		if(p==null)
			return false;
			else
				return true;
	}

}

