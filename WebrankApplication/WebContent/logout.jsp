<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
</script>
</head>
<body dir="ltr">
<font color="red"><label style="position: absolute; right: 110px;"><%=session.getAttribute("userId") %></label></font>
<font color="blue"><a style="position: absolute;right: 30px;" href="doLogOut.jsp">Sign out</a></font>

</body>
</html>