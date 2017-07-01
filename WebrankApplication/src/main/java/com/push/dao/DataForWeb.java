package com.push.dao;
import java.sql.Timestamp;
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
import com.push.vo.ViewBean;
import com.push.vo.UserDetailsVo;
import com.push.vo.UserLoginVo;

@SuppressWarnings("rawtypes")
public class DataForWeb {

private static Logger logger = Logger.getLogger(DataForWeb.class);
	
	public List<ViewBean> getTopDataForWebSites(Date date , int numberOfData){
		
		int count = 0;
		logger.info("DataStore :: getTopDataForWebSites : Start : For Date :"+date);
		StatelessSession session=HibernateConnection.getHibernateStatelessSession();
		List<ViewBean> list= new ArrayList<ViewBean>();
		String sql=null;
		try{
		sql= "select website, sum(visits) from InputFileDataVo where date= :load_date "
				+ "group by website order by sum(visits) desc ";
		Query query=session.createQuery(sql);
		query.setDate("load_date", date);
		query.setMaxResults(numberOfData);
		ScrollableResults results=query.scroll();
		while (results.next()) {
			ViewBean vo = new ViewBean(++count, results.getString(0), results.getLong(1));
			list.add(vo);
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
	
	public boolean storeUser(UserLoginVo userLoginVo, UserDetailsVo userDetailsVo){
		
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
			return false;
		} finally {
			if(session != null){
				session.close();
			}
		}
		logger.info("storeUser : End");
		return true;
	}
	
	public boolean isUserAuthorize(String userId,String password){
		logger.info("isUserAuthorize : Start :");
		
		StatelessSession session=HibernateConnection.getHibernateStatelessSession();
		
		String sql=null;
		try{
			sql= "from UserLoginVo where userId= :User_Id "
					+ "and password= :Pass ";
			Query query=session.createQuery(sql);
			query.setString("User_Id", userId);
			query.setString("Pass",password);
			List results=query.list();
			if(results.size()>0) {
				updateLastLoginTime(userId, session);
			}else{
				return false;
			}
			
		}catch(Exception e){
			logger.error(" isUserAuthorize : Error while fetching the data For SQL :"+sql + "Error :"+e);
			return false;
		}finally {
			if(session!=null){
				session.close();
			}
		}
		
		logger.info("isUserAuthorize : end :");
		return true;
	}
	
	public int updateLastLoginTime(String userId, StatelessSession session) throws Exception{
		
		logger.info("updateLastLoginTime : start :");
		
		String sql = null;
		try{
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		sql= "update UserLoginVo set lastLoginTIme = :Last_Login_Time" +
				" where userId = :User_Id";
		Query updateQuery = session.createQuery(sql);
		updateQuery.setTimestamp("Last_Login_Time", currentTimeStamp);
		updateQuery.setParameter("User_Id", userId);
		int result = updateQuery.executeUpdate();
		
		logger.info("updateLastLoginTime : end with record updated :"+result);
		
		return result;
		}catch(Exception e){
			
			logger.error(" updateLastLoginTime : Error while fetching the data For SQL :"+sql + "Error :"+e);
			
			throw new Exception("updateLastLoginTime");
		}
		
	}
	
	public boolean checkExist(String userId){
		logger.info("checkExist : Start :");
		
		StatelessSession session=HibernateConnection.getHibernateStatelessSession();
		
		String sql=null;
		try{
			sql= "from UserLoginVo where userId= :User_Id ";
			Query query=session.createQuery(sql);
			query.setString("User_Id", userId);
			List results=query.list();
			if(results.size()==0) {
				return false;
			}
			
		}catch(Exception e){
			logger.error(" checkExist : Error while fetching the data For SQL :"+sql + "Error :"+e);
			return false;
		}finally {
			if(session!=null){
				session.close();
			}
		}
		
		logger.info("checkExist : end :");
		return true;
	}
}
