package com.cg.exception;

public class ProductAlreadyExists extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     //default constructor
	public ProductAlreadyExists() {
		super();
		// TODO Auto-generated constructor stub
	}
    //Parameterized constructor
	public ProductAlreadyExists(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	

}
