<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<html>
<kul:page showDocumentInfo="false" htmlFormAction="stockHistoryLookup" renderMultipart="true" showTabButtons="false" docTitle="Stock History" transactionalDocument="false" headerDispatch="true"
	headerTabActive="true" sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start">

	<script>
function closeCurWindow(){
	window.close();
}
</script>
	<c:set var="stockHistoryLookupAttributes" value="${DataDictionary.StockHistory.attributes}" />
	<c:set var="WarehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="stockHistoryAttributes" value="${DataDictionary.StockHistory.attributes}" />
	<kul:tabTop tabTitle="Stock History" defaultOpen="true" tabErrorKey="*">
		<div style="display: block;" id="divDataHistory">
		<div class="tab-container" align=center>
		<div>
		<h2>Stock History</h2>
		</div>
		<table class="datatable" cellpadding="0" cellspacing="0" summary="view history" border="1">
			<tr>
				<th class="grid" colspan="1">Warehouse/Item</th>
				<td class="grid"><kul:htmlControlAttribute attributeEntry="${WarehouseAttributes.warehouseNme}" property="stockHistoryLookupObject.warehouses" readOnly="${true}" />&nbsp;&#47;&nbsp;<kul:htmlControlAttribute
					attributeEntry="${stockAttributes.stockDistributorNbr}" property="stockHistoryLookupObject.stock.stockDistributorNbr" readOnly="${true}" /></td>
			</tr>
			<tr>
				<th class="grid" colspan="1">&nbsp;U/I - Description</th>
				<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockAttributes.buyUnitOfIssueCd}" property="stockHistoryLookupObject.stock.buyUnitOfIssueCd" readOnly="${true}" />&nbsp;&nbsp;<kul:htmlControlAttribute
					attributeEntry="${stockAttributes.stockDesc}" property="stockHistoryLookupObject.stock.stockDesc" readOnly="${true}" /></td>
			</tr>
		</table>
		<div>
		<h2><br>
		Current Information</h2>
		</div>
		<table class="datatable" border="1" summary="view current" cellpadding="0" cellspacing="0">
			<tr>
				<th class="grid" colspan="1">&nbsp;Quantity Available</th>
				<th class="grid" colspan="1">&nbsp;Quantity on Hand</th>
				<th class="grid" colspan="1">&nbsp;Quantity on Order</th>
				<th class="grid" colspan="1">&nbsp;Quantity Allocated</th>
				<th class="grid" colspan="1">&nbsp;Quantity Backorder</th>
				<th class="grid" colspan="1">&nbsp;Reorder Point</th>
				<th class="grid" colspan="1">&nbsp;Order Quantity</th>
				<th class="grid" colspan="1">&nbsp;Perishable</th>
				<th class="grid" colspan="1">&nbsp;Hazardous</th>
				<th class="grid" colspan="1">&nbsp;Average Unit Cost</th>
				<th class="grid" colspan="1">&nbsp;Last Reorder Date</th>
			</tr>
			<tr>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.qtyAvailable" attributeEntry="${stockHistoryAttributes.afterStockQty}"
					readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.quantityOnHand"
					attributeEntry="${stockHistoryAttributes.afterStockQty}" readOnly="${true}" /></td>
				</td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.orderQuantity"
					attributeEntry="${stockHistoryAttributes.afterStockQty}" readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.quantityAllocated"
					attributeEntry="${stockHistoryAttributes.afterStockQty}" readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.backOrderQty" attributeEntry="${stockHistoryAttributes.afterStockQty}"
					readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.reorderPoint" attributeEntry="${stockHistoryAttributes.afterStockQty}"
					readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.minimumOrderQty"
					attributeEntry="${stockHistoryAttributes.afterStockQty}" readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.perishableDesc" attributeEntry="${stockAttributes.stockDesc}"
					readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.hazardousDesc" attributeEntry="${stockAttributes.stockDesc}"
					readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.averageUnitCost"
					attributeEntry="${stockHistoryAttributes.beforeStockPrc}" readOnly="${true}" /></td>
				<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.currentStockHistoryInformation.lastReorderDate"
					attributeEntry="${stockHistoryAttributes.historyTransTimestamp}" readOnly="${true}" /></td>
			</tr>
		</table>
		<div>
		<h2><br>
		Purchase History</h2>
		</div>
		<table class="datatable" border="1" cellpadding="0" cellspacing="0" summary="view po history">
			<tr>
				<th class="grid" colspan="1">&nbsp;PO Number</th>
				<th class="grid" colspan="1">&nbsp;Shipments</th>
				<th class="grid" colspan="1">&nbsp;Order Quantity</th>
				<th class="grid" colspan="1">&nbsp;Received Quantity</th>
				<th class="grid" colspan="1">&nbsp;Order Date</th>
				<th class="grid" colspan="1">&nbsp;Receive Date</th>
				<th class="grid" colspan="1">&nbsp;Lag</th>
				<th class="grid" colspan="1">&nbsp;Additional Charge</th>
				<th class="grid" colspan="1">&nbsp;Unit Price</th>
			</tr>

			<c:forEach var="purchaseHistory" items="${KualiForm.stockHistoryLookupObject.purchaseHistory}" varStatus="sctr">
				<tr>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].poNumber" attributeEntry="${stockHistoryAttributes.afterStockQty}"
						readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].numberOfShipments" attributeEntry="${stockHistoryAttributes.afterStockQty}"
						readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].orderQuantity" attributeEntry="${stockHistoryAttributes.afterStockQty}"
						readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].acceptedQty" attributeEntry="${stockHistoryAttributes.afterStockQty}"
						readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].orderDate" attributeEntry="${stockHistoryAttributes.historyTransTimestamp}"
						readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].receivedDate"
						attributeEntry="${stockHistoryAttributes.historyTransTimestamp}" readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].lag" attributeEntry="${stockHistoryAttributes.afterStockQty}"
						readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].chargedAdditionallyText"
						attributeEntry="${stockHistoryAttributes.worksheetCountDocNbr}" readOnly="${true}" /></td>
					<td class="grid" colspan="1"><kul:htmlControlAttribute property="stockHistoryLookupObject.purchaseHistory[${sctr.count-1}].unitPrice" attributeEntry="${stockHistoryAttributes.beforeStockPrc}"
						readOnly="${true}" /></td>
				</tr>
			</c:forEach>
		</table>

		<div>
		<h2><br>
		Sales History</h2>
		</div>
		<table class="datatable" border="1" cellpadding="0" cellspacing="0" summary="view sales history">
			<tr>
				<th class="grid" colspan="1">&nbsp;</th>
				<th class="grid" colspan="1">&nbsp;Transactions</th>
				<th class="grid" colspan="1">&nbsp;Units</th>
				<th class="grid" colspan="1">&nbsp;Total</th>
				<th class="grid" colspan="1">&nbsp;From Date</th>
				<th class="grid" colspan="1">&nbsp;To Date</th>
			</tr>
			<tr>
				<td class="grid" colspan="1">Current Year To Date</td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["CURRENT_YEAR_TO_DATE"].numberOfTranscations}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["CURRENT_YEAR_TO_DATE"].numberOfUnits}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["CURRENT_YEAR_TO_DATE"].totalCost}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["CURRENT_YEAR_TO_DATE"].formDate}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["CURRENT_YEAR_TO_DATE"].toDate}' /></td>
			</tr>
			<tr>
				<td class="gridr" colspan="1">Cumulative 12 Months:</td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["CUMMULATIVE_12_MONTHS"].numberOfTranscations}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["CUMMULATIVE_12_MONTHS"].numberOfUnits}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["CUMMULATIVE_12_MONTHS"].totalCost}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["CUMMULATIVE_12_MONTHS"].formDate}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["CUMMULATIVE_12_MONTHS"].toDate}' /></td>
			</tr>
			<tr>
				<td class="gridr" colspan="1">Last Fiscal Year:</td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["LAST_FISCAL_YEAR"].numberOfTranscations}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["LAST_FISCAL_YEAR"].numberOfUnits}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["LAST_FISCAL_YEAR"].totalCost}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["LAST_FISCAL_YEAR"].formDate}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["LAST_FISCAL_YEAR"].toDate}' /></td>
			</tr>
			<tr>
				<td class="gridr" colspan="1">2nd Fiscal Year:</td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["SECOND_FISCAL_YEAR"].numberOfTranscations}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["SECOND_FISCAL_YEAR"].numberOfUnits}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["SECOND_FISCAL_YEAR"].totalCost}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["SECOND_FISCAL_YEAR"].formDate}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["SECOND_FISCAL_YEAR"].toDate}' /></td>
			</tr>
			<tr>
				<td class="gridr" colspan="1">3rd Fiscal Year:</td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["THIRD_FISCAL_YEAR"].numberOfTranscations}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["THIRD_FISCAL_YEAR"].numberOfUnits}' /></td>
				<td class="grid" colspan="1"><c:out value='${KualiForm.stockHistoryLookupObject.salesHistory["THIRD_FISCAL_YEAR"].totalCost}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["THIRD_FISCAL_YEAR"].formDate}' /></td>
				<td class="grid" colspan="1"><fmt:formatDate type="date" value='${KualiForm.stockHistoryLookupObject.salesHistory["THIRD_FISCAL_YEAR"].toDate}' /></td>
			</tr>
		</table>


		<div>
		<h2><br>
		Transactions</h2>
		</div>
		<table class="datatable" border="1" cellpadding="0" cellspacing="0" summary="view/edit history">
			<tr>
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.afterStockPrc}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.afterStockQty}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.afterStockUnitOfIssueCd}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.beforeStockPrc}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.beforeStockQty}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.beforeStockUnitOfIssueCd}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.checkinDocNbr}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.historyTransTimestamp}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.pickVerifyDocNbr}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.returnDocNbr}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.stockTransReasonCode}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.transStockPrc}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.transStockQty}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.transStockUnitOfIssueCd}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockHistoryAttributes.worksheetCountDocNbr}" />
			</tr>


			<logic:iterate id="obj" name="KualiForm" property="stockHistoryLookupObject.stockHistoryObjs" indexId="index">
				<tr>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.afterStockPrc}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].afterStockPrc"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.afterStockQty}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].afterStockQty"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.afterStockUnitOfIssueCd}"
						property="stockHistoryLookupObject.stockHistoryObjs[${index}].afterStockUnitOfIssueCd" readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.beforeStockPrc}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].beforeStockPrc"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.beforeStockQty}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].beforeStockQty"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.beforeStockUnitOfIssueCd}"
						property="stockHistoryLookupObject.stockHistoryObjs[${index}].beforeStockUnitOfIssueCd" readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.checkinDocNbr}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].checkinDocNbr"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.historyTransTimestamp}"
						property="stockHistoryLookupObject.stockHistoryObjs[${index}].historyTransTimestamp" readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.pickVerifyDocNbr}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].pickVerifyDocNbr"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.returnDocNbr}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].returnDocNbr"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.stockTransReasonCode}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].stockTransReasonCode"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.transStockPrc}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].transStockPrc"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.transStockQty}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].transStockQty"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.transStockUnitOfIssueCd}"
						property="stockHistoryLookupObject.stockHistoryObjs[${index}].transStockUnitOfIssueCd" readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockHistoryAttributes.worksheetCountDocNbr}" property="stockHistoryLookupObject.stockHistoryObjs[${index}].worksheetCountDocNbr"
						readOnly="${true}" /></td>
				</tr>
			</logic:iterate>
		</table>
		<div id="globalbuttons" class="globalbuttons"><html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_close.gif" styleClass="globalbuttons" title="close"
			onclick="closeCurWindow()" alt="close" /></div>
		</div>
	</kul:tabTop>
	<kul:panelFooter />
</kul:page>