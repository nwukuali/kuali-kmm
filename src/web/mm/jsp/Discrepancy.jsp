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
 <kul:htmlAttributeLabel attributeEntry="${plAttributes.pickTicketNumber}" />	
 limitations under the License.
--%>
	<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
	<c:set var="plAttributes" value="${DataDictionary.DiscrepancyDocument.attributes}" />	
	<c:set var="rDAttributes" value="${DataDictionary.DiscrepancyLine.attributes}" />	
	<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
	
	<kul:documentPage showDocumentInfo="true"
		htmlFormAction="discrepancy"
		documentTypeName="VIRD" renderMultipart="true"
		showTabButtons="true">
	
	<kul:hiddenDocumentFields />
	
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Invoice Discrepancy" defaultOpen="true" tabErrorKey="document.vendorName,document.reportName,document.discrepancyFromDate,document.discrepancyToDate">
		<div class="tab-container" align=center>
			<h3>Invoice Discrepancy</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Invoice Discrepancy" summary="Invoice Discrepancy">
			
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     	<kul:htmlAttributeLabel attributeEntry="${plAttributes.vendorName}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.vendorName" attributeEntry="${plAttributes.vendorName}" readOnly="${readOnly}" />						
				</td>
			</tr>			
									
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     	<kul:htmlAttributeLabel attributeEntry="${plAttributes.reportName}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.reportName" attributeEntry="${plAttributes.reportName}" readOnly="${readOnly}" />						
				</td>
			</tr>					
			
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     	<kul:htmlAttributeLabel attributeEntry="${plAttributes.discrepancyFromDate}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyFromDate" attributeEntry="${plAttributes.discrepancyFromDate}" 
					datePicker="true"/>						
				</td>
			</tr>
			
			<tr>	
				<th width="50%" class="bord-l-b">
		        	<div align="right">		    
			     	<kul:htmlAttributeLabel attributeEntry="${plAttributes.discrepancyToDate}" />	
					</div>
				</th>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyToDate" attributeEntry="${plAttributes.discrepancyToDate}" datePicker="true"/>						
				</td>				
			</tr>		
			
			</table>				
		</div>
	</kul:tab>
	
	<c:if test="${KualiForm.reportNumber == 1}">
		<kul:tab tabTitle="Substitutions Report" defaultOpen="true" tabErrorKey="">
		<table cellpadding="0" cellspacing="0" class="datatable" summary="Over Shipments" borders="1">
			<tr>					        
				<th class="grid" width="10%" align="center">Vendor Name</th>
		        <th class="grid" width="7%" align="center">Order Number</th>		        
		        <th class="grid" width="7%" align="center">Ordered Distributor Number</th>
		        <th class="grid" width="7%" align="center">Shipped Distributor Number</th>
		        <th class="grid" width="15%" align="center">Description</th>
		        <th class="grid" width="5%" align="center">Unit Of Issue</th>				    
		        <th class="grid" width="7%" align="center">Invoice Quantity Shipped</th>
		        <th class="grid" width="7%" align="center">Invoice Amount</th>
		        <th class="grid" width="7%" align="center">Unit Invoice Amount</th>	
		        <th class="grid" width="7%" align="center">Tax Inovice Amount</th>	
		        <th class="grid" width="7%" align="center">Date Shipped</th>
		        <th class="grid" width="7%" align="center">Posted Date</th>	
		        <th class="grid" width="7%" align="center">Invoice Number</th>			        		              	        		       		     					      
			</tr>
			<logic:iterate id="line" name="KualiForm" property="document.discrepancyLines" indexId="ctr">			
			<tr>							
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].vendorName" attributeEntry="${rDAttributes.vendorName}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].orderNumber" attributeEntry="${rDAttributes.orderNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].distributorNumber" attributeEntry="${rDAttributes.distributorNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].shippedDistributorNumber" attributeEntry="${rDAttributes.distributorNumber}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].itemDescription" attributeEntry="${rDAttributes.itemDescription}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitOfIssueCode" attributeEntry="${rDAttributes.unitOfIssueCode}" readOnly="true"  />				
				</td>					
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceQtyShip" attributeEntry="${rDAttributes.invoiceQtyShip}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceAmt" attributeEntry="${rDAttributes.invoiceAmt}" readOnly="true"  />				
				</td>					
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitInvoiceAmt" attributeEntry="${rDAttributes.unitInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].taxInvoiceAmt" attributeEntry="${rDAttributes.taxInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].dateShipped" attributeEntry="${rDAttributes.dateShipped}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].postedDate" attributeEntry="${rDAttributes.postedDate}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceNumber" attributeEntry="${rDAttributes.invoiceNumber}" readOnly="true"  />				
				</td>
			</tr>
			</logic:iterate>						
		</table>				
		</kul:tab>
	</c:if>
	
	<c:if test="${KualiForm.reportNumber == 2}">
		<kul:tab tabTitle="Over Shipments Report" defaultOpen="true" tabErrorKey="">
		<table cellpadding="0" cellspacing="0" class="datatable" summary="Over Shipments" borders="1">
			<tr>					        
				<th class="grid" width="10%" align="center">Vendor Name</th>
		        <th class="grid" width="5%" align="center">Order Number</th>		        
		        <th class="grid" width="5%" align="center">Distributor Number</th>
		        <th class="grid" width="15%" align="center">Description</th>
		        <th class="grid" width="5%" align="center">Unit Of Issue</th>				        	        		       		      
		        <th class="grid" width="5%" align="center">Quantity Ordered</th>
		        <th class="grid" width="5%" align="center">Quantity Shipped</th>
		        <th class="grid" width="5%" align="center">Invoice Quantity Shipped</th>
		        <th class="grid" width="7%" align="center">Invoice Amount</th>
		        <th class="grid" width="7%" align="center">Unit Invoice Amount</th>	
		        <th class="grid" width="7%" align="center">Tax Inovice Amount</th>		
		        <th class="grid" width="7%" align="center">Total Amount</th>
		        <th class="grid" width="5%" align="center">Date Shipped</th>
		        <th class="grid" width="5%" align="center">Posted Date</th>	
		        <th class="grid" width="7%" align="center">Invoice Number</th>						       
			</tr>
			<logic:iterate id="line" name="KualiForm" property="document.discrepancyLines" indexId="ctr">			
			<tr>							
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].vendorName" attributeEntry="${rDAttributes.vendorName}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].orderNumber" attributeEntry="${rDAttributes.orderNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].distributorNumber" attributeEntry="${rDAttributes.distributorNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].itemDescription" attributeEntry="${rDAttributes.itemDescription}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitOfIssueCode" attributeEntry="${rDAttributes.unitOfIssueCode}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].qtyOrdered" attributeEntry="${rDAttributes.qtyOrdered}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].qtyShipped" attributeEntry="${rDAttributes.qtyShipped}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceQtyShip" attributeEntry="${rDAttributes.invoiceQtyShip}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceAmt" attributeEntry="${rDAttributes.invoiceAmt}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitInvoiceAmt" attributeEntry="${rDAttributes.unitInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].taxInvoiceAmt" attributeEntry="${rDAttributes.taxInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].totalAmt" attributeEntry="${rDAttributes.totalAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].dateShipped" attributeEntry="${rDAttributes.dateShipped}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].postedDate" attributeEntry="${rDAttributes.postedDate}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceNumber" attributeEntry="${rDAttributes.invoiceNumber}" readOnly="true"  />				
				</td>
			</tr>
			</logic:iterate>						
		</table>	
		</kul:tab>
	</c:if>
	
	<c:if test="${KualiForm.reportNumber == 3}">
		<kul:tab tabTitle="Price Discrepancies Report" defaultOpen="true" tabErrorKey="">
		<table cellpadding="0" cellspacing="0" class="datatable" summary="Over Shipments" borders="1">
			<tr>					        
				<th class="grid" width="10%" align="center">Vendor Name</th>
		        <th class="grid" width="5%" align="center">Order Number</th>		        
		        <th class="grid" width="5%" align="center">Distributor Number</th>
		        <th class="grid" width="15%" align="center">Description</th>
		        <th class="grid" width="5%" align="center">Unit Of Issue</th>				        	        		       		      
		        <th class="grid" width="5%" align="center">Invoice Quantity Shipped</th>
		        <th class="grid" width="5%" align="center">PO Price</th>		        
		        <th class="grid" width="5%" align="center">Invoice Amount</th>
		        <th class="grid" width="5%" align="center">Unit Invoice Amount</th>	
		        <th class="grid" width="5%" align="center">Tax Inovice Amount</th>		
		        <th class="grid" width="5%" align="center">Total PO Amount</th>
		        <th class="grid" width="5%" align="center">Percentage Difference</th>
		        <th class="grid" width="5%" align="center">Date Shipped</th>
		        <th class="grid" width="5%" align="center">Posted Date</th>	
		        <th class="grid" width="5%" align="center">Invoice Number</th>						       
			</tr>
			<logic:iterate id="line" name="KualiForm" property="document.discrepancyLines" indexId="ctr">			
			<tr>							
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].vendorName" attributeEntry="${rDAttributes.vendorName}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].orderNumber" attributeEntry="${rDAttributes.orderNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].distributorNumber" attributeEntry="${rDAttributes.distributorNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].itemDescription" attributeEntry="${rDAttributes.itemDescription}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitOfIssueCode" attributeEntry="${rDAttributes.unitOfIssueCode}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceQtyShip" attributeEntry="${rDAttributes.invoiceQtyShip}" readOnly="true"  />				
				</td>	
			    <td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].poPrice" attributeEntry="${rDAttributes.poPrice}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceAmt" attributeEntry="${rDAttributes.invoiceAmt}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitInvoiceAmt" attributeEntry="${rDAttributes.unitInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].taxInvoiceAmt" attributeEntry="${rDAttributes.taxInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].totalAmt" attributeEntry="${rDAttributes.totalAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].percentageDiff" attributeEntry="${rDAttributes.percentageDiff}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].dateShipped" attributeEntry="${rDAttributes.dateShipped}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].postedDate" attributeEntry="${rDAttributes.postedDate}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceNumber" attributeEntry="${rDAttributes.invoiceNumber}" readOnly="true"  />				
				</td>
			</tr>
			</logic:iterate>						
		</table>	
			
		</kul:tab>
	</c:if>
	
	<c:if test="${KualiForm.reportNumber == 4}">
		<kul:tab tabTitle="Invoice - No Order Match Report" defaultOpen="true" tabErrorKey="">
		<table cellpadding="0" cellspacing="0" class="datatable" summary="Over Shipments" borders="1">
			<tr>					        
				<th class="grid" width="10%" align="center">Vendor Name</th>
		        <th class="grid" width="5%" align="center">Order Number</th>		        
		        <th class="grid" width="5%" align="center">Distributor Number</th>
		        <th class="grid" width="15%" align="center">Description</th>
		        <th class="grid" width="5%" align="center">Unit Of Issue</th>				        	        		       		      		        
		        <th class="grid" width="5%" align="center">Quantity Shipped</th>		  
		        <th class="grid" width="7%" align="center">Invoice Amount</th>
		        <th class="grid" width="7%" align="center">Unit Invoice Amount</th>	
		        <th class="grid" width="7%" align="center">Tax Inovice Amount</th>		
		        <th class="grid" width="7%" align="center">Total Amount</th>
		        <th class="grid" width="5%" align="center">Date Shipped</th>
		        <th class="grid" width="5%" align="center">Posted Date</th>	
		        <th class="grid" width="7%" align="center">Invoice Number</th>						       
			</tr>
			<logic:iterate id="line" name="KualiForm" property="document.discrepancyLines" indexId="ctr">			
			<tr>							
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].vendorName" attributeEntry="${rDAttributes.vendorName}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].orderNumber" attributeEntry="${rDAttributes.orderNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].distributorNumber" attributeEntry="${rDAttributes.distributorNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].itemDescription" attributeEntry="${rDAttributes.itemDescription}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitOfIssueCode" attributeEntry="${rDAttributes.unitOfIssueCode}" readOnly="true"  />				
				</td>		
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].qtyShipped" attributeEntry="${rDAttributes.qtyShipped}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceAmt" attributeEntry="${rDAttributes.invoiceAmt}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].unitInvoiceAmt" attributeEntry="${rDAttributes.unitInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].taxInvoiceAmt" attributeEntry="${rDAttributes.taxInvoiceAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].totalAmt" attributeEntry="${rDAttributes.totalAmt}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].dateShipped" attributeEntry="${rDAttributes.dateShipped}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].postedDate" attributeEntry="${rDAttributes.postedDate}" readOnly="true"  />				
				</td>
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceNumber" attributeEntry="${rDAttributes.invoiceNumber}" readOnly="true"  />				
				</td>
			</tr>
			</logic:iterate>						
		</table>		
		</kul:tab>
	</c:if>
	
	
	<c:if test="${KualiForm.reportNumber == 5}">
		<kul:tab tabTitle="PCard/Invoice - Transaction Discrepancies Report" defaultOpen="true" tabErrorKey="document.discrepancyLines">
		<div class="tab-container" align=center>				
		<table cellpadding="0" cellspacing="0" class="datatable" summary="Discrepancy Lines" >
			<tr>					        
				<th class="grid" width="15%" align="center">Vendor Name</th>
		        <th class="grid" width="8%" align="center">Order Number</th>		        
		        <th class="grid" width="8%" align="center">Invoice Number</th>
		        <th class="grid" width="8%" align="center">Transaction Amount</th>				        	        
		        <th class="grid" width="8%" align="center">Total PCard Amount</th>
		        <th class="grid" width="8%" align="center">Invoice Amount</th>
		        <th class="grid" width="7%" align="center">Difference</th>
		        <th class="grid" width="7%" align="center">Transaction Date</th>
		        <th class="grid" width="7%" align="center">Posted Date</th>
		        <th class="grid" width="3%" align="center">Remove Discrepancy</th>	
		        <th class="grid" width="21%" align="center">User Comment</th>										
			</tr>					
		
			<logic:iterate id="line" name="KualiForm" property="document.discrepancyLines" indexId="ctr">	
					
			<tr>							
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].vendorName" attributeEntry="${rDAttributes.vendorName}" readOnly="true"  />				
				</td>		
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].orderNumber" attributeEntry="${rDAttributes.orderNumber}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceNumber" attributeEntry="${rDAttributes.invoiceNumber}" readOnly="true"  />				
				</td>		
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].transactionAmt" attributeEntry="${rDAttributes.transactionAmt}" readOnly="true"  />				
				</td>		
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].totalPCardAmt" attributeEntry="${rDAttributes.totalPCardAmt}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].invoiceAmt" attributeEntry="${rDAttributes.invoiceAmt}" readOnly="true"  />				
				</td>		
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].difference" attributeEntry="${rDAttributes.difference}" readOnly="true"  />				
				</td>		
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].transactionDate" attributeEntry="${rDAttributes.transactionDate}" readOnly="true"  />				
				</td>	
				<td class="grid" align="center">
					<kul:htmlControlAttribute property="document.discrepancyLines[${ctr}].postedDate" attributeEntry="${rDAttributes.postedDate}" readOnly="true"  />				
				</td>	
				
				<td class="grid" align="center">
					<kul:htmlControlAttribute 
								attributeEntry="${rDAttributes.selected}" 
								property="document.discrepancyLines[${ctr}].selected"
								readOnly="${document.discrepancyLines[ctr].lineReadOnly}" />			
				</td>			
					
				<td class="grid" align="center">
					<kul:htmlControlAttribute 
								attributeEntry="${rDAttributes.discrepancyComment}" 
								property="document.discrepancyLines[${ctr}].discrepancyComment" 
								readOnly="${document.discrepancyLines[ctr].lineReadOnly}"/>
				</td>	
																	
			</tr>					
			</logic:iterate>				    
        </table>
	    </div>		
		</kul:tab>
	</c:if>
	
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls
	    transactionalDocument="${documentEntry.transactionalDocument}" extraButtons="${KualiForm.extraButtons}" />
</kul:documentPage>
