/**
 *
 */
package org.kuali.ext.mm.document.web.struts;

import static org.kuali.ext.mm.common.sys.MMConstants.LF;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.CatalogPending;
import org.kuali.ext.mm.sys.batch.service.CatalogItemPendingQueryService;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.service.DocumentService;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author rshrivas
 */
public class FinalApproveCatalogAction extends KualiDocumentActionBase {

    public ActionForward initiateFinalApproval(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        FinalApproveCatalogForm cAndFApproveCatalogForm = (FinalApproveCatalogForm) form;
        String documentNumber = cAndFApproveCatalogForm.getDocId();
        CatalogPending cP = (CatalogPending) cAndFApproveCatalogForm.getDocument();
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(documentNumber);
        if (catalogPending != null) {
            cP.setDocumentNumber(documentNumber);
            cP.setDocumentHeader(catalogPending.getDocumentHeader());
            cP.setAgreementNbr(catalogPending.getAgreementNbr());
            cP.setPriorityNbr(catalogPending.getPriorityNbr());
            cP.setCatalogCd(catalogPending.getCatalogCd());
            cP.setCatalogBeginDt(catalogPending.getCatalogBeginDt());
            cP.setCatalogEndDt(catalogPending.getCatalogEndDt());
            cP.setCatalogTimestamp(catalogPending.getCatalogTimestamp());
            cP.setCatalogDesc(catalogPending.getCatalogDesc());
            cP.setBatchLog(catalogPending.getBatchLog());
        }
        CatalogItemPendingQueryService cIPQS = SpringContext.getBean(CatalogItemPendingQueryService.class);
        cIPQS.updateCatalogPendingDoc(documentNumber, "UPLOADING");
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }

    public ActionForward getReports(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String documentNumber = request.getParameter("docId");
        FinalApproveCatalogForm cAndFApproveCatalogForm = (FinalApproveCatalogForm) form;
        CatalogPending cP = (CatalogPending) cAndFApproveCatalogForm.getDocument();
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(documentNumber);
        if (catalogPending != null) {
            cP.setDocumentNumber(documentNumber);
            cP.setDocumentHeader(catalogPending.getDocumentHeader());
            cP.setAgreementNbr(catalogPending.getAgreementNbr());
            cP.setPriorityNbr(catalogPending.getPriorityNbr());
            cP.setCatalogCd(catalogPending.getCatalogCd());
            cP.setCatalogBeginDt(catalogPending.getCatalogBeginDt());
            cP.setCatalogEndDt(catalogPending.getCatalogEndDt());
            cP.setCatalogTimestamp(catalogPending.getCatalogTimestamp());
            cP.setCatalogDesc(catalogPending.getCatalogDesc());
            cP.setBatchLog(catalogPending.getBatchLog());
        }
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    
    @SuppressWarnings("unchecked")
    public ActionForward printSummaryReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        CatalogItemPendingQueryService cIPQS = SpringContext.getBean(CatalogItemPendingQueryService.class); 
        String documentNumber = request.getParameter("docId");
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(documentNumber);
        SimpleDateFormat sf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, oStream);
        document.open();
        document.add(new Paragraph("Summary Report for Catalog - " + catalogPending.getBatchLog() +
                " uploaded on " +  catalogPending.getCatalogTimestamp(), new Font(16)));
        document.add(new Paragraph(""));
        StringBuffer content = new StringBuffer();
        String fileName = catalogPending.getBatchLog().split("\\.")[0] + "-Summary.pdf";
              
        document.add(new Paragraph("    Catalog Code: " + catalogPending.getCatalogCd(), new Font(10)));
        document.add(new Paragraph("    Catalog Begin Date: " + catalogPending.getCatalogBeginDt(), new Font(10)));
        document.add(new Paragraph("    Catalog End Date: " + catalogPending.getCatalogEndDt(), new Font(10)));
        document.add(new Paragraph("    Catalog TimeStamp: " +  sf.format(catalogPending.getCatalogTimestamp()), new Font(10)));
        document.add(new Paragraph("    Catalog Description: " + catalogPending.getCatalogDesc(), new Font(10)));
        document.add(new Paragraph("    Agreement Number: " + catalogPending.getAgreementNbr(), new Font(10)));
        document.add(new Paragraph("    Priority Number: " + catalogPending.getPriorityNbr(), new Font(10)));
        Integer newCatalogCount = cIPQS.getItemCount(documentNumber);
        String newCatalogTotalPrc = cIPQS.getTotalCatalogPrc(documentNumber);
        document.add(new Paragraph("    Total Items Uploaded (Current Count): " + newCatalogCount, new Font(12)));      
        document.add(new Paragraph("    Total Current Pricing: $ " + newCatalogTotalPrc, new Font(10)));
        
        String fDocNbr2 = cIPQS.getPreviousCatalogTimeStamp(catalogPending.getCatalogCd(), documentNumber);       
        Integer previousCatalogCount = cIPQS.getItemCount(fDocNbr2);        
        String previousCatalogTotalPrc = cIPQS.getTotalCatalogPrc(fDocNbr2);
        if(previousCatalogCount!=0){
            CatalogPending previousCPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr2);
            document.add(new Paragraph("Comparision with previous Catalog - " + previousCPending.getBatchLog() + " uploaded " + previousCPending.getCatalogTimestamp(), new Font(16)));
            document.add(new Paragraph("    Total Items in previously Uploaded Catalog (Previous Count): " + previousCatalogCount, new Font(10)));
            document.add(new Paragraph("    Total Previous Pricing: $ " + previousCatalogTotalPrc, new Font(10)));
            double fluctuation = (newCatalogCount.doubleValue()/previousCatalogCount.doubleValue());
            if(fluctuation  > 1){
                document.add(new Paragraph("    Percentage Increase in Items contained in both Catalogs: " + fluctuation+" %", new Font(10)));
            }else{
                document.add(new Paragraph("    Percentage Decrease in Items contained in both Catalogs: " + fluctuation+" %", new Font(10)));
            }
        }
            
        // Items Added
        document.add(new Paragraph(""));
        Collection<CatalogItemPending> additionsCollection = getCurrentAndPreviousCatalogItemPendingCollection(documentNumber, "CatalogAdditionsReport");
        document.add(new Paragraph("    Items Added: " + additionsCollection.size(), new Font(10)));
        double itemsAddedValues = 0;
        for (Iterator iterator = additionsCollection.iterator(); iterator.hasNext();) {
            CatalogItemPending catalogItemPending = (CatalogItemPending) iterator.next();
            itemsAddedValues = itemsAddedValues + catalogItemPending.getCatalogPrc().doubleValue();
        }
        document.add(new Paragraph("    Items Added Value: $ " + itemsAddedValues, new Font(10)));
        
        // Items Deleted
        document.add(new Paragraph(""));
        Collection<CatalogItemPending> deletionsCollection = getCurrentAndPreviousCatalogItemPendingCollection(documentNumber, "CatalogDeletionsReport");
        document.add(new Paragraph("    Items Deleted: " + deletionsCollection.size(), new Font(10)));
        double itemsDeletedValue = 0;
        for (Iterator iterator = deletionsCollection.iterator(); iterator.hasNext();) {
            CatalogItemPending catalogItemPending = (CatalogItemPending) iterator.next();
            itemsDeletedValue = itemsDeletedValue + catalogItemPending.getCatalogPrc().doubleValue();
        }
        document.add(new Paragraph("    Items Deleted Value: $ " + itemsDeletedValue, new Font(10)));        
        document.add(new Paragraph("Overall Impact", new Font(16)));     
        
        if(!previousCatalogTotalPrc.isEmpty() && !previousCatalogTotalPrc.equals("null")){
            double priceDiff = Double.parseDouble(newCatalogTotalPrc)/Double.parseDouble(previousCatalogTotalPrc);
            if(priceDiff  > 1){
            document.add(new Paragraph("    Percentage Pricing Increase between Current and Previous:  " + priceDiff+" %", new Font(10)));
            }else{
            document.add(new Paragraph("    Percentage Pricing Decrease between Current and Previous:  " + priceDiff+" %", new Font(10)));
            }
        }
        document.close();
        byte[] bs = content.toString().getBytes();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/pdf", oStream, fileName);
        return (null);
    }

    public ActionForward catalogAdditionsReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fDocNbr  = request.getParameter("docId");
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr);
        StringBuffer content = new StringBuffer();
        content.append("Catalog Additions Report for - " + catalogPending.getBatchLog() + " uploaded on " +  catalogPending.getCatalogTimestamp() +  LF );
        String fileName = catalogPending.getBatchLog().split("\\.")[0] + "-CatalogAdditionsReport.csv";
        Collection<CatalogItemPending> cIPCollection = getCurrentAndPreviousCatalogItemPendingCollection(fDocNbr, "CatalogAdditionsReport");
        if (!cIPCollection.isEmpty()) {
            content.append("The following " + cIPCollection.size() + " Items are new in this Catalog" + LF );
            content.append("Distributor Number, Manufacturer Number, Unspsc Code, "+
            		"UI Code, Spaid Id, Catalog Price, Recycled, Tax, Description" + LF );
            writeDataToCsv(content, cIPCollection, "catalogAdditionsReport");
        }

        byte[] bs = content.toString().getBytes();
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/csv", oStream, fileName);
        return (null);
    }

    public ActionForward catalogDeletionsReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fDocNbr  = request.getParameter("docId");
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr);
        StringBuffer content = new StringBuffer();
        content.append("Catalog Deletions Report for - " + catalogPending.getBatchLog() + " uploaded on " +  catalogPending.getCatalogTimestamp() + "" + LF );
        String fileName = catalogPending.getBatchLog().split("\\.")[0] + "-CatalogDeletionsReport.csv";
        Collection<CatalogItemPending> cIPCollection = getCurrentAndPreviousCatalogItemPendingCollection(fDocNbr, "CatalogDeletionsReport");
        if (!cIPCollection.isEmpty()) {
           content.append("The following " + cIPCollection.size() + " Items were not present in this Catalog" + LF );
           content.append("Distributor Number, Manufacturer Number, Unspsc Code, " +
           "UI Code, Spaid Id, Catalog Price, Recycled, Tax, Description" + LF );
           writeDataToCsv(content, cIPCollection, "catalogDeletionsReport");
        }
        byte[] bs = content.toString().getBytes();
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/csv", oStream, fileName);
        return (null);
    }

    public ActionForward priceIncreaseReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fDocNbr  = request.getParameter("docId");
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr);
        StringBuffer content = new StringBuffer();
        content.append("Price Increase Report for - " + catalogPending.getBatchLog() + " uploaded on " +  catalogPending.getCatalogTimestamp() + "" + LF );
        String fileName = catalogPending.getBatchLog().split("\\.")[0] + "-PriceIncreaseReport.csv";
        Collection<CatalogItemPending> cIPCollection = getCurrentAndPreviousCatalogItemPendingCollection(fDocNbr, "PriceIncreaseReport");
        if (!cIPCollection.isEmpty()) {
           content.append("The following " + cIPCollection.size() + " Items in this Catalog had a Price Increase" + LF );
           content.append("Distributor Number, Manufacturer Number, Unspsc Code, "+
           "UI Code, Spaid Id, Catalog Price, Old Catalog Price, % Increase, Recycled, Tax, Description" + LF );
           writeDataToCsv(content, cIPCollection, "priceIncreaseReport");
        }
        byte[] bs = content.toString().getBytes();
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/csv", oStream, fileName);
        return (null);
    }

    public ActionForward priceDecreaseReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fDocNbr  = request.getParameter("docId");
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr);
        StringBuffer content = new StringBuffer();
        content.append("Price Decrease Report for - " + catalogPending.getBatchLog() + " uploaded on " +  catalogPending.getCatalogTimestamp() + "" + LF );
        String fileName = catalogPending.getBatchLog().split("\\.")[0] + "-PriceDecreaseReport.csv";
        Collection<CatalogItemPending> cIPCollection = getCurrentAndPreviousCatalogItemPendingCollection(fDocNbr, "PriceDecreaseReport");

        if (!cIPCollection.isEmpty()) {
            content.append("The following " + cIPCollection.size() + " Items in this Catalog had a Price Decrease" + LF );
            content.append("Distributor Number, Manufacturer Number, Unspsc Code, "+
            "UI Code, Spaid Id, Current Catalog Price, Old Catalog Price, % Decrease, Recycled, Tax, Description" + LF );
            writeDataToCsv(content, cIPCollection, "priceDecreaseReport");
        }

        byte[] bs = content.toString().getBytes();
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/csv", oStream, fileName);
        return (null);
    }


    public ActionForward fivePercentOrLessReport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fDocNbr  = request.getParameter("docId");
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr);
        StringBuffer content = new StringBuffer();
        content.append("Five Percent Or Less Report for - " + catalogPending.getBatchLog() + " uploaded on " +  catalogPending.getCatalogTimestamp() + "" + LF );
        String fileName = catalogPending.getBatchLog().split("\\.")[0] + "-FivePercentOrLessReport.csv";
        Collection<CatalogItemPending> cIPCollection = getCurrentAndPreviousCatalogItemPendingCollection(fDocNbr, "FivePercentOrLessReport");
        if (!cIPCollection.isEmpty()) {
           content.append("The following " + cIPCollection.size() + " Items had their Price Increased Or Deacresed by more than 5 Percent:" + LF );
           content.append("Distributor Number, Manufacturer Number, Unspsc Code, "+
           "UI Code, Spaid Id, Catalog Price, Recycled, Tax, Description" + LF );
           writeDataToCsv(content, cIPCollection, "fivePercentOrLessReport");
        }
        byte[] bs = content.toString().getBytes();
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        oStream.write(bs);
     
        WebUtils.saveMimeOutputStreamAsFile(response, "application/csv", oStream, fileName);
        return (null);
    }

    private Collection<CatalogItemPending> getCurrentAndPreviousCatalogItemPendingCollection(String fDocNbr, String reportName) throws WorkflowException{
        Collection<CatalogItemPending> cIPCollection = new ArrayList<CatalogItemPending>();
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        CatalogItemPendingQueryService cIPQS = SpringContext.getBean(CatalogItemPendingQueryService.class);
        CatalogPending catalogPending = (CatalogPending) dOS.getByDocumentHeaderId(fDocNbr);
        String fDocNbr2 = cIPQS.getPreviousCatalogTimeStamp(catalogPending.getCatalogCd(), fDocNbr);
        if(!StringUtils.isBlank(fDocNbr2)){
            if("FivePercentOrLessReport".equalsIgnoreCase(reportName)){
                cIPCollection = cIPQS.getCollectionForFivePercentOrLessReport(fDocNbr, fDocNbr2);
            }else if("PriceDecreaseReport".equalsIgnoreCase(reportName)){
                cIPCollection = cIPQS.getCollectionForPriceDecreaseReport(fDocNbr, fDocNbr2);
            }else if("PriceIncreaseReport".equalsIgnoreCase(reportName)){
                cIPCollection = cIPQS.getCollectionForPriceIncreaseReport(fDocNbr, fDocNbr2);
            }else if("CatalogDeletionsReport".equalsIgnoreCase(reportName)){
                cIPCollection = cIPQS.getCollectionForCatalogDeletionsReport(fDocNbr2, fDocNbr);
            }else if("CatalogAdditionsReport".equalsIgnoreCase(reportName)){
                cIPCollection = cIPQS.getCollectionForCatalogAdditionsReport(fDocNbr, fDocNbr2);
            }
        }
        return cIPCollection;
    }

    private StringBuffer writeDataToCsv(StringBuffer document, Collection<CatalogItemPending> cIPCollection, String reportName) throws DocumentException{
        HashMap<String, Double> priceMap = null;
        if(reportName.equalsIgnoreCase("priceDecreaseReport")){
            CatalogItemPendingQueryService cIPQS = SpringContext.getBean(CatalogItemPendingQueryService.class); 
            priceMap = cIPQS.getCatalogPriceDecreasesMap(cIPCollection);
        }
        
        if(reportName.equalsIgnoreCase("priceIncreaseReport")){
            CatalogItemPendingQueryService cIPQS = SpringContext.getBean(CatalogItemPendingQueryService.class); 
            priceMap = cIPQS.getCatalogPriceIncreasesMap(cIPCollection);
        }
        
        for (Iterator<CatalogItemPending> iterator = cIPCollection.iterator(); iterator.hasNext();) {
            CatalogItemPending catalogItemPending = iterator.next();
            document.append("");
            document.append(catalogItemPending.getDistributorNbr());
            document.append(",");
            document.append(catalogItemPending.getManufacturerNbr());
            document.append(",");
            document.append(catalogItemPending.getUnspscCd());
            document.append(",");
            document.append(catalogItemPending.getCatalogUnitOfIssueCd());
            document.append(",");
            document.append(catalogItemPending.getSpaidId());
            document.append(",");
            document.append(catalogItemPending.getCatalogPrc());
            document.append(",");
            
            if(reportName.equalsIgnoreCase("priceDecreaseReport") ||
                    reportName.equalsIgnoreCase("priceIncreaseReport")){
                double pD = priceMap.get(catalogItemPending.getCatalogItemPndId());
                document.append(pD+"");
                document.append(",");
                
                document.append(Double.parseDouble(catalogItemPending.getCatalogPrc()+"") / pD);
                document.append(",");
            }            
            
            document.append((catalogItemPending.isRecycledInd()==true? "Y" : ""));
            document.append(",");
            document.append((catalogItemPending.isTaxableInd()==true? "Y" : ""));
            document.append(",\"");
            document.append(catalogItemPending.getCatalogDesc());
            document.append("\"" + LF );
        }
        return document;
    }
}