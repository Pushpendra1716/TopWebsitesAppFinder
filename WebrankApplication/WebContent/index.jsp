<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

<link rel='stylesheet prefetch'
	href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

<link rel="stylesheet" href="css/style.css">


</head>

<body>
	<div class="logmod">
		<div class="logmod__wrapper">
			<div class="logmod__container">
				<ul class="logmod__tabs">
					<li data-tabtar="lgm-2"><a href="#">Login</a></li>
					<li data-tabtar="lgm-1"><a href="#">Sign Up</a></li>
				</ul>
				<div class="logmod__tab-wrapper">
					<div class="logmod__tab lgm-1">

						<div class="logmod__form">
							<form accept-charset="utf-8" action="signOn" method="post"
								name="form1" class="simform">
								<div class="sminputs">
									<div class="input string optional">
										<label class="string optional" for="fName">First Name
											*</label> <input class="string optional" maxlength="255" id="fName"
											placeholder="fName" type="text" name="fName" size="50" />
									</div>
									<div class="input string optional">
										<label class="string optional" for="lName">Last Name *</label>
										<input class="string optional" maxlength="255" id="lName"
											placeholder="lName" type="text" name="lName" size="50" />
									</div>
								</div>

								<div class="sminputs">
									<div class="input full">
										<label class="string optional" for="emailId">Email*</label> <input
											class="string optional" maxlength="255" id="user-email"
											placeholder="Email" type="email" name="emailId" size="50" />
									</div>
								</div>
								<div class="sminputs">
									<div class="input string optional">
										<label class="string optional" for="pass">Password *</label> <input
											class="string optional" maxlength="255" id="user-pw"
											placeholder="Password" type="password" name="password"
											size="50" />
									</div>
									<div class="input string optional">
										<label class="string optional" for="repeatPass">Repeat
											password *</label> <input class="string optional" maxlength="255"
											id="user-pw-repeat" placeholder="Repeat password"
											type="password" size="50" />
									</div>
								</div>

								<div class="sminputs">
									<div class="input string optional">
										<label class="string optional" for="userId">UserId *</label> <input
											class="string optional" maxlength="255" id="userId"
											placeholder="userId" type="text" name="userId" size="40"
											onblur="checkExist()" />
									</div>
									<div class="input string optional">
										<span class="string optional" id="user-Status"></span>
									</div>
								</div>
								<div class="sminputs">
									<div class="input string optional">
										<label class="string optional" for="mobileNo">Mobile
											No *</label> <input class="string optional" maxlength="255"
											id="mobileNo" type="text" placeholder="Mobile No"
											name="mobileNo" size="50" />
									</div>
									<div class="input string optional">
										<label class="string optional" for="country">Country *</label>
										<input class="string optional" maxlength="255" id="country"
											placeholder="Country" name="country" type="text" size="50" />
									</div>
								</div>
								<div class="simform__actions">
									<input class="sumbit" type="submit" value="Create Account" />
								</div>
							</form>
						</div>
					</div>
					<div class="logmod__tab lgm-2">
						<div class="logmod__heading">
							<span class="logmod__heading-subtitle">Enter your userId
								and password</span>
						</div>
						<div class="logmod__form">
							<form action="login" method="get" class="simform">
								<div class="sminputs">
									<div class="input full">
										<label class="string optional">User Id*</label> <input
											class="string optional" maxlength="255" name="emailid"
											placeholder="userId" type="text" size="50" />
									</div>
								</div>
								<div class="sminputs">
									<div class="input full">
										<label class="string optional" for="user-pw">Password
											*</label> <input class="string optional" maxlength="255"
											name="password" placeholder="Password" type="password"
											size="50" /> <span class="hide-password">Show</span>
									</div>
								</div>
								<div class="simform__actions">
									<input class="sumbit" type="submit" value="Log In" /> <span
										class="simform__actions-sidetext">
										<%
											String loginMsg = (String) request.getAttribute("error");
											if (loginMsg != null)
												out.println("<font color='red'>"+loginMsg+"</font>");
										%>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="javascript/index.js"></script>
	<script>
		function checkExist() {

			var xmlhttp = new XMLHttpRequest();
			var username = document.forms["form1"]["userId"].value;
			var url = "check.jsp?userId=" + username;
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					if (xmlhttp.responseText == "\n\n\n\n\nUser already exists")
						document.getElementById("user-Status").style.color = "red";
					else
						document.getElementById("user-Status").style.color = "green";
					document.getElementById("user-Status").innerHTML = xmlhttp.responseText;
				}

			};
			try {
				xmlhttp.open("GET", url, true);
				xmlhttp.send();
			} catch (e) {
				alert("unable to connect to server");
			}
		}
	</script>
</body>
</html>
