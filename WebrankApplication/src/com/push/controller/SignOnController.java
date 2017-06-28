package com.push.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.dao.DataForWeb;
import com.push.vo.UserDetailsVo;
import com.push.vo.UserLoginVo;

/**
 * Servlet implementation class SignOnController
 */

public class SignOnController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignOnController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String userName= request.getParameter("fName")+request.getParameter("lName");
		  String userId= request.getParameter("userId");
		  String password= request.getParameter("password");
		  String emailId= request.getParameter("emailId");
		  String country= request.getParameter("country");
		  String phoneNo= request.getParameter("mobileNo");
		 
		  UserLoginVo v1= new UserLoginVo();
		  v1.setUserId(userId);
		  v1.setPassword(password);
		  v1.setLastLoginTIme(new Timestamp(System.currentTimeMillis()));
		  UserDetailsVo v2= new UserDetailsVo();
		  v2.setUserId(userId);
		  v2.setUserName(userName);
		  v2.setCountry(country);
		  v2.setEmailId(emailId);
		  v2.setNumber(new BigDecimal(phoneNo));
		  v2.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		  DataForWeb dfw = new DataForWeb();
		  if(dfw.storeUser(v1, v2)){
			  response.sendRedirect("success.jsp");
		  }else{
			  response.sendRedirect("failed.jsp");
		  }
	}

}
