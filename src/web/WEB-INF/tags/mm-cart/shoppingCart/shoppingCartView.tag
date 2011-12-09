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
<%@ attribute name="shoppingCart" required="true" type="org.kuali.ext.mm.businessobject.ShoppingCart" %>
<%@ attribute name="shoppingCartPropertyName" required="true" %>
<%@ attribute name="showControls" required="true" %>
<%@ attribute name="editMode" required="false" %>
<%@ attribute name="showTaxAndTotal" required="false" %>
<%@ attribute name="displayName" required="false" %>

<c:if test="${empty editMode }" >
	<c:set var="editMode" value="${false}" />
</c:if>

<div id="shoppingCart_container">	
	<c:if test="${not KualiForm.allItemsAuthorized}"><p><strong class="highlight_red">* Some of the items in your cart are not authorized for this profile, please select a different profile before checking out.</strong></p></c:if>
	<p class="shoppingCartName">
		<c:choose>
			<c:when test="${not empty shoppingCart.shopCartHeaderName}" >
				${shoppingCart.shopCartHeaderName}
			</c:when>
			<c:when test="${not empty displayName }">
				${displayName}
			</c:when>
			<c:otherwise>					
				Current Cart (unsaved)
			</c:otherwise>				
		</c:choose>			
		<c:if test="${showControls and not shoppingCart.sessionCartSaved}" >
			<c:choose>
				<c:when test="${fn:length(shoppingCart.cxmlPayloadIdMap) > 0}">
					<br /><span class="highlight_red">This cart contains punchout items and cannot be saved.</span>				
				</c:when>
				<c:otherwise>
					<a class="plain" href='${ConfigProperties.application.url}/mm/savedCarts.do?methodToCall=prepareToSave'>
						<img class="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-save.gif" alt="Save Cart" title="Save Cart" border="0" />
					</a>
				</c:otherwise>
			</c:choose>
		</c:if>		
	</p>
	<table cellpadding="0" cellspacing="0" class="cartItemTable" title="Shopping Cart Items" summary="These are the items currently in your shopping cart">
		<tr>			
			<th class="cartItemHeader" width="65%" colspan="2">&nbsp;&nbsp;Items in your cart</th>
			<th class="cartItemHeader" width="6%">&nbsp;U/I</th>
			<th class="cartItemHeader" width="7%">Qty.</th>
			<th class="cartItemHeader_price" width="11%">Add'l Cost</th>
			<th class="cartItemHeader_price" width="11%">Item Price</th>			
		</tr>
		<c:choose>
			<c:when test="${not empty shoppingCart.shopCartDetails}">
			<c:forEach var="cartDetail" items="${shoppingCart.shopCartDetails}" varStatus="rowCounter" >
				<c:set var="cartItemRowClass" value="cartItemRow_odd" />
				<c:if test="${(rowCounter.count mod 2) eq 0}">
					<c:set var="cartItemRowClass" value="cartItemRow_even" />
				</c:if>
				<c:set var="inactive_class" value="" />
				<c:if test="${not cartDetail.active}">
					<c:set var="inactive_class" value="cartItemTop_inactive" />
				</c:if>
				<tr class="${cartItemRowClass}">				
					<td class="cartItemData ${inactive_class }" width="3%">
						<c:choose>
							<c:when test="${showControls}">
								<html:multibox property="selectedItems" value="${cartDetail.shopCartLineNbr}" disabled="${not cartDetail.active}" />
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose> 	
					</td>					
					<td class="cartItemData ${inactive_class }">		
						<p>
							<c:if test="${not KualiForm.itemAuthorizationMap[cartDetail.shopCartLineNbr]}"><strong class="highlight_red">*</strong></c:if>
							Item #: <c:if test="${cartDetail.active and not cartDetail.fromPunchOut and not cartDetail.trueBuyout}">
										<a href="item.do?methodToCall=start&itemId=${cartDetail.catalogItem.catalogItemId}" >
									</c:if>
									${cartDetail.distributorNumber}
									<c:if test="${cartDetail.active and not cartDetail.fromPunchOut and not cartDetail.trueBuyout}">
										</a>
									</c:if>
							<c:if test="${cartDetail.willcallInd}"><strong style="margin-left:4em;">Warehouse Pickup Required</strong></c:if>
							<c:if test="${cartDetail.fromPunchOut}"><strong style="margin-left:4em;">Punch Out Item</strong></c:if>
							<c:if test="${cartDetail.trueBuyout}"><strong style="margin-left:4em;">True Buyout Item</strong></c:if>
							<c:if test="${cartDetail.catalogItem.stock.rental}"><strong style="margin-left:4em;">Rental Item</strong></c:if>
						</p>		
						<c:if test="${not cartDetail.active }">	
							<div style="position:relative;float:left;width:100%;background-color:transparent;text-align:left;font-size:16pt;font-weight:bold;color:#000000">This Item Is No Longer Available</div>
						</c:if>			
						<p>${cartDetail.shopCartItemDetailDesc }</p>						
					</td>					
					<td class="cartItemData ${inactive_class }">${cartDetail.itemUnitOfIssueCd}</td>
					<td class="cartItemData ${inactive_class }"><html:text property="${shoppingCartPropertyName}.shopCartDetails[${rowCounter.count-1}].shopCartItemQty" size="4" alt="quantity" readonly="${not editMode or not cartDetail.active}" /></td>
					<td class="cartItemData_price ${inactive_class }">${cartDetail.shopCartItemAdditionalCostAmt}</td>
					<td class="cartItemData_price ${inactive_class }">${cartDetail.shopCartItemPriceAmt}</td>				
				</tr>
				
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="5">
					<p class="all_margins">Your shopping cart is currently empty.</p>
				</td
			></tr>
		</c:otherwise>
		</c:choose>
	</table>
	<div>
		<div id="subTotal" class="cartTotals">
			<div class="cartTotals_label">Sub-total:&nbsp;</div>
			<div class="cartTotals_data">${KualiForm.subTotal}</div>
			<div class="clear"></div>
		</div>
		<c:if test="${showTaxAndTotal }">
			<div class="cartTotals">
				<div class="cartTotals_label">Tax:&nbsp;</div>
				<div class="cartTotals_data">${KualiForm.taxTotal}</div>
				<div class="clear"></div>
			</div>
			<div class="cartTotals">
				<div class="cartTotals_label">Total:&nbsp;</div>
				<div class="cartTotals_data">${KualiForm.total}</div>
				<div class="clear"></div>
			</div>
		</c:if>
		<c:if test="${showControls}" >
			<div id="itemControls">				
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-delete.gif" property="methodToCall.deleteItem" value="Delete" title="delete" alt="Delete"/>
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToFavorites.gif" property="methodToCall.addToFavorites" value="Add to Favorites" title="add to favorites" alt="Add to Favorites"/>
			</div>
		</c:if>
	</div>
	<c:if test="${showControls}" >
		<div id="cartControls">
			<c:choose>
				<c:when test="${not editMode}">
					<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-editCart.gif" property="methodToCall.editItems" value="Edit Cart" title="edit cart" alt="Edit Cart"/>
				</c:when>
				<c:otherwise>
					<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-updateCart.gif" property="methodToCall.updateItems" value="Update Cart" title="update cart" alt="Update Cart"/>
				</c:otherwise>
			</c:choose>	
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-clear.gif" property="methodToCall.clearCart" value="Clear Cart" title="clear cart" alt="Clear Cart"/>
			<shopping:buttonLink title="Checkout" url="${ConfigProperties.application.url}/mm/checkout.do?methodToCall=start" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-checkout.gif" />		
		</div>
	</c:if>
</div>	
