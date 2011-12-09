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

<div id="checkout_controls">
	<a class="plain" href='${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start'>
		<img class="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-cancel.gif" alt="Cancel" title="Cancel Checkout" border="0" />
	</a>
	<c:choose>
		<c:when test="${KualiForm.validated}">
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-change.gif" property="methodToCall.changeUserInfo" value="Change" title="Change" alt="Change" />
			<c:if test="${KualiForm.allItemsAuthorized}">
				<c:choose>
					<c:when test="${KualiForm.checkOutAsProfile.personalUseIndicator}">
						<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-placeOrder.gif" property="methodToCall.placeOrder" value="Place Order" title="Place Order" alt="Place Order" />
					</c:when>
					<c:otherwise>
						<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-createOrder.gif" property="methodToCall.placeOrder" value="Create Order" title="Create Order" alt="Create Order" />
					</c:otherwise>
				</c:choose>	
			</c:if>						
		</c:when>
		<c:otherwise>
		<c:if test="${KualiForm.allItemsAuthorized}">
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-continue.gif" property="methodToCall.submitUserInfo" value="Continue" title="Continue" alt="Continue" />
		</c:if>
		</c:otherwise>
	</c:choose>			
</div>			