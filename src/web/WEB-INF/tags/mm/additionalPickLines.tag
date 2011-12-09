<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="additionalLinesList" required="true" type="java.util.List" %>
<%@ attribute name="newPickLineToAdd" required="true" type="org.kuali.ext.mm.businessobject.PickListLine"  %>
<%@ attribute name="newPickLineToAddPropertyName" required="true" %>
<%@ attribute name="lineId" required="true" %>
<%@ attribute name="parentLineErrorPath" required="false" %>

<c:set var="plLineAttributes" value="${DataDictionary.PickListLine.attributes}" />
<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
<c:set var="siAttributes" value="${DataDictionary.SalesInstance.attributes}" />
<c:set var="odAttributes" value="${DataDictionary.OrderDetail.attributes}" />
<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />

<table cellpadding="0" cellspacing="0" class="datatable" title="Add Picklist Line" summary="Add Picklist Line">
	<tr>				
		<th scope="col" width="10%">&nbsp;</th>
		<th class="grid" width="60%" align="center">Additional Pick Locations</th>
        <th class="grid" width="15%" align="center">${plLineAttributes.pickStockQty.shortLabel}</th>
		<th scope="col" align="center">Action</th>
	</tr>
	<tr>
		<th	>add:</th>
		<td class="infoline">
			<center>
				<c:if test="${not empty newPickLineToAdd.binId}">${newPickLineToAdd.itemLocation}</c:if>
				<c:set var="errorPath" value="${newPickLineToAddPropertyName}.itemLocation" />					
				<c:if test="${not empty KualiForm.messageMapFromPreviousRequest.errorMessages[errorPath]}">
	 				<kul:fieldShowErrorIcon />
  				</c:if>
				&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.businessobject.BinLookable" fieldConversions="binId:${newPickLineToAddPropertyName}.binId" lookupParameters="'${newPickLineToAdd.stock.stockDistributorNbr}':stockBalance.stock.stockDistributorNbr" />
			</center>
		</td>
			
		<td class="infoline"><center><kul:htmlControlAttribute property="${newPickLineToAddPropertyName}.pickStockQty" attributeEntry="${plLineAttributes.pickStockQty}" readOnly="${readOnly}" /></center></td>
		<td class="infoline"><center>
			<html:image property="methodToCall.addPickLine.line${lineId}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" styleClass="tinybutton" value="Add Line" title="Add Line" alt="Add Line" />
			<html:image property="methodToCall.clearNewPickLineData.line${lineId}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-clear1.gif" styleClass="tinybutton" alt="Clear" title="Clears the new pick line data without adding."/>
		</center></td>
	</tr>
	<c:forEach var="pickLine" items="${additionalLinesList}" varStatus="rowCounter" >
		<tr>
			<th>${rowCounter.count}:</th>
			<td class="grid" align="center"><center>${pickLine.itemLocation}</center></td>
			<td class="grid" align="center">
				<center>
					${pickLine.pickStockQty}
					<kul:checkErrors keyMatch="${parentLineErrorPath}.additionalLines[${rowCounter.count-1}].pickStockQty" auditMatch="${parentLineErrorPath}.additionalLines[${rowCounter.count-1}].pickStockQty"/>
					<c:if test="${hasErrors}">
						<kul:fieldShowErrorIcon />
					</c:if>
				</center></td>
			<td class="infoline"><center><html:image property="methodToCall.deletePickLine.line${lineId}.item${rowCounter.count-1}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" styleClass="tinybutton" alt="Delete line" title="Delete line"/></center></td>
		</tr>
	</c:forEach>				
</table>
