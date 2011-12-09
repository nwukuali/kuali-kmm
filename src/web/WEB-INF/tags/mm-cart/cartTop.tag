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
<c:set var="displayName" value="${customer.customerName}" />
<c:if test="${empty displayName}">
	<c:set var="displayName" value="${customer.principalName}" />
</c:if>
<div id="topBanner">
	<div id="topBanner_left">
<!--		<div id="logo">-->
<!--			<a href='${ConfigProperties.application.url}/mm/home.do?methodToCall=start'>-->
<!--				<img src="${ConfigProperties.kmm.externalizable.images.url}USBanner_new.jpg" alt="University Stores Logo" title="Go to University Stores Home" border="0" height="130px" />-->
<!--			</a>-->
<!--		</div>-->
		<div id="title_container">
			<a class="plain" href='${ConfigProperties.application.url}/mm/home.do?methodToCall=start'>
				<p class="title"><span style="color:#EEEEEE">Kmm</span>&nbsp;|&nbsp;<span style="color:#EEEEEE">S</span>hopping&nbsp;<span style="color:#EEEEEE">P</span>ortal</p>
			</a>
		</div>
	</div>
	<div id="topBanner_right">
		<c:choose>			
			<c:when test="${customerProfile ne null }">							
				<div id="userControls">
					<p>
						<a href='${ConfigProperties.application.url}/mm/favorites.do?methodToCall=start'>Favorites</a>
						&nbsp;<span style="color:#666666">|</span>&nbsp;
						<a href='${ConfigProperties.application.url}/mm/settings.do'>My Account</a>
						<c:if test="${KualiForm.orderCompletionWaiting}">
							<img src="${ConfigProperties.kmm.externalizable.images.url}warning.gif" alt="notification" title="You have orders that need completion" border="0" height="15px" width="15px" />
						</c:if>						
						&nbsp;<span style="color:#666666">|</span>&nbsp;
						<a href='${ConfigProperties.application.url}/mm/logout.do'>Log Out</a>
					</p>					
				</div>
				<div id="left_corner_userControls">&nbsp;</div>	
				<div class="clear"></div>
				<p class="topBannerInfo">Logged in as ${displayName }&nbsp;</p>
				<div id="currentProfile_notification">Shopping with profile <a class="white" href='${ConfigProperties.application.url}/mm/profile.do?methodToCall=start&profileId=${customerProfile.profileId}'>${customerProfile.profileName}</a></div>			
				<div id="shoppingCart_thumbnail">
					<a class="white" href='${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start' title="Click to checkout">
						<img src="${ConfigProperties.kmm.externalizable.images.url}shopping-cart3.gif" alt="A shopping cart" height="40" width="40" border="0" align="right" />
					</a>
					<div class="shoppingCart_summary">						
						<a class="white" href='${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start' title="Click to view cart">
							View My Cart (<span id="itemCount">${KualiForm.cartItemCount }</span>)				
						</a>
					</div>
					<div class="clear"></div>
				</div>
			</c:when>
			<c:when test="${customer ne null}">
				<div id="userControls">
					<p>
						<a href='${ConfigProperties.application.url}/mm/profile.do?methodToCall=start'>Create Profile</a>
						&nbsp;<span style="color:#666666">|</span>&nbsp;
						<a href='${ConfigProperties.application.url}/mm/logout.do'>Log Out</a>
					</p>
				</div>		
				<div id="left_corner_userControls">&nbsp;</div>
				<div class="clear"> </div>
				<p class="topBannerInfo">Logged in as ${displayName}&nbsp;</p>
			</c:when>
			<c:otherwise>
				<div id="userControls">
					<p>
						<a href='${ConfigProperties.application.url}/mm/login.do?methodToCall=start'>Log In</a>&nbsp;
						<c:if test="${KualiForm.allowsPersonalUse }">
							<span style="color:#666666">|</span>&nbsp;
							<a href='${ConfigProperties.application.url}/mm/register.do?methodToCall=start'>New Customer</a>
						</c:if>
					</p>
				</div> 
				<div id="left_corner_userControls">&nbsp;</div>					
			</c:otherwise>
		</c:choose>
	</div>
	<div class="clear"></div>	
</div>
