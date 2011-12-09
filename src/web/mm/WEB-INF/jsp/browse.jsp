<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="browse">
	<div id="browsePanel_wrapper">
		<shopping:browsePanel baseUrlAction="browse.do" />
	</div>	
	<div id="browsePage_content">
		<div id="browsePage_breadcrumb_container">
			<shopping:breadcrumb breadcrumbList="${KualiForm.browseManager.breadcrumbs}" />
		</div>	
		<shopping:resultsDisplay catalogItems="${KualiForm.browseManager.resultSet}" />
	</div>	
	<script type="text/javascript">
		var main = document.getElementById("browsePage_content");
		main.style.height=document.body.parentNode.scrollHeight + "px";
	</script>
</shopping:page>