package com.push.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyFileLoader {

	private static Properties prop=null;
	private PropertyFileLoader(){}
	
	private static Logger logger = Logger.getLogger(PropertyFileLoader.class);
	
	public static Properties getPropertie(){
		
		if(prop== null){
			try {
				logger.info("  getPropertie : Loaded");
				prop = new Properties();
				InputStream input = new FileInputStream("./src/main/resource/application.properties");
				prop.load(input);
				return prop;
			} catch (IOException e) {
				e.printStackTrace();
			};
			return null;
		}
		
		return prop;
	}
	
}
