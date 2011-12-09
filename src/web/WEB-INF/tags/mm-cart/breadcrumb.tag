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
<%@ attribute name="breadcrumbList" required="true" type="java.util.Collection" %>
<%@ attribute name="readOnlyItemBreadcrumb" required="false" %>

<div id="breadcrumb_container">
<c:forEach var="crumb" items="${breadcrumbList}" varStatus="counter">
	<c:choose>
		<c:when test="${counter.count eq  (fn:length(breadcrumbList)) and empty readOnlyItemBreadcrumb}">
			<span class="breadcrumb_current">${crumb.description}</span>
		</c:when>
		<c:otherwise>	
			<a class="breadcrumb" href="browse.do?methodToCall=followBreadcrumb&breadcrumbId=${counter.count-1}" alt="${crumb.description}" title="${crumb.description}">${crumb.description}</a>&nbsp;&gt;&nbsp;
			<c:if test="${counter.count eq  (fn:length(breadcrumbList)) and not empty readOnlyItemBreadcrumb}">
				<span class="breadcrumb_current">${readOnlyItemBreadcrumb }</span>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:forEach>

</div>
