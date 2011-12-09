package org.kuali.ext.mm.cart.web.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.CatalogGroup;

public class BrowseCatalogForm extends StoresShoppingFormBase {

    private List<List<CatalogGroup>> catalogGroupOrganizer = new ArrayList<List<CatalogGroup>>();
    
    private Integer columnCount = 3;
    
	@Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
        
	}

    public List<List<CatalogGroup>> getCatalogGroupOrganizer() {
        return catalogGroupOrganizer;
    }

    public void setCatalogGroupOrganizer(List<List<CatalogGroup>> catalogGroupOrganizer) {
        this.catalogGroupOrganizer = catalogGroupOrganizer;
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }
    
    @Override
    protected boolean requiresProfile() {
        return false;
    }

}
