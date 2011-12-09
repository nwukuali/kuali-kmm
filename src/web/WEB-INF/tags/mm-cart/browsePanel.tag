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

<%@ attribute name="baseUrlAction" required="true" %>

<div id="browsePanel_container"  class="browseBox">
	<h5>Browse</h5>
	<ul class="browseBox_list">
		<c:choose>
			<c:when test="${empty KualiForm.browseManager.selectedGroup }" >
				<c:forEach var="catalogGroup" items="${KualiForm.browseManager.sortedGroupList}" varStatus="rowCounter" >
					<li class="browseBox_listItem" ><a class="browse" href="${baseUrlAction}?methodToCall=expandCatalogGroup&groupId=${catalogGroup.catalogGroupCd}&resultCount=${catalogGroup.resultSetCount}" alt="${catalogGroup.catalogGroupNm}" title="${catalogGroup.catalogGroupNm}">${catalogGroup.catalogGroupNm} <c:if test="${catalogGroup.resultSetCount > 0}">(${catalogGroup.resultSetCount})</c:if></a></li>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${KualiForm.browseManager.selectedGroup.resultSetCount > 0 }">
						<li class="browseBox_listItem" ><a class="browse" href="${baseUrlAction}?methodToCall=showAllGroups" alt="All Categories" title="All Categories">All Categories</a></li>
					</c:when>
					<c:otherwise>
						<li class="browseBox_listItem" ><a class="browse" href="home.do?methodToCall=start" alt="All Categories" title="All Categories">All Categories</a></li>
					</c:otherwise>
				</c:choose>	
				<c:choose>
					<c:when test="${empty KualiForm.browseManager.selectedSubgroups }">
						<li class="browseBox_listItemSelected" >&nbsp;${KualiForm.browseManager.selectedGroup.catalogGroupNm} <c:if test="${KualiForm.browseManager.selectedGroup.resultSetCount > 0}">(${KualiForm.browseManager.selectedGroup.resultSetCount})</c:if></li>
					</c:when>
					<c:otherwise>
						<li class="browseBox_listItem" ><a class="browse" href="${baseUrlAction}?methodToCall=expandCatalogGroup&groupId=${KualiForm.browseManager.selectedGroup.catalogGroupCd}&resultCount=${KualiForm.browseManager.selectedGroup.resultSetCount}" alt="${KualiForm.browseManager.selectedGroup.catalogGroupNm}" title="${KualiForm.browseManager.selectedGroup.catalogGroupNm}">&nbsp;${KualiForm.browseManager.selectedGroup.catalogGroupNm}</a></li>
					</c:otherwise>
				</c:choose>				
				<c:forEach var="selectedSubgroup" items="${KualiForm.browseManager.selectedSubgroups}" varStatus="rowCounter" >
					<li >
						<ul class="browseBox_list">
						<c:choose>
							<c:when test="${rowCounter.count eq  (fn:length(KualiForm.browseManager.selectedSubgroups))}">
								<li class="browseBox_listItemSelected" >${selectedSubgroup.catalogSubgroupDesc} <c:if test="${selectedSubgroup.resultSetCount > 0}">(${selectedSubgroup.resultSetCount})</c:if></li>
							</c:when>
							<c:otherwise>
								<li class="browseBox_listItem" ><a class="browse" href="${baseUrlAction}?methodToCall=expandCatalogSubgroup&subgroupId=${selectedSubgroup.catalogSubgroupId}" alt="${selectedSubgroup.catalogSubgroupDesc}" title="${selectedSubgroup.catalogSubgroupDesc}"> ${selectedSubgroup.catalogSubgroupDesc}</a></li>
							</c:otherwise>
						</c:choose>							
				</c:forEach>
						<li >
							<ul class="browseBox_list">
						<c:forEach var="catalogSubgroup" items="${KualiForm.browseManager.sortedSubgroupList}" varStatus="rowCounter" >
							<c:choose>
								<c:when test="${KualiForm.browseManager.selectedGroup.resultSetCount > 0}" >
									<c:if test="${catalogSubgroup.resultSetCount > 0 }">
										<li class="browseBox_listItem" ><a class="browse" href="${baseUrlAction}?methodToCall=expandCatalogSubgroup&subgroupId=${catalogSubgroup.catalogSubgroupId}&resultCount=${catalogSubgroup.resultSetCount}" alt="${catalogSubgroup.catalogSubgroupDesc}" title="${catalogSubgroup.catalogSubgroupDesc}">${catalogSubgroup.catalogSubgroupDesc} <c:if test="${catalogSubgroup.resultSetCount > 0}">(${catalogSubgroup.resultSetCount})</c:if></a></li>
									</c:if>
								</c:when>
								<c:otherwise>
									<li class="browseBox_listItem" ><a class="browse" href="${baseUrlAction}?methodToCall=expandCatalogSubgroup&subgroupId=${catalogSubgroup.catalogSubgroupId}&resultCount=${catalogSubgroup.resultSetCount}" alt="${catalogSubgroup.catalogSubgroupDesc}" title="${catalogSubgroup.catalogSubgroupDesc}">${catalogSubgroup.catalogSubgroupDesc}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
							</ul>
						</li>
				<c:forEach var="catalogSubgroup" items="${KualiForm.browseManager.selectedSubgroups}" varStatus="rowCounter" >						
						</ul>
					</li>		
				</c:forEach>
			</c:otherwise>
		</c:choose>	
	</ul>
</div>