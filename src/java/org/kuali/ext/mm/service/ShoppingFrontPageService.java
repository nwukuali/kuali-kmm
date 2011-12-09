/**
 * 
 */
package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.ShoppingFrontPage;


/**
 * @author schneppd
 *
 */
public interface ShoppingFrontPageService {

    /**
     * Sets frontPage to be the current ShoppingFrontPage to be displayed
     * on the shopping portal home page.  Also, if frontPage was not previously
     * the current ShoppingFrontPage, the previous one is set to current = false
     * 
     * @param frontPage
     */
    public void setAsCurrentFrontPage(ShoppingFrontPage frontPage);

    /**
     * @return the current ShoppingFrontPage object 
     */
    public ShoppingFrontPage getCurrentShoppingFrontPage();

}