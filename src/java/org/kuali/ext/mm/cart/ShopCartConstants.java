/*
 * Copyright 2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.cart;

import org.kuali.rice.core.util.JSTLConstants;


/**
 * This class is used to define global constants.
 */
public class ShopCartConstants extends JSTLConstants {

	/**
     *
     */
    private static final long serialVersionUID = -4750124353347119996L;

    public static final String PUBLIC_ID = "PublicID";

	public static final String MAPPING_SYSTEM_ERROR = "systemError";

	public static final Integer MAX_RESULTS_PER_PAGE = 15;
	public static final String ACTION_PARM_PAGE = "page";
	public static final String ACTION_PARM_SORT = "sort";
	public static final String ACTION_PARM_QUERY = "query";
	public static final String QUICK_SEARCH_METHOD = "quickSearch";
	public static final String ADVACNED_SEARCH_METHOD = "advancedSearch";
	public static final String ACTION_PARM_CATALOG = "catalogId";
	public static final String ADD_TO_FAVORITES_METHOD = "addToFavorites";
	public static final String START_METHOD = "start";
	public static final String FAVORITES_ID = "favoritesId";
	public static final String FAVORITES_NEW = "new";
	public static final String ACTION_PARM_CONFRIM = "confirm";
	public static final String DELETE_LIST_METHOD = "deleteList";
	public static final String DELETE_SAVED_CART_METHOD = "deleteSavedCart";
	public static final String SAVE_CONFRIM_METHOD = "saveConfirm";
	public static final String PREPARE_TO_SAVE_METHOD = "prepareToSave";
	public static final Object COMPLETE_METHOD = "complete";
	public static final String CONFIRM_ACTION_DECLINE_METHOD = "confirmActionDecline";
	public static final String DELETE_PROFILE_METHOD = "deleteProfile";
	public static final String END_RECURRENCE_CONFRIM_METHOD = "endRecurrenceConfirm";
	public static final String CART_ID = "cartId";
	public static final String SAVED_CART_NAME = "cartName";

	public static final String PROFILE_ACTION = "profile.do";
	public static final String SAVED_CARTS_ACTION = "savedCarts.do";
	public static final String HOME_ACTION = "home.do";
	public static final String VIEW_CART_ACTION = "viewCart.do";
	public static final String FAVORITES_ACTION = "favorites.do";
	public static final String BROWSE_ACTION = "browse.do";
	public static final String BROWSE_CATALOG_ACTION = "browseCatalog.do";
	public static final String ORDER_COMPLETION_ACTION = "orderCompletion.do";
	public static final String ORDER_SUMMARY_ACTION = "orderSummary.do";
	public static final String ORDER_HISTORY_ACTION = "orderHistory.do";
	public static final String PUNCHOUT_ACTION = "punchOut.do";
	public static final String PUNCHOUT_RETURN_ACTION = "punchOutReturn.do";
	
	public static final String ORDER_COMPLETION_SHOW_ALL_PARAM = "showAll";
	
	public static final String ORDER_DOC_ID_PARAMETER = "docId";

	public static final int MAX_ORDER_DOC_RETURN_COUNT = 50;

	public static final String ITEMS_PER_PAGE = "perPage";

	public static class Parameters {
	    public static final String MAX_SHOPPING_RESULT_LIMIT = "MAX_SHOPPING_RESULT_LIMIT";
	    public static final String ALLOW_PERSONAL_USE = "ALLOW_PERSONAL_USE";
	}

	public static final class User {
		public static final String SHOP_GUEST = "shopguest";
	}

	public static final class Permission {
		public static final String PERSONAL_USE_SHOPPING = "Personal Use Shopping";
	}
	
	public static class Sorting {
	    public static final String BEST_SELLING = "bestselling";
	    public static final String RELEVANCE = "relevance";
	    public static final String PRICE_LH = "price_lh";
	    public static final String PRICE_HL = "price_hl";
	}

	public static class Session {
		public static final String ADD_TO_FAVORITES_ITEMS = "addtoFavoriteItems";
		public static final String BROWSE_MANAGER = "browseManager";
		public static final String CUSTOMER_PROFILE = "customerProfile";
		public static final String SESSION_SHOP_CART = "sessionShopCart";
		public static final String CONFIRM_ACTION = "confirmAction";
		public static final String PROFILE_TO_EDIT = "profileToEdit";
		public static final String SHIP_TO_ADDRESS = "shipToAddress";
		public static final String CUSTOMER = "customer";
		public static final String BILLING_ADDRESS = "billingAddress";
		public static final String ORDER_DOCUMENTS = "orderDocuments";
		public static final String SHOW_ORDER_DETAILS_MAP = "showOrderDetailMap";
        public static final String RECURRING_DOC_ID = "recurringDocId";
        public static final String SHOPPING_ERROR_MSG = "shoppingErrorMsg";
        public static final String SHOPPING_ERROR_RETURN_URL = "shoppingErrorReturnUrl";
        public static final String PUNCHOUT_CATALOG_ID = "punchoutCatalogId";
        public static final String PUNCHOUT_RETURN_CART = "punchOutReturnCart";
	}

	public static class Sequence {
		public static final String ADDERSS_ID_SEQ = "MM_ADDRESS_S";
		public static final String ORDER_ID_SEQ = "MM_ORDER_DOC_S";
	}

	public static class CatalogItemSearch {

		public static final String CATALOG_SUBGROUP_ID = "catalogSubgroupId";
		public static final String CATALOG_GROUP_CD = "catalogGroupCd";
		public static final String CATALOG_DESC = "catalogDesc";
		public static final String PRIOR_CATALOG_SUBGROUP_ID = "priorCatalogSubgroupId";
		public static final String DISTRIBUTOR_NBR = "distributorNbr";
		public static final String DISPLAYABLE_IND = "displayableInd";
        public static final String CATALOG_ITEM_ID = "catalogItemId";
        public static final String ORDER_COUNT = "orderCount";
        public static final String CATALOG_PRC = "catalogPrc";
        public static final String PRIORITY_NUMBER = "priorityNbr";
	}

	public static class DirectEntry {
	    public static final String DIRECT_ENTRY = "directEntry";
	    public static final String TRUE_BUYOUT_ENTRY = "trueBuyoutEntry";
		public static final String CATALOG_NUMBER = "catalogNumber";
		public static final String QUANTITY = "quantity";
        public static final String DESCRIPTION = "catalogDescription";
        public static final String CATALOG_ID = "catalogId";
        public static final String UNIT_OF_ISSUE = "unitOfIssue";
	}

	public static class SessionCart {
		public static final String SHIPPING_ADDRESS = "shippingAddress";
		public static final String BILLING_ADDRESS = "billingAddress";
		public static final String CUSTOMER_PROFILE_ID = "customerProfileId";
		public static final String ORDERED_IND = "ordered";
	}

	public static class ProfileForm {
		public static final String PROFILE_TO_EDIT = "profileToEdit";
	}

	public static final class FavoritesForm {
		public static final String NEW_FAVORITES_NAME = "newFavoritesName";
	}
	
	public static final class PunchOut {
	    public static final String RESPONSE_STATUS_SUCCESS = "200";
        public static final String PUNCHOUT_ACTION_METHOD = "punchOut";
        public static final String VENDOR_REQUEST_ACTION = "B2BReceive.do";
	}

}