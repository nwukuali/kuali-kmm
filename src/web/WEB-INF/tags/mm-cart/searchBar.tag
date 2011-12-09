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

<script type='text/javascript' src="${ConfigProperties.application.url}/dwr/interface/ShopCartSearchService.js"></script>
<script language="JavaScript" type="text/javascript" src="scripts/cart/objectInfo.js"></script>

<div id="searchBanner" >			
		<div id="searchBar_left">
			<span class="searchLabel">Search: </span>
			<html:select style="width:14em" property="browseManager.selectedCatalogId" onfocus="closeSuggestionBox(document.forms['KualiForm'], 'browseManager.selectedSuggestion');" >
				<html:option value="${KualiForm.browseManager.availableCatalogIdString}">All Catalogs</html:option>
				<c:forEach var="catalog" items="${KualiForm.browseManager.availableCatalogs}" varStatus="rowCounter" >
					<html:option value="${catalog.catalogId}">${catalog.catalogDesc}</html:option>
				</c:forEach>
			</html:select>
		</div>
		<div id="searchBar_center">
			<html:text  property="browseManager.quickSearch" size="50" alt="Search keywords" title="Enter your search keywords here." onkeyup="getSearchSuggestions(document.forms['KualiForm'], 'browseManager.quickSearch', 'browseManager.selectedCatalogId', 'browseManager.selectedSuggestion', 'methodToCall.quickSearch', event );" />
			<script>
				document.forms['KualiForm'].elements['browseManager.quickSearch'].setAttribute("autocomplete", "off"); 
			</script>
			<html:select styleId="suggestionBox" property="browseManager.selectedSuggestion" size="10" alt="Search Suggestions" title="Suggestions for your search." onclick="changeSelectedSuggestion(document.forms['KualiForm'], 'browseManager.quickSearch', 'browseManager.selectedSuggestion');" />
			
			<div class="clear"></div>	
		</div>
		<div id="searchBar_right">
						
			<html:image styleId="methodToCall.quickSearch" styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-search.gif" property="methodToCall.quickSearch" value="Search" title="Search" alt="Search" />
			<span >
				<a class="white" href="search.do?methodToCall=start">Advanced Search</span></a>					
			</span>							
			
			
		</div>
</div>
