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
	<c:set var="pvAttributes" value="${DataDictionary.PickVerifyDocument.attributes}" />
	<c:set var="plLineAttributes" value="${DataDictionary.PickListLine.attributes}" />
	<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
	<c:set var="siAttributes" value="${DataDictionary.SalesInstance.attributes}" />
	<c:set var="odAttributes" value="${DataDictionary.OrderDetail.attributes}" />
	<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	<c:set var="rentalAttributes" value="${DataDictionary.Rental.attributes}" />	
	
	<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
	
<kul:documentPage showDocumentInfo="true"
	htmlFormAction="pickVerify"
	documentTypeName="SPKV" renderMultipart="true"
	showTabButtons="true">
	<c:if test="${KualiForm.printReady}">
	 <script type="text/javascript">var excludeSubmitRestriction = true;</script>
	</c:if>
	<kul:hiddenDocumentFields />
	
<!--	<c:if test="${KualiForm.canPrintPackingList}">-->
<!--		${kfunc:registerEditableProperty(KualiForm, "methodToCall")}-->
<!--	   <div align="center">-->
<!--	        <a href='pickVerify.do?methodToCall=printPackingLists'>-->
<!--	            <font color="red""><bean:message key="label.document.pickverify.printPackingList"/></font>-->
<!--	        </a>-->
<!--	        <html:img src="${ConfigProperties.externalizable.images.url}icon-pdf.png" title="print packing list" alt="print packing list" width="16" height="16"/>-->
<!--	   </div>-->
<!--	   <br>-->
<!--	</c:if>-->
	
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
		
	<kul:tab tabTitle="Verify Pick" defaultOpen="true" tabErrorKey="document.pickTicketNumber,document.pickTicket.*,newPickLinesToAdd*">
		<div class="tab-container" align=center>
			<h3>Pick Tickets</h3>
			<table cellpadding="0" cellspacing="0" class="datatable" title="Pick Ticket Content" summary="Verify pick ticket content">
			<tr>
				<th width="20%" class="bord-l-b">
		        	<div align="right">		    
		     			<kul:htmlAttributeLabel attributeEntry="${pvAttributes.pickTicketNumber}" />	
					</div>
				</th>
				<td class="infoline">
					<kul:htmlControlAttribute property="document.pickTicketNumber" attributeEntry="${pvAttributes.pickTicketNumber}" readOnly="${readOnly or KualiForm.pickTicketPopulated}" />
					<c:if test="${!readOnly}">
						<c:choose>
							<c:when test="${!KualiForm.pickTicketPopulated}">
								<kul:lookup boClassName="org.kuali.ext.mm.businessobject.PickTicket" fieldConversions="pickTicketNumber:document.pickTicketNumber" lookupParameters="'PRTD':pickStatusCode.pickStatusCode" />
								&nbsp;&nbsp;&nbsp;&nbsp;
								<html:image property="methodToCall.addTicket" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" styleClass="tinybutton" alt="Add a pick ticket" title="Add a Pick Ticket"/>
							</c:when>
							<c:otherwise>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<html:image property="methodToCall.clearTicket" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-clear1.gif" styleClass="tinybutton" alt="Clear the current pick ticket" title="Clear the current Pick Ticket"/>
							</c:otherwise>
						</c:choose>
					</c:if>			
				</td>
			</tr>
			
			<c:if test="${KualiForm.pickTicketPopulated}">
			<c:set var="colCount" value="9" />
			<tr>
				<td colspan="7" style="padding: 0px;border-bottom-style:none;border-left-style:none;border-right-style:none;border-top-style:none;">
					<table cellpadding="0" cellspacing="0" class="datatable" title="Pick Ticket Detail" summary="Pick Ticket Detail">
					<tr>						
						<th class="grid" width="20%" align="center">${plLineAttributes.itemLocation.label}</th>
				        <th class="grid" width="6%" align="center">${plLineAttributes.stockQty.shortLabel}</th>
				        <th class="grid" width="5%" align="center">${plLineAttributes.pickStockQty.shortLabel}</th>
				        <th class="grid" width="5%" align="center">${plLineAttributes.backOrderQty.shortLabel}</th>
				        <th class="grid" width="5%" align="center">${odAttributes.stockUnitOfIssueCd.shortLabel}</th>
				        <c:choose>
							<c:when test="${KualiForm.hasTrackableStock}" >
								<th class="grid" width="22%" align="center">${stockAttributes.stockDesc.shortLabel}</th>
				        		<th class="grid" width="4%" align="center">${plLineAttributes.pickTubNbr.shortLabel}</th>
						        <th class="grid" width="9%" align="center">${siAttributes.orderId.label}</th>
						        <th class="grid" width="9%" align="center">${stockAttributes.stockDistributorNbr.shortLabel}</th>
								<th class="grid" width="10%" align="center">${rentalAttributes.rentalSerialNumber.shortLabel}</th>
								<c:set var="colCount" value="10" />
							</c:when>
							<c:otherwise>
								<th class="grid" width="30%" align="center">${stockAttributes.stockDesc.shortLabel}</th>
				       			<th class="grid" width="4%" align="center">${plLineAttributes.pickTubNbr.shortLabel}</th>
								<th class="grid" width="10%" align="center">${orderAttributes.orderId.label}</th>
						        <th class="grid" width="10%" align="center">${stockAttributes.stockDistributorNbr.shortLabel}</th>
							</c:otherwise>
						</c:choose>
					</tr>
					<logic:iterate id="line" name="KualiForm" property="document.pickTicket.pickListLines" indexId="ctr">
					<tr>						
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].itemLocation" attributeEntry="${plLineAttributes.itemLocation}" readOnly="true" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].stockQty" attributeEntry="${plLineAttributes.stockQty}" readOnly="true" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].pickStockQty" attributeEntry="${plLineAttributes.pickStockQty}" readOnly="${readOnly}" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].backOrderQty" attributeEntry="${plLineAttributes.backOrderQty}" readOnly="${readOnly}" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].orderDetail.stockUnitOfIssueCd" attributeEntry="${odAttributes.stockUnitOfIssueCd}" readOnly="true" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].stock.stockDesc" attributeEntry="${stockAttributes.stockDesc}" readOnly="true" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].pickTubNbr" attributeEntry="${plLineAttributes.pickTubNbr}" readOnly="true" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].salesInstance.orderDocument.orderId" attributeEntry="${siAttributes.orderId}" readOnly="true" /></td>
						<td class="grid" align="center"><kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].stock.stockDistributorNbr" attributeEntry="${stockAttributes.stockDistributorNbr}" readOnly="true" /></td>
						<c:if test="${KualiForm.hasTrackableStock}" >					
							<td class="grid" align="center">						
								<c:if test="${line.trackableWithSerialNumber}">
									<c:forEach var="rental" items="${line.rentals}" varStatus="rowCounter" >										
										<kul:htmlControlAttribute property="document.pickTicket.pickListLines[${ctr}].rentals[${rowCounter.count-1}].rentalSerialNumber" attributeEntry="${rentalAttributes.rentalSerialNumber}" readOnly="${readOnly}" /><br />
									</c:forEach>
								</c:if>&nbsp;
							</td>
						</c:if>																	
					</tr>
					<c:if test="${!readOnly and not empty KualiForm.newPickLinesToAdd[line.pickListLineId]}">
					<tr>
						<th  colspan="${5}">
							&nbsp;
						</th>
						<td colspan="${colCount - 5 }">
							<mm:additionalPickLines additionalLinesList="${line.additionalLines}" newPickLineToAdd="${KualiForm.newPickLinesToAdd[line.pickListLineId]}" newPickLineToAddPropertyName="newPickLinesToAdd(${line.pickListLineId})" lineId="${line.pickListLineId}" parentLineErrorPath="document.pickTicket.pickListLines[${ctr}]" />
						</td> 
					</tr>
					<tr>
						<th colspan="${colCount}">&nbsp;</th>
					</tr>
					</c:if>
					</logic:iterate>
					</table>
				</td>
			</tr>
			</c:if>
			</table>
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
