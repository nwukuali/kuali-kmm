<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="StyleSheet" href="kr/css/kuali.css" type="text/css">
<title>Finance system not available</title>
</head>
<body>
<br>
<br>
<div class="topblurb">
<div align="left">
<table cellpadding="10" cellspacing="0" border="0" class="container2">
	<tr>
		<td colspan="2">
		<div align="left"><font color="red">Finance system [<a
			href="<%=request.getAttribute("financeAppurl")%>" target="_blank"><%=request.getAttribute("financeApp")%></a>
		] provided services are not available at this point of time. Using
		Materiel Management System in the absence of finance system services
		could produce undesired results.</font></div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div align="left"><font color="blue">We suggest you leave
		this application by closing the browser now. If you would like to
		proceed please click <a href="<%=request.getAttribute("redirect")%>">here</a>
		to continue.</font></div>
		</td>
	</tr>
</table>
</div>
</div>

</body>
</html>