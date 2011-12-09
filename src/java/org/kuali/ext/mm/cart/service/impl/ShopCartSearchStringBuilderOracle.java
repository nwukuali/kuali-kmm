/**
 * 
 */
package org.kuali.ext.mm.cart.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.cart.service.ShopCartSearchStringBuilder;

/**
 * @author schneppd
 *
 * This implementation of ShopCartSearchStringBuilder supports Oracle Text Indexing Context/Catsearch grammar.
 *
 */
public class ShopCartSearchStringBuilderOracle implements ShopCartSearchStringBuilder {
    
    private static final String EMPTY_STRING = "";
    private static final String BLANK_SPACE = "";
    
    protected static Set<Character> TRIM_CHARACTERS = new HashSet<Character>();
    static {
        TRIM_CHARACTERS.add(' ');
        TRIM_CHARACTERS.add('-');
        TRIM_CHARACTERS.add('|');        
    }
    
    protected static Map<Character, String> REPLACE_CHARACTERS = new HashMap<Character, String>();
    static {
//        REPLACE_CHARACTERS.put('-', EMPTY_STRING);
        REPLACE_CHARACTERS.put('|', BLANK_SPACE);
        REPLACE_CHARACTERS.put('(', BLANK_SPACE);
        REPLACE_CHARACTERS.put(')', BLANK_SPACE);
        REPLACE_CHARACTERS.put('\"', BLANK_SPACE);
        REPLACE_CHARACTERS.put('\'', BLANK_SPACE);
    }
    
    protected static final Set<String> STOP_WORDS = new HashSet<String>();
    static {
        //Matches Oracle Default English Stoplist
        STOP_WORDS.addAll(Arrays.asList(new String[]{
                "a", "did", "in", "only", "then", "where", 
                "all", "do", "into", "onto", "there", "whether", 
                "almost", "does", "is", "or", "therefore", "which",
                "also", "either", "it", "our", "these", "while",
                "although", "for", "its", "ours", "they", "who",
                "an", "from", "just", "s", "this", "whose",
                "and", "had", "ll", "shall", "those", "why",
                "any", "has", "me", "she", "though", "will",
                "are", "have", "might", "should", "through", "with",
                "as", "having", "Mr", "since", "thus", "would",
                "at", "he", "Mrs", "so", "to", "yet",
                "be", "her", "Ms", "some", "too", "you",
                "because", "here", "my", "still", "until", "your",
                "been", "hers", "no", "such", "ve", "yours",
                "both", "him", "non", "t", "very",
                "but", "his", "nor", "than", "was", 
                "by", "how", "not", "that", "we", 
                "can", "however", "of", "the", "were", 
                "could", "i", "on", "their", "what", 
                "d", "if", "one", "them", "when"}));
    }
    
    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsAllWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsAllWordsString(String currentString, String addKeywords) {        
        String searchString = currentString != null ? currentString : "";
        List<String> tokens = getTokens(addKeywords);
        
        searchString += " ";        
        String word = "";
        for(String token : tokens) {
            word = (canAddWildCard(token) ? token + getWildcard() : token);
            searchString += getAndOperator() + word + " ";             
        }
        
        return trim(searchString, TRIM_CHARACTERS);
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
            word = (canAddWildCard(token) ? token + getWildcard() : token);
            searchString +=  getNotOperator() + word + " ";    
        }
        
        return trim(searchString, TRIM_CHARACTERS);
    }

    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsAnyWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsAnyWordsString(String currentString, String addKeywords) {
        String searchString = currentString != null ? currentString : "";
        List<String> tokens = getTokens(addKeywords);
        searchString += " (";
        
        int count = 1;
        String operator = "";
        String word = "";
        for(String token : tokens) {
            operator = (count++ > 1 ? getOrOperator() : "");
            word = (canAddWildCard(token) ? token + getWildcard() : token);
            searchString +=  operator + word + " ";  
        }
        searchString += ")";
        
        return trim(searchString, TRIM_CHARACTERS);
    }
    
    /**
     * @see org.kuali.ext.mm.cart.service.impl.ShopCartSearchStringBuilder#appendAsExactWordsString(java.lang.String, java.lang.String)
     */
    public String appendAsExactWordsString(String currentString, String addKeywords) {
        String searchString = currentString != null ? currentString : "";
        searchString += formatAsExact(addKeywords);
        return trim(searchString, TRIM_CHARACTERS);
    }
    
    /**
     * Removes leading and trailing spaces and special characters contained in trimCharacterList.
     * If trimCharacterList is null all leading and trail non-alphanumeric characters are removed.
     * 
     * @param searchString
     * @param trimCharacterList
     * @return a trimmed searchString
     */
    private String trim(String searchString, Set<Character> trimCharacterList) {
        if(searchString == null || searchString.isEmpty())
            return "";
        
        Integer lastIdx = searchString.length() - 1;
        
        int start = 0, end = lastIdx;
        if(trimCharacterList != null) {
            while(start <= lastIdx && trimCharacterList.contains(String.valueOf(searchString.charAt(start)))) {
                start++;
            }
            while(start <= end && trimCharacterList.contains(String.valueOf(searchString.charAt(end)))) {
                end--;
            }
        }
        else {
            while(start <= lastIdx && !StringUtils.isAlphanumeric(String.valueOf(searchString.charAt(start)))) {
                start++;
            }
            while(start <= end && !StringUtils.isAlphanumeric(String.valueOf(searchString.charAt(end)))) {
                end--;
            }
        }
                
        return searchString.substring(start, end + 1);
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
        return "| ";
    }
    
    /**
     * Oracle default no-operator is 'and'
     * 
     * @return the 'And' operator for the implemented text indexer grammar
     */
    private String getAndOperator() {
        return "";
    }

    
    /**
     * @param word
     * @return word formatted as exact text for the implemented text indexer grammar
     */
    protected String formatAsExact(String word) {
        return "\"" + word + "\"";
    }
    
    private boolean canAddWildCard(String word) {
        return StringUtils.isAlpha(String.valueOf(word.charAt(word.length()-1))) && word.length() > 2;
    }
    
    private List<String> getTokens(String searchString) {
        List<String> tokenList = new ArrayList<String>();
        if(StringUtils.isNotBlank(searchString)) {
            String trimmedElement = "";
            for(String element : filterInput(searchString).split(" ")) {
                trimmedElement = trim(element, null);
                if(!trimmedElement.isEmpty() 
                        && !STOP_WORDS.contains(trimmedElement.toLowerCase())
                        && ((StringUtils.isNumeric(String.valueOf(trimmedElement.charAt(trimmedElement.length()-1))))
                                || trimmedElement.length() > 2)) {
                    tokenList.add(trimmedElement);
                }                
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
            if(REPLACE_CHARACTERS.containsKey(rawString[i])) {
                filteredString += REPLACE_CHARACTERS.get(rawString[i]);
            }
            else {
                filteredString += rawString[i];
            }
        }
        
        return filteredString;
    }
    
}
