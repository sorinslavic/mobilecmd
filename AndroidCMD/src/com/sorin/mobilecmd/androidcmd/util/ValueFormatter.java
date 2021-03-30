package com.sorin.mobilecmd.androidcmd.util;

import java.util.Date;

/**
 * This interface defines the methods used for formatting objects as strings. 
 */
public interface ValueFormatter {
	
	public String format(Integer value);
	
	public String format(Long value);
	
	public String format(Double value);
	
	public String format(String value);
	
	public String format(Date value);
	
	public String format(Boolean value);
	
	public String format(Object value);

}
