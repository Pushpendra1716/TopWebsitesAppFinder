package com.push.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.StatelessSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.push.database.connection.HibernateConnection;
import com.push.vo.UserDetailsVo;
import com.push.vo.UserLoginVo;

public class DataReadTest {

	private StatelessSession statelessSession;
	private DataForWeb dataRead;
	private UserLoginVo userLoginVo;
	private UserDetailsVo userDetailsVo;
	
	@Before
	public void setUp(){
		statelessSession=HibernateConnection.getHibernateStatelessSession();
		dataRead= new DataForWeb();
		userDetailsVo= new UserDetailsVo();
		userLoginVo= new UserLoginVo();
	}
	
	@After
	public void closeUp(){
		statelessSession.close();
	}
	
	@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void getTopDataForWebSitesTest1(){
		Assert.assertEquals(3, dataRead.getTopDataForWebSites(new Date("06-Jan-2016"), 3).size());
	}
	
	@Ignore
	@Test
	public void storeUserTest1(){
		
		userLoginVo.setUserId(String.valueOf(Math.random()));
		userLoginVo.setPassword("1234");
		
		userDetailsVo.setCountry("India");
		userDetailsVo.setEmailId("Pushpendra1716@gmail.com");
		userDetailsVo.setNumber(new BigDecimal("9742331500"));
		userDetailsVo.setSequerityQuestion("Name Please");
		userDetailsVo.setSequerityAnswer("Pushpendra Singh");
		userDetailsVo.setUserName("Push");
		
		dataRead.storeUser(userLoginVo, userDetailsVo);
	}
	
}
