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

<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>

<script type='text/javascript' src="${ConfigProperties.application.url}/dwr/interface/ShopCartCatalogService.js"></script>
<script language="JavaScript" type="text/javascript" src="scripts/cart/objectInfo.js"></script>


<shopping:infoEntry title="Direct Entry" id="directEntry" canHide="true" toggleMethod="toggleDirectEntry" toggleValue="${KualiForm.showDirectEntry}">
		<html:hidden property="customerProfile.profileId" />
		<p class="entryData">
			<span class="entryLabel">Catalog:&nbsp;</span>
			<html:select property="directEntry.catalogId" onchange="loadCatalogInfo(document.forms['KualiForm'], 'directEntry.catalogNumber', 'directEntry.catalogId', 'directEntry.quantity', 'customerProfile.profileId', 'directEntry.unitOfIssue', 'directEntry.catalogPrice', 'directEntry.catalogDescription');">
				<c:forEach var="catalog" items="${KualiForm.browseManager.availableCatalogs}" varStatus="rowCounter" >
					<html:option value="${catalog.catalogId}">${catalog.catalogDesc}</html:option>
				</c:forEach>
			</html:select>
		</p>
		<p class="entryData">
<!--			<span class="entryLabel">Item Number:&nbsp;</span><html:text property="directEntry.catalogNumber" size="15" alt="catalog number"  />		-->
			<span class="entryLabel">Item Number:&nbsp;</span><html:text property="directEntry.catalogNumber" size="15" alt="catalog number" onblur="loadCatalogInfo(document.forms['KualiForm'], 'directEntry.catalogNumber', 'directEntry.catalogId', 'directEntry.quantity', 'customerProfile.profileId', 'directEntry.unitOfIssue', 'directEntry.catalogPrice', 'directEntry.catalogDescription');" />
			<span class="entryLabel">Qty:&nbsp;</span><html:text property="directEntry.quantity" size="4" alt="quantity" onblur="loadCatalogInfo(document.forms['KualiForm'], 'directEntry.catalogNumber', 'directEntry.catalogId', 'directEntry.quantity', 'customerProfile.profileId', 'directEntry.unitOfIssue', 'directEntry.catalogPrice', 'directEntry.catalogDescription');" />		
			<span class="entryLabel">Will Call:&nbsp;</span><html:checkbox property="directEntry.willCall" />
			<span class="margin_left"><html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToCart.gif" property="methodToCall.addDirectEntry" value="Add to Cart" title="Add to Cart" alt="Add to Cart" /></span>
			<div id="itemInfoLine">
				<span class="entryLabel">Description:&nbsp;</span><html:text styleClass="textNoBox" property="directEntry.catalogDescription" alt="Description" size="72" readonly="true" />
				<br />	
				<span class="entryLabel">U/I:&nbsp;</span><html:text styleClass="textNoBox" property="directEntry.unitOfIssue" alt="Unit Of Issue" size="3" readonly="true" />
				<span class="entryLabel">Price:&nbsp;</span><html:text styleClass="textNoBox" property="directEntry.catalogPrice" alt="Catalog Price" size="15" readonly="true" />
			</div>
		</p>
</shopping:infoEntry>