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
<c:set var="catalogAttributes" value="${DataDictionary.Catalog.attributes}" />
<c:set var="stockTypeAttributes" value="${DataDictionary.StockType.attributes}" />
<c:set var="trueBuyoutDetailAttributes" value="${DataDictionary.TrueBuyoutDetail.attributes}" />
<c:set var="trueBuyoutAttributes" value="${DataDictionary.TrueBuyoutDocument.attributes}" />

<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

<kul:documentPage showDocumentInfo="true"
	htmlFormAction="trueBuyout"
	documentTypeName="STBO" renderMultipart="true"
	showTabButtons="true">
	
	<kul:hiddenDocumentFields />
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="True Buyout Order Header" defaultOpen="true" tabErrorKey="">
		<div class="tab-container" align=center>
			<h3>True Buyout Order</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="True Buyout Content" summary="Review True Buyout content">
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		Customer Name:&nbsp;	
					</div>
				</th>
				<td class="datacell-nowrap">
					${KualiForm.document.customerProfile.customer.customerName }							
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${profileAttributes.principalName}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.customerProfile.principalName" attributeEntry="${profileAttributes.principalName}" readOnly="${true}" />							
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${profileAttributes.profilePhoneNumber}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.customerProfile.profilePhoneNumber" attributeEntry="${profileAttributes.profilePhoneNumber}" readOnly="${true }" />		
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${profileAttributes.profileEmail}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.customerProfile.profileEmail" attributeEntry="${profileAttributes.profileEmail}" readOnly="${true }" />		
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutAttributes.campusCode}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.campusCode" attributeEntry="${trueBuyoutAttributes.campusCode}" readOnly="${true }" />		
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutAttributes.deliveryDepartmentName}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.deliveryDepartmentName" attributeEntry="${trueBuyoutAttributes.deliveryDepartmentName}" readOnly="${true }" />		
				</td>
			</tr>
			</table>
		</div>			
		<kul:tab tabTitle="True Buyout Order Details" defaultOpen="true" tabErrorKey="document.trueBuyoutDetails*">
			<div class="tab-container" align=center>
				<c:forEach var="detail" items="${KualiForm.document.trueBuyoutDetails}" varStatus="rowCounter" >
					<h3>Item ${rowCounter.count}</h3>						
					<table cellpadding="0" cellspacing="0" class="datatable" title="Order Line Items" summary="Review order line items">
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${catalogAttributes.catalogCd}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].catalog.catalogCd" attributeEntry="${catalogAttributes.catalogCd}" readOnly="${true}" />
								<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Catalog" fieldConversions="catalogId:document.trueBuyoutDetails[${rowCounter.count -1}].catalogId,catalogCd:document.trueBuyoutDetails[${rowCounter.count -1}].catalog.catalogCd,warehouseCd:document.trueBuyoutDetails[${rowCounter.count -1}].catalog.warehouseCd" lookupParameters="'4':catalogTypeCd" /></c:if>							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${catalogAttributes.warehouseCd}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].catalog.warehouseCd" attributeEntry="${catalogAttributes.warehouseCd}" readOnly="${true}" />
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.agreementNumber}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].agreementNumber" attributeEntry="${trueBuyoutDetailAttributes.agreementNumber}" readOnly="${readOnly}" />
								<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Agreement" fieldConversions="agreementNbr:document.trueBuyoutDetails[${rowCounter.count -1}].agreementNumber" /></c:if>							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.stockTypeCode}" forceRequired="true" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].stockTypeCode" attributeEntry="${trueBuyoutDetailAttributes.stockTypeCode}" readOnly="${readOnly}" />
								<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.StockType" fieldConversions="stockTypeCode:document.trueBuyoutDetails[${rowCounter.count -1}].stockTypeCode" /></c:if>							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.rentalObjectCode}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].rentalObjectCode" attributeEntry="${trueBuyoutDetailAttributes.rentalObjectCode}" readOnly="${readOnly}" />
								<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.RentalObjectCode" fieldConversions="rentalObjectCode:document.trueBuyoutDetails[${rowCounter.count -1}].rentalObjectCode" /></c:if>							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.stockDistributorNumber}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].stockDistributorNumber" attributeEntry="${trueBuyoutDetailAttributes.stockDistributorNumber}" readOnly="${readOnly}" />							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.orderItemDescription}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].orderItemDescription" attributeEntry="${trueBuyoutDetailAttributes.orderItemDescription}" readOnly="${readOnly}" />							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.orderItemUnitOfIssueCode}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].orderItemUnitOfIssueCode" attributeEntry="${trueBuyoutDetailAttributes.orderItemUnitOfIssueCode}" readOnly="${readOnly}" />
								<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.UnitOfIssue" fieldConversions="unitOfIssueCode:document.trueBuyoutDetails[${rowCounter.count -1}].orderItemUnitOfIssueCode" /></c:if>							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.orderItemQuantity}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].orderItemQuantity" attributeEntry="${trueBuyoutDetailAttributes.orderItemQuantity}" readOnly="${readOnly}" />							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.orderItemCost}" forceRequired="true" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].orderItemCost" attributeEntry="${trueBuyoutDetailAttributes.orderItemCost}" readOnly="${readOnly}" forceRequired="true" />							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.markupCode}" forceRequired="true" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].markupCode" attributeEntry="${trueBuyoutDetailAttributes.markupCode}" readOnly="${readOnly}" forceRequired="true" />
								<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Markup" fieldConversions="markupCd:document.trueBuyoutDetails[${rowCounter.count -1}].markupCode" /></c:if>							
							</td>
						</tr>
						<tr>
							<th width="50%" class="bord-l-b">
					        	<div align="right">		    
						     		<kul:htmlAttributeLabel attributeEntry="${trueBuyoutDetailAttributes.willCall}" />	
								</div>
							</th>
							<td class="datacell-nowrap">
								<kul:htmlControlAttribute property="document.trueBuyoutDetails[${rowCounter.count -1}].willCall" attributeEntry="${trueBuyoutDetailAttributes.willCall}" readOnly="${readOnly}" />							
							</td>
						</tr>
					</table>			
				</c:forEach>
			</div>
		</kul:tab>
	</kul:tab>
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls
	    transactionalDocument="${documentEntry.transactionalDocument}" extraButtons="${KualiForm.extraButtons}" />
</kul:documentPage>
