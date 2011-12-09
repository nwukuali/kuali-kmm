package org.kuali.ext.mm.cart.web.struts;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CustomerFavHeader;
import org.kuali.ext.mm.cart.service.ShopCartServiceLocator;
import org.kuali.ext.mm.cart.valueobject.DirectEntry;


public class ViewCartForm extends ShopCartFormBase {
	/**
     *
     */
    private static final long serialVersionUID = 4214317045474377223L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ViewCartForm.class);

	private DirectEntry directEntry;
	
	private DirectEntry trueBuyoutEntry;
	
	private boolean showTrueBuyout;
	
	private boolean showDirectEntry;

	private String formMessage;

	public ViewCartForm() {
		super();
		setDirectEntry(new DirectEntry());
		setTrueBuyoutEntry(new DirectEntry());
	}

	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);

        if(getDirectEntry().getQuantity() == null)
        	getDirectEntry().setQuantity("1");
        
        if(getTrueBuyoutEntry().getQuantity() == null)
            getTrueBuyoutEntry().setQuantity("1");

        setFormMessage(StringUtils.EMPTY);

    }

	public void setDirectEntry(DirectEntry directEntry) {
		this.directEntry = directEntry;
	}

	public DirectEntry getDirectEntry() {
		return directEntry;
	}

	public Collection<CustomerFavHeader> getAvailableFavorites() {
		return ShopCartServiceLocator.getShopCartFavoriteService().getFavoritesByCustomerId(getCustomer().getPrincipalName(), false);
	}

	public String getFormMessage() {
		return this.formMessage;
	}

	public void setFormMessage(String formMessage) {
		this.formMessage = formMessage;
	}

    public DirectEntry getTrueBuyoutEntry() {
        return trueBuyoutEntry;
    }

    public void setTrueBuyoutEntry(DirectEntry trueBuyoutEntry) {
        this.trueBuyoutEntry = trueBuyoutEntry;
    }

    public boolean isShowTrueBuyout() {
        return showTrueBuyout;
    }

    public void setShowTrueBuyout(boolean showTrueBuyout) {
        this.showTrueBuyout = showTrueBuyout;
    }

    public boolean isShowDirectEntry() {
        return showDirectEntry;
    }

    public void setShowDirectEntry(boolean showDirectEntry) {
        this.showDirectEntry = showDirectEntry;
    }


}
