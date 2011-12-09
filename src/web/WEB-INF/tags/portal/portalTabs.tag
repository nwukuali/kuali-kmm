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
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="selectedTab" required="true"%>

<div id="tabs" class="tabposition">
	<ul>
		<c:if test='${selectedTab == "main" || empty selectedTab}'>
			<c:set var="mainTabClass" value="red"/>
			<c:set var="adminTabClass" value="green"/>
		</c:if>
		<c:if test='${selectedTab == "administration"}'>
			<c:set var="mainTabClass" value="green"/>
			<c:set var="adminTabClass" value="red"/>
		</c:if>		
		<li class="<c:out value="${mainTabClass}" />">
			<a class="<c:out value="${mainTabClass}" />" href="portal.do?selectedTab=main" title="Main Menu">Main Menu</a>
		</li>
		<li class="<c:out value="${adminTabClass}" />">
			<a class="<c:out value="${adminTabClass}" />" href="portal.do?selectedTab=administration" title="Administration">Administration</a>
		</li>		
		<c:if test='${ConfigProperties.environment == "dev" || ConfigProperties.environment == "ptd"}'>
		<%-- Future Modules --%>		
		<%--
		<c:if test='${selectedTab == "future"}'>
			<li class="red">
				<a class="red" href="portal.do?selectedTab=future"
					title="Future">Future</a>
			</li>
		</c:if>
		<c:if test='${selectedTab != "future"}'>
			<li class="green">
				<a class="green"
					href="portal.do?selectedTab=future"
					title="Future">Future</a>
			</li>
		</c:if>
		--%>
		</c:if>
		
	</ul>
</div>
