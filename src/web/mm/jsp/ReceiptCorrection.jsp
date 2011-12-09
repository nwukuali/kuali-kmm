<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<kul:documentPage showDocumentInfo="true" htmlFormAction="receiptCorrection" documentTypeName="SRCN" renderMultipart="true" showTabButtons="true">
	<SCRIPT type="text/javascript">
	    function submitForChangedType(value) {
		var str = "document.checkinDetails["+value+"].";
		var acceptedQty = str + "acceptedItemQty";
		var correctedQty = str+ "correctedCheckinDetail.acceptedItemQty";
		var varianceVal = str+ "correctedQuantity";
	    	var val = document.getElementById(correctedQty).value - 
	    				document.getElementById(acceptedQty).value;
			if(val > 0 )
				document.getElementById(varianceVal).value = val;
			else
				document.getElementById(varianceVal).value = 0;
       		
	    }
    </SCRIPT>
	<c:set var="checkinDetailAttributes" value="${DataDictionary.CheckinDetail.attributes}" />
	<c:set var="checkinDocAttributes" value="${DataDictionary.CheckinDocument.attributes}" />
	<c:set var="unitOfIssueAttributes" value="${DataDictionary.UnitOfIssue.attributes}" />
	<c:set var="returnStatusCodeAttributes" value="${DataDictionary.ReturnStatusCode.attributes}" />
	<c:set var="returnDetailAttributes" value="${DataDictionary.ReturnDetail.attributes}" />
	<c:set var="warehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	<c:set var="rentalAttributes" value="${DataDictionary.Rental.attributes}" />
	<c:set var="newDetailIndex" value="${0}" />
	<kul:hiddenDocumentFields />
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
	<c:set var="pageReadOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT__DOCUMENT_OVERVIEW]}" />
	<kul:tab tabTitle="Check in Item(s)" defaultOpen="true" tabErrorKey="document.checkinDetails*,selectedItems*,addSerialNumbers*">
		<div class="tab-container" align=center>
		<h3>Correct Checked In Order</h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Item(s)" summary="Item(s)">
			<tr>
				<kul:htmlAttributeHeaderCell literalLabel="Warehouse" />
				<td class="grid"><kul:htmlControlAttribute attributeEntry="${checkinDocAttributes.warehouseCode}" property="document.warehouse.warehouseNme" readOnly="true" /></td>
				<kul:htmlAttributeHeaderCell literalLabel="Order Doc #" />
				<td class="grid"><kul:htmlControlAttribute attributeEntry="${checkinDocAttributes.orderDocNumber}" property="document.orderDocNumber" readOnly="true" /></td>
				<kul:htmlAttributeHeaderCell literalLabel="Order Number" />
				<td class="grid"><kul:htmlControlAttribute attributeEntry="${checkinDocAttributes.orderDocNumber}" property="document.orderDocument.orderId" readOnly="true" /></td>

			</tr>
		</table>
		</div>
		<logic:iterate id="orderItem" name="KualiForm" property="checkedInOrderDetails" indexId="orderItemIndx">
			<div class="tab-container" align=center>
			<h3>Correct Item <c:out value="${orderItem.distributorNbr}" /></h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Correct Item(s)" summary="Correct Item(s)">
				<tr>
					<kul:htmlAttributeHeaderCell literalLabel=" # " />
					<kul:htmlAttributeHeaderCell literalLabel="PO" />
					<kul:htmlAttributeHeaderCell hideRequiredAsterisk="true" attributeEntry="${stockAttributes.stockDistributorNbr}" />
					<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.acceptedItemQty}" />
					<kul:htmlAttributeHeaderCell literalLabel="New Accepted Quantity" />
					<kul:htmlAttributeHeaderCell literalLabel="Corrected Quantity" />
					<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.binDisDesc}" />
					<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.availableQty}" />
					<c:if test="${!pageReadOnly}">
						<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.lineCorrected}" />
					</c:if>
				</tr>
				<logic:iterate id="rdetail" name="KualiForm" property="document.checkinDetails" indexId="index">
					<c:if test="${KualiForm.document.checkinDetails[index].stock.stockDistributorNbr eq orderItem.distributorNbr }">
						<tr>
							<th><c:out value="${index + 1}" />:</th>
							<td rowspan="1" valign="top"><kul:htmlControlAttribute attributeEntry="${warehouseAttributes.warehouseNme}" property="document.checkinDetails[${index}].orderDetail.poId" readOnly="true" />
							</td>
							<td class="grid" align="center"><mm:stockHistoryLookup displayLabel="${KualiForm.document.checkinDetails[index].stock.stockDistributorNbr}"
								stockId="${KualiForm.document.checkinDetails[index].stockId}" /></td>
							<td rowspan="1" valign="top">
							<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="document.checkinDetails[${index}].correctedCheckinDetail.acceptedItemQty" disabled="true" />
							</td>
							<td rowspan="1" valign="top">
							<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="document.checkinDetails[${index}].acceptedItemQty" onchange="submitForChangedType(${index})"
								readOnly="${pageReadOnly}" />
							</td>
							<td rowspan="1" valign="top">							
							<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="document.checkinDetails[${index}].correctedQuantity" disabled="true" />
							</td>
							<td class="grid">
							<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.binZoneDesc}" property="document.checkinDetails[${index}].binZoneDesc" readOnly="true" /></center>
							</td>
							<td class="grid">
							<center><kul:htmlControlAttribute attributeEntry="${binAttributes.availableQty}" property="document.checkinDetails[${index}].availableQty" readOnly="true" /></center>
							</td>
							<c:if test="${!pageReadOnly}">
								<td class="grid" align="center" rowspan="1"><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.lineCorrected}" property="document.checkinDetails[${index}].lineCorrected"
									readOnly="${pageReadOnly}" /></td>
							</c:if>
						</tr>
						<!-- Begin Rentals section -->
						<c:if test="${KualiForm.document.checkinDetails[index].stock.rental}">
							<c:set var="colSpanVal" value="7" />
							<c:if test="${pageReadOnly}">
								<c:set var="colSpanVal" value="6" />
							</c:if>					
							<mm:rentalTracking rentalTrackingDetail="${KualiForm.document.checkinDetails[index]}" 
												rentalTrackingDetailPropertyName="document.checkinDetails[${index}]" 
												rentalTrackingDetailIndex="${index}"
												columnSpan="${colSpanVal}" 
												readOnly="${pageReadOnly }" />								
						</c:if>								
					<!-- End Rentals section -->
					</c:if>
				</logic:iterate>
			</table>
			</div>
			<!-- first if tag -->
		</logic:iterate>
	</kul:tab>
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}" />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls suppressRoutingControls="${KualiForm.onlyLineViewed}" transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>
