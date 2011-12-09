<%--
 Copyright 2006-2007 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
	<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
	<c:set var="profileAttributes" value="${DataDictionary.Profile.attributes}" />
	<c:set var="poVendorAttributes" value="${DataDictionary.PunchOutVendor.attributes}" />
	<c:set var="orderDetailAttributes" value="${DataDictionary.OrderDetail.attributes}" />
	<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	
	<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
	
	<kul:documentPage showDocumentInfo="true"
		htmlFormAction="order"
		documentTypeName="SORD" renderMultipart="true"
		showTabButtons="true">
	
	<kul:hiddenDocumentFields />
	<html:hidden property="returnToSenderUrl" />

	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Order" defaultOpen="true" tabErrorKey="">
		<div class="tab-container" align=center>
			<h3>Order</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Order Content" summary="Review order content">
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${orderAttributes.orderTypeCode}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.orderTypeCode" attributeEntry="${orderAttributes.orderTypeCode}" readOnly="${true}" >
						<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.OrderType" keyValues="orderTypeCode=${KualiForm.document.orderTypeCode}" render="true">
		              		<html:hidden write="true" property="document.orderTypeCode" />
			            </kul:inquiry>&nbsp;
		       		</kul:htmlControlAttribute>			
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${orderAttributes.orderId}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.orderId" attributeEntry="${orderAttributes.orderId}" readOnly="${true }" />		
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${orderAttributes.orderStatusCd}" useShortLabel="true" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="orderDocument.orderStatus.orderStatusDesc" attributeEntry="${orderDocument.orderStatus.orderStatusDesc}" readOnly="${true}" >
						<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.OrderStatus" keyValues="orderStatusCd=${KualiForm.document.orderStatusCd}" render="true">
		              		<html:hidden write="true" property="document.orderStatusCd" />
			            </kul:inquiry>&nbsp;
		       		</kul:htmlControlAttribute>			
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${profileAttributes.principalName}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.customerProfile.principalName" attributeEntry="${orderAttributes.orderStatusCd}" readOnly="${true}" >
						<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Profile" keyValues="principalName=${KualiForm.document.customerProfile.principalName}" render="true">
		              		<html:hidden write="true" property="document.customerProfile.principalName" />
			            </kul:inquiry>&nbsp;
		       		</kul:htmlControlAttribute>			
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${orderAttributes.campusCd}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.campusCd" attributeEntry="${orderAttributes.campusCd}" readOnly="${readOnly}" >
<!--						<kul:inquiry boClassName="" keyValues="campusCd=${}" render="true">-->
<!--		              		<html:hidden write="true" property="document.campusCd" />-->
<!--			            </kul:inquiry>&nbsp;-->
		       		</kul:htmlControlAttribute>			
				</td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${KualiForm.orderDocument.orderTypeCode == 'PUNCH'}">
						<th width="50%" class="bord-l-b">
				        	<div align="right">		    
					     		<kul:htmlAttributeLabel attributeEntry="${poVendorAttributes.punchOutVendorName}" />	
							</div>
						</th>
						<td class="datacell-nowrap">
							<kul:htmlControlAttribute property="punchOutVendor.punchOutVendorName" attributeEntry="${poVendorAttributes.punchOutVendorName}" readOnly="${true}" >
								<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Warehouse" keyValues="warehouseCd=${KualiForm.punchOutVendor.punchOutVendorName}" render="true">
				              		<html:hidden write="true" property="punchOutVendor.punchOutVendorName" />
					            </kul:inquiry>&nbsp;
				       		</kul:htmlControlAttribute>			
						</td>
					</c:when>
					<c:when test="${KualiForm.orderDocument.orderTypeCode == 'HOSTED'}">
					</c:when>
					<c:otherwise>
						<th width="50%" class="bord-l-b">
				        	<div align="right">		    
					     		<kul:htmlAttributeLabel attributeEntry="${orderAttributes.warehouseCd}" />	
							</div>
						</th>
						<td class="datacell-nowrap">
							<kul:htmlControlAttribute property="document.warehouseCd" attributeEntry="${orderAttributes.warehouseCd}" readOnly="${true}" >
								<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Warehouse" keyValues="warehouseCd=${KualiForm.document.warehouseCd}" render="true">
				              		<html:hidden write="true" property="document.warehouseCd" />
					            </kul:inquiry>&nbsp;
				       		</kul:htmlControlAttribute>			
						</td>
					</c:otherwise>					
				</c:choose>				
			</tr>
			</table>
			
			<mm:accountingLines accountingLines="${KualiForm.document.accounts}" newAccountingLinePropertyName="newAccountingLine" accountingLinesPropertyName="document.accounts" tabTitle="Accounts" tabErrorKey="document.accounts*,newAccountingLine.*" defaultOpen="${true}" readOnly="${readOnly}" />
			
			<kul:tab tabTitle="Order Details" defaultOpen="true" tabErrorKey="">
				<div class="tab-container" align=center>
					<h3>Order Line Items</h3>
					<c:forEach var="detail" items="${KualiForm.document.orderDetails}" varStatus="rowCounter" >						
						<table cellpadding="0" cellspacing="0" class="datatable" title="Order Line Items" summary="Review order line items">
							<tr>
								<kul:htmlAttributeHeaderCell width="10%" attributeEntry="${orderDetailAttributes.distributorNbr}" useShortLabel="true" hideRequiredAsterisk="true" scope="col"/>								
								<kul:htmlAttributeHeaderCell width="55%" attributeEntry="${orderDetailAttributes.orderItemDetailDesc}" useShortLabel="true" hideRequiredAsterisk="true" scope="col"/>
								<kul:htmlAttributeHeaderCell width="5%" attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}" useShortLabel="true" hideRequiredAsterisk="true" scope="col"/>
								<kul:htmlAttributeHeaderCell width="5%" attributeEntry="${orderDetailAttributes.orderItemQty}" useShortLabel="true" hideRequiredAsterisk="true" scope="col"/>
								<kul:htmlAttributeHeaderCell width="8%" attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" useShortLabel="true" hideRequiredAsterisk="true" scope="col"/>
								<kul:htmlAttributeHeaderCell width="8%" attributeEntry="${orderDetailAttributes.orderItemPriceAmt}" useShortLabel="true" hideRequiredAsterisk="true" scope="col"/>
								<th width="9%" scope="col">Line Total</th>
<!--								<th width="8%" scope="col">Approved</th>-->
							</tr>						
								<tr>									
									<td class="grid" rowspan="3"><kul:htmlControlAttribute property="document.orderDetails[${rowCounter.count -1}].distributorNbr" attributeEntry="${orderDetailAttributes.distributorNbr}" readOnly="${true}" /></td>
									<td class="grid"><kul:htmlControlAttribute property="document.orderDetails[${rowCounter.count -1}].orderItemDetailDesc" attributeEntry="${orderDetailAttributes.orderItemDetailDesc}" readOnly="${true}" /></td>
									<td class="grid"><kul:htmlControlAttribute property="document.orderDetails[${rowCounter.count -1}].stockUnitOfIssueCd" attributeEntry="${orderDetailAttributes.stockUnitOfIssueCd}" readOnly="${true}" /></td>
									<td class="grid"><kul:htmlControlAttribute property="document.orderDetails[${rowCounter.count -1}].orderItemQty" attributeEntry="${orderDetailAttributes.orderItemQty}" readOnly="${true}" /></td>
									<td class="grid"><kul:htmlControlAttribute property="document.orderDetails[${rowCounter.count -1}].orderItemAdditionalCostAmt" attributeEntry="${orderDetailAttributes.orderItemAdditionalCostAmt}" readOnly="${true}" /></td>
									<td class="grid"><kul:htmlControlAttribute property="document.orderDetails[${rowCounter.count -1}].orderItemPriceAmt" attributeEntry="${orderDetailAttributes.orderItemPriceAmt}" readOnly="${true}" /></td>
									<td class="grid">${detail.displayTotal }</td>
<%--									<td class="grid"><html:multibox property="approvedItems" value="${KualiForm.document.orderDetails[rowCounter.count-1].orderDetailId}" disabled="${readOnly }"/></td>--%>
								</tr>
								<tr>
									<th colspan="6">
										<mm:accountingLines newAccountingLinePropertyName="newOrderDetailAccountingLines[${rowCounter.count -1}]" accountingLines="${detail.accounts}" accountingLinesPropertyName="document.orderDetails[${rowCounter.count -1}].accounts" tabTitle="Accounts - ${detail.distributorNbr}" lineId="${rowCounter.count -1}" tabErrorKey="document.orderDetails[${rowCounter.count -1}].accounts*,newOrderDetailAccountingLines[${rowCounter.count -1}].*" readOnly="${readOnly}" />
									</th>
								</tr>
								<tr>
									<th colspan="6">
										<fp:capitalAssetEditTab newCapitalAssetInfo="${detail.capitalAssetInformation}" newCapitalAssetInfoName="document.orderDetails[${rowCounter.count -1}].capitalAssetInformation" capitalAssetInfo="${detail.capitalAssetInformation}" capitalAssetInfoName="document.orderDetails[${rowCounter.count -1}].capitalAssetInformation" readOnly="${readOnly}" detailLineId="${rowCounter.count -1}" />
									</th>
								</tr>	
						</table>			
					</c:forEach>
						<table cellpadding="0" cellspacing="0" class="datatable" title="Order Line Item Totals" summary="Review order line item totals">
							<tr>
								<th class="bord-l-b" width="80%" ><div align="right">Sub total:</div></td>
								<td class="grid" >${KualiForm.document.displaySubTotal }</td>
							</tr>
							<tr>
								<th class="bord-l-b" ><div align="right">Tax:</div></td>
								<td class="grid" >${KualiForm.document.tax }</td>
							</tr>
							<tr>
								<th class="bord-l-b" ><div align="right">Total:</div></td>
								<td class="grid" >${KualiForm.document.displayTotal }</td>
							</tr>
					</table>
				</div>
			</kul:tab>
		 	<mm:recurringOrder recurringOrder="${KualiForm.recurringOrder}" recurringOrderPropertyName="recurringOrder" readOnly="${readOnly}" tabErrorKey="recurringOrder.*" />
		</div>
	</kul:tab>
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}"/>	
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls
	    transactionalDocument="${documentEntry.transactionalDocument}" extraButtons="${KualiForm.extraButtons}" />
</kul:documentPage>
