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
<%@ tag import="org.kuali.ext.mm.businessobject.CatalogItem"%>
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp"%>

<%@ attribute name="orderDocument" required="true" type="org.kuali.ext.mm.document.OrderDocument" %>
<%@ attribute name="orderDocumentPropertyName" required="true" %>
<%@ attribute name="returnUrl" required="true"  %>
<%@ attribute name="showDetails" required="false"  %>

<c:set var="orderAttributes" value="${DataDictionary.OrderDocument.attributes}" />

<div class="orderSummary">
	<span class="highlight_red">${KualiForm.orderMessageMap[orderDocument.documentNumber]}</span>
	<div class="orderSummary_header">	
		<div class="orderHeader_left">
			<p><span class="orderSummary_label">${orderAttributes.orderId.label}: </span>${orderDocument.orderId}</p>	
			<p><span class="orderSummary_label">Order Placed: </span><fmt:formatDate value="${orderDocument.creationDate}" type="date" pattern="dd-MMM-yyyy" /></p>
			<p><span class="orderSummary_label">Order Status: </span>${orderDocument.orderStatus.orderStatusDesc }</p>
			<p><span class="orderSummary_label">Profile: </span>${orderDocument.customerProfile.profileName }</p>		
			<p><span class="orderSummary_label">Order Total: </span>${orderDocument.displayTotal }</p>
			<c:if test="${not KualiForm.externalUser}">
				<p><a class="blue" href="${ConfigProperties.application.url}/order.do?methodToCall=docHandler&docId=${orderDocument.documentNumber}&command=displayDocSearchView&returnToSenderUrl=${returnUrl}" target="_blank">View Internal Document</a></p>
			</c:if>
		</div>	
		<div class="orderHeader_center">
			&nbsp;
		</div>
		<div class="orderHeader_right">
			<c:if test="${orderDocument.orderStatusInitiated and not KualiForm.externalUser}">
				<shopping:buttonLink styleClass="imageButton_vMargin" url="${ConfigProperties.application.url}/order.do?methodToCall=docHandler&docId=${orderDocument.documentNumber}&command=displayDocSearchView&returnToSenderUrl=${returnUrl}" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-completeOrder.gif" title="Complete Order" />
				<br />	
			</c:if>
			<c:if test="${not empty orderDocument.recurringOrder and orderDocument.recurringOrder.active and not empty orderDocument.recurringOrder.nextRecurringDt}">
				<html:image styleClass="imageButton_vMargin" src="${ConfigProperties.kmm.externalizable.images.url}button-endRecurrence.gif" property="methodToCall.endRecurrence.tab${orderDocument.documentNumber}" value="End Recurrence" title="End Recurrence" alt="End Recurrence" />
				<br />
			</c:if>
			<c:choose>
				<c:when test="${showDetails }">&nbsp;</c:when>
				<c:when test="${KualiForm.showOrderDetails[orderDocument.documentNumber]}">
					<html:image styleClass="imageButton_vMargin" src="${ConfigProperties.kmm.externalizable.images.url}button-hideDetails.gif" property="methodToCall.toggleOrderDetails.tab${orderDocument.documentNumber}" value="Hide Details" title="Hide Details" alt="Hide Details" />	
				</c:when>
				<c:otherwise>
					<html:image styleClass="imageButton_vMargin" src="${ConfigProperties.kmm.externalizable.images.url}button-showDetails.gif" property="methodToCall.toggleOrderDetails.tab${orderDocument.documentNumber}" value="Show Details" title="Show Details" alt="Show Details" />	
				</c:otherwise>
			</c:choose>		
		</div>
		<div class="orderHeader_notification">
			<c:if test="${orderDocument.orderStatusInitiated and not KualiForm.externalUser}">
				<p><span class="highlight_red">This order is incomplete ---&gt;</span></p>
			</c:if>
			<c:if test="${not empty orderDocument.recurringOrder}">
				<p><span class="highlight_red">This order recurs <em>${KualiForm.frequencyMap[orderDocument.recurringOrder.timesPerYr] }</em> from ${orderDocument.recurringOrder.startDt}
					<c:if test="${not empty orderDocument.recurringOrder.endDt }">to ${orderDocument.recurringOrder.endDt}</c:if>.
					<c:choose>
						<c:when test="${orderDocument.recurringOrder.active and not empty orderDocument.recurringOrder.nextRecurringDt }">
							<br />The next order will be ${orderDocument.recurringOrder.nextRecurringDt}. 
						</c:when>
						<c:when test="${not orderDocument.recurringOrder.active and not empty orderDocument.recurringOrder.nextRecurringDt}">
							<br />This recurring order has been manually ended.
						</c:when>
						<c:otherwise>
							<br />This recurring order has completed.
						</c:otherwise>
					</c:choose>
				</span></p>
			</c:if>
		</div>
		<div class="clear"></div>	
	</div>
	<c:if test="${showDetails or KualiForm.showOrderDetails[orderDocument.documentNumber]}">
		<div class="orderSummary_detailList">
			<div class="orderSummary_addressInfo">
				<div class="orderSummary_shippingAddress">
					<cart:address title="Shipping Address" id="orderSummary_ShipAddress${orderDocument.documentNumber}" address="${orderDocument.shippingAddress}" addressPropertyName="${orderDocumentPropertyName}.shippingAddress" readOnly="${true}" />
				</div>
				<div class="orderSummary_billingAddress">
					<cart:address title="Billing Address" id="orderSummary_BillAddress${orderDocument.documentNumber}" address="${orderDocument.billingAddress}" addressPropertyName="${orderDocumentPropertyName}.billingAddress" readOnly="${true}" />
				</div>
				<div class="clear"></div>
			</div>
			<table cellpadding="0" cellspacing="0" class="orderDetailTable" title="Order Detail" summary="These are the items in your order">
				<tr>			
					<th class="cartItemHeader" width="70%" colspan="2"><span class="margin_left">Detail</span></th>
					<th class="cartItemHeader" width="10%">U/I</th>
					<th class="cartItemHeader" width="7%">Qty.</th>
					<th class="cartItemHeader" width="10%">Total</th>			
				</tr>				
				<c:forEach var="detail" items="${orderDocument.orderDetails}" varStatus="rowCounter" >
					<c:set var="cartItemRowClass" value="cartItemRow_odd" />
					<c:if test="${(rowCounter.count mod 2) eq 0}">
						<c:set var="cartItemRowClass" value="cartItemRow_even" />
					</c:if>					
					<tr class="${cartItemRowClass}" style="vertical-align:top;">
						<order:orderDetail orderDetail="${detail}" />
					</tr>
				</c:forEach>
			</table>		
		</div>		
	</c:if>
</div>
