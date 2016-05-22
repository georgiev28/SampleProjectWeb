package com.ibsbg.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtil {

	public static String md5(String password) {
		
	    byte[] passwordBytes = null;
	    
		try {
			passwordBytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
		return DigestUtils.md5Hex(passwordBytes);
		
	}
	
	//...
	public static void main(String[] args) {
		
		String pass2 = "12345";
		System.out.println(md5(pass2));
	}
}
