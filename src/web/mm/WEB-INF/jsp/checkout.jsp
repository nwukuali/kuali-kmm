<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<shopping:page title="Checkout" htmlFormAction="checkout">
	
	<c:if test="${not KualiForm.validated }">
		<h3>Checkout : <span class="highlight">Enter User Information</span>&nbsp;&nbsp;>&nbsp;&nbsp;Confirmation</h3>
	</c:if>
	<c:if test="${KualiForm.validated }">
		<h3>Checkout : Enter User Information > <span class="highlight">Confirmation</span></h3>		
	</c:if>	
	<div id="checkout_container">
		<br />
		<div class="margin_left">			
			<c:if test="${KualiForm.forceWillCall}">
				<div class="highlight_red">*All personal use customers must visit the University Stores showroom to pay for and pick up their orders.</div>
			</c:if>
			<div class="left-errmsg-tab"><kul:errors keyMatch="*"/></div>
		</div>
		<c:choose>
			<c:when test="${KualiForm.validated and KualiForm.sessionCart.ordered }" >
				<div id="orderPlaced_notification">Your order has been submitted. </div>				
			</c:when>
			<c:otherwise>
				<cart:checkoutControls />
			</c:otherwise>
		</c:choose>
		<div id="checkoutAs">
			<html:hidden property="profileId" />
			<span class="entryLabel">Profile: </span>
			<c:choose>
				<c:when test="${KualiForm.validated }">
					${KualiForm.checkOutAsProfile.profileName }
					<html:hidden property="checkOutAsProfile.profileId" />
				</c:when>
				<c:otherwise>
					<html:select style="width:14em" property="checkOutAsProfile.profileId" onchange="submit()" disabled="${KualiForm.validated}" >
					<html:option value="${KualiForm.customerProfile.profileId}">${KualiForm.customerProfile.profileName} (Current)</html:option>
					<c:forEach var="profile" items="${KualiForm.availableProfiles}" varStatus="rowCounter" >
						<c:if test="${profile.profileId ne KualiForm.customerProfile.profileId }">
							<html:option value="${profile.profileId}">${profile.profileName}</html:option>
						</c:if>
					</c:forEach>
				</html:select>
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-reset.gif" property="methodToCall.resetAll" value="Reset" title="Reset" alt="Reset" />
				</c:otherwise>
			</c:choose>				
		</div>
		<div id="shippingAddress" >			
			<p>&nbsp;</p>
			<cart:address title="Shipping Address" id="checkout_ShipAddress" address="${KualiForm.sessionCart.shippingAddress}" addressPropertyName="sessionCart.shippingAddress" readOnly="${KualiForm.validated}" />			
		</div>
		<div id="billingAddress" >
			<p><html:checkbox property="sameAsShipping" disabled="${KualiForm.validated }" />&nbsp;Same As Shipping</p>			
			<cart:address title="Billing Address" id="checkout_BillAddress" address="${KualiForm.sessionCart.billingAddress}" addressPropertyName="sessionCart.billingAddress" readOnly="${KualiForm.validated}" />
		</div>
		<div class="clear"> </div>
		
<%--		<cart:payment readOnly="${KualiForm.validated}" /> --%>
		<br />
		<c:if test="${KualiForm.validated }">	
			<cart:shoppingCartView shoppingCart="${KualiForm.sessionCart}" shoppingCartPropertyName="sessionCart" showControls="false" showTaxAndTotal="${KualiForm.validated}"/>	
		</c:if>		

		<c:if test="${!KualiForm.sessionCart.ordered }" >
			<cart:checkoutControls />
		</c:if>
	</div>		

</shopping:page>