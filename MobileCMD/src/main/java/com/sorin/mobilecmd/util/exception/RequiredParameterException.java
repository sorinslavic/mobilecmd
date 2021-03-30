package com.sorin.mobilecmd.util.exception;

public class RequiredParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308940969321327146L;

	private String label;
	
	public RequiredParameterException() {
		super();
		label = "";
	}
	
	public RequiredParameterException(String message) {
		super(message + " is a required field!");
		label = message;
	}
	
	public String getLabel() {
		return label;
	}
}
