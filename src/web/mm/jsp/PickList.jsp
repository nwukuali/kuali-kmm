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
	<c:set var="plAttributes" value="${DataDictionary.PickListDocument.attributes}" />	
	<c:set var="plLineAttributes" value="${DataDictionary.PickListLine.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
	<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />

	<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
	<kul:documentPage showDocumentInfo="true"
		htmlFormAction="pickList"
		documentTypeName="SPKL" renderMultipart="true"
		showTabButtons="true">
	
	<kul:hiddenDocumentFields />
	
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Pick List" defaultOpen="true" tabErrorKey="document.warehouseCd,document.maxOrders,document.pickListLines,pickStatusCode">
		<div class="tab-container" align=center>
			<h3>Pick List</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Pick List Content" summary="Build pick list content">
			<tr>
				<th width="50%" class="bord-l-b">
			        	<div align="right">		    
				     		<kul:htmlAttributeLabel attributeEntry="${plAttributes.warehouseCd}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.warehouseCd" attributeEntry="${plAttributes.warehouseCd}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Warehouse" fieldConversions="warehouseCd:document.warehouseCd" /></c:if>			
					</td>
			</tr>		
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${plAttributes.maxOrders}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.maxOrders" attributeEntry="${plAttributes.maxOrders}" readOnly="${readOnly}" />						
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${plAttributes.sortCode}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<div >
						<kul:htmlControlAttribute property="document.sortCode" attributeEntry="${plAttributes.sortCode}" readOnly="${readOnly}" />
					</div>						
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
						<kul:multipleValueLookup boClassName="org.kuali.ext.mm.businessobject.PickListLine" lookedUpCollectionName="document.pickListLines" lookupParameters="document.warehouseCd:bin.zone.warehouseCd,'INIT':pickStatusCode.pickStatusCode" />
					</td>
				</tr>				   
			</c:if>			
				<c:if test="${KualiForm.numberOfLines > 0}">
					<tr> <td colspan="2">
					<h3>Pick List Summary</h3>
					<table cellpadding="0" cellspacing="0" class="datatable" summary="PickListSummary" border="0">
						<tr>
							<th class="grid" width="18%" align="center" style="padding: border-top-style:solid;">Warehouse</th>
					        <th class="grid" width="18%" align="center">Orders</th>
					        <th class="grid" width="18%" align="center">Lines</th>
					        <th class="grid" width="18%" align="center">Will Calls</th>
					        <th class="grid" width="28%" align="center">Oldest Date</th>					        
						</tr>
						<tr>
							<td class="grid" align="center">
								<c:if test="${KualiForm.singleWarehouse}" >
									<kul:htmlControlAttribute property="document.warehouseCd" attributeEntry="${plAttributes.warehosueCd}" readOnly="true" readOnlyBody="true">
										<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Warehouse" keyValues="warehouseCd=${KualiForm.document.warehouseCd}" render="true">
						              		<html:hidden write="true" property="document.warehouseCd" />
							           	</kul:inquiry>&nbsp;
					       			</kul:htmlControlAttribute>
					       		</c:if>
							</td>
							<c:set var="numberOfOrders" value="${KualiForm.numberOfOrders}" />			
							<td class="grid" align="center">${numberOfOrders}</td>			
							<td class="grid" align="center">${KualiForm.numberOfLines}</td>			
							<td class="grid" align="center">${KualiForm.numberOfWillCalls}</td>
							<td class="grid" align="center">${KualiForm.oldestDate}</td>
						</tr>
					</table>
					</td>
					</tr>			
				</c:if>
			</table>
				<c:if test="${KualiForm.numberOfLines > 0}">
				<kul:tab tabTitle="Pick List Lines (${KualiForm.numberOfLines})" defaultOpen="false" >
				<div class="tab-container" align=center>
				<h3>Pick List Lines</h3>
					<table cellpadding="0" cellspacing="0" class="datatable" summary="PickListLines" borders="1">
						<tr>					        
					        <th class="grid" width="12%" align="center">Order</th>
					        <th class="grid" width="12%" align="center">Warehouse</th>
					        <th class="grid" width="12%" align="center">Zone</th>
					        <th class="grid" width="12%" align="center">Stock #</th>				        	        
					        <th class="grid" width="10%" align="center">Route</th>
					        <th class="grid" width="10%" align="center">Campus</th>
					        <th class="grid" width="12%" align="center">Building</th>
					        <th class="grid" width="16%" align="center">Date</th>										
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
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].routeCd" attributeEntry="${plLines.routeCd}" readOnly="true" readOnlyBody="true">
									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.Route" keyValues="routeCd=${KualiForm.document.pickListLines[ctr].routeCd}" render="true">
					              		<html:hidden write="true" property="document.pickListLines[${ctr}].routeCd" />
						           	</kul:inquiry>&nbsp;
				       			</kul:htmlControlAttribute>				
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].salesInstance.orderDocument.campusCd" attributeEntry="${plLineAttributes.salesInstance.orderDocument.campusCd}" readOnly="true" >
<!--									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.OrderDocument" keyValues="salesInstanceId=${KualiForm.document.pickListLines[ctr].salesInstance.orderDocument.campusCd}" render="true">-->
<!--					              		<html:hidden write="true" property="document.pickListLines[${ctr}].salesInstance.orderDocument.campusCd" />-->
<!--						           	</kul:inquiry>&nbsp;-->
				       			</kul:htmlControlAttribute>			
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.pickListLines[${ctr}].salesInstance.orderDocument.deliveryBuildingCd" attributeEntry="${plLineAttributes.salesInstance.orderDocument.deliveryBuildingCd}" readOnly="true" >
<!--									<kul:inquiry boClassName="org.kuali.ext.mm.businessobject.OrderDocument" keyValues="salesInstanceId=${KualiForm.document.pickListLines[ctr].salesInstance.orderDocument.deliveryBuildingCd}" render="true">-->
<!--					              		<html:hidden write="true" property="document.pickListLines[${ctr}].salesInstance.orderDocument.deliveryBuildingCd" />-->
<!--						           	</kul:inquiry>&nbsp;-->
				       			</kul:htmlControlAttribute>		
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
