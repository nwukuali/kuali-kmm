<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:deadPage title="University Stores" htmlFormAction="logout">

	<div id="logout_container">
		<div id="logout_topMessage">
			Thank you for visiting!
		</div>
		<div id="logout_explanation">
			<p style="text-indent:25px">You are now logged out of Shop at State. 
			You have not been logged out of any other applications that share this login. 
			If you wish to log out of all the applications that share this login, 
			please click the link below.</p>
			<p id="logout_sso_link"><a class="blue" href="https://login.dev.ais.msu.edu/Logout" title="Click to Logout of Sentinal SSO">Logout from Sentinel SSO Access.</a></p>
		</div>
		
	</div>
	
</shopping:deadPage>