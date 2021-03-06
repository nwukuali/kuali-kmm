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

<div class="header2">
  	<div class="header2-left-focus">
	    <div class="breadcrumb-focus"><a href="asdf.html" /> 
	    	<portal:portalLink displayTitle="false" title='Action List' url='${ConfigProperties.finance.system.url}/kew/ActionList.do'>
	   		<img src="images/portal/icon-port-actionlist.gif" alt="action list" width="91" height="19" border="0"></portal:portalLink>
	    	<portal:portalLink displayTitle="false" title='Document Search' url='${ConfigProperties.finance.system.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kew.docsearch.DocSearchCriteriaDTO&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true'>
	    	<img src="images/portal/icon-port-docsearch.gif" alt="doc search" width="96" height="19" border="0"></portal:portalLink>
		</div>
	</div>
</div>
<div id="login-info"> <c:choose> <c:when test="${empty UserSession.loggedInUserPrincipalName}" > <strong>You are not logged in.</strong> </c:when> <c:otherwise> <strong>Logged in User:&nbsp;${UserSession.loggedInUserPrincipalName}</strong> <c:if test="${UserSession.backdoorInUse}" > <strong>&nbsp;&nbsp;Impersonating User:&nbsp;${UserSession.principalName}</strong> </c:if> </c:otherwise> </c:choose></div>
<div id="search">
<c:choose>
	<c:when test="${empty UserSession.loggedInUserPrincipalName}">
	</c:when>
	<c:when	test="${fn:trim(ConfigProperties.environment) == fn:trim(ConfigProperties.production.environment.code)}">
		<html:form action="/logout.do" method="post" style="margin:0; display:inline">
          <input name="imageField" type="submit" value="Logout" class="go" title="Click to logout.">
        </html:form>
	</c:when>
	<c:otherwise>
		<html:form action="/backdoorlogin.do" method="post" style="margin:0; display:inline">
			<input name="backdoorId" type="text" class="searchbox" size="10"
				title="Enter your backdoor ID here.">
			<input name="channelUrl" type="hidden"
				value="${ConfigProperties.kmm.url}/backdoorlogin.do">
			<input name="channelTitle" type="hidden" value="Workflow Services">
			<input name="imageField" type="submit" value="login" class="go"
				title="Click to login.">			
		</html:form>
		<html:form action="/backdoorlogin.do" method="post" style="margin:0; display:inline">
          	<input name="imageField" type="submit" value="Logout" class="go" title="Click to logout.">
          	<input name="methodToCall" type="hidden" value="logout" />
        </html:form>
	</c:otherwise>
</c:choose>
</div>