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
<%@ attribute name="pageCount" required="true" type="java.lang.Integer" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="itemsPerPage" required="false" %>
<%@ attribute name="sort" required="false" %>
<%@ attribute name="pageAction" required="true" %>


<div id="pageNavigation">	
	<c:if test="${pageCount > 1 }">
		<c:choose>
			<c:when test="${currentPage > 1}">
				<a class="pageNav_control" href="${ConfigProperties.application.url}/mm/${pageAction}&page=${currentPage - 1}&perPage=${itemsPerPage}&sort=${sort}" >&lt;&lt; Previous</a>					
			</c:when>
			<c:otherwise>
				<span class="pageNav_control_disable">&lt;&lt; Previous</span>
			</c:otherwise>
		</c:choose>		 
		<c:choose>
			<c:when test="${currentPage <= 6}">
				<c:set var="startPage" value="${2}" />				
			</c:when>
			<c:when test="${currentPage >= pageCount - 2}">
				<c:set var="startPage" value="${pageCount - 4}" />				
			</c:when>
			<c:otherwise>
				<c:set var="startPage" value="${currentPage - 2}" />
			</c:otherwise>
		</c:choose>
		
		<c:set var="endPage" value="${startPage + 4}" />
		<c:if test="${pageCount <= 6}">
			<c:set var="endPage" value="${pageCount}" />
		</c:if>
		
		<c:choose>
			<c:when test="${currentPage eq 1}">
				<span class="pageNav_current">1</span>				
			</c:when>			
			<c:otherwise>
				<a class="pageNav" href="${ConfigProperties.application.url}/mm/${pageAction}&page=${1}&perPage=${itemsPerPage}&sort=${sort}" >1</a>
			</c:otherwise>
		</c:choose>
		<c:if test="${currentPage > 6}">
			&nbsp;...&nbsp;				
		</c:if>
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<c:choose>
				<c:when test="${i eq currentPage}">
					<span class="pageNav_current">${i}</span>
				</c:when>
				<c:otherwise>
					<a class="pageNav" href="${ConfigProperties.application.url}/mm/${pageAction}&page=${i}&perPage=${itemsPerPage}&sort=${sort}" >${i}</a>
				</c:otherwise>
			</c:choose>				
		</c:forEach>
		<c:if test="${(pageCount > 6) and (currentPage < (pageCount - 2))}">
			&nbsp;...&nbsp;				
		</c:if>
		<c:choose>
			<c:when test="${currentPage < pageCount}">
				<a class="pageNav_control" href="${ConfigProperties.application.url}/mm/${pageAction}&page=${currentPage + 1}&perPage=${itemsPerPage}&sort=${sort}" >Next &gt;&gt;</a>					
			</c:when>
			<c:otherwise>
				<span class="pageNav_control_disable">Next &gt;&gt;</span>
			</c:otherwise>
		</c:choose>	
	</c:if>
</div>