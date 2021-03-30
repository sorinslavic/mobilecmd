package com.sorin.mobilecmd.data;

import java.util.ArrayList;
import java.util.List;

import com.sorin.mobilecmd.util.exception.RequiredParameterException;

public class Validated {
	private List<String> errors;
	private RequiredParameterException e = new RequiredParameterException();
	
	public Validated() {;
		errors = new ArrayList<String>();
	}
	
	public boolean isValid() {
		return errors.isEmpty() && e.getLabel().isEmpty();
	}
	
	public boolean isInvalid() {
		return !isValid();
	}
	
	public boolean isInvalidError() {
		return !errors.isEmpty();
	}
	
	public String getError() {
		if (!errors.isEmpty())
			return errors.get(0);
		return "";
	}
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public void setException(RequiredParameterException e) {
		this.e = e;
	}
	
	public RequiredParameterException getException() {
		return e;
	}
	
	public String getAllErrors() {
		String ret = "";
		for (String error : errors)
			ret += "{ " + error + " }";
		return ret;
	}
	
	public void validateParamLength(String value, int length, String label) {
		if (value != null)
			if (value.length() > length)
				addError(label + " cannot be longer than " + length + " characters! ");
	}
}
