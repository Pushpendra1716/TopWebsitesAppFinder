package com.push.dao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.StatelessSession;

import com.push.database.connection.HibernateConnection;

public class DataRead {

private static Logger logger = Logger.getLogger(DataRead.class);
	
	public List<Object> getTopDataForWebSites(Date date , int numberOfData){
		
		logger.info("DataStore :: getTopDataForWebSites : Start :");
		StatelessSession session=HibernateConnection.getHibernateStatelessSession();
		List<Object> list= new ArrayList<Object>();
		String sql=null;
		try{
		sql= "from InputFileData where date= :load_date "
				+ "group by website order by sum(visits) desc ";
		Query query=session.createQuery(sql);
		query.setDate("load_date", date);
		query.setMaxResults(numberOfData);
		ScrollableResults results=query.scroll();
		while (results.next()) {
			list.add(results.get());
		}
		}catch (Exception e) {
			logger.error(" getTopDataForWebSites : Error while fetching the data For SQL :"+sql + "Error :"+e);
		}finally {
			if(session!=null){
				session.close();
			}
		}
		logger.info("DataStore :: getTopDataForWebSites : End : for SQL :"+sql);
		return list;
	}
}
