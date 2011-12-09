<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<kul:documentPage showDocumentInfo="true" htmlFormAction="reorderItems" documentTypeName="SORD" renderMultipart="true" showTabButtons="true">
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="orderDetailAttributes" value="${DataDictionary.OrderDetail.attributes}" />
	<c:set var="additionalCostTypeAttributes" value="${DataDictionary.AdditionalCostType.attributes}" />
	<c:set var="docInMyActonList" value="${!empty KualiForm.editingMode['docInMyActonList']}" />
	<c:set var="pageReadOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_SAVE]}" />
	<kul:hiddenDocumentFields />
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
	<kul:tab tabTitle="Reorder" tabErrorKey="document.orderDetails*" defaultOpen="true">
		<div class="tab-container" align=center>
		<h3>Header</h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Reorder Item(s)" summary="Reorder Item(s)">
			<tr>
				<kul:htmlAttributeHeaderCell literalLabel="Warehouse" />
				<td class="grid"><kul:htmlControlAttribute attributeEntry="${stockAttributes.stockDistributorNbr}" property="document.warehouse.warehouseNme" readOnly="${true}" /></td>
				<kul:htmlAttributeHeaderCell literalLabel="Agreement" />
				<td>${KualiForm.document.agreementNbr}&nbsp;</td>				
				<kul:htmlAttributeHeaderCell literalLabel="Vendor" />
				<td>${KualiForm.document.agreement.vendorNm}&nbsp;</td>
			</tr>
		</table>
		</div>
		<div class="tab-container" align=center>
		<h3>Reorder Items</h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Reorder Items" summary="Reorder Items">
			<tr>
				<kul:htmlAttributeHeaderCell hideRequiredAsterisk="true" attributeEntry="${stockAttributes.stockDistributorNbr}" />
				<kul:htmlAttributeHeaderCell literalLabel="Quantity On Hand" />
				<kul:htmlAttributeHeaderCell forceRequired="${!pageReadOnly}" attributeEntry="${orderDetailAttributes.orderItemQty}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.manufacturerNbr}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.distributorNbr}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.orderItemCostAmt}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.extendedCost}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${additionalCostTypeAttributes.additionalCostTypeLookable}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.totalCost}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.expectedDate}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.itemToBeRemoved}" />
				<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.removeUntilDate}" />
			</tr>
			<logic:iterate id="rdetail" name="KualiForm" property="document.orderDetails" indexId="index">
				<tr>
					<td class="grid" align="center"><mm:stockHistoryLookup displayLabel="${KualiForm.document.orderDetails[index].catalogItem.stock.stockDistributorNbr}"
						stockId="${KualiForm.document.orderDetails[index].catalogItem.stock.stockId}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.orderItemQty}" property="document.orderDetails[${index}].catalogItem.stock.totalStockedQuantity"
						readOnly="${true}" /></td>
					<td class="grid"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.orderItemQty}" property="document.orderDetails[${index}].orderItemQty" readOnly="${pageReadOnly}" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}"
						property="document.orderDetails[${index}].stockUnitOfIssueCd" readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockAttributes.manufacturerNbr}" property="document.orderDetails[${index}].catalogItem.stock.manufacturerNbr"
						readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockAttributes.distributorNbr}" property="document.orderDetails[${index}].catalogItem.stock.distributorNbr"
						readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.orderItemCostAmt}" property="document.orderDetails[${index}].orderItemCostAmt"
						readOnly="true" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.extendedCost}" property="document.orderDetails[${index}].extendedCost" readOnly="true" /></td>
					<td class="grid" align="center"><C:if test="${!pageReadOnly}">
						<kul:htmlControlAttribute attributeEntry="${additionalCostTypeAttributes.additionalCostTypeLookable}" property="document.orderDetails[${index}].additionalCostTypeCode" readOnly="${pageReadOnly}" />
					</C:if> <C:if test="${pageReadOnly}">
						<kul:htmlControlAttribute attributeEntry="${additionalCostTypeAttributes.additionalCostTypeLookable}" property="document.orderDetails[${index}].additionalCostType.additionalCostTypeName"
							readOnly="${true}" />
					</C:if></td>
					<td class="grid" align="center"><kul:htmlControlAttribute property="document.orderDetails[${index}].orderItemAdditionalCostAmt"
						attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" readOnly="${pageReadOnly}" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.totalCost}" property="document.orderDetails[${index}].totalCost" readOnly="${pageReadOnly}" /></td>
					<td class="grid" align="center"><c:choose>
						<c:when test="${!pageReadOnly}">
							<kul:dateInput attributeEntry="${orderDetailAttributes.expectedDate}" property="document.orderDetails[${index}].expectedDate" />
						</c:when>
						<c:otherwise>
							<kul:htmlControlAttribute property="document.orderDetails[${index}].expectedDate" attributeEntry="${orderDetailAttributes.expectedDate}" readOnly="${true}" />
						</c:otherwise>
					</c:choose></td>
					<C:if test="${!pageReadOnly}">
						<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.itemToBeRemoved}" property="document.orderDetails[${index}].itemToBeRemoved"
							readOnly="${pageReadOnly}" /></td>
					</C:if>
					<td class="grid" align="center"><c:choose>
						<c:when test="${!pageReadOnly}">
							<kul:dateInput attributeEntry="${orderDetailAttributes.expectedDate}" property="document.orderDetails[${index}].catalogItem.stock.removeUntilDate" />
						</c:when>
						<c:otherwise>
							<kul:htmlControlAttribute property="document.orderDetails[${index}].catalogItem.stock.removeUntilDate" attributeEntry="${orderDetailAttributes.expectedDate}" readOnly="${true}" />
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell literalLabel="" />
					<kul:htmlAttributeHeaderCell literalLabel="Vendor :" />
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.orderItemQty}" property="document.orderDetails[${index}].buyOrderQuantity"
						readOnly="${true}" /></td>
					<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockAttributes.manufacturerNbr}" property="document.orderDetails[${index}].buyUnitOfIssueCd"
						readOnly="${true}" /></td>

					<c:choose>
						<c:when test="${!pageReadOnly}">
							<td class="grid" colspan="13" align="center"><kul:htmlControlAttribute attributeEntry="${additionalCostTypeAttributes.additionalCostTypeName}"
								property="document.orderDetails[${index}].catalogItem.stock.stockDesc" readOnly="${true}" /></td>
						</c:when>
						<c:otherwise>
							<td class="grid" colspan="12" align="center"><kul:htmlControlAttribute attributeEntry="${additionalCostTypeAttributes.additionalCostTypeName}"
								property="document.orderDetails[${index}].catalogItem.stock.stockDesc" readOnly="${true}" /></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</logic:iterate>
		</table>
		</div>
	</kul:tab>
	<c:if test="${!empty KualiForm.document.agreementNbr && !pageReadOnly}">
		<mm:newOrderItem />
	</c:if>
	<mm:viewFinancialDocs relatedFinancialDocNumbers="${KualiForm.document.relatedFinancialDocNumbers}" />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>
