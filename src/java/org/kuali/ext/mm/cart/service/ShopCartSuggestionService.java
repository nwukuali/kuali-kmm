/**
 * 
 */
package org.kuali.ext.mm.cart.service;

import java.util.List;


/**
 * @author schneppd
 *
 */
public interface ShopCartSuggestionService {

    public List<String> performSearch(String searchPrefx, String searchTerm, Integer resultsLimit);

}