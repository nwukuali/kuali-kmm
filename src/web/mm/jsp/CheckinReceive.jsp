<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<kul:documentPage showDocumentInfo="true" htmlFormAction="checkinReceive" documentTypeName="SCHK" renderMultipart="true" showTabButtons="true">
	<c:set var="checkinDetailAttributes" value="${DataDictionary.CheckinDetail.attributes}" />
	<c:set var="checkinDocAttributes" value="${DataDictionary.CheckinDocument.attributes}" />
	<c:set var="unitOfIssueAttributes" value="${DataDictionary.UnitOfIssue.attributes}" />
	<c:set var="returnStatusCodeAttributes" value="${DataDictionary.ReturnStatusCode.attributes}" />
	<c:set var="returnDetailAttributes" value="${DataDictionary.ReturnDetail.attributes}" />
	<c:set var="warehouseAttributes" value="${DataDictionary.Warehouse.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="binAttributes" value="${DataDictionary.Bin.attributes}" />
	<c:set var="rentalAttributes" value="${DataDictionary.Rental.attributes}" />
	<kul:hiddenDocumentFields />
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
	<c:set var="pageReadOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT__DOCUMENT_OVERVIEW]}" />
	<kul:tab tabTitle="Check in Item(s)" defaultOpen="true" tabErrorKey="document.checkinDetails*,addSerialNumbers*,rejectedItems*,selectedItems*">
		<div class="tab-container" align=center>
		<h3>Check in Order : <c:out value="${KualiForm.document.orderDocNumber}" /></h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">
			<kul:htmlAttributeHeaderCell attributeEntry="${warehouseAttributes.warehouseNme}" />
			<td class="grid"><kul:htmlControlAttribute attributeEntry="${warehouseAttributes.warehouseNme}" property="document.warehouse.warehouseNme" readOnly="true" /> <kul:htmlAttributeHeaderCell
				attributeEntry="${checkinDocAttributes.vendorRefNbr}" />
			<td class="grid"><kul:htmlControlAttribute attributeEntry="${checkinDocAttributes.vendorRefNbr}" property="document.vendorRefNbr" readOnly="${pageReadOnly}" />
		</table>
		</div>
		<c:set var="newCheckInPos" value="-1" />
		<logic:iterate id="orderItem" name="KualiForm" property="document.orderDocument.orderDetails" indexId="orderItemIndx">
			<c:set var="orderStart" value="0" />
			<c:set var="newCheckInPos" value="${newCheckInPos+1}" />
			<c:if test="${!orderItem.orderLineComplete }">
				<div class="tab-container" align=center>
				<h3>Check in Item : <c:out value="${orderItem.distributorNbr}" /></h3>
				<table cellpadding="0" cellspacing="0" class="datatable" title="Check In Item(s)" summary="Check In Item(s)" border="0">
					<tr class="odd">
						<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.poId}" />
						<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.stockDesc}" />
						<kul:htmlAttributeHeaderCell literalLabel="Remaining" />
						<kul:htmlAttributeHeaderCell literalLabel="Total Quantity" />
					</tr>
					<tr class="odd">
						<td class="grid">
						<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.poId}" property="document.orderDocument.orderDetails[${orderItemIndx}].poId" readOnly="true" /></center>
						</td>
						<td class="grid">
						<center><kul:htmlControlAttribute attributeEntry="${stockAttributes.stockDesc}" property="document.orderDocument.orderDetails[${orderItemIndx}].orderItemDetailDesc" readOnly="true" /></center>
						</td>
						<td class="grid">
						<center><kul:htmlControlAttribute attributeEntry="${stockAttributes.manufacturerNbr}" property="document.orderDocument.orderDetails[${orderItemIndx}].remainingItemQuantityForDisplay"
							readOnly="true" />
						</td>
						<td class="grid" rowspan="1">
						<center><kul:htmlControlAttribute attributeEntry="${stockAttributes.manufacturerNbr}" property="document.orderDocument.orderDetails[${orderItemIndx}].orderItemQty" readOnly="true" /></center>
						</td>
						<!--  condition should end here -->
					</tr>
					<tr>
						<td colspan="4"><c:if
							test="${!pageReadOnly && (empty KualiForm.document.selectedOrderDetailId || KualiForm.document.selectedOrderDetailId == orderItem.orderDetailId) && orderItem.lineStatusCheckinable}">
							<h3>Add Location for Stock : <c:out value="${KualiForm.document.orderDocument.orderDetails[orderItemIndx].distributorNbr}" /></h3>
							<table cellpadding="0" cellspacing="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">								
								<tr class="odd">
									<th>&nbsp;</th>
									<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.acceptedItemQty}" />
									<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.binDisDesc}" />
									<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.availableQty}" />
									<kul:htmlAttributeHeaderCell literalLabel="Action" />
								</tr>
								<tr class="odd">
									<th>add:</th>
									<td class="grid">
									<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="addCheckinDetails[${newCheckInPos}].acceptedItemQty"
										readOnly="${pageReadOnly}" /></center>
									</td>
									<td class="grid">
									<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.binZoneDesc}" property="addCheckinDetails[${newCheckInPos}].binZoneDesc" readOnly="true" /> <kul:lookup
										boClassName="org.kuali.ext.mm.businessobject.BinLookable" autoSearch="yes" lookupParameters="document.warehouseCode:zone.warehouseCd"
										fieldConversions="binDisDesc:addCheckinDetails[${newCheckInPos}].binZoneDesc,availableQty:addCheckinDetails[${newCheckInPos}].availableQty,binId:addCheckinDetails[${newCheckInPos}].binId" /></center>
									</td>
									<td class="grid">
									<center><kul:htmlControlAttribute attributeEntry="${binAttributes.availableQty}" property="addCheckinDetails[${newCheckInPos}].availableQty" readOnly="true" /></center>
									</td>
									<td class="grid" align="center" rowspan="1">
									<center><html:image property="methodToCall.addCheckinDetail.line${KualiForm.document.orderDocument.orderDetails[orderItemIndx].orderDetailId}"
										src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" title="Add Location" alt="Add Location" styleClass="tinybutton" /></center>
									</td>
								</tr>
							</table>
						</c:if>
						<table cellpadding="0" cellspacing="0" class="datatable" title="Checked In Lines" summary="Checked In Line(s)">
							<logic:iterate id="cdetail" name="KualiForm" property="document.checkinDetails" indexId="sctr">
								<c:if test="${KualiForm.document.checkinDetails[sctr].stock.stockDistributorNbr eq orderItem.distributorNbr}">
									<c:set var="curOrderId" value="${KualiForm.document.checkinDetails[sctr].orderDetailId}" />
									<c:set var="perOrderId" value="${KualiForm.document.checkinDetails[sctr-1].orderDetailId}" />

									<c:choose>
										<c:when test="${(KualiForm.document.checkinLinesCount-1) > sctr}">
											<c:set var="nextOrderId" value="${KualiForm.document.checkinDetails[sctr+1].orderDetailId}" />
										</c:when>
										<c:otherwise>
											<c:set var="nextOrderId" value="" />
										</c:otherwise>
									</c:choose>
									<!-- logic for added items  -->
									<%-- <c:if test="${! empty KualiForm.document.checkinDetails[sctr].binId}">--%>
										<c:set var="colSize" value="5" />
										<c:if test="${orderStart == 0}">
											<tr class="odd">
												<th>#</th>
												<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.acceptedItemQty}" />
												<c:if test="${KualiForm.document.checkinDetails[sctr].stock.perishableInd}">
													<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.stockPerishableDate}" />
												</c:if>
												<kul:htmlAttributeHeaderCell attributeEntry="${binAttributes.binDisDesc}" />
												<c:if test="${!pageReadOnly}">
													<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.closedInd}" />
												</c:if>
												<kul:htmlAttributeHeaderCell attributeEntry="${checkinDetailAttributes.rejectedItemQty}" />
												<kul:htmlAttributeHeaderCell attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}" />
												<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.returnUnitOfIssueOfCode}" />
												<c:if test="${!pageReadOnly}">
													<kul:htmlAttributeHeaderCell literalLabel="Action" />
												</c:if>
											</tr>
										</c:if>
										<tr class="odd">
											<th><c:out value="${sctr + 1}" />:</th>
											<td class="grid">
											<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.acceptedItemQty}" property="document.checkinDetails[${sctr}].acceptedItemQty" readOnly="${pageReadOnly}" /></center>
											</td>
											<c:if test="${KualiForm.document.checkinDetails[sctr].stock.perishableInd}">
												<c:set var="colSize" value="${colSize+1}" />
												<td class="grid">
												<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.stockPerishableDate}" property="document.checkinDetails[${sctr}].stockPerishableDate"
													readOnly="${pageReadOnly}" /></center>
												</td>
											</c:if>
											<td class="grid">
											<center><mm:lookupControlAttribute attributeEntry="${checkinDetailAttributes.binZoneDesc}" property="document.checkinDetails[${sctr}].binZoneDesc" readOnly="true" /> <c:if
												test="${!pageReadOnly}">
												<kul:lookup boClassName="org.kuali.ext.mm.businessobject.BinLookable" autoSearch="yes"
													lookupParameters="document.warehouseCode:zone.warehouseCd,document.checkinDetails[${sctr}].stock.stockDistributorNbr:stockBalance.stock.stockDistributorNbr"
													fieldConversions="availableQty:document.checkinDetails[${sctr}].availableQty,binDisDesc:document.checkinDetails[${sctr}].binZoneDesc,binId:document.checkinDetails[${sctr}].binId" />
											</c:if></center>
											</td>
											<c:if test="${!pageReadOnly}">
												<c:set var="colSize" value="${colSize+1}" />
												<td class="grid" rowspan="1">
												<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.closedInd}" property="document.checkinDetails[${sctr}].closedInd" readOnly="${pageReadOnly}" /></center>
												</td>
											</c:if>
											<td class="grid">
											<center><kul:htmlControlAttribute attributeEntry="${checkinDetailAttributes.rejectedItemQty}" property="document.checkinDetails[${sctr}].rejectedItemQty" readOnly="${pageReadOnly}" /></center>
											</td>
											<c:if test="${!pageReadOnly}">
												<td class="grid">
												<center><kul:htmlControlAttribute attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}" property="document.checkinDetails[${sctr}].returnDetailStatusCode"
													readOnly="${pageReadOnly}" /></center>
												</td>
											</c:if>
											<c:if test="${pageReadOnly}">
												<td class="grid">
												<center><kul:htmlControlAttribute attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}"
													property="document.checkinDetails[${sctr}].returnStatus.returnStatusCodeName" readOnly="${pageReadOnly}" /></center>
												</td>
											</c:if>
											<c:if test="${!pageReadOnly}">
												<td class="grid">
												<center><kul:htmlControlAttribute attributeEntry="${unitOfIssueAttributes.unitOfIssueCodeLookable}" property="document.checkinDetails[${sctr}].returnUnitOfIssueOfCode"
													readOnly="${pageReadOnly}" /></center>
												</td>
											</c:if>
											<c:if test="${pageReadOnly}">
												<td class="grid">
												<center><kul:htmlControlAttribute attributeEntry="${unitOfIssueAttributes.unitOfIssueCodeLookable}" property="document.checkinDetails[${sctr}].returnUnitOfIssue.unitOfIssueDesc"
													readOnly="${pageReadOnly}" /></center>
												</td>
											</c:if>
											<c:if test="${!pageReadOnly}">
												<c:set var="colSize" value="${colSize+1}" />
												<td class="grid" align="center" rowspan="1"><html:image property="methodToCall.deleteCheckinDetail.line${sctr}"
													src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" title="Delete Location" alt="Delete Location" styleClass="tinybutton" /></td>
											</c:if>
										</tr>
										<c:if test="${KualiForm.document.checkinDetails[sctr].stock.rental}">
											<mm:rentalTracking rentalTrackingDetail="${KualiForm.document.checkinDetails[sctr]}" 
												rentalTrackingDetailPropertyName="document.checkinDetails[${sctr}]" 
												rentalTrackingDetailIndex="${sctr}"
												columnSpan="${colSize}" 
												readOnly="${pageReadOnly }" />											
										</c:if>
									</c:if>
									<!-- logic for added items  ends here -->
									<!-- first if tag -->
									<c:set var="orderStart" value="${orderStart+1}" />
								<%--</c:if> --%>
							</logic:iterate>
						</table>
						</td>
					</tr>
				</table>
				</div>
			</c:if>
		</logic:iterate>
	</kul:tab>
	
	<c:if test="${KualiForm.document.itemsReturned}">
		<kul:tab tabTitle="Return Document" defaultOpen="true">
			<div class="tab-container">
			<h3>Return Document</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Return Items" summary="Return Document">
				<tbody>
					<tr>
						<th align="left"><a class="portal_link" href="kew/DocHandler.do?command=displayDocSearchView&docId=${KualiForm.document.returnDocuments[0].documentNumber}" target='_blank' title="Return Document">${KualiForm.document.returnDocuments[0].documentNumber}</a></th>
					</tr>
				</tbody>
			</table>
			</div>
		</kul:tab>
	</c:if>
	<mm:closedOrderItems />
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}" />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls suppressRoutingControls="${KualiForm.onlyLineViewed}" transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>
