package com.ea.bl.core;

import java.io.File;

import org.springframework.web.util.HtmlUtils;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    
    public static void main(String[] args){
    	File file = new File(System.getProperty("java.io.tmpdir"));
    	System.out.println(file.getAbsolutePath());
    	
    	System.out.println(HtmlUtils.htmlEscape("<span>"));
    }
}
