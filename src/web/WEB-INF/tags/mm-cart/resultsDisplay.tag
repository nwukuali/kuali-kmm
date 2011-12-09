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
<%@ attribute name="catalogItems" required="true" type="java.util.Collection" %>

<div id="resultsDisplay">
	<div id="resultsSummary">
		<div id="resultsSummary_info">
			<c:choose>
				<c:when test="${empty catalogItems}">
					No results to display for <strong>"${KualiForm.queryDescription }"</strong>.
				</c:when>
				<c:otherwise>
					<c:set var="startIndex" value="${(KualiForm.pageNumber-1)*KualiForm.resultsPerPage + 1}" />
					<c:set var="endIndex" value="${KualiForm.pageNumber * KualiForm.resultsPerPage}" />
				<c:if test="${endIndex > KualiForm.browseManager.totalResultCount}" >					
					<c:set var="endIndex" value="${KualiForm.browseManager.totalResultCount}" />
				</c:if>
					Displaying items <strong>${startIndex}</strong> - <strong>${endIndex}</strong> of <strong>${KualiForm.browseManager.totalResultCount}</strong> results. 
				</c:otherwise>
			</c:choose>
		</div>
		<div id="results_sort_container">
			<span style="vertical-align:top">
				<span class="sort_label">Sort by: </span>
				<html:select property="sortOrder" >
					<html:option value="relevance">Relevance</html:option>
					<html:option value="bestselling">Best Selling</html:option>
					<html:option value="price_lh">Price (Low to High)</html:option>
					<html:option value="price_hl">Price (High to Low)</html:option>
				</html:select>
			</span>
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-go.gif" property="methodToCall.changeSortBy" value="Refresh View" title="Refresh View" alt="Refresh View" />
		</div>
		<div class="clear"></div>		
	</div>
	<c:forEach var="item" items="${catalogItems}" varStatus="rowCounter" >
		<shopping:itemPreview catalogItem="${item}" />
	</c:forEach>
	<shopping:pageNavigation pageAction="browse.do?methodToCall=goToPage" pageCount="${KualiForm.pageCount}" currentPage="${KualiForm.pageNumber}" sort="${KualiForm.sortOrder}" />

</div>
