package com.push.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.push.service.UserService;


public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Authentication.class);       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(request.getSession().getAttribute("viewBean")!=null){
			request.getSession().removeAttribute("viewBean");
		}
		String emailId=request.getParameter("emailid");
		String password=request.getParameter("password");
		UserService user= new UserService();
		if(user.userAuthentication(emailId.trim(), password.trim())){
			logger.info("Authetication successfully");
			response.sendRedirect("home.jsp");
		}else{
			logger.info("Authetication Failed");
			response.sendRedirect("index.html");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
