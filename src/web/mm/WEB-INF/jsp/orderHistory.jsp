<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="orderHistory">
	<div id="orderHistoryPage_container">
		<h3>Order History</h3>		
		<div id="orderHistory_dateControls" >
			<span style="vertical-align:top">
				<label><strong>Orders placed during: </strong></label>
				<html:select property="selectedMonth" >
					<c:forEach var="month" items="${KualiForm.monthList}" varStatus="rowCounter" >
						<html:option value="${rowCounter.count-1}">${month}</html:option>
					</c:forEach>
				</html:select>
				<html:select property="selectedYear" >
					<c:forEach var="year" begin="2006" end="${KualiForm.currentYear}" varStatus="rowCounter" >
						<html:option value="${year}">${year}</html:option>
					</c:forEach>
				</html:select>
			</span>
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-go.gif" property="methodToCall.start" value="Refresh View" title="Refresh View" alt="Refresh View" />
			<br />
			<label><strong>Displaying </strong></label>
				<html:text property="itemsPerPage" size="2" alt="Items Per Page" title="Items displayed per page" />
			<label><strong> items per page.</strong></label>
		</div>
		<c:choose>
			<c:when test="${not empty KualiForm.orderDocumentList}">
				<c:set var="startIndex" value="${(KualiForm.currentPage -1) * KualiForm.itemsPerPage }" />
				<c:set var="endIndex" value="${(KualiForm.currentPage * KualiForm.itemsPerPage) - 1 }" />
				<c:if test="${endIndex >= fn:length(KualiForm.orderDocumentList) }">
					<c:set var="endIndex" value="${fn:length(KualiForm.orderDocumentList) - 1 }" />
				</c:if>			
<!--				(${KualiForm.currentPage } - 1) *  ${KualiForm.itemsPerPage} = ${startIndex } ;  (${KualiForm.currentPage} * ${KualiForm.itemsPerPage}) - 1 = ${endIndex }	 -->
				<c:forEach var="order" items="${KualiForm.orderDocumentList}" begin="${startIndex}" end="${endIndex}" varStatus="rowCounter" >
					<order:orderSummary orderDocument="${order}" orderDocumentPropertyName="orderDocumentList[${rowCounter.count-1}]" returnUrl="${ConfigProperties.application.url}/mm/orderHistory.do?methodToCall=start" showDetails="${false}" />
				</c:forEach>
				<shopping:pageNavigation pageAction="orderHistory.do?methodToCall=start&selectedMonth=${KualiForm.selectedMonth}&selectedYear=${KualiForm.selectedYear}" pageCount="${KualiForm.pageCount}" currentPage="${KualiForm.currentPage}" itemsPerPage="${KualiForm.itemsPerPage}" />
			</c:when>
			<c:otherwise>
				<div id="orderHistory_center_msg">
					No orders found.  Please choose another month and year.
				</div>
			</c:otherwise>
		</c:choose>
		
	</div>	
</shopping:page>