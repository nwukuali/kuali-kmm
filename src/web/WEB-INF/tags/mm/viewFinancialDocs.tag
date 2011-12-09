<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<%@ attribute name="relatedFinancialDocNumbers" required="false"
	type="java.util.List"%>
<%@ attribute name="fromMaintenance" required="false"
	type="java.lang.Boolean"%>
<c:set var="documentNumbersList" value="${relatedFinancialDocNumbers}" />
<c:if test="${empty documentNumbersList && !fromMaintenance}">
	<c:set var="documentNumbersList"
		value="${KualiForm.document.relatedFinancialDocNumbers}" />
</c:if>
<kul:tab tabTitle="Related Financial Document(s)" defaultOpen="false">
	<div class="tab-container" align=center>
	<h3>Related Financial Document(s)</h3>
	<table cellpadding="0" cellspacing="0" class="datatable"
		summary="view/financial docs">

	<c:if test="${empty documentNumbersList}">
		<tr>
		<td class="datacell" height="50" colspan="12">
		<div align="center">There are currently no financial documents associated with this document.</div>
		</td>
		</tr>
	</c:if>
	<c:if test="${!empty documentNumbersList}">		
		<c:forEach var="entry" items="${documentNumbersList}">
			<tr>
			<td class="datacell center">
			<a target="_blank"
				href="<c:out value="${ConfigProperties.finance.system.url}" />/kew/DocHandler.do?command=displayDocSearchView&docId=<c:out value="${entry}" />">
			<c:out value="${entry}" />
			</a>&nbsp;</td>
			</tr>
		</c:forEach>
	</c:if>
	</table>
	</div>
</kul:tab>
