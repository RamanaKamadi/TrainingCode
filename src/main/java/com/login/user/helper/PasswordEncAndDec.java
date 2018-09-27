package com.login.user.helper;

import org.apache.commons.codec.binary.Base64;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncAndDec {

	
	
	
	    public String passwordEncoder(String str) {
	     
	    	  byte[] encoded = Base64.encodeBase64(str.getBytes());  
	        
	        
	        return new String(encoded);
	    }
	   
	    public String passwordDencoder(String str) {
	      
	    	 byte[] decoded =  Base64.decodeBase64(str.getBytes()); 
	        
	        
	        return new String(decoded);
	    }
	
	
}
