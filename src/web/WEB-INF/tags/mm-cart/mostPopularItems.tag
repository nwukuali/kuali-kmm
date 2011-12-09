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

<%@ attribute name="catalogItems" required="true" type="java.util.Collection" %>
<c:set var="itemAttributes" value="${DataDictionary.CatalogItem.attributes}" />

<div id="mostPopular_display">
<!--	<p id="mostPopular_title">Best Selling</p>-->
<h6>Best Selling</h6>
	<div id="mostPopular_center">
		<c:forEach var="item" items="${catalogItems}" varStatus="rowCounter" >
			<shopping:itemThumbnail catalogItem="${item}" />
			<div class="clear"></div>
		</c:forEach>
	</div>
	<div class="clear"></div>
</div>
