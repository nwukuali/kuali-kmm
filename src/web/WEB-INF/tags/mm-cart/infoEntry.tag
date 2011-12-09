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
<%@ attribute name="title" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="canHide" required="true" %>
<%@ attribute name="toggleMethod" required="false" %>
<%@ attribute name="toggleValue" required="false" %>

<div id="${id}" class="infoEntry_container">
	<div class="infoEntry_banner" >
		<div class="infoEntry_title" ><h4>${title}</h4></div>
		<div class="infoEntry_controls">
			<c:choose>
				<c:when test="${!canHide}">
					&nbsp;
				</c:when>
				<c:when test="${toggleValue}">
					<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-tiny-hide.gif" property="methodToCall.${toggleMethod}" value="Hide" title="Hide" alt="Hide" />
				</c:when>
				<c:otherwise>
					<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-tiny-show.gif" property="methodToCall.${toggleMethod}" value="Show" title="Show" alt="Show" />
				</c:otherwise>
			</c:choose>
		</div>
		<div class="clear"></div>
	</div>
	<c:if test="${toggleValue or !canHide}">
		<div class="infoEntryElement_container">
			<jsp:doBody/>
		</div>
	</c:if>
</div>


