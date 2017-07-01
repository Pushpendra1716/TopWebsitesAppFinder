package com.push.service;

import com.push.dao.DataForWeb;

public class UserService {
	
	public boolean userAuthentication(String userId, String pass){

		DataForWeb dfw= new DataForWeb();
		return dfw.isUserAuthorize(userId, pass);
	}

}
