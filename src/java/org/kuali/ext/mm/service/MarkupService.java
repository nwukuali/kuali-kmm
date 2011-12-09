package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.util.MMDecimal;

public interface MarkupService {

	/**
	 * Gets the best applying markup given the parameters.  If profile is null
	 * only markups that do not consider profile attributes will be considered.
	 * 
	 * @param item
	 * @param profile
	 * @param quantity
	 * @param ignorePersonalUse
	 * @return Markup determined to be the "best" for item with the given profile and quantity
	 */
	public Markup getMarkup(CatalogItem item, Profile profile, Integer quantity, boolean ignorePersonalUse);
	    
    /**
     * @param item
     * @param profile
     * @param quantity
     * @return Markup determined to be the "best" for item with the given profile and quantity
     */
    public Markup getMarkupByType(String markupTypeCode, CatalogItem item, Profile profile, Integer quantity);

	/**
	 * Applies either the fixed amount or the rate of markup to the price of item.
	 *
	 * @param markup
	 * @param item
	 * @return The Markup applied price of item.
	 */
	public MMDecimal applyMarkup(Markup markup, CatalogItem item);

}
