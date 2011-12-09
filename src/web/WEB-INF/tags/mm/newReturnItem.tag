<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<%@ attribute name="vendorDocument" required="true" %>

<c:set var="returnDetailAttributes"
	value="${DataDictionary.ReturnDetail.attributes}" />
<c:set var="returnStatusCodeAttributes"
	value="${DataDictionary.ReturnStatusCode.attributes}" />
<c:set var="unitOfIssueAttributes"
	value="${DataDictionary.UnitOfIssue.attributes}" />
<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
<c:set var="vendorReturnStatusCodeAttributes"
		value="${DataDictionary.VendorReturnStatusCode.attributes}" />


<kul:tab tabTitle="Return Item Not in the Order" defaultOpen="true"
	tabErrorKey="newReturnDetail*">
	<div class="tab-container" align=center>
	<h3>Add Item</h3>
	<table cellpadding="0" cellspacing="0" class="datatable" title="Return Items"
		summary="Return Items">
		<tbody>
	<tr>
	<th>
	<c:out value=" # " />:
				</th>
	<kul:htmlAttributeHeaderCell forceRequired = "true"
		attributeEntry="${returnDetailAttributes.returnQuantity}" />
	<kul:htmlAttributeHeaderCell forceRequired = "true"
		attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}" />
	<kul:htmlAttributeHeaderCell forceRequired = "true"
		attributeEntry="${returnDetailAttributes.returnUnitOfIssueOfCode}" />
	<kul:htmlAttributeHeaderCell forceRequired = "true"
		attributeEntry="${stockAttributes.stockDistributorNbr}" />
	<kul:htmlAttributeHeaderCell forceRequired = "true"
		attributeEntry="${returnDetailAttributes.returnItemPrice}" />
	<kul:htmlAttributeHeaderCell literalLabel="Extended Cost" />
	<kul:htmlAttributeHeaderCell literalLabel="Action" />
	</tr>
	<tr>
	<td></td>
	<td class="grid" align="center">
	<kul:htmlControlAttribute
		attributeEntry="${returnDetailAttributes.returnQuantity}"
		property="newReturnDetail.returnQuantity" readOnly="false" />
	</td>
		<c:if test="${vendorDocument}">
	<td class="grid">
	<kul:htmlControlAttribute
		attributeEntry="${vendorReturnStatusCodeAttributes.returnStatusCodeLookable}"
		property="newReturnDetail.returnDetailStatusCode"
		readOnly="false" />
		</td>
	</c:if>
	<c:if test="${!vendorDocument}">
	<td class="grid">
	<kul:htmlControlAttribute
		attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}"
		property="newReturnDetail.returnDetailStatusCode"
		readOnly="false" />
		</td>
		</c:if>
	<td class="grid" align="center">
	<kul:htmlControlAttribute
		attributeEntry="${unitOfIssueAttributes.unitOfIssueCodeLookable}"
		property="newReturnDetail.returnUnitOfIssueOfCode" readOnly="false" />
	</td>
	<td class="grid" align="center">
	<kul:htmlControlAttribute
		attributeEntry="${stockAttributes.stockDistributorNbr}"
		property="newReturnDetail.stockDistributorNumber" readOnly="false" />
	<kul:lookup boClassName="org.kuali.ext.mm.businessobject.CatalogItem"
		fieldConversions="distributorNbr:newReturnDetail.stockDistributorNumber,catalogItemId:newReturnDetail.catalogItemId,stock.stockPrice:newReturnDetail.returnItemPrice" />

	</td>
	<td class="grid" align="center">
	<kul:htmlControlAttribute
		attributeEntry="${returnDetailAttributes.returnItemPrice}"
		property="newReturnDetail.returnItemPrice" readOnly="false" />
	</td>
	<td class="grid" align="center">
	</td>
	<td class="grid" align="center">
	<div align="center">
	<html:image property="methodToCall.addReturnLine.line${0}"
		src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
		title="Add Returns" alt="Add Returns" styleClass="tinybutton" />
	</div>
	</td>
	</tr>
	<tr>

	</tr>
	<logic:iterate id="rdetail" name="KualiForm"
		property="document.newReturnDetails" indexId="indexx">
		<c:if test="${! empty rdetail}">
		<tr>
		<th>
		<c:out value="${indexx + 1}" />:
				</th>
		<td class="grid" align="center">
		<kul:htmlControlAttribute
			attributeEntry="${returnDetailAttributes.returnQuantity}"
			property="document.newReturnDetails[${indexx}].returnQuantity"
			readOnly="true" />
		</td>
		<td class="grid">
		<kul:htmlControlAttribute
			attributeEntry="${returnStatusCodeAttributes.returnStatusCodeLookable}"
			property="document.newReturnDetails[${indexx}].returnStatus.returnStatusCodeName"
			readOnly="true" />
		</td>
		<td class="grid" align="center">
		<kul:htmlControlAttribute
			attributeEntry="${returnDetailAttributes.returnUnitOfIssueOfCode}"
			property="document.newReturnDetails[${indexx}].returnUnitOfIssue.unitOfIssueDesc"
			readOnly="true" />
		</td>
		<td class="grid" align="center">
		<kul:htmlControlAttribute
			attributeEntry="${stockAttributes.distributorNbr}"
			property="document.newReturnDetails[${indexx}].stockDistributorNumber"
			readOnly="true" />
		</td>
		<td class="grid" align="center">
		<kul:htmlControlAttribute
			attributeEntry="${returnDetailAttributes.returnItemPrice}"
			property="document.newReturnDetails[${indexx}].returnItemPrice"
			readOnly="true" />
		</td>
		<td class="grid" align="center">
		<kul:htmlControlAttribute
			attributeEntry="${returnDetailAttributes.extendedCost}"
			property="document.newReturnDetails[${indexx}].extendedCost"
			readOnly="true" />
		</td>
		<td rowspan="2" class="grid" align="center">
		<div align="center">
		<html:image property="methodToCall.deleteReturnLine.line${indexx}"
			src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif"
			title="Delete Returns" alt="Delete Returns" styleClass="tinybutton" />
		</div>
		
		</td>
		</tr>
		<tr>
		<td colspan="7" class="grid" align="center">
		<kul:htmlControlAttribute
			attributeEntry="${returnDetailAttributes.returnItemDetailDescription}"
			property="document.newReturnDetails[${indexx}].returnItemDetailDescription"
			readOnly="true" />
		</td>
</c:if>
		</tr>

	</logic:iterate>
</tbody>
	</table>
	</div>
</kul:tab>