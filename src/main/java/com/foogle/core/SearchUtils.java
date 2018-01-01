package com.foogle.core;

/**
 * A simple utility class
 * 
 * @author Nilanjan Sarkar
 *
 */
public class SearchUtils {
	
	private SearchUtils(){
		
	}

	public static boolean isBlank(String content){
		
		if(null == content || "".equals(content) ){
			return true;
		}else{
			return false;
		}
	}
}
