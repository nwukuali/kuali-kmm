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
<kul:page showDocumentInfo="false" htmlFormAction="orderCorrection" renderMultipart="true" showTabButtons="true" docTitle="Order Correction" transactionalDocument="false" headerDispatch="true"
	headerTabActive="true" sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start">
	<c:set var="OrderDocumentAttributes" value="${DataDictionary.OrderDocument.attributes}" />
	<kul:tabTop tabTitle="Order Document Correction" defaultOpen="true" tabErrorKey="orderDocumentNumber,orderDocument.*">
		<div class="tab-container" align=center>
		<h3>Order Document</h3>
		<table cellpadding="0" cellspacing="0" class="datatable">
			<c:choose>
				<c:when test="${KualiForm.orderDocument == null}">
					<tr>
						<th class="grid" width="50%" align="right"><kul:htmlAttributeLabel attributeEntry="${OrderDocumentAttributes.documentNumber}" readOnly="true" /></th>
						<td class="grid"><kul:htmlControlAttribute property="orderDocumentNumber" attributeEntry="${OrderDocumentAttributes.documentNumber}" readOnly="false" /> <html:image
							property="methodToCall.add" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" styleClass="tinybutton" alt="Add Order Document" title="Add Order Document" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<th class="grid" width="50%" align="right"><kul:htmlAttributeLabel attributeEntry="${OrderDocumentAttributes.documentNumber}" readOnly="true" /></th>
						<td class="grid"><kul:htmlControlAttribute property="orderDocumentNumber" attributeEntry="${OrderDocumentAttributes.documentNumber}" readOnly="true" /> <c:if test="${!KualiForm.readOnly}">
						&nbsp;&nbsp;&nbsp; <html:image property="methodToCall.clear" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-clear1.gif" styleClass="tinybutton" alt="Clear the order"
								title="Clear the current order selection" />
						</c:if></td>
					</tr>
					<tr>
						<th class="grid" width="50%" align="right"><strong><kul:htmlAttributeLabel attributeEntry="${OrderDocumentAttributes.orderId}" readOnly="true" /></th>
						<td class="grid"><kul:htmlControlAttribute property="orderDocument.orderId" attributeEntry="${OrderDocumentAttributes.orderId}" readOnly="true" /></td>
					</tr>
					<tr>
						<th class="grid" width="50%" align="right"><strong><kul:htmlAttributeLabel attributeEntry="${OrderDocumentAttributes.warehouseCd}" readOnly="true" /></th>
						<td class="grid"><kul:htmlControlAttribute property="orderDocument.warehouseCd" attributeEntry="${OrderDocumentAttributes.warehouseCd}" readOnly="true" /></td>
					</tr>
					<tr>
						<th class="grid" width="50%" align="right"><strong><kul:htmlAttributeLabel attributeEntry="${OrderDocumentAttributes.reqsId}" readOnly="true" /></th>
						<td class="grid"><kul:htmlControlAttribute property="orderDocument.reqsId" attributeEntry="${OrderDocumentAttributes.reqsId}" readOnly="${KualiForm.readOnly}" /></td>
					</tr>
					<tr>
						<th class="grid" width="50%" align="right"><strong><kul:htmlAttributeLabel attributeEntry="${OrderDocumentAttributes.orderStatusCd}" readOnly="true" /></th>
						<td class="grid"><kul:htmlControlAttribute property="orderDocument.orderStatusCd" attributeEntry="${OrderDocumentAttributes.orderStatusCd}" readOnly="${KualiForm.readOnly}" /> <c:if
							test="${!KualiForm.readOnly}">
						&nbsp; <kul:lookup boClassName="org.kuali.ext.mm.businessobject.OrderStatus" fieldConversions="orderStatusCd:orderDocument.orderStatusCd"
								lookupParameters="orderDocument.orderStatusCd:orderStatusCd" /></td>
						</c:if>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		</div>
	</kul:tabTop>
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons"><c:if test="${!KualiForm.readOnly}">
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" property="methodToCall.submit" title="Submit" alt="Submit" />
	</c:if> <html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_cancel.gif" styleClass="globalbuttons" property="methodToCall.cancel" title="Cancel" alt="Cancel" /></div>
</kul:page>
