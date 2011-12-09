<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="search">
	<div id="searchPanel_content">
		<h3>Advanced Search</h3>
		<div id="searchCriteria_container">
			<div id="searchCriteria_labels">
				<p class="searchCriteria_row">
					<span class="advancedSearchLabel">Catalog:&nbsp;</span>
				</p>
			</div>
			<div id="searchCriteria_editable">
				<p class="searchCriteria_row">
					<html:select style="width:14em" property="browseManager.selectedCatalogId" >
						<html:option value="${KualiForm.browseManager.availableCatalogIdString}">All Catalogs</html:option>
						<c:forEach var="catalog" items="${KualiForm.browseManager.availableCatalogs}" varStatus="rowCounter" >
							<html:option value="${catalog.catalogId}">${catalog.catalogDesc}</html:option>
						</c:forEach>
					</html:select>
				</p>
			</div>
			<div id="searchCriteria_labels">
				<p class="searchCriteria_row">
					<span class="advancedSearchLabel">All of these words:&nbsp;</span>
				</p>
			</div>
			<div id="searchCriteria_editable">
				<p class="searchCriteria_row">
					<html:text property="browseManager.advancedSearch.allKeywords" size="50" alt="All of these words" title="Enter your search keywords here." />
				</p>
			</div>			
			<div id="searchCriteria_labels">
				<p class="searchCriteria_row">
					<span class="advancedSearchLabel">Any of these words:&nbsp;</span>
				</p>
			</div>
			<div id="searchCriteria_editable">
				<p class="searchCriteria_row">
					<html:text property="browseManager.advancedSearch.anyKeywords" size="50" alt="Any of these words" title="Enter your search keywords here." />
				</p>
			</div>
			<div id="searchCriteria_labels">
				<p class="searchCriteria_row">
					<span class="advancedSearchLabel">None of these words:&nbsp;</span>
				</p>
			</div>
			<div id="searchCriteria_editable">
				<p class="searchCriteria_row">
					<html:text property="browseManager.advancedSearch.noneKeywords" size="50" alt="None of these words" title="Enter your search keywords here." />
				</p>
			</div>
			<div id="searchCriteria_labels">
				<p class="searchCriteria_row">
					<span class="advancedSearchLabel">Only in current results:&nbsp;</span>
				</p>
			</div>
			<div id="searchCriteria_editable">
				<p class="searchCriteria_row">
					<html:checkbox property="browseManager.advancedSearch.withinResults" alt="Only in current results" title="Search only within current results." />
				</p>
			</div>				
			<div id="advancedSearch_controls">
				<html:image src="${ConfigProperties.kmm.externalizable.images.url}button-search.gif" property="methodToCall.search" value="Search" title="Search" alt="Search" />
			</div>
		</div>	
	</div>
	
</shopping:page>