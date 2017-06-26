package com.push.input;

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
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.push.dao.DataStore;
import com.push.vo.InputFileDataVo;

public class CsvFileReader {
	
	private static Logger logger = Logger.getLogger(CsvFileReader.class);
	private static final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").maxLinesPerRow(10).build();

    public static void readCsv(String csvFileName, long fileId) {

    	logger.info("readCsv : Start :");
        ICsvBeanReader beanReader = null;
        DataStore dataStore= new DataStore();
        try {
        	if(csvFileName!=null){
        		beanReader = new CsvBeanReader(new FileReader(csvFileName), PIPE_DELIMITED);

                // the header elements are used to map the values to the bean (names must match)
                final String[] header = beanReader.getHeader(true);
                final CellProcessor[] processors = getProcessors();

                InputFileDataVo inputFileData;
                List<InputFileDataVo> inputFileDatas= new ArrayList<InputFileDataVo>();
                while ((inputFileData = beanReader.read(InputFileDataVo.class, header, processors)) != null) {
                	inputFileData.setFileId(fileId);
                	inputFileDatas.add(inputFileData);
                	if(inputFileDatas.size()==Integer.parseInt(PropertyFileLoader.getPropertie().getProperty("sizeOfData").toString())){
                		dataStore.saveFileData(inputFileDatas);
                		inputFileDatas.clear();
                	}
                }
                if(inputFileDatas.size()>0){
                	dataStore.saveFileData(inputFileDatas);
                }
        	}else{
        		logger.error(" readCsv : No File Found ");
        	}
        } catch (FileNotFoundException ex) {
        	logger.error(" readCsv : Could not find the CSV file: " + ex);
        } catch (IOException ex) {
        	logger.error(" readCsv : Error reading the CSV file: " + ex);
        }finally {
            if (beanReader != null) {
                try {
					beanReader.close();
				} catch (IOException e) {
					logger.error(" readCsv : Error closing beanReader: " + e);
				}
            }
        }
        logger.info(" readCsv : End :");
    }

    private static CellProcessor[] getProcessors(){
        return new CellProcessor[] {
        		new ParseDate("yyyy-MM-dd"),
                new NotNull(),
                new ParseLong()};
    }

	
}
