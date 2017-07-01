<%@page import="com.push.dao.DataForWeb"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<link rel="stylesheet" href="css/jquery-ui.css">
<script src="javascript/jquery-1.10.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
</head>
<body bgcolor=" silver " >
<form style="top color: black; font: bold; font-size: xx-small;"> 
	<a href="<%=new DataForWeb().checkExist("psuh")%>"> singOut</a>
</form>
<center>
	<p style="color: red; font: bold; font-size: large;">Top WebSites Details</p>
	<form action="process" method="post">
		<p> Date: <input type="text" size="15" name="datepicker" id="datepicker" width=50px>
			<input type="submit" value="Go" onclick="formSubmit()" />
		</p>
		<c:if test='${viewBean != null}'>
		<c:choose>
			<c:when test='${ fn:length(viewBean) gt 0}'>
				<table border="1" bordercolor="gray">
					<tr bgcolor="DarkSalmon">
						<th align="center" >ID</th>
						<th align="center">Web Site</th>
						<th align="center">Hits</th>
					</tr>
					<c:forEach items="${viewBean}" var="ViewBean">
						<tr>
							<td align="center" width="50" style="font:small-caps; color: black  ;">${ViewBean.id}</td>
							<td align="left" width="150" style="font:small-caps; color: black  ;">${ViewBean.siteName}</td>
							<td align="left" style="font:small-caps; color: black  ;">${ViewBean.visit}</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>There is no Records found for the searched date</c:otherwise>
		</c:choose>
		</c:if>
	<h6 onclick="link">..clickTo see More</h6>
	</form>
	
</center>
</body>
</html>