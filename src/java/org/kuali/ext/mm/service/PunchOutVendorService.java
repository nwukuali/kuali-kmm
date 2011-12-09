package org.kuali.ext.mm.service;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.PunchOutVendor;



public interface PunchOutVendorService {

    /**
     * Gets the PunchOutVendor for the catalogId
     * 
     * @param catalogId
     * @return a PunchOutVendor object
     */
    public PunchOutVendor getPunchOutVendorByCatalogId(String catalogId);
    
    /**
     * Gets the PunchOutVendor with matching the domain and identity credentials.
     * 
     * @param vendorCredentialDomain
     * @param vendorIdentity
     * @return a PunchOutVendor object
     */
    public PunchOutVendor getPunchOutVendorByVendorCredentials(String vendorCredentialDomain, String vendorIdentity);
    
    /**
     * Gets the default (dummy) catalogItem for the PunchOutVendor.  
     * 
     * @param punchOutVendor
     * @return a CatalogItem object
     */
    public CatalogItem getDefaultPunchOutCatalogItem(PunchOutVendor punchOutVendor);
    
}
