/**
 * 
 */
package org.kuali.ext.mm.cart.valueobject;


/**
 * @author schneppd
 *
 */
public class PagingElement {
    
    private int pageNumber;
    private int resultsPerPage;
    private int pageCount;
    
    /**
     * @param pageNumber
     * @param resultsPerPage
     * @param pageCount
     */
    public PagingElement(int pageNumber, int resultsPerPage, int pageCount) {        
        this.pageNumber = pageNumber;
        this.resultsPerPage = resultsPerPage;
        this.pageCount = pageCount;
    }
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
    public int getResultsPerPage() {
        return resultsPerPage;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public int getPageCount() {
        return pageCount;
    }

   
    
    
}
