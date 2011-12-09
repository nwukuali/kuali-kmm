<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="orderSummary">
	<div id="orderSummaryPage_container">
		<h3>Order Summary</h3>
		<div id="orderPlaced_notification">These orders have been completed.</div>
		<c:if test="${KualiForm.forceWillCall}">
			<div class="highlight_red margin_left">*All personal use customers must provide their order number(s) at the University Stores showroom to pay for and pick up their orders.</div>
		</c:if>
		<c:forEach var="order" items="${KualiForm.orderDocumentList}" varStatus="rowCounter" >
			<order:orderSummary orderDocument="${order}" orderDocumentPropertyName="orderDocumentList[${rowCounter.count-1}]" returnUrl="${ConfigProperties.application.url}/mm/orderSummary.do?methodToCall=summary" showDetails="${true}" />
		</c:forEach>
	</div>
</shopping:page>