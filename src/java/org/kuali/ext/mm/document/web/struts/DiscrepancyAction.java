/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DiscrepancyDocument;
import org.kuali.ext.mm.document.DiscrepancyLine;
import org.kuali.ext.mm.sys.batch.service.DiscrepancyLineService;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class DiscrepancyAction extends KualiTransactionalDocumentActionBase{
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiscrepancyForm rForm = (DiscrepancyForm) form;
        DiscrepancyDocument rDoc = (DiscrepancyDocument)rForm.getDocument();     
        List returnList = new ArrayList();
        if(rDoc != null){
            List<DiscrepancyLine> discrepancyLines = rDoc.getDiscrepancyLines();            
            for (int i = 0; i < discrepancyLines.size(); i++){
                DiscrepancyLine discrepancyLine = discrepancyLines.get(i);
                
                if(discrepancyLine.isSelected()){                                        
                    if(MMConstants.DiscrepancyDocument.FROM_ACTION.equalsIgnoreCase(mapping.getPath())){   
                        discrepancyLine.setActive(false);
                    }else{
                        discrepancyLine.setActive(true);
                    }
                    returnList.add(discrepancyLine);
                }
            }
            rDoc.setDiscrepancyLines(returnList);
            rForm.setDocument(rDoc);
        }       
        return super.execute(mapping, form, request, response);
    }
    
    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        ActionForward actionForward = super.docHandler(mapping, form, request, response);
        DiscrepancyForm rForm = (DiscrepancyForm) form;
        DiscrepancyDocument rDoc = (DiscrepancyDocument) rForm.getDocument();
        String documentNumber = rDoc.getDocumentNumber();  
        
        if(MMConstants.DiscrepancyDocument.FROM_ACTION.equalsIgnoreCase(mapping.getPath())){
            rDoc.setReportName(MMConstants.DiscrepancyDocument.OPTION_TRAN_DISCREPANCY);
            rForm.getDocument().getDocumentHeader().setDocumentDescription("Undo Invoice Discrepancy - " +  documentNumber);
        }else{
            //  automatically create document description template                   
            rDoc.setDiscrepancyToDate(getTodaysDateMinusSeven());                 
            rForm.getDocument().getDocumentHeader().setDocumentDescription("Post Invoice Discrepancy - " +  documentNumber);
        }
        return actionForward;
    }
    
    public ActionForward getReconciliationReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {       
        DiscrepancyForm rForm = (DiscrepancyForm) form;
        DiscrepancyDocument rDoc = (DiscrepancyDocument)rForm.getDocument();                      
        if(fieldCheck(rDoc)){
            getReportAndSetReportNumber(mapping, rForm, rDoc);   
        }                  
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    private ActionForm getReportAndSetReportNumber(ActionMapping mapping, DiscrepancyForm rForm, DiscrepancyDocument rDoc){
        String reportName = rDoc.getReportName();
        String vendorName = rDoc.getVendorName();  
        Date fromDate = rDoc.getDiscrepancyFromDate();
        Date toDate   = rDoc.getDiscrepancyToDate();
        
        if(reportName.equalsIgnoreCase(MMConstants.DiscrepancyDocument.OPTION_SUBSTITUTION)){
            rForm.setReportNumber(1);            
            rDoc.setDiscrepancyLines(getSubstitutions(vendorName, fromDate, toDate));    
        } else if(reportName.equalsIgnoreCase(MMConstants.DiscrepancyDocument.OPTION_OVER_SHIPMENT)){
            rForm.setReportNumber(2);
            rDoc.setDiscrepancyLines(getOverShipments(vendorName, fromDate, toDate));        
        } else if(reportName.equalsIgnoreCase(MMConstants.DiscrepancyDocument.OPTION_PRICE_DISCREPANCY)){                
            rForm.setReportNumber(3);        
            rDoc.setDiscrepancyLines(getPriceDiscrepancies(vendorName, fromDate, toDate));   
        } else if(reportName.equalsIgnoreCase(MMConstants.DiscrepancyDocument.OPTION_NO_ORDERMATCH)){
            rForm.setReportNumber(4);            
            rDoc.setDiscrepancyLines(getNoOrderMatches(vendorName, fromDate, toDate));   
        } else if(reportName.equalsIgnoreCase(MMConstants.DiscrepancyDocument.OPTION_TRAN_DISCREPANCY)){
            rForm.setReportNumber(5);
            if(MMConstants.DiscrepancyDocument.FROM_ACTION.equalsIgnoreCase(mapping.getPath())){
                rDoc.setDiscrepancyLines(removePostedInvoiceTransactionDiscrepancies(vendorName, fromDate, toDate));
            }else{
                rDoc.setDiscrepancyLines(getInvoiceTransactionDiscrepancies(vendorName, fromDate, toDate));
            }
        } else{
            rForm.setReportNumber(0);
        }
        
        rForm.setDocument(rDoc);
        return rForm;
    }
    
    // Query the service to get the Results... 
    private List<DiscrepancyLine> getSubstitutions(String vendorName, Date fromDate, Date toDate){
        List<DiscrepancyLine> results = new ArrayList();
        DiscrepancyLine one = new DiscrepancyLine();
        one.setVendorName(vendorName);
        one.setOrderNumber("2345242344");
        one.setDistributorNumber("6342342");
        one.setUnitOfIssueCode("BX");
        one.setItemDescription("This is a test decription");
        one.setQtyOrdered("3");
        one.setQtyShipped("1");
        one.setInvoiceQtyShip("1");
        one.setInvoiceAmt("($17.72)");
        one.setUnitInvoiceAmt("1.3");
        one.setTaxInvoiceAmt("34");
        one.setTotalAmt("34234");
        one.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        one.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        one.setInvoiceNumber("4334");
        results.add(one);
        
        DiscrepancyLine two = new DiscrepancyLine();
        two.setVendorName(vendorName);
        two.setOrderNumber("5242344");
        two.setDistributorNumber("42342");
        two.setUnitOfIssueCode("BX");
        two.setItemDescription("Discrepancies - This is a test decription");
        two.setQtyOrdered("23");
        two.setQtyShipped("31");
        two.setInvoiceQtyShip("31");
        two.setInvoiceAmt("($ 17.72)");
        two.setUnitInvoiceAmt("$ 1.3");
        two.setTaxInvoiceAmt("$ 34");
        two.setTotalAmt("234");
        two.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        two.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        two.setInvoiceNumber("334");
        results.add(two);
        
        return results;        
    }      
    
    // Query the service to get the Results... 
    private List<DiscrepancyLine> getOverShipments(String vendorName, Date fromDate, Date toDate){
        List<DiscrepancyLine> results = new ArrayList();
        DiscrepancyLine one = new DiscrepancyLine();
        one.setVendorName(vendorName);
        one.setOrderNumber("2345242344");
        one.setDistributorNumber("6342342");
        one.setUnitOfIssueCode("BX");
        one.setItemDescription("This is a test decription");
        one.setQtyOrdered("3");
        one.setQtyShipped("1");
        one.setInvoiceQtyShip("1");
        one.setInvoiceAmt("($17.72)");
        one.setUnitInvoiceAmt("1.3");
        one.setTaxInvoiceAmt("34");
        one.setTotalAmt("34234");
        one.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        one.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        one.setInvoiceNumber("4334");
        results.add(one);
        
        DiscrepancyLine two = new DiscrepancyLine();
        two.setVendorName(vendorName);
        two.setOrderNumber("5242344");
        two.setDistributorNumber("42342");
        two.setUnitOfIssueCode("BX");
        two.setItemDescription("Discrepancies - This is a test decription");
        two.setQtyOrdered("23");
        two.setQtyShipped("31");
        two.setInvoiceQtyShip("31");
        two.setInvoiceAmt("($ 17.72)");
        two.setUnitInvoiceAmt("$ 1.3");
        two.setTaxInvoiceAmt("$ 34");
        two.setTotalAmt("234");
        two.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        two.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        two.setInvoiceNumber("334");
        results.add(two);
        
        return results;        
    }
    
    // Query the service to get the Results... 
    private List<DiscrepancyLine> getPriceDiscrepancies(String vendorName, Date fromDate, Date toDate){
        List<DiscrepancyLine> results = new ArrayList();
        DiscrepancyLine one = new DiscrepancyLine();
        one.setVendorName(vendorName);
        one.setOrderNumber("2345242344");
        one.setDistributorNumber("6342342");
        one.setUnitOfIssueCode("BX");
        one.setItemDescription("This is a test decription");
        one.setPoPrice("$ 5.00");
        one.setPercentageDiff("234 %");
        one.setQtyOrdered("3");
        one.setQtyShipped("1");
        one.setInvoiceQtyShip("1");
        one.setInvoiceAmt("($17.72)");
        one.setUnitInvoiceAmt("1.3");
        one.setTaxInvoiceAmt("34");
        one.setTotalAmt("34234");
        one.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        one.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        one.setInvoiceNumber("4334");
        results.add(one);
        
        DiscrepancyLine two = new DiscrepancyLine();
        two.setVendorName(vendorName);
        two.setOrderNumber("5242344");
        two.setDistributorNumber("42342");
        two.setUnitOfIssueCode("BX");
        two.setItemDescription("Discrepancies - This is a test decription");
        two.setQtyOrdered("23");
        two.setQtyShipped("31");
        two.setInvoiceQtyShip("31");
        two.setInvoiceAmt("($ 17.72)");
        two.setUnitInvoiceAmt("$ 1.3");
        two.setTaxInvoiceAmt("$ 34");
        two.setTotalAmt("234");
        two.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        two.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        two.setInvoiceNumber("334");
        results.add(two);
        
        return results;        
    }
    
    // Query the service to get the Results... 
    private List<DiscrepancyLine> getNoOrderMatches(String vendorName, Date fromDate, Date toDate){
        List<DiscrepancyLine> results = new ArrayList();
        DiscrepancyLine one = new DiscrepancyLine();
        one.setVendorName(vendorName);
        one.setOrderNumber("2345242344");
        one.setDistributorNumber("6342342");
        one.setUnitOfIssueCode("BX");
        one.setItemDescription("This is a test decription");
        one.setQtyOrdered("3");
        one.setQtyShipped("1");
        one.setInvoiceQtyShip("1");
        one.setInvoiceAmt("($17.72)");
        one.setUnitInvoiceAmt("1.3");
        one.setTaxInvoiceAmt("34");
        one.setTotalAmt("34234");
        one.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        one.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        one.setInvoiceNumber("4334");
        results.add(one);
        
        DiscrepancyLine two = new DiscrepancyLine();
        two.setVendorName(vendorName);
        two.setOrderNumber("5242344");
        two.setDistributorNumber("42342");
        two.setUnitOfIssueCode("BX");
        two.setItemDescription("Discrepancies - This is a test decription");
        two.setQtyOrdered("23");
        two.setQtyShipped("31");
        two.setInvoiceQtyShip("31");
        two.setInvoiceAmt("($ 17.72)");
        two.setUnitInvoiceAmt("$ 1.3");
        two.setTaxInvoiceAmt("$ 34");
        two.setTotalAmt("234");
        two.setDateShipped(new java.sql.Date(new java.util.Date().getTime()));
        two.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        two.setInvoiceNumber("334");
        results.add(two);
        return results;        
    }
    
    private List<DiscrepancyLine> getInvoiceTransactionDiscrepancies(String vendorName, Date fromDate, Date toDate){
        List<DiscrepancyLine> invoiceTransactionDiscrepancies = new ArrayList();
        DiscrepancyLine one = new DiscrepancyLine();
        one.setVendorName(vendorName);       
        one.setOrderNumber("2345242344");
        one.setInvoiceNumber("4334");
        one.setTransactionAmt("3.45");
        one.setTotalPCardAmt("434.4");
        one.setInvoiceAmt("23");
        one.setDifference("3");    
        
        one.setTransactionDate(new java.sql.Date(new java.util.Date().getTime()));
        one.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));        
        
        DiscrepancyLine two = new DiscrepancyLine();
        two.setVendorName(vendorName);       
        two.setOrderNumber("22344");
        two.setInvoiceNumber("44");
        two.setTransactionAmt("53.45");
        two.setTotalPCardAmt("434.4");
        two.setInvoiceAmt("253");
        two.setDifference("390");           
        two.setTransactionDate(new java.sql.Date(new java.util.Date().getTime()));
        two.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));        
        
        DiscrepancyLine three = new DiscrepancyLine();
        three.setVendorName(vendorName);       
        three.setOrderNumber("6563SRDF");
        three.setInvoiceNumber("544");
        three.setTransactionAmt("73.45");
        three.setTotalPCardAmt("4.4");
        three.setInvoiceAmt("8253");
        three.setDifference("89");    
        three.setTransactionDate(new java.sql.Date(new java.util.Date().getTime()));
        three.setPostedDate(new java.sql.Date(new java.util.Date().getTime()));
        
        invoiceTransactionDiscrepancies.add(one);
        invoiceTransactionDiscrepancies.add(two);
        invoiceTransactionDiscrepancies.add(three);
        
        return removeAlreadySubmitedLines(invoiceTransactionDiscrepancies);
    }
    
    private List<DiscrepancyLine> removeAlreadySubmitedLines(List<DiscrepancyLine> invoiceTransactionDiscrepancies){
        List<DiscrepancyLine> returnList = new ArrayList();
        HashMap<String, String> newHash = null;
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        for (int a = 0; a < invoiceTransactionDiscrepancies.size(); a++) {
            DiscrepancyLine discrepancyLine = invoiceTransactionDiscrepancies.get(a);            
            String orderNumber = discrepancyLine.getOrderNumber();
            String invoiceNumber = discrepancyLine.getInvoiceNumber();            
            newHash = new HashMap<String, String>();
            newHash.put("orderNumber", orderNumber);
            newHash.put("invoiceNumber", invoiceNumber);
             
            // The following code removes the discrepancies, which have been submitted already.
            List<DiscrepancyLine> ddisLines = (List<DiscrepancyLine>) bOS.findMatchingOrderBy(DiscrepancyLine.class, newHash, "discrepancyLineDocNbr", false);            
            if(!ddisLines.isEmpty()){
                for (int i = 0; i < ddisLines.size(); i++) {
                    DiscrepancyLine dL = ddisLines.get(i);
                    if(dL.isActive() != true){
                        returnList.add(discrepancyLine);                      
                    }
                    break;
                }               
            }else{
                returnList.add(discrepancyLine);
            }
        }
        return returnList;        
    }
    
    private List<DiscrepancyLine> removePostedInvoiceTransactionDiscrepancies(String vendorName, Date fromDate, Date toDate){
        DiscrepancyLineService dLS = SpringContext.getBean(DiscrepancyLineService.class);
        return dLS.getDiscrepancyLines(vendorName, fromDate, toDate);
    }
    
    private boolean fieldCheck(DiscrepancyDocument rDoc){
        
        String reportName = rDoc.getReportName();
        String vendorName = rDoc.getVendorName();  
        Date fromDate = rDoc.getDiscrepancyFromDate();
        Date toDate   = rDoc.getDiscrepancyToDate();
        
        boolean errorAbsent = true;
        if(StringUtils.isBlank(reportName)){            
            GlobalVariables.getMessageMap().putErrorForSectionId(MMConstants.DiscrepancyDocument.REPORT_NOT_SELECTED, 
                    MMKeyConstants.DiscrepancyDocument.REPORT_NOT_SELECTED);     
            errorAbsent = false;
        }
        
        if(StringUtils.isBlank(vendorName)){            
            GlobalVariables.getMessageMap().putErrorForSectionId(MMConstants.DiscrepancyDocument.VENDOR_NOT_SELECTED, 
                    MMKeyConstants.DiscrepancyDocument.VENDOR_NOT_SELECTED);
            errorAbsent = false;
        }
        
        if (fromDate != null && toDate != null){
            if (fromDate.after(toDate)){
                GlobalVariables.getMessageMap().putErrorForSectionId(MMConstants.DiscrepancyDocument.FROMDATE_NOT_SELECTED, 
                        MMKeyConstants.DiscrepancyDocument.ERROR_INCORRECT_DATE_RANGE);
                errorAbsent = false;
            }
        }        
    
        return errorAbsent;
    }
    
    private Date getTodaysDateMinusSeven(){       
        Calendar cal = Calendar.getInstance();       
        cal.setTime(new java.sql.Date(new java.util.Date().getTime()));       
        cal.roll(Calendar.DATE, -7); // roll down, subtract 7 days        
        java.util.Date returnDate = cal.getTime();
        return new java.sql.Date(returnDate.getTime());
    }
}
