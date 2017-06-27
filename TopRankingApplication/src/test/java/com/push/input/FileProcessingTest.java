package com.push.input;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileProcessingTest {
	
	FileProcessing fileProcessing=null;
	Properties properties=null;
    @Mock
    private Logger logger;
	
	@Before
	public void setUp(){
		 
		InputStream input;
		properties=new Properties();
		try {
			input = new FileInputStream("./src/test/resource/application.properties");
			properties.load(input);
			fileProcessing = spy(new FileProcessing(properties.getProperty("systemPath")+properties.getProperty("inputFileLocation"),
					properties.getProperty("systemPath")+properties.getProperty("processingFileLocation"),
					properties.getProperty("systemPath")+properties.getProperty("outputFileLocation"),
					properties.getProperty("systemPath")+properties.getProperty("archiveLocation")));
	       
			when(fileProcessing.logger()).thenReturn(logger);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This Test to validate the directory is present or not
	 */
	@Test
	public void getInputFileLacatoinTest() {

		fileProcessing = spy(new FileProcessing("1234",
				properties.getProperty("systemPath")+properties.getProperty("processingFileLocation"),
				properties.getProperty("systemPath")+properties.getProperty("outputFileLocation"),
				properties.getProperty("systemPath")+properties.getProperty("archiveLocation")));
		when(fileProcessing.logger()).thenReturn(logger);
		
		fileProcessing.processInputFile();
		
		verify(logger, times(1)).error("Error , is not directory :1234 is not directory");
		
	}
	
	/**
	 * This test is used to validate file extension. 
	 * Negative Condition
	 */
	@Test
	public void getInputFileLacatoinTest1() {

		Assert.assertNull(fileProcessing.fileExtenstionCheck(Paths.get("data.text")));
	}

	/**
	 * This test is used to validate file extension.
	 * Positive Condition 
	 */
	@Test
	public void getInputFileLacatoinTest2() {

		Assert.assertEquals(Paths.get("data.csv"),fileProcessing.fileExtenstionCheck(Paths.get("data.csv")));
	}
	
	/**
	 * This test is used validate file extension while processing
	 */
	@Test
	public void getInputFileLacatoinTest3() {

		fileProcessing.startFileProcessing(Paths.get("data.text"));
		verify(logger, times(1)).error(" startFileProcessing : File Not Found with .csv extenstion ");
	}
	
	/**
	 * This test is used check the move of file from one folder to another
	 */
	@Test
	public void getInputFileLacatoinTest4() {

		fileProcessing.movefile(Paths.get("data.text"));
		verify(logger, times(1)).error("Error whiel moving the input file");
	}
	
}
