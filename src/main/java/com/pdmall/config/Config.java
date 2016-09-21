package com.pdmall.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/*
*  读取java配置文件
*  Java的Properties对象，该对象很重要.
* */
public class Config {
	private static String filePath="/build.properties";
	private static Properties prop;
	static {   
		buildProp();
    }   
	public static String get(String propertyName){
		return prop.getProperty(propertyName.trim());
	}
	
	private static void buildProp(){
		prop = new Properties();   
        InputStream in = Object.class.getResourceAsStream(filePath);   
        try { 
            prop.load(in);   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
	}
	
	public static void setFilePath(String path){
		filePath = path; 
	}
}
