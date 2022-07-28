package com.cg.model;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {
	
	@Id
	private int pid;
	private int code;
	private Date validFromDate;
	private Date validToDate;
	private int price;
	

}
