<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="home">
	<div id="home_wrapper">
		<div id="browsePanel_wrapper">
			<shopping:browsePanel baseUrlAction="browse.do" />
		</div>		
		<div id="homePanel_content">	
			<div id="homePanel_left">
				<cart:shoppingFrontPage />
				<div id="catalog_list_container">
					<h6>Browse Catalogs</h6>
					<span class="margin_left">
						<c:forEach var="catalog" items="${KualiForm.browseManager.availableCatalogs}" varStatus="rowCounter" >
							<a class="blue" href='${ConfigProperties.application.url}/mm/browseCatalog.do?methodToCall=start&catalogId=${catalog.catalogId}'>${catalog.catalogDesc}</a>&nbsp;&nbsp;
						</c:forEach>
					</span>
				</div>
				<c:if test="${KualiForm.customerProfile ne null }">
					<div id="punchOut_container">
						<h6>Visit Our External Suppliers</h6>
						<ul class="punchout_list">
							<c:forEach var="catalog" items="${KualiForm.punchOutCatalogs}" varStatus="rowCounter" >
								<li><a class="blue" href='${ConfigProperties.application.url}/mm/punchOut.do?methodToCall=start&catalogId=${catalog.catalogId}'>${catalog.catalogDesc}</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>			
			<div id="homePanel_right">
				<shopping:mostPopularItems catalogItems="${KualiForm.bestSellingList}" />
			</div>			
		</div>		
		<script type="text/javascript">
			var main = document.getElementById("homePanel_content");
			main.style.height=document.body.parentNode.scrollHeight + "px";
		</script>
		<div class="clear"></div>
	</div>
</shopping:page>