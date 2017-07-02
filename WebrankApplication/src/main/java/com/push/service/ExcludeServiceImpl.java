package com.push.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.push.vo.JSonObject;

public class ExcludeServiceImpl {
	private static Logger logger = Logger.getLogger(ExcludeServiceImpl.class);
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
