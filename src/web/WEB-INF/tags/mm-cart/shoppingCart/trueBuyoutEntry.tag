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

<c:set var="trueBuyoutDetailAttributes" value="${DataDictionary.TrueBuyoutDetail.attributes}" />

<shopping:infoEntry title="Special Orders Entry" id="trueBuyoutEntry" canHide="true" toggleMethod="toggleTrueBuyout" toggleValue="${KualiForm.showTrueBuyout}">
		<p class="entryData">
			<p><span class="entryLabel" style="vertical-align:top">Description:&nbsp;</span><html:textarea property="trueBuyoutEntry.catalogDescription" alt="Description" cols="42" /></p>
			<p class="entryData">
				<span class="entryLabel">U/I:&nbsp;</span><html:text property="trueBuyoutEntry.unitOfIssue" alt="Unit Of Issue" size="10" maxlength="10" />				
				<kul:lookup boClassName="org.kuali.ext.mm.businessobject.UnitOfIssue" fieldConversions="unitOfIssueCode:trueBuyoutEntry.unitOfIssue" lookupParameters="trueBuyoutEntry.unitOfIssue:unitOfIssueCode" />
				<span class="entryLabel">Qty:&nbsp;</span><html:text property="trueBuyoutEntry.quantity" size="4" maxlength="10" alt="quantity" />
				<span class="entryLabel">Will Call:&nbsp;</span><html:checkbox property="trueBuyoutEntry.willCall" />
				<span class="margin_left"><html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToCart.gif" property="methodToCall.addTrueBuyoutEntry" value="Add True Buyout" title="Add True Buyout" alt="Add True Buyout" /></span></p>
<%--			
				<span class="entryLabel">Object Code (Optional):&nbsp;</span><html:text property="trueBuyoutEntry.finObjectCode" alt="ObjectCode" size="4" maxlength="4" />
				<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialObjectCode" fieldConversions="financialObjectCode:trueBuyoutEntry.finObjectCode" lookupParameters="trueBuyoutEntry.finObjectCode:financialObjectCode" />
--%>
			</p>
		</p>
</shopping:infoEntry>