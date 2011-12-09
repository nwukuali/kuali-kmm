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

<c:if test="${not empty KualiForm.frontPage}" >
	<c:set var="frontPageUrl" value="frontPage.do" />
	<c:if test="${not empty KualiForm.frontPage.frontPageURL}">
		<c:set var="frontPageUrl" value="${KualiForm.frontPage.frontPageURL}" />
	</c:if>	
	<c:set var="displayHeight" value="500" />
	<c:if test="${not empty KualiForm.frontPage.displayHeight}">
		<c:set var="displayHeight" value="${KualiForm.frontPage.displayHeight}" />
	</c:if>	
	<iframe src="${frontPageUrl}" height="${displayHeight}" width="99%" frameborder="0" scrolling="no">
		<p>Failed to load shopping front page.</p>
	</iframe>	
</c:if>
