<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.push.dao.DataForWeb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%

String userId=request.getParameter("userId");
if(userId != null && userId.length()!=0){
	DataForWeb dfw= new DataForWeb();
	if(dfw.checkExist(userId.trim())){
		out.print("User already exists");
	}else{
		out.print("User name is valid");	
	}	
}else{
	out.print(" ");
}


%>
</body>
</html>