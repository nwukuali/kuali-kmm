
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>

<c:set var="shopCartAttributes" value="${DataDictionary.ShoppingCart.attributes}" />

<shopping:page title="Load Cart" htmlFormAction="savedCarts">
	<div class="sidePanel_container">
		<customer:savedCartList savedCartList="${KualiForm.savedCarts}" />
	</div>
	<div id="loadCart_container">
		<h3>My Saved Shopping Carts</h3>
		<c:choose>
			<c:when test="${empty KualiForm.cartToLoad }">
				<c:choose>
					<c:when test="${empty KualiForm.savedCarts }">
						<p class="margin_left">You have no saved carts to load.</p>
					</c:when>
					<c:otherwise>
						<p class="margin_left">To view and load a previously saved cart, select a saved cart from the Saved Carts list on the left.</p>
					</c:otherwise>
				</c:choose>				
				<br />
				<div id="loadCart_controls_left">
<!--					<shopping:buttonLink title="Return to My Cart" url="${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-cancel.gif" />-->
				</div>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${KualiForm.savingShopCart}">
						<shopping:infoEntry title="Save Shopping Cart" id="saveCart_container" canHide="false">
							<p class="entryData">Please enter a name for your cart:
								<br />
								<kul:htmlControlAttribute property="saveShopCartName" attributeEntry="${shopCartAttributes.shopCartHeaderName}" />
								<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-save.gif" property="methodToCall.saveCart" alt="Save Cart" value="Save Cart" title="Save Cart" />
								<shopping:buttonLink title="Return to My Cart" url="${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-cancel.gif" />
							</p>
						</shopping:infoEntry>
					</c:when>
					<c:otherwise>
						<p class="margin_left" style="margin-right:1em;"><strong>Note:</strong>  Loading a saved cart will replace any items in your current shopping cart.  If you would like to retain the current cart please return to the previous page and save your cart first.</p>
						<br />
<!--						<div id="loadCart_controls_left">-->
<%--							<shopping:buttonLink title="Load Cart" url="${ConfigProperties.application.url}/mm/savedCarts.do?methodToCall=loadCart&cartId=${KualiForm.cartToLoad.shoppingCartId}" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-loadCart.gif" />--%>
<%--							<shopping:buttonLink title="Return to My Cart" url="${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start" imageSource="_blank" />--%>
<!--						</div>-->
						<div id="loadCart_controls_right">
							<shopping:buttonLink title="Delete Cart" url="${ConfigProperties.application.url}/mm/savedCarts.do?methodToCall=deleteSavedCart&cartId=${KualiForm.cartToLoad.shoppingCartId}" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-delete.gif" />
							<shopping:buttonLink title="Load Cart" url="${ConfigProperties.application.url}/mm/savedCarts.do?methodToCall=loadCart&cartId=${KualiForm.cartToLoad.shoppingCartId}" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-loadCart.gif" />
						</div>
					</c:otherwise>					
				</c:choose>			
				<div id="loadCart_shoppingCartView">
					<cart:shoppingCartView shoppingCart="${KualiForm.cartToLoad}" shoppingCartPropertyName="cartToLoad" showControls="false" />
				</div>
			</c:otherwise>
		</c:choose>		
	</div>	
	<script type="text/javascript">
		var main = document.getElementById("loadCart_container");
		main.style.height=document.body.parentNode.scrollHeight + "px";
	</script>
</shopping:page>