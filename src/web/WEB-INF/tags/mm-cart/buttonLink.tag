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
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>

<%@ attribute name="url" required="true" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="imageSource" required="false" %>
<%@ attribute name="styleClass" required="false" %>
<%@ attribute name="target" required="false" %>

<c:if test="${empty styleClass}" >
	<c:set var="styleClass" value="plain" />
</c:if>

<c:if test="${empty target}" >
	<c:set var="target" value="_self" />
</c:if>


<a class="${styleClass}" href="${url}" target="${target}">
	<img class="imageButton" src="${imageSource}" alt="${title}" title="${title}" border="0" />
</a>

