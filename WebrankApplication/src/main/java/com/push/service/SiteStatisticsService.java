package com.push.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.push.dao.DataForWeb;
import com.push.vo.ViewBean;

public class SiteStatisticsService {
	
	public List<ViewBean> getTopSiteDetails(Date date, int noOfRow){
		
		DataForWeb dr = new DataForWeb();
		List <ViewBean> listOfObj=dr.getTopDataForWebSites(date, noOfRow);
		Collections.sort(listOfObj);
		return listOfObj;
		
	}
	
}
