package com.push.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.vo.ViewBean;
import com.push.service.SiteStatisticsService;

/**
 * Servlet implementation class FindTopWebsites
 */
@WebServlet("/FindTopWebsites")
public class FindTopWebsites extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindTopWebsites() {
        super();
        // TODO Auto-generated constructor stub
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date =null;
		try {
			date = sdf.parse(request.getParameter("datepicker"));
		} catch (ParseException e) {
			System.out.println("Exception occurred: "+e);
		}
		System.out.println(date);
		SiteStatisticsService ss= new SiteStatisticsService();
		List<ViewBean> list= ss.getTopSiteDetails(date,5);
		request.getSession().setAttribute("viewBean", list);
		response.sendRedirect("home.jsp");
		
	}

}
