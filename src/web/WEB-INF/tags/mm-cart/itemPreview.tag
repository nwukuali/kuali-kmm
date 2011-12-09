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
<%@ tag import="org.kuali.ext.mm.businessobject.CatalogItem"%>
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp"%>

<%@ attribute name="catalogItem" required="true" type="org.kuali.ext.mm.businessobject.CatalogItem" %>
<%@ attribute name="showItemControls" required="false"  %>
<c:set var="itemAttributes" value="${DataDictionary.CatalogItem.attributes}" />

<div class="itemPreview">	
	<a href="item.do?methodToCall=start&itemId=${catalogItem.catalogItemId}" >
		<c:choose>
			<c:when test="${not empty catalogItem.catalogItemImages}">
				<img class="itemPreviewImg" src="${catalogItem.catalogItemImages[0].catalogImage.catalogImagePath}" alt="${catalogItem.catalogItemImages[0].catalogImage.catalogImageName}" align="left" />
			</c:when>
			<c:otherwise>
				<img class="itemPreviewImg" src="${ConfigProperties.kmm.externalizable.images.url}noimage.jpg" alt="No image available" align="left" />	
			</c:otherwise>
		</c:choose>
	</a>
	<div class="itemPreview_info">
		<p >
			<a href="item.do?methodToCall=start&itemId=${catalogItem.catalogItemId}" >
				<span class="itemLabel"><kul:htmlAttributeLabel attributeEntry="${itemAttributes.distributorNbr}" useShortLabel="true"/></span>${catalogItem.distributorNbr}
			</a>
			<span class="catalogName_small">&nbsp;&nbsp;(${catalogItem.catalog.catalogDesc })</span>
		</p>
		<p >${catalogItem.catalogDesc}</p>
		<p >
			<span class="itemLabel"><kul:htmlAttributeLabel attributeEntry="${itemAttributes.catalogUnitOfIssueCd}" useShortLabel="true"/></span>
			<span class="itemDetail">${catalogItem.salesUnitOfIssue.unitOfIssueDesc}</span>
			&nbsp;&nbsp;&nbsp;
			<c:if test="${not empty KualiForm.customerProfile }">
				<c:choose>
					<c:when test="${catalogItem.stock.rental}">
						<span class="itemLabel">Price/Day: $${catalogItem.stock.rentalObject.dailyDemurragePrice}</span>
						&nbsp;&nbsp;&nbsp;						
						<span class="itemLabel">Deposit: $${catalogItem.stock.rentalObject.depositPrice}</span>
						<br />
						<c:if test="${KualiForm.actualPriceMap[catalogItem.catalogItemId] > 0}">
							<span class="itemLabel">Cost Of Goods (non-deposit): $${KualiForm.actualPriceMap[catalogItem.catalogItemId]}</span>
							<br />
						</c:if>
						<span class="highlight_red">Rental Item</span>
					</c:when>
					<c:otherwise>
						<span class="itemLabel">Price: $${KualiForm.actualPriceMap[catalogItem.catalogItemId]}</span>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${showItemControls}">
				<div class="itemPreview_controls">
					<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToCart.gif" property="methodToCall.addToCart.line${catalogItem.catalogItemId}" value="Add to Cart" title="Add to Cart" alt="Add to Cart" />
					<c:if test="${KualiForm.inCartCount[catalogItem.catalogItemId] > 0}"> <span class="itemDetail_notice">&nbsp;In cart (${KualiForm.inCartCount[catalogItem.catalogItemId]})</span></c:if>		
				</div>
			</c:if>
		</p>
	</div>
</div>
