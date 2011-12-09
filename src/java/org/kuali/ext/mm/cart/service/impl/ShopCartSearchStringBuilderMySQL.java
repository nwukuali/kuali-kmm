/**
 * 
 */
package org.kuali.ext.mm.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.cart.service.ShopCartSearchStringBuilder;

/**
 * @author schneppd
 *
 * This implementation of ShopCartSearchStringBuilder supports MySQL Full-Text Indexing grammar.
 *
 */
public class ShopCartSearchStringBuilderMySQL implements ShopCartSearchStringBuilder {
    
    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsAllWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsAllWordsString(String currentString, String addKeywords) {        
        String searchString = currentString != null ? currentString : "";
        List<String> tokens = getTokens(addKeywords);
        
        searchString += " ";        
        String word = "";
        for(String token : tokens) {
            word = (token.length() > 2 ? token + getWildcard() : token);
            searchString += getAndOperator() + word + " ";             
        }
        
        return searchString.trim();
    }

    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsNotWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsNotWordsString(String currentString, String addKeywords) {
        String searchString = currentString != null ? currentString : "";
        List<String> tokens = getTokens(addKeywords);        
        
        searchString += " ";
        String word = "";
        for(String token : tokens) {
            word = (token.length() > 2 ? token + getWildcard() : token);
            searchString +=  getNotOperator() + word + " ";    
        }
        
        return searchString.trim();
    }

    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsAnyWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsAnyWordsString(String currentString, String addKeywords) {
        String searchString = currentString != null ? currentString : "";
        List<String> tokens = getTokens(addKeywords);
        
        searchString += " ";        
        String word = "";
        for(String token : tokens) {
            word = (token.length() > 2 ? token + getWildcard() : token);
            searchString += getOrOperator() + word + " ";             
        }
        
        return searchString.trim();
    }
    
    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsExactWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsExactWordsString(String currentString, String addKeywords) {
        String searchString = currentString != null ? currentString : "";
        searchString += formatAsExact(addKeywords);
        return searchString.trim();
    }
    
    /**
     * @return the wildcard character for the implemented text indexer grammar
     */
    protected String getWildcard() {
        return "*";
    }
    
    /**
     * @return the 'Not' operator for the implemented text indexer grammar
     */
    protected String getNotOperator() {
        return "- ";
    }
    
    /**
     * @return the 'Or' operator for the implemented text indexer grammar
     */
    protected String getOrOperator() {
        return "";
    }
    
    /**
     * Oracle default no-operator is 'and'
     * 
     * @return the 'And' operator for the implemented text indexer grammar
     */
    private String getAndOperator() {
        return "+";
    }

    
    /**
     * @param word
     * @return word formatted as exact text for the implemented text indexer grammar
     */
    protected String formatAsExact(String word) {
        return "\"" + word + "\"";
    }
    
    private List<String> getTokens(String searchString) {
        List<String> tokenList = new ArrayList<String>();
        if(StringUtils.isNotBlank(searchString)) {
            for(String element : filterInput(searchString).trim().split(" ")) {
                tokenList.add(element);
            }
        }
        return tokenList;
    }
    
    
    /**
     * @param currentString
     * @return a string in which all non-alphanumeric, non-space characters have been removed.
     */
    protected String filterInput(String currentString) {
        String filteredString = "";
        char[] rawString = currentString.toCharArray();
        
        for(int i=0; i < rawString.length; i++) {
            if(StringUtils.isAlphanumericSpace(String.valueOf(rawString[i]))) {
                filteredString += rawString[i];
            }
        }
        
        return filteredString;
    }
}
