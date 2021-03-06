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

<%@ attribute name="favoritesList" required="true" type="java.util.Collection" %>
<%@ attribute name="listLimit" required="false"  %>

<div id="favoriteList_container" class="browseBox">
	<h4>Favorites</h4>	
	<ul class="browseBox_list">
		<c:forEach var="favorites" items="${favoritesList}" varStatus="rowCounter" >
			<c:if test="${empty listLimit or rowCounter.count < listLimit}">
				<li class="browseBox_listItem"><a href="${ConfigProperties.application.url}/mm/favorites.do?methodToCall=start&favoritesId=${favorites.customerFavId}" alt="${favorites.customerFavName}" title="${favorites.customerFavName}">${favorites.customerFavName}</a></li>
			</c:if>
		</c:forEach>
		<c:if test="${(fn:length(favoritesList)) >= listLimit }">
			<li class="browseBox_listItem"><a href="${ConfigProperties.application.url}/mm/favorites.do?methodToCall=start" alt="More Favorites" title="More favorites">more...</a></li>
		</c:if>
	</ul>
</div>
