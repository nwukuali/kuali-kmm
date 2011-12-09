<%--
 Copyright 2005-2007 The Kuali Foundation.
 
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
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp"%>
<%@ attribute name="title" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="address" required="true" type="org.kuali.ext.mm.businessobject.Address" %>
<%@ attribute name="addressPropertyName" required="true" %>
<%@ attribute name="readOnly" required="true" %>

<c:set var="addressAttributes" value="${DataDictionary.Address.attributes}" />

<shopping:infoEntry title="${title}" id="${id}" canHide="false">
	
	<c:choose>
		<c:when test="${readOnly}">
			<p>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressName" attributeEntry="${addressAttributes.addressName}" readOnly="${readOnly}" />
			</p>
			<p>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressLine1" attributeEntry="${addressAttributes.addressLine1}" readOnly="${readOnly }" />
			</p>
			<c:if test="${not empty address.addressLine2}" >
				<p>
					<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressLine2" attributeEntry="${addressAttributes.addressLine2}" readOnly="${readOnly }" />
				</p>
			</c:if>			
			<p>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressCityName" attributeEntry="${addressAttributes.addressCityName}" readOnly="${readOnly }" />,
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressStateCode" attributeEntry="${addressAttributes.addressStateCode}" readOnly="${readOnly }" />
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressPostalCode" attributeEntry="${addressAttributes.addressPostalCode}" readOnly="${readOnly }" />
			</p>
			<p>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressCountryCode" attributeEntry="${addressAttributes.addressCountryCode}" readOnly="${readOnly }" />
			</p>
			<p>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressPhoneNumber" attributeEntry="${addressAttributes.addressPhoneNumber}" readOnly="${readOnly }" />
			</p>
		</c:when>
		<c:otherwise>
			<div class="infoEntry_row" style="margin-top:.5em;">
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressName}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressName" attributeEntry="${addressAttributes.addressName}" readOnly="${readOnly}" />
			</div>			
			<div class="infoEntry_row">
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressLine1}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressLine1" attributeEntry="${addressAttributes.addressLine1}" readOnly="${readOnly }" />
			</div>
			<div class="infoEntry_row">
				<span class="address_label">&nbsp;&nbsp;<kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressLine2}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressLine2" attributeEntry="${addressAttributes.addressLine2}" readOnly="${readOnly }" />
			</div>
			<div class="infoEntry_row">
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressCityName}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressCityName" attributeEntry="${addressAttributes.addressCityName}" readOnly="${readOnly }" />
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressStateCode}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressStateCode" attributeEntry="${addressAttributes.addressStateCode}" readOnly="${readOnly }" />
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressPostalCode}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressPostalCode" attributeEntry="${addressAttributes.addressPostalCode}" readOnly="${readOnly }" />
			</div>
			<div class="infoEntry_row">
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressCountryCode}" useShortLabel="true"/></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressCountryCode" attributeEntry="${addressAttributes.addressCountryCode}" readOnly="${readOnly }" />
			</div>
			<div class="infoEntry_row">
				<span class="address_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressPhoneNumber}" useShortLabel="true" /></span>
				<kul:htmlControlAttribute styleClass="address_data" property="${addressPropertyName}.addressPhoneNumber" attributeEntry="${addressAttributes.addressPhoneNumber}" readOnly="${readOnly }" />
			</div>
		</c:otherwise>
	</c:choose> 
		
		
</shopping:infoEntry>	
