package com.sorin.mobilecmd.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.Properties;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Facade for a {@link java.util.Properties} object that allows the client
 * to convert properties to java objects or basic types.
 * 
 * Because java.util.Properties is thread safe this class is also thread safe.
 */
public class PropertiesFacade {
	private final Properties properties;
	
	/**
	 * Constructor.
	 * @param properties the {@link java.util.Properties} instance
	 * 			to be wrapped.
	 */
	public PropertiesFacade(Properties properties) {
		Assert.notNull(properties, "properties");
		this.properties = properties;
	}


	/** @return the wrapped {@link java.util.Properties} instance. */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * Returns the value of the specified property or throws an exception
	 * if the property is missing. 
	 * @param name the name of the property to retrieve.
	 * @return the value of the property with the specified name.
	 * @throws MissingResourceException for one of the following:
	 * 			- the property is not defined,
	 * 			- the property is defined, but its value is empty string or white spaces only. 
	 */
	public String getRequiredString(String name) throws MissingResourceException {
		Assert.notNull(name, "name");
		String s = properties.getProperty(name);
    	if (StringUtils.hasText(s)) {
    		return s.trim();
    	} else {
    		throw new MissingResourceException("Missing required property '" + name + "'.", 
    				String.class.getName(), name);
    	}
	}
	
	
	/**
     * @param name
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as String or 
     * 		   <code>defaultValue</code> if the property does not exist or 
     * 			is empty string or has only white spaces.  
     */
    public String getString(String name, String defaultValue) {
    	try {
    		return getRequiredString(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	}
    }
    
    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as String or
     * 			empty string if the property does not exist
     * 			or is empty string or has only white spaces.  
     */
    public String getString(String name) {
    	return getString(name, "");
    }
    
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method used the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first. Returns true if 
     * the value is one of: yes, y, true, on.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public boolean getRequiredBoolean(String name) throws MissingResourceException {
   		String s = getRequiredString(name);
    	if (s.equalsIgnoreCase("yes") 
    			|| s.equalsIgnoreCase("y")
    			|| s.equalsIgnoreCase("true") 
    			|| s.equalsIgnoreCase("on")){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Return true if the value is one of: yes, y, true, on.
     * The value is case insensitive.
     * @param name
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as boolean or 
     * 		   <code>defaultValue</code> if the property does not exist or is empty string or has only white spaces.    
     */
    public boolean getBoolean(String name, boolean defaultValue) {
    	try {
    		return getRequiredBoolean(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	}
    }
    
    /**
     * Return true if the value is one of: yes, y, true, on.
     * The value is case insensitive.
     * @param name
     * @return the value of the property with the provided <code>name</code> as boolean or 
     * 		   false if the property does not exist or is empty string or has only white spaces.  
     */
    public boolean getBoolean(String name) {
    	return getBoolean(name, false);
    }
    
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid int.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public int getRequiredInt(String name) throws MissingResourceException, IllegalStateException {
    	String s = getRequiredString(name);
    	try {
    		return Integer.parseInt(s);
    	} catch (NumberFormatException e) {
   			throw new IllegalStateException(createExceptionString(name, s, int.class), e);
    	}
    }
    
    /**
     * 
     * @param name
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as int or 
     * 		   <code>defaultValue</code> if the property does not exist 
     * 			or is empty string or has only white spaces or the value can not be
     * 			parsed as int.
     * 
     * @see Integer#parseInt(String)
     */
    public int getInt(String name, int defaultValue) {
    	try {
    		return getRequiredInt(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof NumberFormatException) {
    			return defaultValue;
    		}  else {
    			throw e;
    		}
    	}
    }

    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as int or 
     * 		   	0 if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as int.
     * 
     * @see Integer#parseInt(String)  
     */
    public int getInt(String name) {
    	return getInt(name, 0);
    }
    
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid long.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public long getRequiredLong(String name) throws MissingResourceException, IllegalStateException {
    	String s = getRequiredString(name);
    	try{
    		return Long.parseLong(s);
		} catch (NumberFormatException e) {
	    	throw new IllegalStateException(createExceptionString(name, s, long.class), e);
		}
    }
    
    /**
     * @param name
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as long or 
     * 		   <code>defaultValue</code> if the property does not exist 
     * 			or is empty string or has only white spaces or
     * 			the value can not be parsed as long.
     *   
     * @see Long#parseLong(String)
     */
    public long getLong(String name, long defaultValue){
    	try {
    		return getRequiredLong(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof NumberFormatException) {
    			return defaultValue;
    		}  else {
    			throw e;
    		}
    	}
    }

    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as long or 
     * 		   	0L if the property does not exist or is empty string or has only white spaces
     *  		or the value can not be parsed as long.
     *   
     * @see Long#parseLong(String)
     */
	public long getLong(String name) {
    	return getLong(name, 0L);
    }
    
	
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid double.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public double getRequiredDouble(String name) throws MissingResourceException, IllegalStateException {
    	String s = getRequiredString(name);
    	try {
    		return Double.parseDouble(s);
    	} catch (NumberFormatException e) {
    		throw new IllegalStateException(createExceptionString(name, s, double.class), e); 
    	}
    }
	
	
    /**
     * @param name
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as double or 
     * 		   <code>defaultValue</code> if the property does not exist 
     * 			or is empty string or has only white spaces
     * 			or the value can not be parsed as double.  
     * 
     * @see Double#parseDouble(String)
     */
    public double getDouble(String name, double defaultValue) {
    	try {
    		return getRequiredDouble(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof NumberFormatException) {
    			return defaultValue;
    		}  else {
    			throw e;
    		}
    	}
    }

    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as double or 
     * 		   	0.0d if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as double.
     * 
     * @see Double#parseDouble(String)
     */
	public double getDouble(String name) {
    	return getDouble(name, 0.0d);
    }
	
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid float.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public float getRequiredFloat(String name) throws MissingResourceException, IllegalStateException {
    	String s = getRequiredString(name);
    	try {
    		return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			throw new IllegalStateException(createExceptionString(name, s, float.class), e); 
		}
    }
	
    
    /**
     * @param name
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as float or 
     * 		   <code>defaultValue</code> if the property does not exist or is empty string or has only white spaces
     *			or the value can not be parsed as float. 
     * 
     * @see Float#parseFloat(String) 
     */
    public float getFloat(String name, float defaultValue) {
    	try {
    		return getRequiredFloat(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof NumberFormatException) {
    			return defaultValue;
    		}  else {
    			throw e;
    		}
    	}
    }

    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as float or 
     * 		   	0.0f if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as float. 
     * 
     * @see Float#parseFloat(String)   
     */
	public float getFloat(String name) {
    	return getFloat(name, 0.0f);
    }
	
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid date.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public Date getRequiredDate(String name, DateFormat dateFormat) throws MissingResourceException, IllegalStateException {
    	Assert.notNull(dateFormat, "dateFormat");
    	String s = getRequiredString(name);
		try {
			return dateFormat.parse(s);
		} catch (ParseException e) {
	    	throw new IllegalStateException(createExceptionString(name, s, Date.class), e);	    	
		}
    }
	

    /**
     * @param name
     * @param dateFormat - formatter used for date parsing.
     * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as Date or 
     * 		   	<code>defaultValue</code> if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as Date using the DateFormat parameter.  
     */
    public Date getDate(String name, DateFormat dateFormat, Date defaultValue) {
    	Assert.notNull(dateFormat, "dateFormat");
    	try {
    		return getRequiredDate(name, dateFormat);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof ParseException) {
    			return defaultValue;
    		} else {
    			throw e;
    		}
    	}
    }

    /**
     * @param name
     * @param dateFormat - formatter used for date parsing.
     * @return the value of the property with the provided <code>name</code> as Date or 
     * 		   	null if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as Date using the DateFormat parameter.    
     */
	public Date getDate(String name, DateFormat dateFormat) {
    	return getDate(name, dateFormat, null);
    }
	
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method used the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid Date.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public Date getRequiredDate(String name) throws MissingResourceException, IllegalStateException {
    	return getRequiredDate(name, new SimpleDateFormat("MM/dd/yyyy"));
    }
	
	
	/**
	 * @param name
	 * @param defaultValue
     * @return the value of the property with the provided <code>name</code> as Date or 
     * 		   	<code>defaultValue</code> if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as Date using the MM/dd/yyyy format.  
	 */
	public Date getDate(String name, Date defaultValue) {
		return getDate(name, new SimpleDateFormat("MM/dd/yyyy"), defaultValue);
	}
	
	/**
	 * @param name
     * @return the value of the property with the provided <code>name</code> as Date or 
     * 		   	null if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as Date using the MM/dd/yyyy format.    
	 */
	public Date getDate(String name) {
		return getDate(name, (Date)null);
	}
	
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid BigDecimal.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public BigDecimal getRequiredBigDecimal(String name) throws MissingResourceException, IllegalStateException {
    	String s = getRequiredString(name);
    	try {
    		return new BigDecimal(s);
		} catch (NumberFormatException e) {
			throw new IllegalStateException(createExceptionString(name, s, BigDecimal.class), e); 
		}
    }
	
	
    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as {@link BigDecimal} or 
     * 		   <code>defaultValue</code> if the property does not exist 
     * 			or is empty string or has only white spaces
     * 			or the value can not be parsed as BigDecimal.  
     */
	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
    	try {
    		return getRequiredBigDecimal(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof NumberFormatException) {
    			return defaultValue;
    		} else {
    			throw e;
    		}
    	}
	}

	/**
	 * @param name
     * @return the value of the property with the provided <code>name</code> as double or 
     * 		   	{@link BigDecimal#ZERO} if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as BigDecimal.
	 */
	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, BigDecimal.ZERO);
	}
	
	
    /**
     * @return the value of the specified property or throws an exception if
     * the property is missing. This method uses the {@link #getRequiredString(String)} 
     * method to retrieve the value of the property as String first.
     * 
     * @throws MissingResourceException if {@link #getRequiredString(String)} throws the exception.
     * @throws IllegalStateException if the property value is not a valid BigInteger.
     * 
     * @see {@link #getRequiredString(String)}
     */
    public BigInteger getRequiredBigInteger(String name) throws MissingResourceException, IllegalStateException {
    	return getRequiredBigDecimal(name).toBigInteger();
    }
	
	
    /**
     * @param name
     * @return the value of the property with the provided <code>name</code> as {@link BigInteger} or 
     * 		   <code>defaultValue</code> if the property does not exist 
     * 			or is empty string or has only white spaces
     * 			or the value can not be parsed as BigInteger.  
     */
	public BigInteger getBigInteger(String name, BigInteger defaultValue) {
    	try {
    		return getRequiredBigInteger(name);
    	} catch (MissingResourceException e) {
    		return defaultValue;
    	} catch (IllegalStateException e) {
    		if (e.getCause() instanceof NumberFormatException) {
    			return defaultValue;
    		} else {
    			throw e;
    		}
    	}
	}
	
	
	/**
	 * @param name
     * @return the value of the property with the provided <code>name</code> as double or 
     * 		   	{@link BigInteger#ZERO} if the property does not exist or is empty string or has only white spaces
     * 			or the value can not be parsed as BigInteger.
	 */
	public BigInteger getBigInteger(String name) {
		return getBigInteger(name, BigInteger.ZERO);
	}
	
	protected String createExceptionString(String name, String value, Class<?> clasz) {
		return "Property '" + name + "' with value '" + value + "' can not be parsed as " + clasz.getName() + " .";		
	}
}
