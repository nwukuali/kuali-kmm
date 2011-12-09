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

<%@ attribute name="orderDetail" required="true" type="org.kuali.ext.mm.businessobject.OrderDetail" %>

<c:set var="detailAttributes" value="${DataDictionary.OrderDetail.attributes}" />

<div class="orderDetail">
	<td class="cartItemData" width="3%">
		<a href="item.do?methodToCall=start&itemId=${orderDetail.catalogItem.catalogItemId}" >
			<c:choose>
				<c:when test="${not empty orderDetail.catalogItem.catalogItemImages}">
					<img class="itemPreviewImg" src="${orderDetail.catalogItem.catalogItemImages[0].catalogImage.catalogImageUrl}" height="100" width="100" alt="${orderDetail.catalogItem.catalogItemImages[0].catalogImage.catalogImageName}" align="left" />
				</c:when>
				<c:otherwise>
					<img class="itemPreviewImg" src="images/noimage.jpg" height="100" width="100" alt="No image available" align="left" />	
				</c:otherwise>
			</c:choose>
		</a> 	
	</td>					
	<td class="cartItemData ">						
		<p>
			Item #: <a href="item.do?methodToCall=start&itemId=${orderDetail.catalogItem.catalogItemId}" >${orderDetail.catalogItem.distributorNbr}</a>
			<c:if test="${orderDetail.willCall}"><strong style="margin-left:4em;">Warehouse Pickup Required</strong></c:if>
		</p>				
		<p>${orderDetail.orderItemDetailDesc }</p>						
	</td>					
	<td class="cartItemData ">${orderDetail.stockUnitOfIssueCd}</td>
	<td class="cartItemData ">${orderDetail.orderItemQty}</td>
	<td class="cartItemData ">${orderDetail.displayTotal}</td>				

</div>
