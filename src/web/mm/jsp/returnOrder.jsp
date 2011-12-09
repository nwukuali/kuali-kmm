<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<kul:documentPage showDocumentInfo="true" htmlFormAction="returnOrder" documentTypeName="SRET" renderMultipart="true" showTabButtons="true">

	<c:set var="returnDetailAttributes" value="${DataDictionary.ReturnDetail.attributes}" />
	<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	<c:set var="orderDetailAttributes" value="${DataDictionary.OrderDetail.attributes}" />
	<c:set var="additionalCostType" value="${DataDictionary.AdditionalCostType.attributes}" />
	<c:set var="rentalAttributes" value="${DataDictionary.Rental.attributes}" />
	<c:set var="checkinDetailAttributes" value="${DataDictionary.CheckinDetail.attributes}" />
	<c:set var="returnStatusCodeAttributes" value="${DataDictionary.ReturnStatusCode.attributes}" />
	<c:set var="vendorReturnStatusCodeAttributes" value="${DataDictionary.VendorReturnStatusCode.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="docInFinalState" value="${!empty KualiForm.editingMode['docInFinalState']}" />
	<c:set var="docReadyToBeReviewed" value="${!empty KualiForm.editingMode['docReadyToBeReviewed']}" />
	<c:set var="docInMyActonList" value="${!empty KualiForm.editingMode['docInMyActonList']}" />
	<c:set var="pageReadOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_SAVE]}" />
	<c:set var="isVendorReturnDoc" value="${!empty KualiForm.editingMode['isVendorReturnDoc']}" />

	<c:set var="returnColumnRowSpan" value="2" />
	<c:set var="rentalColumnSpan" value="7" />

	<kul:hiddenDocumentFields />

	<kul:documentOverview editingMode="${KualiForm.editingMode}" />

	<c:if test="${isVendorReturnDoc}">
		<mm:vendor vendorLookupRequired="${!pageReadOnly}" />
	</c:if>
	<kul:tab tabTitle="Order" defaultOpen="true" tabErrorKey="document.orderDocument.*">
		<div class="tab-container" align=center>
		<h3>Order Details</h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Return Items" summary="Return Items">
			<tr>
			<th class="grid" align="right" width="12%" ><kul:htmlAttributeLabel attributeEntry="${orderAttributes.documentNumber}" /></th>
			<td class="grid" align="left" width="12%" ><kul:htmlControlAttribute attributeEntry="${orderAttributes.documentNumber}" property="document.orderDocument.documentNumber" readOnly="true" /></td>
			<th class="grid" align="right" width="12%" ><kul:htmlAttributeLabel attributeEntry="${orderAttributes.orderId}" /></th>
			<td class="grid" align="left" width="12%" ><kul:htmlControlAttribute attributeEntry="${orderAttributes.orderId}" property="document.orderDocument.orderId" readOnly="true" /></td>
			<th class="grid" align="right" width="15%" >Profile Name:</th>
			<td class="grid" align="left" width="15%" ><kul:htmlControlAttribute attributeEntry="${orderAttributes.customerProfile.profileName}" property="document.orderDocument.customerProfile.profileName" readOnly="true" /></td>
			<th class="grid" align="right"><kul:htmlAttributeLabel attributeEntry="${orderAttributes.creationDate}" /></th>
			<td class="grid" align="left"><kul:htmlControlAttribute attributeEntry="${orderAttributes.creationDate}" property="document.orderDocument.creationDate" readOnly="true" /></td>
			</tr>
			<tr>
			<th class="grid" align="right" width="12%" ><kul:htmlAttributeLabel attributeEntry="${orderAttributes.campusCd}" /></th>
			<td class="grid" align="left" width="12%" ><kul:htmlControlAttribute attributeEntry="${orderAttributes.campusCd}" property="document.orderDocument.campusCd" readOnly="true" /></td>
			<th class="grid" align="right" width="12%" ><kul:htmlAttributeLabel attributeEntry="${orderAttributes.deliveryBuildingCd}" /></th>
			<td class="grid" align="left" width="12%" ><kul:htmlControlAttribute attributeEntry="${orderAttributes.deliveryBuildingCd}" property="document.orderDocument.deliveryBuildingCd" readOnly="true" /></td>
			<th class="grid" align="right" width="15%" ><kul:htmlAttributeLabel attributeEntry="${orderAttributes.deliveryBuildingRmNbr}" /></th>
			<td class="grid" align="left" width="15%" ><kul:htmlControlAttribute attributeEntry="${orderAttributes.deliveryBuildingRmNbr}" property="document.orderDocument.deliveryBuildingRmNbr" readOnly="true" /></td>
			<th class="grid" align="right"><kul:htmlAttributeLabel attributeEntry="${orderAttributes.deliveryDepartmentNm}" /></th>
			<td class="grid" align="left"><kul:htmlControlAttribute attributeEntry="${orderAttributes.deliveryDepartmentNm}" property="document.orderDocument.deliveryDepartmentNm" readOnly="true" /></td>
			</tr>
		</table>
		</div>
	</kul:tab>
	<kul:tab tabTitle="Return Item(s)" defaultOpen="true" tabErrorKey="document.returnDetails*,addSerialNumbers*,selectedItems*">
		<logic:iterate id="rdetail" name="KualiForm" property="document.returnDetails" indexId="index">
			<div class="tab-container" align=center>
			<h3>Return Item</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Return Items" summary="Return Items">
				<tr>
					<td>
					<div class="tab-container" align=center>
					<h3>Item Info</h3>
					<table cellpadding="0" cellspacing="0" class="datatable" title="Return Items" summary="Return Items">
						<tr>
							<kul:htmlAttributeHeaderCell literalLabel=" # " />
							<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.returnQuantity}" />
							<kul:htmlAttributeHeaderCell attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}" />
							<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.returnUnitOfIssueOfCode}" />
							<kul:htmlAttributeHeaderCell attributeEntry="${stockAttributes.stockDistributorNbr}" />
							<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.returnItemPrice}" />
							<kul:htmlAttributeHeaderCell attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" useShortLabel="true" />
							<kul:htmlAttributeHeaderCell attributeEntry="${additionalCostType.additionalCostTypeName}" useShortLabel="true" />

							<kul:htmlAttributeHeaderCell literalLabel="Extended Cost" />
							<c:if test="${!docReadyToBeReviewed and !docInFinalState }">
								<kul:htmlAttributeHeaderCell literalLabel="Return" />
							</c:if>
							<c:if test="${docReadyToBeReviewed && docInMyActonList}">
								<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.returnItemPercentage}" />
								<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.returnCreditAmt}" />
							</c:if>
						</tr>
						<tr>
							<th><c:out value="${index + 1}" />:</th>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnQuantity}" property="document.returnDetails[${index}].returnQuantity" readOnly="${pageReadOnly}" /></td>
							<c:if test="${!pageReadOnly}">
								<c:if test="${isVendorReturnDoc}">
									<td class="grid"><kul:htmlControlAttribute attributeEntry="${vendorReturnStatusCodeAttributes.returnStatusCodeLookable}" property="document.returnDetails[${index}].returnDetailStatusCode" readOnly="${pageReadOnly}" /></td>
								</c:if>
								<c:if test="${!isVendorReturnDoc}">
									<td class="grid"><kul:htmlControlAttribute attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}" property="document.returnDetails[${index}].returnDetailStatusCode" readOnly="${pageReadOnly}" /></td>
								</c:if>

							</c:if>
							<c:if test="${pageReadOnly}">
								<td class="grid"><kul:htmlControlAttribute attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}" property="document.returnDetails[${index}].returnStatusName"
									readOnly="${pageReadOnly}" /></td>
							</c:if>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnUnitOfIssueOfCode}"
								property="document.returnDetails[${index}].returnUnitOfIssue.unitOfIssueDesc" readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${stockAttributes.stockDistributorNbr}"
								property="document.returnDetails[${index}].catalogItem.stock.stockDistributorNbr" readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnItemPrice}" property="document.returnDetails[${index}].returnItemPrice"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" property="document.returnDetails[${index}].orderDetail.orderItemAdditionalCostAmt"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${additionalCostType.additionalCostTypeName}" property="document.returnDetails[${index}].orderDetail.additionalCostType.additionalCostTypeName"
								readOnly="true" /></td>
							<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.extendedCost}" property="document.returnDetails[${index}].extendedCost" readOnly="true" /></td>
							<c:if test="${!docReadyToBeReviewed && !docInFinalState}">
								<c:if test="${rdetail.lineItemRental}" >
									<c:set var="returnColumnRowSpan" value="3" />
									<c:if test="${!empty rdetail.stagingRentals}" >
										<c:set var="returnColumnRowSpan" value="4" />
									</c:if>
								</c:if>
								<td rowspan="${returnColumnRowSpan}" class="grid" align="center"><kul:htmlControlAttribute property="document.returnDetails[${index}].itemReturned" attributeEntry="${returnDetailAttributes.itemReturned}"
									readOnly="${pageReadOnly}" /></td>
							</c:if>
							<c:if test="${docReadyToBeReviewed && docInMyActonList}">
								<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnItemPercentage}" property="document.returnDetails[${index}].returnItemPercentage"
									readOnly="${pageReadOnly}" /></td>
								<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnCreditAmt}" property="document.returnDetails[${index}].returnCreditAmt"
									readOnly="${pageReadOnly}" /></td>
								<c:set var="rentalColumnSpan" value="9" />
							</c:if>
						</tr>
						<tr>
							<c:set var="descriptionColSpan" value="8" />
							<c:if test="${docReadyToBeReviewed && docInMyActonList}">
								<c:set var="descriptionColSpan" value="10" />
							</c:if>
							<th>&nbsp;</th>							
							<td colspan="${descriptionColSpan}" class="grid" align="center">
								<kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.returnItemDetailDescription}" property="document.returnDetails[${index}].returnItemDetailDescription" readOnly="true" />
							</td>
						</tr>
						<c:if test="${rdetail.lineItemRental }">
							<c:set var="tabOpene" value="${ KualiForm.document.returnDetails[index].rentalError}" />
							<mm:rentalTracking rentalTrackingDetail="${KualiForm.document.returnDetails[index]}" 
												rentalTrackingDetailPropertyName="document.returnDetails[${index}]" 
												rentalTrackingDetailIndex="${index}"
												columnSpan="${rentalColumnSpan}" 
												readOnly="${pageReadOnly}" />
							</c:if>
					</table>
					</div>

					<c:if test="${docReadyToBeReviewed && docInMyActonList}">
						<div class="tab-container" align=center>
						<h3>Action for the Item</h3>
						<table cellpadding="0" cellspacing="0" class="datatable" title="Action for the Item" summary="Action for the Item">
							<tr>
								<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.dispostitionCode}" forceRequired="${true}" />
								
								<c:if test="${( KualiForm.document.returnDetails[index].deptCreditRequired) && 
								(!empty KualiForm.document.returnDetails[index].actionCd)}">
									<c:choose>
										<c:when test="${readOnly}">
											<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.departmentCreditInd}" forceRequired="${true}" />
										</c:when>
										<c:otherwise>
											<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.departmentCreditStringInd}" forceRequired="${true}" />
										</c:otherwise>
									</c:choose>									
								</c:if>
								<c:if test="${(! KualiForm.document.returnDetails[index].customerLine) && 
								(!empty KualiForm.document.returnDetails[index].actionCd)}">
									<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.vendorCreditInd}" />
									<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.vendorReshipInd}" />
								</c:if>
								<kul:htmlAttributeHeaderCell attributeEntry="${returnDetailAttributes.vendorDispositionInd}" />
							</tr>
							<tr>
								<td class="grid"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.dispostitionCode}" property="document.returnDetails[${index}].dispostitionCode"
									readOnly="${pageReadOnly}" forceRequired="${true}" /></td>
								<c:if test="${( KualiForm.document.returnDetails[index].deptCreditRequired) && 
								(!empty KualiForm.document.returnDetails[index].actionCd)}">
									<td class="grid" align="center">
										<c:choose>
											<c:when test="${readOnly}">
												<kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.departmentCreditInd}" property="document.returnDetails[${index}].departmentCreditInd"
													readOnly="${pageReadOnly}" forceRequired="${true}" />
											</c:when>
											<c:otherwise>
												<kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.departmentCreditStringInd}" property="document.returnDetails[${index}].departmentCreditStringInd"
													readOnly="${pageReadOnly}" forceRequired="${true}" />
											</c:otherwise>
										</c:choose>
									</td>
								</c:if>
								<c:if test="${(! KualiForm.document.returnDetails[index].customerLine) && 
								(!empty KualiForm.document.returnDetails[index].actionCd)}">
									<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.vendorCreditInd}" property="document.returnDetails[${index}].vendorCreditInd"
										readOnly="${pageReadOnly}" /></td>
									<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.vendorReshipInd}" property="document.returnDetails[${index}].vendorReshipInd"
										readOnly="${pageReadOnly}" /></td>
								</c:if>
								<td class="grid" align="center"><kul:htmlControlAttribute attributeEntry="${returnDetailAttributes.vendorDispositionInd}" property="document.returnDetails[${index}].vendorDispositionInd"
									readOnly="${pageReadOnly}" /></td>
							</tr>
						</table>
						</div>
					</c:if></td>
				</tr>
			</table>
			</div>
		</logic:iterate>
	</kul:tab>

	<c:if test="${docInFinalState}">
		<kul:tab tabTitle="Print RTVs" defaultOpen="true">
			<div class="tab-container" align=center>
			<h3>Print RTVs</h3>
			<table cellpadding="0" class="datatable" title="Return Items" summary="Return Items">
				<tbody>
					<tr>
						<th><a class="portal_link" href="returnOrder.do?methodToCall=display&documentId=${KualiForm.document.documentNumber}" target='_blank' title="Print RTVs">Print RTVs</a></th>
					</tr>
				</tbody>
			</table>
			</div>
		</kul:tab>
	</c:if>

	<c:if test="${!docReadyToBeReviewed && !pageReadOnly}">
		<mm:newReturnItem vendorDocument="${isVendorReturnDoc}" />
	</c:if>
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}" />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>
