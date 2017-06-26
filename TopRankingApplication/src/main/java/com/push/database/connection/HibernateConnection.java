package com.push.database.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateConnection{

	private static SessionFactory sessionFactory;
	private HibernateConnection(){
	}
	private static SessionFactory getSessionFactory(){
		Configuration cfg=new Configuration(); 
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory=cfg.buildSessionFactory(); 
		return factory;
	}
	
	public static Session getHibernateSession(){
		
		if(sessionFactory == null){
			sessionFactory= getSessionFactory();
		}
		return sessionFactory.openSession();
	}
	
	public static StatelessSession getHibernateStatelessSession(){
		
		if(sessionFactory == null){
			sessionFactory= getSessionFactory();
		}
		return sessionFactory.openStatelessSession();
	}
}
