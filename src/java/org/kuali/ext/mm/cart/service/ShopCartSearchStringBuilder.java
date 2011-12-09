/**
 * 
 */
package org.kuali.ext.mm.cart.service;


/**
 * @author schneppd
 *
 */
public interface ShopCartSearchStringBuilder {

    public String appendAsAllWordsString(String currentString, String addKeywords);

    public String appendAsNotWordsString(String currentString, String addKeywords);

    public String appendAsAnyWordsString(String currentString, String addKeywords);

    public String appendAsExactWordsString(String currentString, String addKeywords);

}