package com.push.input;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.push.dao.DataStore;
import com.push.exception.ExceptionForDataBase;
import com.push.vo.FileDataErrorVo;

public class FileProcessing {
	
	private static final Logger logger = Logger.getLogger(FileProcessing.class);
	
	private String inputFileLocation;
	private String processingFileLocation;
	private String outputFileLocation;
	private String archiveLocation;
	
	public FileProcessing(String inputFileLocation, String processingFileLocation, String outputFileLocation,String archiveLocation) {
		super();
		this.inputFileLocation = inputFileLocation;
		this.processingFileLocation = processingFileLocation;
		this.outputFileLocation = outputFileLocation;
		this.archiveLocation = archiveLocation;
	}
	
	public void processInputFile(){
		logger().info("  processInputFile : Start : System is Ready to process the files");
		
		try{
			if(Files.isDirectory(Paths.get(inputFileLocation))) {
				logger().info("processInputFile : Valid directory:");
				while(true){
			        try(DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get(inputFileLocation))) {
			            for(Path file : files) {
			                if(Files.isRegularFile(file) || Files.isSymbolicLink(file)) {
			                	startFileProcessing(file.getFileName());
			                }
			            }
			        }catch (Exception e) {
			        	logger().error("Error whiel processing the input file");
			        	throw new Exception(inputFileLocation+" Error whiel processing the input file");
			        }
				}
			}else{
			       throw new NotDirectoryException(inputFileLocation+" is not directory");
			 }
		} catch(NotDirectoryException notDirectoryException){
			logger().error("Error , is not directory :"+notDirectoryException.getMessage());
		}
		catch (Exception e) {
			logger().error("Error whiel before the input file",e);
		}
		logger().info(" processInputFile : End");
	}
	
	public Path fileExtenstionCheck(Path fileName){
		
		final String fileNameWithpath=inputFileLocation+"/"+fileName;
		if(fileNameWithpath.lastIndexOf(".") != -1 && fileNameWithpath.lastIndexOf(".") != 0){
			 return fileNameWithpath.substring(fileNameWithpath.lastIndexOf(".")+1).equals(PropertyFileLoader.getPropertie().get("fileType")) ? fileName : null;
		}else{
			return null;
		}
	}
	
	public void startFileProcessing(Path fileName){
		
		logger().info("  startFileProcessing : Start For :"+fileName);
		DataStore dataStore= new DataStore();
		CsvFileReader csvFileReader= new CsvFileReader();
		try{
			if(fileName!=null){
				if(fileExtenstionCheck(fileName)!= null){
					final String fileNameWithpath=inputFileLocation+"/"+fileName;
					csvFileReader.readCsv(fileNameWithpath,dataStore.saveFile(fileName.toString()));
					movefile(fileName);
				}else{
					logger().error(" startFileProcessing : File Not Found with .csv extenstion ");
					movefile(fileName);
				}
		}	
		}catch (ExceptionForDataBase e) {
			movefile(fileName);
			FileDataErrorVo dataErrorVo= new FileDataErrorVo();
			dataErrorVo.setFileId(0);
			String message=e.getMessage()+fileName.toString();
			dataErrorVo.setError(message.substring(0, message.length() >=1000 ? 1000 : message.length()));
			List<FileDataErrorVo> fileDataErrorVos= new ArrayList<FileDataErrorVo>();
			fileDataErrorVos.add(dataErrorVo);
			dataStore.storeErrorData(fileDataErrorVos);
		}
		logger().info("  startFileProcessing : End For :"+fileName);
	}
	
	public void movefile(Path fileName){
		
		try {
			Files.move(Paths.get(processingFileLocation+"/"+fileName), Paths.get(outputFileLocation+"/"+fileName),StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			logger().error("Error whiel moving the input file");
			logger().error("for :"+processingFileLocation+"/"+fileName+ " to "+ Paths.get(outputFileLocation+"/"+fileName),e);
		}
	}
	
	public void archiveFile(Path fileName){
		try {
			Files.move(Paths.get(outputFileLocation+"/"+fileName), Paths.get(archiveLocation+"/"+fileName),StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	Logger logger() {
        return logger;
    }
	
}
