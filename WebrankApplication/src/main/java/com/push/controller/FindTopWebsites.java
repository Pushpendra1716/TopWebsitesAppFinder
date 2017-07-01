package com.push.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.google.gson.Gson;
import com.push.service.SiteStatisticsService;
import com.push.vo.JSonObject;
import com.push.vo.ViewBean;

/**
 * Servlet implementation class FindTopWebsites
 */
@WebServlet("/FindTopWebsites")
public class FindTopWebsites extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FindTopWebsites.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FindTopWebsites() {
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

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date excludedSince = null;
		Date excludedTill = null;
		int numberOfData=0;
		Map<String, Date> excludedSinceMap= new HashMap<String, Date>();
		Map<String, Date> excludedTillMap= new HashMap<String, Date>();
		try {
			date = sdf.parse(request.getParameter("datepicker"));
			if(request.getParameter("numberOfData")!= null){
				numberOfData = Integer.parseInt(request.getParameter("numberOfData"));
			}else{
				numberOfData=5;
			}
			
			logger.info("doPost Selected date for processing : "+date);
			List<JSonObject> excludedList = excludedService();
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
		request.getSession().setAttribute("viewBean", viewBeans);
		response.sendRedirect("home.jsp");
	}

	public List<JSonObject> excludedService(){
		List<JSonObject> excludedList = new ArrayList<JSonObject>();
		StringBuffer sb = new StringBuffer();
		String line;
		try{
			URL url = new URL("http://private-1de182-mamtrialrankingadjustments4.apiary-mock.com/exclusions");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			logger.info("Connection Done for URL :"+url.toString());
			if (conn.getResponseCode() != 200) {
				//throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
				logger.error("Failed : HTTP error code : "+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			while ((line = br.readLine()) != null) {				
				if(line.contains("[") || line.contains("]")){
					continue;
				}else{
					sb.append(line.trim());
				}
				
			}
			String []JsonObArr = sb.toString().split("},");
			for(int i=0;i < JsonObArr.length;i++){
				if(!JsonObArr[i].contains("}")){
					StringBuffer sb1= new StringBuffer();
					JsonObArr[i] = sb1.append(JsonObArr[i]).append("}").toString();
				}
				JSonObject jsonObj= stringToObjectConvert(JsonObArr[i].toString());
				if(jsonObj != null){
					excludedList.add(jsonObj);
					
				}
			}

			conn.disconnect();
			logger.info(" Disconnect Completed ");
		}catch(Exception e){
          logger.error("ERROR OCCURED: "+e);
		}
		return excludedList;
	}

	public JSonObject stringToObjectConvert(String jsonString){
		Gson g = new Gson();
		JSonObject jsonObj = g.fromJson(jsonString, JSonObject.class);
		return jsonObj;

	}

	//Method to clear the String Buffer
	public boolean clear(StringBuffer sb){
		try{
			for(int i=0;i<sb.length();i++){
				sb.deleteCharAt(i);
			}
			return true;
		}catch(ArrayIndexOutOfBoundsException ae){
			return false;
		}
	}

}
