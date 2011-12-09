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
<c:set var="itemAttributes" value="${DataDictionary.CatalogItem.attributes}" />

<div class="itemThumbnail">	
	<div class="itemThumbnail_img">
		<a href="item.do?methodToCall=start&itemId=${catalogItem.catalogItemId}" >
			<c:choose>
				<c:when test="${not empty catalogItem.catalogItemImages}">
					<img class="itemPreviewImg_thumb" src="${catalogItem.catalogItemImages[0].catalogImage.catalogImagePath}" alt="${catalogItem.catalogItemImages[0].catalogImage.catalogImageName}" align="left" />
				</c:when>
				<c:otherwise>
					<img class="itemPreviewImg_thumb" src="${ConfigProperties.kmm.externalizable.images.url}noimage.jpg" alt="No image available" align="left" />	
				</c:otherwise>
			</c:choose>
		</a>
		<div class="clear"></div>
	</div>
	<div class="itemThumbnail_info">
		<a href="item.do?methodToCall=start&itemId=${catalogItem.catalogItemId}" >
			Item #:&nbsp;${catalogItem.distributorNbr}		
		</a>
		<br />
		${catalogItem.shortDescription}
	</div>
</div>
