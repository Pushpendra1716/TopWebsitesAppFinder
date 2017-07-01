package com.push.service;

import com.push.dao.DataForWeb;

public class UserService {
	
	public boolean userAuthentication(String userId, String pass){
		/*Connection connection = null;
		try{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "rahul123");
				String query = "select * from USERLOGIN where USER_EMAIL_ID=? and USER_PASSWORD=?";
				PreparedStatement pstmt = connection.prepareStatement(query);
				pstmt.setString(1, emailId);
				pstmt.setString(2, pass);
				if(pstmt.execute()){
					System.out.println("Authentication completed successfully");
					return true;
				}else{
					System.out.println("Authentication Failed ");
				}
		
		}catch(Exception e){
			System.out.println("connection failed: "+e);
		}
		return false;*/
		DataForWeb dfw= new DataForWeb();
		return dfw.isUserAuthorize(userId, pass);
	}

}
