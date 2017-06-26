package com.push.main;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.push.input.FileProcessing;
import com.push.input.PropertyFileLoader;

public class TopRankApplication {

	private static Logger logger = Logger.getLogger(FileProcessing.class);
	
	public static void main(String[] args) throws IOException {

		logger.info(" Application : Start :");
		
		Properties properties=PropertyFileLoader.getPropertie();
		
		FileProcessing fileProcessing = new FileProcessing(properties.getProperty("systemPath")+properties.getProperty("inputFileLocation"),
				properties.getProperty("systemPath")+properties.getProperty("processingFileLocation"),
				properties.getProperty("systemPath")+properties.getProperty("outputFileLocation"),
				properties.getProperty("systemPath")+properties.getProperty("archiveLocation"));
		
		fileProcessing.processInputFile();
		
		logger.info(" Application : Shutdown : Check the log for more details");
		
	}

}
