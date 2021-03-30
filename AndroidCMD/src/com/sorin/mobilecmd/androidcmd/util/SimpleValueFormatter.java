package com.sorin.mobilecmd.androidcmd.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;


/**
 * ValueFormatter implementation. This class has not state so it is thread safe.
 * @see ValueFormatter
 */
public class SimpleValueFormatter implements ValueFormatter {


	@Override
	public String format(Integer value) {
		if (value == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getIntegerInstance();
		return nf.format(value.intValue());
	}

	@Override
	public String format(Long value) {
		if (value == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getIntegerInstance();
		return nf.format(value.longValue());
	}

	@Override
	public String format(Double value) {
		if (value == null) {
			return "";
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		return nf.format(value.doubleValue());
	}

	@Override
	public String format(String value) {
		return value;
	}

	@Override
	public String format(Date value) {
		if (value == null) {
			return "";
		}
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return df.format(value);
	}

	@Override
	public String format(Boolean value) {
		if (value == null) {
			return "";
		}
		return value.booleanValue()?"Yes":"No";
	}
	
	
	@Override
	public String format(Object value) {
		if (value == null) {
			return "";
		}
		if (value instanceof Integer){
			return format((Integer)value);
		} else if (value instanceof Long) {
			return format((Long)value);
		} else if (value instanceof Double) {
			return format((Double)value);
		} else if (value instanceof String) {
			return format((String)value);
		} else if (value instanceof Date) {
			return format((Date)value);
		} else if (value instanceof Boolean) {
			return format((Boolean)value);
		} else {
			throw new IllegalArgumentException("Invalid value class.");
		}
	}

}
