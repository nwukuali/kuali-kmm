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
<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
<kul:documentPage showDocumentInfo="true" 
	htmlFormAction="uploadCatalog"
	documentTypeName="SUPC" renderMultipart="true"
	showTabButtons="true">
	
	<kul:hiddenDocumentFields />
	
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Upload Catalog" defaultOpen="true" tabErrorKey="document.catalogCd,document.catalogBeginDt,document.fileContent,document.catalogFile">
		<div class="tab-container" align=center>
			<h3>Upload Catalog</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Upload Catalog" summary="Upload Catalog">
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogCd}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
				  <kul:htmlControlAttribute attributeEntry="${cpAttributes.catalogCd}" property="document.catalogCd" readOnly="true"/>
						<kul:lookup boClassName="org.kuali.ext.mm.businessobject.Catalog" lookupParameters="document.catalogCd:catalogCd"
							fieldConversions="catalogCd:document.catalogCd" />
				</td>
			</tr>
			
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogDesc}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogDesc" attributeEntry="${cpAttributes.catalogDesc}" readOnly="${readOnly}" />
					
				</td>
			</tr>		
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogBeginDt}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogBeginDt" attributeEntry="${cpAttributes.catalogBeginDt}" datePicker="true"  readOnly="${readOnly}" />					
				</td>
			</tr>	
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogEndDt}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.catalogEndDt" attributeEntry="${cpAttributes.catalogEndDt}" datePicker="true"  readOnly="${readOnly}" />					
				</td>
			</tr>	
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.priorityNbr}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.priorityNbr" attributeEntry="${cpAttributes.priorityNbr}" readOnly="${readOnly}" />
					
				</td>
			</tr>
			
			<tr>
				<th width="50%" class="bord-l-b">
			       	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.agreementNbr}" />	
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="document.agreementNbr" attributeEntry="${cpAttributes.agreementNbr}" readOnly="${readOnly}" />					
				</td>
			</tr>									
			
			<c:if test="${readOnly}">
				<tr>
					<th width="50%" class="bord-l-b">
				       	<div align="right">		    
					   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.catalogTimestamp}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.catalogTimestamp" attributeEntry="${cpAttributes.catalogTimestamp}" readOnly="${readOnly}" />					
					</td>
				</tr>		
			</c:if>
			
			  
             <c:if test="${!readOnly}">     
	             <tr>
	             <th width="50%" class="bord-l-b">
	             	<div align="right">		    
				   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.fileContent}" />	
					</div>
	             </th>                     
	             <td class="datacell-nowrap">						
						<html:file property="document.fileContent"  />	
				 </td>
				 </tr>
			 </c:if>
           	
           	<c:if test="${readOnly}">
				<tr>
					<th width="50%" class="bord-l-b">
				       	<div align="right">		    
					   		<kul:htmlAttributeLabel attributeEntry="${cpAttributes.batchLog}" />	
						</div>
					</th>
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.batchLog" attributeEntry="${cpAttributes.batchLog}" readOnly="${readOnly}" />					
					</td>
				</tr>		
			</c:if>
           		
			</table>	        
		</div>
	</kul:tab>
	
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>
