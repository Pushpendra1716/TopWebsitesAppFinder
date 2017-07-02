<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Details</title>
<script>
	history.forward();
</script>
</head>
<body bgcolor=" silver ">
<form action="detail_Info" method="post">
<center>
Select No of Row To Be Shown:
<select id="no_row" name="numberOfData">
<option value="10" selected="selected">10</option>
<option value="20">20</option>
<option value="50">50</option>
<option value="100">100</option>
</select>
<input type="submit" value="submit">
<br><br>
<c:if test='${detailViewBean != null}'>
		<c:choose>
			<c:when test='${fn:length(detailViewBean) gt 0}'>
				<table border="1" bordercolor="gray">
					<tr bgcolor="DarkSalmon">
						<th align="center" >ID</th>
						<th align="center">Web Site</th>
						<th align="center">Hits</th>
					</tr>
					<c:forEach items="${detailViewBean}" var="ViewBean">
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
		</center>
</form>
</body>
</html>