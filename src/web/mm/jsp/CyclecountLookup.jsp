<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<script type="text/javascript">
		function diff(elemId, newVal, dbVal, stkId){
			var prfx = '';
			if(!isNaN(newVal) && (dbVal-newVal) != 0 ){			
			if((dbVal-newVal) > 0){prfx='+';}
				document.getElementById(elemId).innerHTML='<a target="'+stkId+'" href="stockHistoryLookup.do?stockId='+stkId+'">'+prfx+(dbVal-newVal)+'</a>';
			}else{
				document.getElementById(elemId).innerHTML = '';
			}
		}
	</script>
<kul:documentPage showDocumentInfo="true" htmlFormAction="initiateCycleCountEntry" documentTypeName="SWKC" renderMultipart="true" showTabButtons="true">

	<c:set var="markupTypeAttributes" value="${DataDictionary.MarkupType.attributes}" />
	<c:set var="warehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	<c:set var="worksheetCounterAttributes" value="${DataDictionary.WorksheetCounter.attributes}" />
	<c:set var="worksheetCountAttributes" value="${DataDictionary.WorksheetCountDocumentLookable.attributes}" />
	<c:set var="stockCountAttributes" value="${DataDictionary.StockCount.attributes}" />
	<c:set var="readOnly" value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

	<kul:hiddenDocumentFields />

	<kul:documentOverview editingMode="${KualiForm.editingMode}" />

	<mm:worksheetCounters reviewMode="${!KualiForm.documentInMyActionList}" />
	<kul:tab tabTitle="Cycle Count Entry" defaultOpen="true" tabErrorKey="document.stockCounts*">
		<div class="tab-container" align=center>
		<h3>Update Stock Item counts for Worksheet : <c:out value="${KualiForm.document.documentNumber}" /></h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Cycle Count Entry" summary="Cycle Count Entry">
			<tr>
				<kul:htmlAttributeHeaderCell literalLabel="&nbsp;" />
				<kul:htmlAttributeHeaderCell attributeEntry="${zoneAttributes.zoneCd}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.binDisDesc}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.stockDesc}" />
				<kul:htmlAttributeHeaderCell hideRequiredAsterisk="true" attributeEntry="${stockAttributes.stockDistributorNbr}" />
				<kul:htmlAttributeHeaderCell hideRequiredAsterisk="${!KualiForm.documentInMyActionList}" attributeEntry="${stockCountAttributes.stockCountItemQty}" />
				<kul:htmlAttributeHeaderCell literalLabel="Diff" />
			</tr>
			<c:set var="index" value="${0}" />
			<c:forEach var="stockCount" items="${KualiForm.document.stockCounts}" varStatus="loop">
				<tr>
					<th><c:out value="${index + 1}" />:</th>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${zoneAttributes.zoneCd}" property="document.stockCounts[${loop.index}].bin.zone.zoneCd" readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${binAttributes.binDisDesc}" property="document.stockCounts[${loop.index}].bin.binDisDesc" readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockAttributes.stockDesc}" property="document.stockCounts[${loop.index}].stock.stockDesc" readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockAttributes.stockDistributorNbr}" property="document.stockCounts[${loop.index}].stock.stockDistributorNbr"
						readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockCountAttributes.stockCountItemQty}" forceRequired="true"
						property="document.stockCounts[${loop.index}].stockCountItemQty" readOnly="${!KualiForm.documentInMyActionList}"						
						onchange="diff('${stockCount.stockCountId}',this.value,'${stockCount.beforeItemQty}','${stockCount.stockId}');" /></td>
					<td class="grid" align="center">
					<div id="${stockCount.stockCountId}"><c:if test="${!readOnly and stockCount.mismatchCount != 0}">
						<c:set var="prfx" value="" />
						<c:if test="${stockCount.mismatchCount > 0}">
							<c:set var="prfx" value="+" />
						</c:if>
						<a target="${stockCount.stockId}" href="stockHistoryLookup.do?stockId=${stockCount.stockId}">${prfx}${stockCount.mismatchCount}</a>
					</c:if> &nbsp;</div>
					</td>
				</tr>
				<c:set var="index" value="${index+1}" />
			</c:forEach>
		</table>
		</div>
	</kul:tab>
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}" />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons">
		<c:if test="${KualiForm.documentInMyActionList}">
			<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_refresh.gif" styleClass="globalbuttons" property="methodToCall.save" title="check variance" alt="check variance" />
			<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_save.gif" styleClass="globalbuttons" property="methodToCall.save" title="save" alt="save" />
			<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" styleClass="globalbuttons" property="methodToCall.route" title="submit" alt="submit" />
			<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_cancel.gif" styleClass="globalbuttons" property="methodToCall.cancel" title="cancel" alt="cancel" />
		</c:if>
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_close.gif" styleClass="globalbuttons" property="methodToCall.close" title="cancel" alt="close" />
	</div>
</kul:documentPage>