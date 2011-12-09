package org.kuali.ext.mm.cart.service;

import java.sql.Timestamp;
import java.util.List;

import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.businessobject.ShopCartDetail;
import org.kuali.ext.mm.businessobject.ShoppingCart;
import org.kuali.ext.mm.document.OrderDocument;

public interface ShopCartOrderService {
	/**
	 * Builds and initiates an OrderDocument from a shoppingCart, customerProfile, and shopCartDetails
	 *
	 * @param shoppingCart
	 * @param customerProfile
	 * @param shopCartDetails
	 * @return an initiated OrderDocument for the shoppingCart
	 */
	public OrderDocument createNewOrderDocument(ShoppingCart shoppingCart, Profile customerProfile, List<ShopCartDetail> shopCartDetails);

	/**
	 * Routes the document
	 *
	 * @param document
	 */
	public void routeOrderDocument(OrderDocument document);

	/**
	 * Saves the order document
	 *
	 * @param document
	 */
	public void saveOrderDocument(OrderDocument document);

	/**
	 * @param customerProfile
	 * @return list of OrderDocuments needing to be completed by customer owning customerProfile
	 */
	public List<OrderDocument> getIncompleteOrders(Profile customerProfile);

	/**
	 * @param customerProfile
	 * @param toDate
	 * @param fromDate
	 * @return List of OrderDocuments between toDate and fromDate
	 */
	public List<OrderDocument> getOrderHistory(Profile customerProfile, Timestamp fromDate, Timestamp toDate);

}
