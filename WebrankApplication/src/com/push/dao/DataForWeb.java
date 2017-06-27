package com.push.dao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import com.push.database.connection.HibernateConnection;
import com.push.vo.UserDetailsVo;
import com.push.vo.UserLoginVo;

public class DataForWeb {

private static Logger logger = Logger.getLogger(DataForWeb.class);
	
	public List<Object> getTopDataForWebSites(Date date , int numberOfData){
		
		logger.info("DataStore :: getTopDataForWebSites : Start :");
		StatelessSession session=HibernateConnection.getHibernateStatelessSession();
		List<Object> list= new ArrayList<Object>();
		String sql=null;
		try{
		sql= "from InputFileDataVo where date= :load_date "
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
	
	public void storeUser(UserLoginVo userLoginVo, UserDetailsVo userDetailsVo){
		
		logger.info("storeUser : Start :");
		Session session=null;
		Transaction transaction= null;
		
		try {
			
			session=HibernateConnection.getHibernateSession();
			transaction=session.beginTransaction();
			
			session.save(userLoginVo);
			session.flush();
			session.clear();
			
			userDetailsVo.setUserId(userLoginVo.getUserId());
			session.save(userDetailsVo);
			
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error(" storeUser : Error while creating the user account for User Id:"+userLoginVo.getUserId());
		} finally {
			if(session != null){
				session.close();
			}
		}
		logger.info("storeUser : End");
	}
}
