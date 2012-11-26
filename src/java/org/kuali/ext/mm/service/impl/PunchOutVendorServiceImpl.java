package org.kuali.ext.mm.service.impl;

import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.service.B2BPunchOutService;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.ext.mm.service.PunchOutVendorService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.TransactionalServiceUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



/**
 * @author schneppd
 *
 */
public class PunchOutVendorServiceImpl implements PunchOutVendorService {
    
    private BusinessObjectService businessObjectService;

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /**
     * @see org.kuali.ext.mm.service.PunchOutVendorService#getPunchOutVendorByCatalogId(java.lang.String)
     */
    public PunchOutVendor getPunchOutVendorByCatalogId(String catalogId) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.Catalog.CATALOG_ID, catalogId);
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        
        Collection<PunchOutVendor> results = getBusinessObjectService().findMatching(PunchOutVendor.class, fieldValues);
        
        return TransactionalServiceUtils.retrieveFirstAndExhaustIterator(results.iterator());
    }

    /**
     * @see org.kuali.ext.mm.service.PunchOutVendorService#getPunchOutVendorByVendorCredentials(java.lang.String, java.lang.String)
     */
    public PunchOutVendor getPunchOutVendorByVendorCredentials(String vendorCredentialDomain, String vendorIdentity) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.PunchOutVendor.VENDOR_CREDENTIAL_DOMAIN, vendorCredentialDomain);
        fieldValues.put(MMConstants.PunchOutVendor.VENDOR_IDENTITY, vendorIdentity);
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        
        Collection<PunchOutVendor> results = getBusinessObjectService().findMatching(PunchOutVendor.class, fieldValues);
        
        return TransactionalServiceUtils.retrieveFirstAndExhaustIterator(results.iterator());
    }

    /**
     * @see org.kuali.ext.mm.service.PunchOutVendorService#getDefaultPunchOutCatalogItem(org.kuali.ext.mm.businessobject.PunchOutVendor)
     */
    public CatalogItem getDefaultPunchOutCatalogItem(PunchOutVendor punchOutVendor) {
        if(punchOutVendor == null)
            return null;
        
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.CatalogItem.CATALOG_ID, punchOutVendor.getCatalogId());
        fieldValues.put(MMConstants.CatalogItem.DIST_NUMBER, MMConstants.CatalogItem.GENERIC_DUMMY_DISTRIBUTOR_NBR);

        Collection<CatalogItem> results = getBusinessObjectService().findMatching(CatalogItem.class, fieldValues);
        CatalogItem defaultCatalogItem = TransactionalServiceUtils.retrieveFirstAndExhaustIterator(results.iterator());
        
        if(defaultCatalogItem == null & ObjectUtils.isNotNull(punchOutVendor.getCatalog())) {
            B2BPunchOutService punchOutService = MMServiceLocator.getB2BPunchOutService();                
            defaultCatalogItem = punchOutService.createPunchOutCatalogItem(punchOutVendor.getCatalog());
            defaultCatalogItem.setStock(null);
            getBusinessObjectService().save(defaultCatalogItem);
        }
        
        return defaultCatalogItem;
    }
}
