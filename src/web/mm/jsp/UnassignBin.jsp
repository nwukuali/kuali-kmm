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
<kul:page showDocumentInfo="false" htmlFormAction="unassignBin" renderMultipart="true" showTabButtons="true" docTitle="Unassign Bin Location" transactionalDocument="false" headerDispatch="true"
	headerTabActive="true" sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start">
	<c:set var="CatalogAttributes" value="${DataDictionary.Catalog.attributes}" />
	<c:set var="CatalogItemAttributes" value="${DataDictionary.CatalogItem.attributes}" />
	<c:set var="StockBalanceAttributes" value="${DataDictionary.StockBalance.attributes}" />
	<kul:tabTop tabTitle="Unassign Empty Bins" defaultOpen="true" tabErrorKey="catalogItem*">
		<div class="tab-container" align=center>
		<h3>Catalog Item</h3>
		<table cellpadding="0" cellspacing="0" class="datatable">
			<c:if test="${KualiForm.catalogItem != null}">
				<tr>
					<th class="grid" width="25%" align="right"><kul:htmlAttributeLabel attributeEntry="${CatalogAttributes.warehouseCd}" readOnly="true" /></th>
					<td class="grid" width="25%"><kul:htmlControlAttribute property="catalogItem.catalog.warehouseCd" attributeEntry="${CatalogAttributes.warehouseCd}" readOnly="true" /></td>
					<th class="grid" width="25%" align="right"><kul:htmlAttributeLabel attributeEntry="${CatalogAttributes.catalogDesc}" readOnly="true" /></th>
					<td class="grid" width="25%"><kul:htmlControlAttribute property="catalogItem.catalog.catalogDesc" attributeEntry="${CatalogAttributes.catalogDesc}" readOnly="true" /></td>
				</tr>
				<tr>
					<th class="grid" width="25%" align="right"><kul:htmlAttributeLabel attributeEntry="${CatalogItemAttributes.distributorNbr}" readOnly="true" /></th>
					<td class="grid" width="25%"><kul:htmlControlAttribute property="catalogItem.distributorNbr" attributeEntry="${CatalogItemAttributes.distributorNbr}" readOnly="true" /></td>
					<th class="grid" width="25%" align="right"><kul:htmlAttributeLabel attributeEntry="${CatalogItemAttributes.catalogPrc}" readOnly="true" /></th>
					<td class="grid" width="25%"><kul:htmlControlAttribute property="catalogItem.catalogPrc" attributeEntry="${CatalogItemAttributes.catalogPrc}" readOnly="true" /></td>
				</tr>
				<tr>
					<th class="grid" width="25%" align="right"><kul:htmlAttributeLabel attributeEntry="${CatalogItemAttributes.catalogDesc}" readOnly="true" /></th>
					<td class="grid" width="75%" colspan="3"><kul:htmlControlAttribute property="catalogItem.catalogDesc" attributeEntry="${CatalogItemAttributes.catalogDesc}" readOnly="true" /></td>
				</tr>
			</c:if>
		</table>
		</div>
	</kul:tabTop>
	<kul:tab tabTitle="Empty Locations" defaultOpen="true" tabErrorKey="emptyLocations*">
		<div class="tab-container" align=center>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Stock Item(s)" summary="Stock Item(s)">
		<tr>
			<td class="tab-subhead" width="25%">Zone/Bin/Shelf ID/Shelf Number</td>
			<td class="tab-subhead" width="25%">Qty On Hand</td>
			<td class="tab-subhead" width="25%">Last Check In Date</td>
			<td class="tab-subhead" width="25%">Unassign?</td>
		</tr>						
		<c:forEach items="${KualiForm.emptyLocations}" var="sb" varStatus="status">
			<tr>
				<td class="grid" width="25%">${sb.bin.binDisDesc}</td>
				<td class="grid" width="25%">${sb.qtyOnHand}</td>
				<td class="grid" width="25%">${sb.lastCheckinDt}</td>
				<td class="grid" width="25%"><kul:htmlControlAttribute property="emptyLocations[${status.index}].unassign" attributeEntry="${StockBalanceAttributes.unassign}" readOnly="${KualiForm.readOnly}" /></td>
			</tr>
		</c:forEach>
		</table>
		</div>
	</kul:tab>
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons">
	<c:if test="${!KualiForm.readOnly}">
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" property="methodToCall.submit" title="Submit" alt="Submit" />
	</c:if>
	<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_cancel.gif" styleClass="globalbuttons" property="methodToCall.cancel" title="Cancel" alt="Cancel" /></div>
</kul:page>
