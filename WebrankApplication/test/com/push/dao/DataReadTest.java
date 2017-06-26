package com.push.dao;

import java.util.Date;

import org.hibernate.StatelessSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.push.database.connection.HibernateConnection;

public class DataReadTest {

	private StatelessSession statelessSession;
	private DataRead dataRead;
	
	@Before
	public void setUp(){
		statelessSession=HibernateConnection.getHibernateStatelessSession();
		dataRead= new DataRead();
	}
	
	@After
	public void closeUp(){
		statelessSession.close();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getTopDataForWebSitesTest1(){
		Assert.assertEquals(1, dataRead.getTopDataForWebSites(new Date("06-Jan-2016"), 1).size());
	}
	
}
