package com.push.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.push.database.connection.HibernateConnection;
import com.push.vo.InputFileData;

public class DataStore {

	private static Logger logger = Logger.getLogger(DataStore.class);
	
	public void saveFileData(List<InputFileData> inputFileDatas){
		
		logger.info("DataStore :: saveFileData : Start :");
		Session session=HibernateConnection.getHibernateSession();
		Transaction transaction=null;
		try{
			transaction= session.beginTransaction();
			for (int index = 0; index < inputFileDatas.size(); index++) {
				InputFileData inputFileData =inputFileDatas.get(index);
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
}
