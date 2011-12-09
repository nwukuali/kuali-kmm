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

<%@ attribute name="profileList" required="true" type="java.util.Collection" %>
<%@ attribute name="selectedProfileId" required="false"  %>
<%@ attribute name="listLimit" required="false"  %>

<div id="profileList_container" class="browseBox">
	<h5>Customer Profiles</h5>	
	<ul class="browseBox_list">
		<li class="browseBox_listItem"><a href="${ConfigProperties.application.url}/mm/profile.do?methodToCall=start&create=true" alt="Create new" title="Create New">[ Create New ]</a></li>
		<c:forEach var="profile" items="${profileList}" varStatus="rowCounter" >
			<c:if test="${empty listLimit or rowCounter.count < listLimit}">
				<c:set var="listItemClass" value="browseBox_listItem" />
				<c:if test="${selectedProfileId eq profile.profileId }">
					<c:set var="listItemClass" value="browseBox_listItemSelected" />
				</c:if>
				<li class="${listItemClass}">
					<a href="${ConfigProperties.application.url}/mm/profile.do?methodToCall=start&profileId=${profile.profileId}" alt="${profile.profileName}" title="${profile.profileName}">&nbsp;${profile.profileName}<c:if test="${profile.profileDefaultIndicator}">&nbsp;*</c:if></a>
				</li>
			</c:if>
		</c:forEach>
		<c:if test="${(fn:length(favoritesList)) >= listLimit }">
			<li ><a href="${ConfigProperties.application.url}/mm/profile.do?methodToCall=start" alt="More Profiles" title="More Profiles">more...</a></li>
		</c:if>
	</ul>
</div>
