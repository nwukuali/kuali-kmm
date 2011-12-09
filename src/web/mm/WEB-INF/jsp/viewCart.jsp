<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<shopping:page title="My Shopping Cart" htmlFormAction="viewCart">	
	<cart:cartSidePanel />	
	<div id="viewCart_container">
		<h3>My Shopping Cart</h3>
		<c:if test="${not empty KualiForm.formMessage }">
			<div class="margin_left"><span class="highlight_red">${KualiForm.formMessage}</span></div>
		</c:if>	
		<div class="errors_top_wide"><kul:errors keyMatch="*"/></div>
		<html:hidden property="showDirectEntry" />	
		<cart:directEntry />
		<c:if test="${not empty KualiForm.browseManager.trueBuyoutCatalogs }">
			<html:hidden property="showTrueBuyout" />
			<cart:trueBuyoutEntry />
		</c:if>
		<br />
		<div class="margin_left">
			<cart:shoppingCartView shoppingCart="${KualiForm.sessionCart}" shoppingCartPropertyName="sessionCart" showControls="${fn:length(KualiForm.sessionCart.shopCartDetails) > 0}" editMode="${KualiForm.editMode}"/>
		</div>
	</div>
	<script type="text/javascript">
		var main = document.getElementById("viewCart_container");
		main.style.height=document.body.parentNode.scrollHeight + "px";
	</script>
</shopping:page>