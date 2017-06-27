package com.push.input;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CsvFileReaderTest {

	private String path=null;
	private Properties properties=null;
	private long fileId=0;
	CsvFileReader csvFileReader=null;
	@Mock
    private Logger logger;
	
	@Before
	public void setUp(){
		
		fileId=0;
		InputStream input;
		properties=new Properties();
		try {
			input = new FileInputStream("./src/test/resource/application.properties");
			properties.load(input);
			
			path=properties.getProperty("systemPath")+properties.getProperty("inputFileLocation")+"/ExceptionTest.csv";	
			csvFileReader=spy(new CsvFileReader());	
			when(csvFileReader.logger()).thenReturn(logger);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Used for to test the SuperCsvException while reading the file
	 */
	@Test
	public void readCsvTest(){
		
		csvFileReader.readCsv(path, fileId);
		verify(logger, times(1)).error("readCsv : SuperCsvException while readind the data for file: ExceptionTest.csv");
	}
	
	/**
	 * This Used for to test the Exception while reading the file
	 */
	@Test
	public void readCsvTest2(){
		
		csvFileReader.readCsv(path, fileId);
		verify(logger, times(1)).error(" readCsv : Error while readind the data: ExceptionTest.csv");
	}
	
	/**
	 * This Used for to test the file not found
	 */
	@Test
	public void readCsvTest3(){
		
		csvFileReader.readCsv("", fileId);
		verify(logger, times(1)).error(" readCsv : Could not find the CSV file: java.io.FileNotFoundException: ");
	}
	
	/**
	 * This Used for to test the file is null
	 */	
	@Test
	public void readCsvTest4(){
		
		csvFileReader.readCsv(null, fileId);
		verify(logger, times(1)).error(" readCsv : No File Found ");
	}
	
}
