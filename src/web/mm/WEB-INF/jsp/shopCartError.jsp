<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<shopping:page title="Shopping Error" htmlFormAction="systemError">
	<div id="shoppingError_container">
		<div id="shoppingError_topMessage">
			An error has occurred.
		</div>
		<div id="shoppingError_explanation">
			<p>${KualiForm.errorMessage}</p>
			<p id="shoppingError_continue_line"><a class="blue" href="${KualiForm.returnUrl}" title="Click to continue">Click here to continue.</a></p>
		</div>
		&nbsp;
	</div>		
</shopping:page>