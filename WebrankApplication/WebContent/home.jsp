<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<link rel="stylesheet" href="css/jquery-ui.1.css">
<script src="javascript/jquery-1.10.2.js"></script>
<script src="javascript/jquery-ui.1.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
</head>
<body>
<center>
	<form style="border-style: solid;" action="process" method="post">
		<p>
			Date: <input type="text" name="datepicker" id="datepicker" width=50px><input
				type="submit" value="go" onclick="formSubmit()" />
		</p>
		<br>
		<c:if test='${viewBean != null}'>
		<c:choose>
			<c:when test='${ fn:length(viewBean) gt 0}'>
				<table>
					<tr>
						<th>ID</th>
						<th>Web Site</th>
						<th>Hits</th>
					</tr>
					<c:forEach items="${viewBean}" var="ViewBean">
						<tr>
							<td>${ViewBean.id}</td>
							<td>${ViewBean.siteName}</td>
							<td>${ViewBean.visit}</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>There is no Records found for the searched date</c:otherwise>
		</c:choose>
		</c:if>
	</form>
</center>
</body>
</html>