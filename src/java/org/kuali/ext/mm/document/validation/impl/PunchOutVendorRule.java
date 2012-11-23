package org.kuali.ext.mm.document.validation.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;


public class PunchOutVendorRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = super.processCustomRouteDocumentBusinessRules(document);

        PunchOutVendor newVal = (PunchOutVendor) document.getNewMaintainableObject().getBusinessObject();
        PunchOutVendor oldVal = (PunchOutVendor) document.getOldMaintainableObject().getBusinessObject();

        PunchOutVendor existing = MMServiceLocator.getPunchOutVendorService()
            .getPunchOutVendorByVendorCredentials(newVal.getVendorCredentialDomain(), newVal.getVendorIdentity());

        if (document.isNew() && existing != null) {
            GlobalVariables.getMessageMap().putError(
                    KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.PunchOutVendor.VENDOR_IDENTITY,
                    MMKeyConstants.PunchOutVendor.ERROR_VENDOR_EXISTS, 
                    newVal.getVendorCredentialDomain(),
                    newVal.getVendorIdentity());
            valid = false;
        }

        if (document.isEdit()
                && (!newVal.getVendorIdentity().equalsIgnoreCase(oldVal.getVendorIdentity()) 
                        || !newVal.getVendorCredentialDomain().equalsIgnoreCase(
                                oldVal.getVendorCredentialDomain()))
                && existing != null) {
            GlobalVariables.getMessageMap().putError(
                    KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.PunchOutVendor.VENDOR_IDENTITY,
                    MMKeyConstants.PunchOutVendor.ERROR_VENDOR_EXISTS, 
                    newVal.getVendorCredentialDomain(),
                    newVal.getVendorIdentity());
            valid = false;
        }
        
        if(MMConstants.CatalogType.PUNCHOUT.equals(newVal.getCatalog().getCatalogTypeCd())
                && StringUtils.isBlank(newVal.getPunchOutUrl())) {
            String errorLabel = getDataDictionaryService().getAttributeErrorLabel(PunchOutVendor.class, MMConstants.PunchOutVendor.PUNCH_OUT_URL);
            GlobalVariables.getMessageMap().putError(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE + MMConstants.PunchOutVendor.PUNCH_OUT_URL, MMKeyConstants.PunchOutVendor.ERROR_PUNCHOUT_URL_BLANK, errorLabel);
            valid = false;
        }

        return valid;
    }
}
