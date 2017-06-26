package com.push.database.connection;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.junit.Assert;
import org.junit.Test;


public class HibernateConnectionTest {
	
	private Session session;
	private StatelessSession statelessSession;
	
	@Test
	public void HibernateSessionFactoryTest(){
		session=HibernateConnection.getHibernateSession();
		Assert.assertNotNull(session);
		session.close();
	}
	
	@Test
	public void HibernateStatelessSessionTest(){
		statelessSession=HibernateConnection.getHibernateStatelessSession();
		Assert.assertNotNull(statelessSession);
		statelessSession.close();
	}	
}
