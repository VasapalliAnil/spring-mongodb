package com.test.springbootmongodb.exception;



public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public ValidationException(String message) {
		 super(message);
	 }
	 
	 public static String NotFoundException(String id) {
		 return "Todo Not found with id:"+id;
	 }
	 
	 public static String TodoAlreadyExists() {
		 return "Todo with this name already exists";
	 }

}
