<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="settings">
	<div class="sidePanel_container" id="settings_sidePanel" >
		<div id="currentProfile_controls" class="browseBox">
			<h4>Current Profile</h4>	
			<p style="margin-top:1em; margin-bottom:.5em;">
				<strong>Currently shopping with profile:</strong>
			</p>
			<html:select style="margin-bottom:.75em;width:14em" property="currentProfile.profileId" >
				<html:option value="${KualiForm.customerProfile.profileId}">${KualiForm.customerProfile.profileName}</html:option>
				<c:forEach var="profile" items="${KualiForm.availableProfiles}" varStatus="rowCounter" >
					<c:if test="${profile.profileId ne KualiForm.customerProfile.profileId }">
						<html:option value="${profile.profileId}">${profile.profileName}</html:option>
					</c:if>
				</c:forEach>
			</html:select>				
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-change.gif" property="methodToCall.changeProfile" value="Change" title="Change" alt="Change" />
		</div>
	</div>
	<div id="settings_container">
		<h3>My Account</h3>
		
		<div class="settings_menu_row">
			<div id="profile_menu" class="settings_menu">
				<h5>Customer Information</h5>
				<ul class="settings_list">
					<li class="settings_list_item" ><a href='${ConfigProperties.application.url}/mm/profile.do?methodToCall=start'>Manage Profiles</a></li>
					<li class="settings_list_item"><a href='${ConfigProperties.application.url}/mm/favorites.do?methodToCall=start'>My Favorites</a></li>
					<li class="settings_list_item"><a href='${ConfigProperties.application.url}/mm/savedCarts.do?methodToCall=start'>Saved Carts</a></li>
				</ul>			
			</div>
			<div id="order_menu" class="settings_menu">
				<h5>Orders</h5>
				<ul class="settings_list">
					<li class="settings_list_item"><a href='${ConfigProperties.application.url}/mm/orderHistory.do?methodToCall=start'>Order History</a></li>
					<c:if test="${KualiForm.orderCompletionWaiting}">					
						<li class="settings_list_item">
							<a href='${ConfigProperties.application.url}/mm/orderCompletion.do?methodToCall=complete&showAll=true'>Incomplete Orders</a>
							<img src="${ConfigProperties.kmm.externalizable.images.url}warning.gif" alt="notification" title="You have orders that need completion" border="0" height="15px" width="15px" />
						</li>
					</c:if>
<!--				<li class="settings_list_item"><a href='${ConfigProperties.application.url}/mm/recuring.do?methodToCall=start'>Recurring Orders</a></li>-->
				</ul>
			</div>
		</div>
<!--		<div class="settings_menu_row">-->
<!--			<div id="commonLinks_menu" class="settings_menu">-->
<!--				<h5>Common links</h5>-->
<!--				<ul class="settings_list">-->
<!--					-->
<!--				</ul>-->
<!--			</div>-->
<!--		</div>-->
	</div>

</shopping:page>