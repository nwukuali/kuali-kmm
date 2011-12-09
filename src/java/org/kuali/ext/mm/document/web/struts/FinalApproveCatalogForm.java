/**
 *
 */
package org.kuali.ext.mm.document.web.struts;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogPendingHelper;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.CatalogPending;
import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.kns.web.ui.ExtraButton;


/**
 * @author rshrivas
 *
 */
public class FinalApproveCatalogForm extends KualiTransactionalDocumentFormBase{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public FinalApproveCatalogForm(){
        super();
        setDocument(new CatalogPending());
        setDocTypeName("SUPC");
    }

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }

    public CatalogPending getCatalogPending() {
        return (CatalogPending) getDocument();       
    }

    private ExtraButton createPrintButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.printSummaryReport");
        printButton.setExtraButtonSource(imageUrl+"Summary_Report.gif");
        printButton.setExtraButtonAltText("Summary Report");
        return printButton;
    }

    private ExtraButton catalogAdditionsReportButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.catalogAdditionsReport");
        printButton.setExtraButtonSource(imageUrl+"CatalogAdditionsReport.gif");
     //           + "}CatalogAdditionsReport.gif");
        printButton.setExtraButtonAltText("Catalog Additions Report");
        return printButton;
    }

    private ExtraButton catalogDeletionsReportButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.catalogDeletionsReport");
      //  printButton.setExtraButtonSource("${" + MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY
        printButton.setExtraButtonSource(imageUrl+"CatalogDeletionsReport.gif");
        printButton.setExtraButtonAltText("Catalog Deletions Report");
        return printButton;
    }

    private ExtraButton priceIncreaseReportButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.priceIncreaseReport");
   //     printButton.setExtraButtonSource("${" + MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY
        printButton.setExtraButtonSource(imageUrl+"PrintIncreaseReport.gif");
        printButton.setExtraButtonAltText("Price Increase Report");
        return printButton;
    }

    private ExtraButton priceDecreaseReportButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.priceDecreaseReport");
        printButton.setExtraButtonSource(imageUrl+"PrintDecreaseReport.gif");
        printButton.setExtraButtonAltText("Price Decrease Report");
        return printButton;
    }

    private ExtraButton fivePercentOrLessReportButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.fivePercentOrLessReport");
        printButton.setExtraButtonSource(imageUrl+"FiveorLessReport.gif");
        printButton.setExtraButtonAltText("Five Percent Or Less Report");
        return printButton;
    }
    private ExtraButton initiateFinalApprovalButton(String imageUrl) {
        ExtraButton printButton = new ExtraButton();
        printButton.setExtraButtonProperty("methodToCall.initiateFinalApproval");
        printButton.setExtraButtonSource(imageUrl+"buttonsmall_approve.gif");
        printButton.setExtraButtonAltText("Approve Catalog");
        return printButton;
    }

    @Override
    public List<ExtraButton> getExtraButtons() {
        String fDocNbr = this.getDocId();
        String krImageUrl = SpringContext.getBean(KualiConfigurationService.class).getPropertyString(KFSConstants.RICE_EXTERNALIZABLE_IMAGES_URL_KEY);
        String imageUrl = KNSServiceLocator.getKualiConfigurationService().getPropertyString(MMConstants.EXTERNALIZABLE_IMAGES_URL_KEY);
        CatalogItemPendingQueryService cIPQS = SpringContext.getBean(CatalogItemPendingQueryService.class);
        CatalogPendingHelper catalog = getCatalogFromCatalogPendingDocObject(fDocNbr);
        String documentNumber = cIPQS.getPreviousCatalogTimeStamp(catalog.getCatalogCd(), fDocNbr);
        extraButtons.clear();
        extraButtons.add(createPrintButton(imageUrl));
        if(!StringUtils.isBlank(documentNumber)){
            extraButtons.add(catalogAdditionsReportButton(imageUrl));
            extraButtons.add(catalogDeletionsReportButton(imageUrl));
            extraButtons.add(priceIncreaseReportButton(imageUrl));
            extraButtons.add(priceDecreaseReportButton(imageUrl));
            extraButtons.add(fivePercentOrLessReportButton(imageUrl));
        }
        if("UPLOAD".equalsIgnoreCase(catalog.getCatalogUploadStatus())){
            extraButtons.add(initiateFinalApprovalButton(krImageUrl));            
        }
        return extraButtons;
    }

    @Override
    public void addRequiredNonEditableProperties() {
        super.addRequiredNonEditableProperties();
        registerRequiredNonEditableProperty("printSummaryReport");
    }

    public boolean shouldMethodToCallParameterBeUsed
        (String methodToCallParameterName, String methodToCallParameterValue, HttpServletRequest request) {
        return true;
    }
    
    private CatalogPendingHelper getCatalogFromCatalogPendingDocObject (String catalogPendingDocNbr){
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        HashMap<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("documentNumber", catalogPendingDocNbr);
        CatalogPendingHelper catalog = (CatalogPendingHelper) bOS.findByPrimaryKey(CatalogPendingHelper.class, primaryKeys);
        return catalog;
    }

}
