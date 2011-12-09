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
<c:set var="cpAttributes" value="${DataDictionary.CatalogPending.attributes}" />	

<kul:documentPage showDocumentInfo="true" 
	htmlFormAction="initiateFinalApproval"
	documentTypeName="SUPC" 
	showTabButtons="false">
	<kul:hiddenDocumentFields />
	
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Pre Approve Scheduler" defaultOpen="true" tabErrorKey="document.catalogCd">
		<div class="tab-container" align=center>
			<h3>PreApprove Scheduler</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Pre Approve Scheduler" summary="Pre Approve Scheduler">
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogCd}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogCd" attributeEntry="${cpAttributes.catalogCd}" readOnly="true"/>								
				</td>
			</tr>
			
						<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogDesc}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogDesc" attributeEntry="${cpAttributes.catalogDesc}" readOnly="true"/>	
					
				</td>
			</tr>		
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogBeginDt}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogBeginDt" attributeEntry="${cpAttributes.catalogBeginDt}" datePicker="true"  readOnly="true"/>						
				</td>
			</tr>	
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogEndDt}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogEndDt" attributeEntry="${cpAttributes.catalogEndDt}" datePicker="true"  readOnly="true"/>				
				</td>
			</tr>	
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.priorityNbr}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.priorityNbr" attributeEntry="${cpAttributes.priorityNbr}" readOnly="true"/>	
					
				</td>
			</tr>
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.agreementNbr}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.agreementNbr" attributeEntry="${cpAttributes.agreementNbr}" readOnly="true"/>				
				</td>
			</tr>		
			
		
				<tr>
					<th width="50%" class="bord-l-b">
				       	<div align="right">		    
					   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogTimestamp}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.catalogTimestamp" attributeEntry="${cpAttributes.catalogTimestamp}" readOnly="true"/>						
					</td>
				</tr>		
			
           		<tr>
					<th width="50%" class="bord-l-b">
				       	<div align="right">		    
					   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.batchLog}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.batchLog" attributeEntry="${cpAttributes.batchLog}"  readOnly="true"/>						
					</td>
				</tr>	
			</table>	        
		</div>
	</kul:tab>	
	<kul:panelFooter />	
	<kul:documentControls transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>
