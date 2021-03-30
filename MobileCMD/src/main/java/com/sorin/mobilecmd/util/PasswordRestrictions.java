package com.sorin.mobilecmd.util;

/**
 * Describes the password restrictions as they are defined in the mobilecmd.properties file.
 * Only one object of this type should exist. Use {@link MobileCMDPropertiesFacade#getPasswordRestrictions()}
 * method to retrieve it.
 * 
 * @see MobileCMDPropertiesFacade#getPasswordRestrictions()
 */
public class PasswordRestrictions {
    
	private final int minLength;
    private final int duration;
    private final boolean reqNumbers;
    private final boolean reqSpecial;
    private final boolean reqUpLetters;
    
    public PasswordRestrictions(
    		int minLength,
    	    int duration,
    	    boolean reqNumbers,
    	    boolean reqSpecial,
    	    boolean reqUpLetters
    		) {
    	this.minLength = minLength;
    	this.duration = duration;
    	this.reqNumbers = reqNumbers;
    	this.reqSpecial = reqSpecial;
    	this.reqUpLetters = reqUpLetters;
    }

    
    public int getMinLength() {
    	return minLength;
    }
    
    public int getDuration() {
    	return duration;
    }
    
    public boolean getReqNumbers() {
    	return reqNumbers;
    }
    
    public boolean getReqSpecial() {
    	return reqSpecial;
    }
    
    public boolean getReqUpLetters() {
    	return reqUpLetters;
    }

    @Override
	public String toString() {
		return "PasswordRestrictions [minLength=" + minLength + ", duration="
				+ duration + ", reqNumbers=" + reqNumbers + ", reqSpecial="
				+ reqSpecial + ", reqUpLetters=" + reqUpLetters + "]";
	}
    
    public String getHtmlFormat() {
    	String restrictions = "Password";
    	if (minLength > 0)
    		restrictions += " must have at least " + minLength + " characters;";
    	if (reqNumbers)
    		restrictions += " must contain at least a numberic character;";
    	if(reqSpecial)
    		restrictions += " must contain at least a special character;";
    	if(reqUpLetters)
    		restrictions += " must contain at least an UPPER case letter;";
    	
    	return restrictions;
    }
    
    public final boolean validatePassword(String password) {
    	if (password.length() < minLength)
    		return false;
    	if (reqNumbers)
    		return false;
    	if(reqSpecial)
    		return false;
    	if(reqUpLetters)
    		return false;
    	
    	return true;
    }
}
