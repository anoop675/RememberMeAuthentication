package com.anoopsen.RememberMe_SpringSecurityDemo.exceptions;

public class MyPasswordMismatchException extends Exception{
	
	public MyPasswordMismatchException() {
		super("Password Mismatch has occured");
	}
	
	public MyPasswordMismatchException(String message) {
		super(message);
	}

}
