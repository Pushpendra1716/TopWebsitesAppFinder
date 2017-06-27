package com.push.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.push.database.connection.HibernateConnection;
import com.push.exception.ExceptionForDataBase;
import com.push.vo.FileDataErrorVo;
import com.push.vo.InputFileDataVo;
import com.push.vo.InputFileVo;

public class DataStore {

	private static Logger logger = Logger.getLogger(DataStore.class);
	
	public void saveFileData(List<InputFileDataVo> inputFileDatas){
		
		logger.info("DataStore :: saveFileData : Start :");
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateConnection.getHibernateSession();
			transaction= session.beginTransaction();
			for (int index = 0; index < inputFileDatas.size(); index++) {
				InputFileDataVo inputFileData =inputFileDatas.get(index);
				session.save(inputFileData);
				if(index % 50 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		}catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("DataStore :: saveFileData : ror while data persistace :",e);
		}finally {
			if(session!=null){
				session.close();
			}
		}
		logger.info("DataStore :: saveFileData : End :");
	}
	
	@SuppressWarnings("unchecked")
	public long saveFile(String fileName) throws ExceptionForDataBase{
		
		logger.info("saveFile : Start : For File :"+ fileName);
		
		long count=0;
		Session session=null;
		Transaction transaction=null;
		String query;
		InputFileVo inputFileVo=null;
		List<Object> object= new ArrayList<Object>();
		try {
			if(count==0){
				session=HibernateConnection.getHibernateSession();
				query="SELECT max(fileId) from InputFileVo";
				object= session.createQuery(query).setMaxResults(1).list();
				if(object.get(0)!= null){
					count=(long) object.get(0)+1;
				}else{
					count=count+1;
				}
			}else{
				count=count+1;
			}
		} catch (Exception exp) {
			logger.error("saveFile : Error while get the sequence for input file :"+ fileName +" Error : ",exp);
			throw new ExceptionForDataBase("saveFile : Error while get the sequence for input file :");
		}finally {
			if(session != null){
				session.flush();
				session.clear();
			}
		}
		try {
			transaction=session.beginTransaction();
			inputFileVo= new InputFileVo();
			inputFileVo.setFileId(count);
			inputFileVo.setFileName(fileName);
			session.save(inputFileVo);
			transaction.commit();
		} catch (Exception exp) {
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("saveFile : Error while saving the input file :"+ fileName +" Error : ",exp);
			throw new ExceptionForDataBase("saveFile : Error while saving the input file :");
		}finally {
			if(session != null){
				session.close();
			}	
		}
		logger.info("saveFile : End : For File :"+ fileName);
		return count;
	}
	
	public void storeErrorData(List<FileDataErrorVo> fileDataErrorVos){
		
		logger.info("storeErrorData : Start :"+fileDataErrorVos.size());
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateConnection.getHibernateSession();
			transaction=session.beginTransaction();
			for (int index = 0; index < fileDataErrorVos.size(); index++) {
				FileDataErrorVo fileDataErrorVo =fileDataErrorVos.get(index);
				session.save(fileDataErrorVo);
				if(index % 50 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
		}catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("storeErrorData : Error while data persistace :",e);
		}finally {
			if(session!=null){
				session.close();
			}
		}
		logger.info("storeErrorData : End :");
	}
	
}
