<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="orderCompletion">
	<div id="orderCompletion_center">
		<h3>Order Completion</h3>
		<c:forEach var="order" items="${KualiForm.orderDocumentList}" varStatus="rowCounter" >
			<order:orderSummary orderDocument="${order}" orderDocumentPropertyName="orderDocumentList[${rowCounter.count-1}]" returnUrl="${ConfigProperties.application.url}/mm/orderCompletion.do?methodToCall=complete" />
		</c:forEach>
	</div>

</shopping:page>