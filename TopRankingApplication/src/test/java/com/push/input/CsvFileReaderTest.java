package com.push.input;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CsvFileReaderTest {

	String path=null;
	Properties properties=null;
	long fileId=0;
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
			spy(new CsvFileReader());	
			when(CsvFileReader.logger()).thenReturn(logger);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private CsvFileReader spy(CsvFileReader csvFileReader2) {
		// TODO Auto-generated method stub
		return null;
	}


	@Ignore
	@Test
	public void readCsvTest(){
		when(CsvFileReader.logger()).thenReturn(logger);
		
		CsvFileReader.readCsv(path, fileId);
		
		verify(logger, times(1)).error("Error , is not directory :1234 is not directory");
	}
}
