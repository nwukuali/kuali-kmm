<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="browseCatalog">
	<div id="browseCatalog_content">
		<h3>
			<shopping:breadcrumb breadcrumbList="${KualiForm.browseManager.breadcrumbs}" />
		</h3>
		<div id="browseCatalog_columns">
			<c:forEach var="groupColumn" items="${KualiForm.catalogGroupOrganizer}" varStatus="colCounter" >
				<div class="catalogGroup_column">
					<ul class="browseBox_list">
					<c:forEach var="catalogGroup" items="${groupColumn}" varStatus="groupCounter" >
						<li class="browseBox_listItem" ><a class="browse" href="browse.do?methodToCall=expandCatalogGroup&groupId=${catalogGroup.catalogGroupCd}&resultCount=${catalogGroup.resultSetCount}" alt="${catalogGroup.catalogGroupNm}" title="${catalogGroup.catalogGroupNm}">${catalogGroup.catalogGroupNm} <c:if test="${catalogGroup.resultSetCount > 0}">(${catalogGroup.resultSetCount})</c:if></a></li>
					</c:forEach>
					</ul>
				</div>
			</c:forEach>
			<div class="clear"></div>
		</div>
		&nbsp;
	</div>	
	<script type="text/javascript">
		var main = document.getElementById("browseCatalog_content");
		main.style.height=document.body.parentNode.scrollHeight + "px";
	</script>
</shopping:page>