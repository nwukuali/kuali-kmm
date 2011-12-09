<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>

<c:set var="customerAttributes" value="${DataDictionary.Customer.attributes}" />
<c:set var="favoritesAttributes" value="${DataDictionary.CustomerFavHeader.attributes}" />

<shopping:page title="Favorites" htmlFormAction="favorites">
	<div class="sidePanel_container">
		<html:hidden property="viewingShared" />
		<div id="sharedCustomerSwitch_container" class="browseBox">
			<h5>View User Favorites</h5>
			<div id="sharedSwitch_controls">View favorite lists for:
				<br />
				<kul:htmlControlAttribute property="customerId" attributeEntry="${customerAttributes.principalName}" readOnly="${false}" />						
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-view.gif" property="methodToCall.viewFavorites" value="View" title="View" alt="View" />
				<a class="blue" href='${ConfigProperties.application.url}/mm/favorites.do?methodToCall=resetFavorites'>Reset</a>
			</div>
		</div>
		<customer:favoritesList favoritesList="${KualiForm.availableFavorites}" />
	</div>
	<div id="favoritesPage_container">
		<h3>My Favorites</h3>		
		<c:choose>
			<c:when test="${KualiForm.showAdd}">
				<div class="margin_left"><kul:errors keyMatch="*"/></div>
				<shopping:infoEntry title="Add item(s) to a Favorites List" id="addToFavorites" canHide="false">					
					<p class="entryData">
						<span class="favoritesLabel">Select existing: </span>
						<html:select style="width:14em" property="selectedFavoriteHeaderId" >
						<html:option value="new">New</html:option>
						<c:forEach var="favorites" items="${KualiForm.availableFavorites}" varStatus="rowCounter" >
							<html:option value="${favorites.customerFavId}">${favorites.customerFavName}</html:option>
						</c:forEach>
						</html:select>
						<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-add.gif" property="methodToCall.addToExisting" value="Add" title="Add" alt="Add" />
					</p>
					<strong>Or</strong>
					<p class="entryData">
						<span class="favoritesLabel">Enter a new name: </span>
						<kul:htmlControlAttribute property="newFavoritesName" attributeEntry="${favoritesAttributes.customerFavName}"  />					
						<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-createNew.gif" property="methodToCall.createNew" value="Create New" title="Create New" alt="Create New" />
					</p>
				</shopping:infoEntry>
			</c:when>
			<c:otherwise>
				<html:hidden property="selectedFavoriteHeaderId" />
			</c:otherwise>
		</c:choose>					
		<div id="displayList_controls">
			<span id="displayListTitle">${KualiForm.displayListTitle }&nbsp;&nbsp;</span>
			<c:if test="${not empty KualiForm.selectedFavHeader }" >
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToCart.gif" property="methodToCall.addAllToCart" value="Add all items to cart" title="Add all items to cart" alt="Add to cart" />
				<c:if test="${KualiForm.selectedFavHeader.principalName eq KualiForm.customerProfile.customer.principalName }">
					<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-deleteList.gif" property="methodToCall.deleteList" value="Delete this favorites list" title="Delete this favorites list" alt="Delete List" />
					<p id="displayList_shareInfoLine">This list is currently <c:if test="${not KualiForm.selectedFavHeader.customerFavShareInd}"><strong>not</strong>&nbsp;</c:if>being shared.
						<c:choose>
							<c:when test="${KualiForm.selectedFavHeader.customerFavShareInd}">
								<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-unshare.gif" property="methodToCall.unshareList" value="Unshare this list" title="Unshare this list" alt="Unshare" />							
							</c:when>
							<c:otherwise>
								<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-share.gif" property="methodToCall.shareList" value="Share this list" title="Share this list" alt="Share" />
							</c:otherwise>
						</c:choose>
					</p>
				</c:if>
				<c:if test="${KualiForm.selectedFavHeader.principalName != KualiForm.customerProfile.customer.principalName }">
					<p id="displayList_shareInfoLine">This list is shared by ${KualiForm.selectedFavHeader.principalName }.</p>
				</c:if>
			</c:if>				
		</div>		
		<div id="favoritesDetail_container">
			<c:forEach var="detail" items="${KualiForm.favoriteDetails}" varStatus="rowCounter" >
				<div class="favoritesDetail_display">
					<shopping:itemPreview catalogItem="${detail.catalogItem}" showItemControls="${detail.catalogItem.validAddToCart}" />
				</div>
				<div class="favoritesDetail_delete">
					<c:if test="${KualiForm.selectedFavHeader.principalName eq KualiForm.customerProfile.customer.principalName }">
						<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-delete.gif" property="methodToCall.deleteDetail.line${detail.customerFavDetailId}" value="Delete from list" title="Delete from list" alt="Delete" />
					</c:if>
				</div>
			</c:forEach>
		</div>		
	</div>	
	<script type="text/javascript">
		var main = document.getElementById("favoritesPage_container");
		main.style.height=document.body.parentNode.scrollHeight + "px";
	</script>
</shopping:page>