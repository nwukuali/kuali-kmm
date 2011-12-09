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
	<c:set var="plAttributes" value="${DataDictionary.DeliveryLogDocument.attributes}" />	
	<c:set var="plLineAttributes" value="${DataDictionary.PickListLine.attributes}" />
	<c:set var="zoneAttributes" value="${DataDictionary.Zone.attributes}" />
	<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
    <c:set var="packStatusCodeAttributes" value="${DataDictionary.PackStatusCode.attributes}" />
	<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
	<kul:documentPage showDocumentInfo="true"
		htmlFormAction="deliveryLog"
		documentTypeName="DLDL" renderMultipart="true"
		showTabButtons="true">	
	<kul:hiddenDocumentFields />
	<script type="text/javascript">var excludeSubmitRestriction = true;</script>
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Driver Log" defaultOpen="true" tabErrorKey="document.routeCd,document.deliveryLabelLines">
		<div class="tab-container" align=center>
			<h3>Delivery Log</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Driver Log Content" summary="Build Driver Log Content">
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     		<kul:htmlAttributeLabel attributeEntry="${plAttributes.routeCd}" />	  
					</div>
				</th>
				<td class="grid" align="center">
					<div >
						<kul:htmlControlAttribute property="document.routeCd" attributeEntry="${plAttributes.routeCd}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Route" fieldConversions="routeCd:document.routeCd" /></c:if>									
					</div>						
				</td>
			</tr>			
			
			<c:if test="${!readOnly}">
				<tr>					
					<th width="50%" class="bord-l-b">
					    <div align="right">		    
					 		<kul:htmlAttributeLabel attributeEntry="${plAttributes.deliveryLabelLines}" />	
						</div>
					</th>
					<td class="datacell-nowrap" align="center">
						<c:if test="${KualiForm.numberOfLines > 0}">&nbsp;${KualiForm.numberOfLines}&nbsp;</c:if>					
						<mm:multipleValueLookupEx boClassName="org.kuali.ext.mm.document.DeliveryLabelDocumentLines" lookedUpCollectionName="document.deliveryLabelLines" lookupParameters="document.routeCd:route.routeCd" fieldConversions="route.routeCd:document.routeCd" />
					</td>
				</tr>				   
			</c:if>
			</table>
				<c:if test="${KualiForm.numberOfLines > 0}">
				<kul:tab tabTitle="Delivery Lines (${KualiForm.numberOfLines})" defaultOpen="true" >
				<div class="tab-container" align=center>
				<h3>Delivery Lines</h3>
					<table cellpadding="0" cellspacing="0" class="datatable" summary="DLLines" borders="1">
						<tr>					        
						 	<th class="grid" width="10%" align="center">Stop Code</th>
						 	<th class="grid" width="22%" align="center">Building Name</th>
					        <th class="grid" width="23%" align="center">Department Name</th>	
					        <th class="grid" width="5%"  align="center">Building Code</th>
					        <th class="grid" width="5%"  align="center">Campus Code</th>					        					        		
					        <th class="grid" width="10%" align="center">Pack List Line Id</th>
					        <th class="grid" width="10%" align="center">Delivery Label Number</th>
					        <th class="grid" width="5%"  align="center">Cartons</th>			       
						</tr>					
					<logic:iterate id="line" name="KualiForm" property="document.deliveryLabelLines" indexId="ctr">
						<tr>							
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].stopCd" attributeEntry="${plLineAttributes.stopCd}" readOnly="true"  />									
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].deliveryBuildingNm" attributeEntry="${plLineAttributesdeliveryBuildingNm}" readOnly="true" >
				       			</kul:htmlControlAttribute>			
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].deliveryDepartmentNm" attributeEntry="${plLineAttributes.deliveryDepartmentNm}" readOnly="true" >
				       			</kul:htmlControlAttribute>			
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].deliveryBuildingCd" attributeEntry="${plLineAttributes.deliveryBuildingCd}" readOnly="true" >
				       			</kul:htmlControlAttribute>		
							</td>							
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].deliveryCampusCd" attributeEntry="${plLineAttributes.deliveryCampusCd}" readOnly="true" >
				       			</kul:htmlControlAttribute>		
							</td>
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].packListLineId" attributeEntry="${plLineAttributes.packListLineId}" readOnly="true"  />				
							</td>	
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].packListDocNbr" attributeEntry="${plLineAttributes.packListDocNbr}" readOnly="true"  />				
							</td>	
							<td class="grid" align="center">
								<kul:htmlControlAttribute property="document.deliveryLabelLines[${ctr}].nbrOfCartons" attributeEntry="${plLineAttributes.nbrOfCartons}" readOnly="true"  />				
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
