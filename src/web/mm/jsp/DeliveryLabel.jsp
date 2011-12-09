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
	<c:set var="plAttributes" value="${DataDictionary.DeliveryLabelDocument.attributes}" />	
	<c:set var="plLineAttributes" value="${DataDictionary.PickListLine.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
	<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
    <c:set var="packStatusCodeAttributes" value="${DataDictionary.PackStatusCode.attributes}" />
	<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
	<kul:documentPage showDocumentInfo="true"
		htmlFormAction="deliveryLabel"
		documentTypeName="SPDL" renderMultipart="true"
		showTabButtons="true">
	
	<kul:hiddenDocumentFields />
	<script type="text/javascript">var excludeSubmitRestriction = true;</script>
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Delivery Label" defaultOpen="true" tabErrorKey="document.pickTicketNumber,document.pickStatusCodeCd,document.pickListLines">
		<div class="tab-container" align=center>
			<h3>Delivery Label</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Pick List Content" summary="Build pick list content">
			
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${plAttributes.pickTicketNumber}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.pickTicketNumber" attributeEntry="${plAttributes.pickTicketNumber}" readOnly="${readOnly}" />						
				</td>
			</tr>	
			<c:if test="${!readOnly}">
				<tr>					
					<th class="bord-l-b">
					    <div align="right">		    
					 		<kul:htmlAttributeLabel attributeEntry="${plAttributes.pickListLines}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<c:if test="${KualiForm.numberOfLines > 0}">&nbsp;${KualiForm.numberOfLines}&nbsp;</c:if>					
						<mm:multipleValueLookupEx boClassName="org.kuali.ext.mm.businessobject.PickListLine" lookedUpCollectionName="document.pickListLines" lookupParameters="document.pickTicketNumber:pickTicket.pickTicketNumber" fieldConversions="bin.zone.warehouseCd:document.warehouseCd" />
					</td>
				</tr>				   
			</c:if>			
				<c:if test="${KualiForm.numberOfLines > 0}">
					<tr> <td colspan="2">
					<h3>Pick List Summary</h3>
					<table cellpadding="0" cellspacing="0" class="datatable" summary="PickListSummary" border="0">
						<tr>						
					        <th class="grid" width="18%" align="center">Orders</th>
					        <th class="grid" width="18%" align="center">Lines</th>
					        <th class="grid" width="18%" align="center">Number Of Cartons</th>				        
						</tr>
						<tr>										
							<c:set var="numberOfOrders" value="${KualiForm.numberOfOrders}" />			
							<td class="grid" align="center">${numberOfOrders}</td>			
							<td class="grid" align="center">${KualiForm.numberOfLines}</td>			
							<td class="grid" align="center">
							<kul:htmlControlAttribute property="document.nbrOfCartons" attributeEntry="${plAttributes.nbrOfCartons}" />
							</td>							
						</tr>
					</table>
					</td>
					</tr>			
				</c:if>
			</table>
				<c:if test="${KualiForm.numberOfLines > 0}">
				<kul:tab tabTitle="Pick List Lines (${KualiForm.numberOfLines})" defaultOpen="true" >
				<div class="tab-container" align=center>
				<h3>Pick List Lines</h3>
					<table cellpadding="0" cellspacing="0" class="datatable" summary="PickListLines" borders="1">
						<tr>					        
					        <th class="grid" width="10%" align="center">Order Number</th>
					        <th class="grid" width="10%" align="center">Warehouse</th>
					        <th class="grid" width="10%" align="center">Zone</th>
					        <th class="grid" width="10%" align="center">Stock Distributor Number</th>	
					        <th class="grid" width="10%" align="center">Ordered Quantity</th>						        	        
					        <th class="grid" width="10%" align="center">Back Order Quantity</th>
					        <th class="grid" width="10%" align="center">Campus Code</th>
					        <th class="grid" width="10%" align="center">Building Code</th>
					        <th class="grid" width="10%" align="center">Pick Status Code</th>
					        <th class="grid" width="10%" align="center">Date Created</th>										
						</tr>					
					<logic:iterate id="line" name="KualiForm" property="document.pickListLines" indexId="ctr">
						<tr>							
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].salesInstance.orderDocument.orderId" attributeEntry="${orderAttributes.orderId}" readOnly="true" readOnlyBody="true">
									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.SalesInstance" keyValues="salesInstanceId=${KualiForm.document.pickListLines[ctr].salesInstanceId}" render="true">
					              		<html:hidden write="true" property="document.pickListLines[${ctr}].salesInstance.orderDocument.orderId" />
						           	</kul:inquiry>&nbsp;
				       			</kul:htmlControlAttribute>											
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].bin.zone.warehouseCd" attributeEntry="${zoneAttributes.warehosueCd}" readOnly="true" readOnlyBody="true">
									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Warehouse" keyValues="warehouseCd=${KualiForm.document.pickListLines[ctr].bin.zone.warehouseCd}" render="true">
					              		<html:hidden write="true" property="document.pickListLines[${ctr}].bin.zone.warehouseCd" />
						           	</kul:inquiry>&nbsp;
				       			</kul:htmlControlAttribute>
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].bin.zone.zoneCd" attributeEntry="${zoneAttributes.zoneCd}" readOnly="true" readOnlyBody="true">
									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Zone" keyValues="zoneId=${KualiForm.document.pickListLines[ctr].bin.zoneId}" render="true">
					              		<html:hidden write="true" property="document.pickListLines[${ctr}].bin.zone.zoneCd" />
						           	</kul:inquiry>&nbsp;
				       			</kul:htmlControlAttribute>
				       		</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].stock.stockDistributorNbr" attributeEntry="${stockAttributes.stockDistributorNbr}" readOnly="true" readOnlyBody="true">
									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Stock" keyValues="stockId=${KualiForm.document.pickListLines[ctr].stock.stockId}" render="true">
					              		<html:hidden write="true" property="document.pickListLines[${ctr}].stock.stockDistributorNbr" />
						           	</kul:inquiry>&nbsp;
				       			</kul:htmlControlAttribute>				
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].stockQty" attributeEntry="${plLineAttributes.stockQty}" readOnly="true">
								</kul:htmlControlAttribute>				
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].backOrderQty" attributeEntry="${plLineAttributes.backOrderQty}" readOnly="true">
								</kul:htmlControlAttribute>				
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].salesInstance.orderDocument.campusCd" attributeEntry="${plLineAttributes.salesInstance.orderDocument.campusCd}" readOnly="true" >
				       			</kul:htmlControlAttribute>			
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].salesInstance.orderDocument.deliveryBuildingCd" attributeEntry="${plLineAttributes.salesInstance.orderDocument.deliveryBuildingCd}" readOnly="true" >
				       			</kul:htmlControlAttribute>		
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].pickStatusCode.pickStatusCode" attributeEntry="${packStatusCodeAttributes.pickStatusCode}" readOnly="true"  />				
							</td>	
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].createdDate" attributeEntry="${plLineAttributes.createdDate}" readOnly="true"  />				
							</td>							
						</tr>					
					</logic:iterate>				    
	          	</table>
	          </div>
	          </kul:tab>
	          </c:if>	        
		</div>
	</kul:tab>
	
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls
	    transactionalDocument="${documentEntry.transactionalDocument}" extraButtons="${KualiForm.extraButtons}" />
</kul:documentPage>
