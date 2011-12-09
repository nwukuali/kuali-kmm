<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="punchOut" hideSearchBar="true">	
	<div>
		<iframe src="punchOut.do?methodToCall=viewSite" scrolling="auto" height="750" width="100%">
			<p>Failed to load the vendor site</p>
		</iframe>
	</div>
</shopping:page>