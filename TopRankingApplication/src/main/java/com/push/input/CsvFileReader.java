package com.push.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.push.dao.DataStore;
import com.push.vo.FileDataErrorVo;
import com.push.vo.InputFileDataVo;

public class CsvFileReader {
	
	private static Logger logger = Logger.getLogger(CsvFileReader.class);
	private static final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").maxLinesPerRow(10).build();

    public void readCsv(String csvFileName, long fileId) {

    	logger().info("readCsv : Start :");
        ICsvBeanReader beanReader = null;
        DataStore dataStore= new DataStore();
        try {
        	if(csvFileName!=null){
        		beanReader = new CsvBeanReader(new FileReader(csvFileName), PIPE_DELIMITED);

                // the header elements are used to map the values to the bean (names must match)
                final String[] header = beanReader.getHeader(true);
                final CellProcessor[] processors = getProcessors();
                InputFileDataVo inputFileData;
                FileDataErrorVo dataErrorVo;
                List<InputFileDataVo> inputFileDatas= new ArrayList<InputFileDataVo>();
                List<FileDataErrorVo> dataErrorVos= new ArrayList<FileDataErrorVo>();
                
                while (true) {
                	try{
                		inputFileData = beanReader.read(InputFileDataVo.class, header, processors);
                		if(inputFileData==null){
                			break;
                		}
	                	inputFileData.setFileId(fileId);
	                	inputFileDatas.add(inputFileData);
	                	if(inputFileDatas.size()==Integer.parseInt(PropertyFileLoader.getPropertie().getProperty("sizeOfData").toString())){
	                		dataStore.saveFileData(inputFileDatas);
	                		inputFileDatas.clear();
	                	}
	                }catch (SuperCsvException e) {
	                	dataErrorVo= new FileDataErrorVo();
	                	dataErrorVo.setFileId(fileId);
	                	if(e.getCsvContext()!=null){
	                		dataErrorVo.setError(e.getCsvContext().toString().substring(0, 
		                			e.getCsvContext().toString().length() >=1000 ? 1000 : e.getCsvContext().toString().length()));	
	                	}else{
	                		dataErrorVo.setError(e.getMessage().toString().substring(0, 
		                			e.getMessage().toString().length() >=1000 ? 1000 : e.getMessage().toString().length()));
	                	}
	                	
	                	dataErrorVos.add(dataErrorVo);
	                	if(dataErrorVos.size()==Integer.parseInt(PropertyFileLoader.getPropertie().getProperty("sizeOfErrorData").toString())){
	                		dataStore.storeErrorData(dataErrorVos);
	    	            }
	                	logger().error("readCsv : SuperCsvException while readind the data for file: "+new File(csvFileName).getName());
	                	logger().error(" readCsv : SuperCsvException : " + e);
	        		}catch (Exception e) {
	        			dataErrorVo= new FileDataErrorVo();
	                	dataErrorVo.setFileId(fileId);
	                	dataErrorVo.setError(e.getMessage().substring(0,
	                			e.getMessage().toString().length() >=1000 ? 1000 : e.getMessage().toString().length()));
	                	dataErrorVos.add(dataErrorVo);
	                	if(dataErrorVos.size()==Integer.parseInt(PropertyFileLoader.getPropertie().getProperty("sizeOfErrorData").toString())){
	                		dataStore.storeErrorData(dataErrorVos);
	    	            }
	                	logger().error(" readCsv : Error while readind the data: "+new File(csvFileName).getName());
	        			logger().error(" readCsv : Error while readind the data: " + e);
					}	
                }
	            if(inputFileDatas.size()>0){
	            	dataStore.saveFileData(inputFileDatas);
	            }
	            if(dataErrorVos.size()>0){
	            	dataStore.storeErrorData(dataErrorVos);
	            }
        	}else{
        		logger().error(" readCsv : No File Found ");
        	}
        }catch (FileNotFoundException ex) {
        	logger().error(" readCsv : Could not find the CSV file: " + ex);
        }catch (IOException ex) {
        	logger().error(" readCsv : Error reading the CSV file: " + ex);
        }catch (Exception e) {
        	logger().error(" readCsv : Error reading the CSV file: " + e);
        }
        finally {
            if (beanReader != null) {
                try {
					beanReader.close();
				} catch (IOException e) {
					logger().error(" readCsv : Error closing beanReader: " + e);
				}
            }
        }
        logger().info(" readCsv : End :");
    }

    private CellProcessor[] getProcessors(){
        return new CellProcessor[] {
        		new ParseDate("yyyy-MM-dd"),
                new NotNull(),
                new ParseLong()};
    }

    Logger logger() {
        return logger;
    }	
}
