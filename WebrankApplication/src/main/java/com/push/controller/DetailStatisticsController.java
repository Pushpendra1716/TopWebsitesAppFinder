package com.push.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.push.service.ExcludeServiceImpl;
import com.push.service.SiteStatisticsService;
import com.push.vo.JSonObject;
import com.push.vo.ViewBean;

/**
 * Servlet implementation class DetailStatisticsController
 */
@WebServlet("/DetailStatisticsController")
public class DetailStatisticsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DetailStatisticsController.class);  
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailStatisticsController() {
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
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date excludedSince = null;
		Date excludedTill = null;
		int numberOfData=0;
		Map<String, Date> excludedSinceMap= new HashMap<String, Date>();
		Map<String, Date> excludedTillMap= new HashMap<String, Date>();
		try {
			date = sdf.parse(request.getSession().getAttribute("date").toString());
			if(request.getParameter("numberOfData")!= null){
				numberOfData = Integer.parseInt(request.getParameter("numberOfData"));
			}else{
				numberOfData=5;
			}

			logger.info("doPost Selected date for processing : "+date);
			ExcludeServiceImpl esl = new ExcludeServiceImpl();
			List<JSonObject> excludedList = esl.excludedService();
			for(JSonObject jsObj: excludedList){
				excludedSince = sdf1.parse(jsObj.getExcludedSince());
				if(jsObj.getExcludedTill() == null){
					if( date.getTime() >= excludedSince.getTime()){
						excludedSinceMap.put(jsObj.getHost(), excludedSince);
					}
				}else{
					excludedTill = sdf1.parse(jsObj.getExcludedTill());
					if(date.getTime() >= excludedSince.getTime() && date.getTime() <= excludedTill.getTime() ){
						excludedTillMap.put(jsObj.getHost(), excludedTill);
					}
				}
			}

		} catch (ParseException e) {
			logger.error("Exception occurred:  "+e);
		}
		logger.info("Size of list which is going to fetch from DataBase :"+(excludedTillMap.size()+excludedSinceMap.size()+numberOfData));
		SiteStatisticsService ss = new SiteStatisticsService();
		List<ViewBean> viewBeansList = ss.getTopSiteDetails(date,(excludedTillMap.size()+excludedSinceMap.size()+numberOfData));
		/*Logic to the data which has excluded the data from API*/
		List<ViewBean> viewBeans= new ArrayList<ViewBean>();
		int count=0;
		for (ViewBean viewBean : viewBeansList) {
			if(excludedSinceMap.containsKey(viewBean.getSiteName().substring(viewBean.getSiteName().indexOf(".")+1))){
				continue;	
			}
			if(excludedTillMap.containsKey(viewBean.getSiteName().substring(viewBean.getSiteName().indexOf(".")+1))){
				continue;	
			}
			count=count+1;
			viewBean.setId(count);
			viewBeans.add(viewBean);
			if(count==numberOfData){
				break;
			}
		}
		request.getSession().setAttribute("detailViewBean", viewBeans);
		response.sendRedirect("details.jsp");
	}

}

