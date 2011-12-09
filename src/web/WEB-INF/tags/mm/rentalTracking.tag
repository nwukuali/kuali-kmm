<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="rentalTrackingDetail" required="true" type="org.kuali.ext.mm.businessobject.RentalTrackingDetail" %>
<%@ attribute name="rentalTrackingDetailPropertyName" required="true" %>
<%@ attribute name="rentalTrackingDetailIndex" required="true" %>
<%@ attribute name="readOnly" required="true" %>
<%@ attribute name="columnSpan" required="true" %>

<c:set var="rentalAttributes"	value="${DataDictionary.Rental.attributes}" />
<c:set var="canAdd" value="${rentalTrackingDetail.rentalAddable }" />

<c:if test="${!readOnly}">
	<tr>
		<th>&nbsp;</th>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${rentalAttributes.rentalSerialNumber}" useShortLabel="${false}" />
			</div>
		</th>
		<td class="grid" colspan="${columnSpan}">				
			<kul:htmlControlAttribute attributeEntry="${rentalAttributes.rentalSerialNumber}" property="addSerialNumbers[${rentalTrackingDetailIndex}].serialNumber" readOnly="${readOnly}" disabled="${!canAdd}" />
			<c:if test="${canAdd}">
				&nbsp;<html:image property="methodToCall.addRental.line${rentalTrackingDetailIndex}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" title="Add Rental" alt="Add Rental" styleClass="tinybutton" />
			</c:if>											
		</td>
	</tr>
</c:if>
<c:if test="${not empty rentalTrackingDetail.stagingRentals}">
	<tr>									
		<th>&nbsp;</th>
		<kul:htmlAttributeHeaderCell literalLabel="Added Rental Serial Numbers:" horizontal="true" />
		<td class="grid" colspan="${columnSpan}">									
			<c:choose>
				<c:when test="${readOnly}">
					<c:out value="${rentalTrackingDetail.rentalsForDisplay}" />
				</c:when>
				<c:otherwise>											
					&nbsp;<html:select property="selectedItems" >
						<html:optionsCollection property="${rentalTrackingDetailPropertyName}.stagingRentals" value="displayLabel" label="displayLabel" />
					</html:select>
					<kul:checkErrors keyMatch="selectedItems[${rentalTrackingDetailIndex}]." auditMatch="selectedItems[${rentalTrackingDetailIndex}]."/>										
					<c:if test="${hasErrors}">
						<kul:fieldShowErrorIcon />
					</c:if>
					&nbsp;<html:image property="methodToCall.deleteRental.line${rentalTrackingDetailIndex}" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif" title="Delete Rental" alt="Delete Rental" styleClass="tinybutton" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</c:if>
							