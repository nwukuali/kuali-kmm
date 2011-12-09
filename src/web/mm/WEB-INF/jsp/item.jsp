<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="itemAttributes" value="${DataDictionary.CatalogItem.attributes}" />
<c:set var="catalogAttributes" value="${DataDictionary.Catalog.attributes}" />

<shopping:page title="UStores : Item# - ${KualiForm.displayItem.distributorNbr}" htmlFormAction="item">
<div id="displayItem_container">
	<div id="displayItem_breadcrumb_container">
		<shopping:breadcrumb breadcrumbList="${KualiForm.browseManager.breadcrumbs}" readOnlyItemBreadcrumb="${KualiForm.displayItem.distributorNbr}" />
	</div>
	<c:if test="${KualiForm.displayItem ne null }">
		<html:hidden property="displayItem.catalogItemId" />
		<div id="itemInfo_wrapper">
			<div id="itemSummary">				
				<c:choose>
					<c:when test="${KualiForm.displayImage ne null}">
						<img id="itemImage" src="${KualiForm.displayImage.catalogImagePath}" height="200" width="200" alt="${KualiForm.displayImage.catalogImageName}" align="left" />
					</c:when>
					<c:otherwise>
						<img id="itemImage" src="images/noimage.jpg" height="200" width="200" alt="No image available" align="left" />	
					</c:otherwise>
				</c:choose>			
				<p id="itemNumber">
					<span class="itemLabel"><kul:htmlAttributeLabel attributeEntry="${itemAttributes.distributorNbr}" useShortLabel="true"/></span>
					<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.distributorNbr" attributeEntry="${itemAttributes.distributorNbr}" readOnly="${true}" />
					<span class="catalogName_small">&nbsp;(<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.catalog.catalogDesc" attributeEntry="${catalogAttributes.catalogDesc}" readOnly="${true}" />)</span>
				</p>						
				<p>					
					<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.catalogDesc" attributeEntry="${itemAttributes.catalogDesc}" readOnly="${true}" />
				</p>
				
				<p id="purchaseDetails">					
					<span class="itemLabel"><kul:htmlAttributeLabel attributeEntry="${itemAttributes.catalogUnitOfIssueCd}" useShortLabel="true"/></span>
					<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.salesUnitOfIssue.unitOfIssueDesc" attributeEntry="${itemAttributes.salesUnitOfIssue.unitOfIssueDesc}" readOnly="${true}" />
					&nbsp;&nbsp;&nbsp;
					<c:if test="${not empty KualiForm.customerProfile }">
						<c:choose>
							<c:when test="${KualiForm.displayItem.stock.rental}">
								<span class="itemLabel">Price/Day: $${KualiForm.displayItem.stock.rentalObject.dailyDemurragePrice}</span>
								<br />
								<span class="itemLabel">Deposit: $${KualiForm.displayItem.stock.rentalObject.depositPrice}</span>
								<br />
								<c:if test="${KualiForm.itemPrice > 0}">
									<span class="itemLabel">Cost Of Goods (non-deposit): $${KualiForm.itemPrice}</span>
									<br />
								</c:if>
								<span class="highlight_red">Rental Item</span>
							</c:when>
							<c:otherwise>
								<span class="itemLabel">Price: ${KualiForm.itemPrice}</span>
							</c:otherwise>
						</c:choose>
					</c:if>					
					<br />
					<br />
					<c:if test="${KualiForm.warehouseItem}">
						<span class="itemDetail_notice">
							<c:set var="QtyOnHand" value="${KualiForm.quantityOnHand}" />
							<c:if test="${QtyOnHand > 0}" >In Stock:&nbsp</c:if>
							<c:if test="${QtyOnHand <= 0}" >Out of Stock</c:if>
						</span>
						<c:if test="${QtyOnHand > 0}" >						
							${QtyOnHand} Remaining.
						</c:if>
					</c:if>
					<c:if test="${KualiForm.displayItem.willcallInd}">
						<br />
						<span class="highlight_red">Pick-up Only</span>
					</c:if>
					<c:if test="${KualiForm.displayItem.recycledInd}">
						<br />
						<span class="highlight_green">This item is Recycled.</span>
					</c:if>
				</p>			
			</div>
			<div id="imagesDisplay">
				<c:forEach var="imageLink" items="${KualiForm.displayItem.catalogItemImages}" varStatus="rowCounter" >
					<c:if test="${imageLink.catalogImage.catalogImageId ne KualiForm.displayImage.catalogImageId }">
						<a href="${imageLink.catalogImage.catalogImagePath}" target="_blank"><img class="imageThumb" src="${imageLink.catalogImage.catalogImagePath}" height="75" width="75" alt="${imageLink.catalogImage.catalogImageName }" /></a>
					</c:if>
				</c:forEach>
			</div>					
			<div id="itemDetails" >
				<h5>Product Details</h5>
				<ul id="detailList">
					<li>
						<kul:htmlAttributeLabel attributeEntry="${itemAttributes.manufacturerNbr}" useShortLabel="true"/>
						<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.manufacturerNbr" attributeEntry="${itemAttributes.manufacturerNbr}" readOnly="${true}" />
					</li>								
					<li>
						<kul:htmlAttributeLabel attributeEntry="${itemAttributes.shippingWgt}" useShortLabel="true"/>
						<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.shippingWgt" attributeEntry="${itemAttributes.shippingWgt}" readOnly="${true}" />
					</li>
					<li>
						<kul:htmlAttributeLabel attributeEntry="${itemAttributes.shippingLh}" useShortLabel="true"/>
						<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.shippingLh" attributeEntry="${itemAttributes.shippingLh}" readOnly="${true}" />
					</li>
					<li>
						<kul:htmlAttributeLabel attributeEntry="${itemAttributes.shippingWd}" useShortLabel="true"/>
						<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.shippingWd" attributeEntry="${itemAttributes.shippingWd}" readOnly="${true}" />
					</li>
					<li>
						<kul:htmlAttributeLabel attributeEntry="${itemAttributes.shippingHt}" useShortLabel="true"/>
						<kul:htmlControlAttribute styleClass="itemDetail" property="displayItem.shippingHt" attributeEntry="${itemAttributes.shippingHt}" readOnly="${true}" />
					</li>
				</ul>	
			</div>
		</div>		
		<div id="addToCart">
			<div id="addToCart_top"></div>
			<div id="addToCart_center">
				<c:choose>
					<c:when test="${empty KualiForm.customerProfile }">
						<p class="inCartNotice" >To view an item's price or add it to your cart you must be logged in.</p>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${KualiForm.displayItem.validAddToCart}">
								<c:if test="${KualiForm.itemInCart}">
									<p class="inCartNotice" >This item is already in your cart.</p>
								</c:if>
								<p id="quantityField">
									<span class="dataEntryLabel">Qty:</span>
									<html:text property="addQuantity" size="2" alt="Add Quantity" maxlength="8" />
								</p>
								<p>
									<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToCart.gif" property="methodToCall.addToCart" value="Add to Cart" title="Add to Cart" alt="Add to Cart" />
								</p>
								<%--
								<c:if test="${KualiForm.itemInCart}">
									<p style="margin-top:.25em;">
										<a class="blue" href="${ConfigProperties.application.url}/mm/viewCart.do?methodToCall=start" >Go to My Cart</a>
									</p>
								</c:if>
								 --%>
							</c:when>
							<c:otherwise>
								<p class="inCartNotice" >Due to a stocking discrepancy this item cannot currently be added to the cart.</p>
							</c:otherwise>
						</c:choose>			
				
						<p id="addToFavorites" >
							<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-addToFavorites.gif" property="methodToCall.addToFavorites" value="Add to Cart" title="Add to favorites" alt="Add to Favorites" />
						</p>
					</c:otherwise>
				</c:choose>							
			</div>
			<div id="addToCart_bottom"></div>
		</div>
	</c:if>	
</div>
</shopping:page>