/**
 * 
 */
package org.kuali.ext.mm.document.web.struts;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.DeliveryLine;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.ext.mm.document.DeliveryLogDocument;
import org.kuali.ext.mm.sys.service.SegmentedLookupResultsService;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.*;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class DeliveryLogAction extends KualiTransactionalDocumentActionBase{

    @Override
    @SuppressWarnings("unused")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

       DeliveryLogForm dLForm = (DeliveryLogForm) form;
       DeliveryLogDocument dLDoc = (DeliveryLogDocument)dLForm.getDocument();
       BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
       if(dLDoc!=null){
           List<DeliveryLine> list = new ArrayList<DeliveryLine>();           
           List<DeliveryLabelDocumentLines> pickLineList = dLDoc.getDeliveryLabelLines();
           
           for (Iterator itr = pickLineList.iterator(); itr.hasNext();) {
               DeliveryLabelDocumentLines pLL = (DeliveryLabelDocumentLines) itr.next();                  
               DeliveryLine deliveryLine = new DeliveryLine();               
               deliveryLine.setDeliveryDocNbr(dLDoc.getDocumentNumber());
               deliveryLine.setPackListLineId(pLL.getPackListLineId());
               deliveryLine.setDeliveryReasonCode(MMConstants.Delivery.DELIVERY_REASON_CD);                                     
               list.add(deliveryLine);
           }
           dLDoc.setDeliveryLines(list);
       }              
       return super.execute(mapping, form, request, response);
    }
    
    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     *      
     *      Populate the form and the form description 
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.docHandler(mapping, form, request, response);
        DeliveryLogForm dlForm = (DeliveryLogForm) form;
        String documentNumber = dlForm.getDocument().getDocumentNumber();        
        dlForm.getDocument().getDocumentHeader().setDocumentDescription("Driver Log Number: " +  documentNumber);               
        if (dlForm.getDocument().getDocumentHeader().getWorkflowDocument().isFinal()) {
            DocumentService dOS = SpringContext.getBean(DocumentService.class);
            DeliveryLogDocument dLDoc = (DeliveryLogDocument) dOS.getByDocumentHeaderIdSessionless(documentNumber);
            List<DeliveryLabelDocumentLines> dLs = dLDoc.getDeliveryLabelLines();
            dlForm.setNumberOfLines(dLs.size());
            dlForm.getDeliveryLogDocument().setDeliveryLabelLines(dLs);        
        }
        
        resetSummaryValues((DeliveryLogForm) form);
        computePickListSummaryValues((DeliveryLogForm) form);        
        return actionForward;
    } 
       
    public ActionForward printDeliveryLog(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String documentNumber = request.getParameter(MMConstants.Delivery.DOC_ID);        
        DocumentService dOS = SpringContext.getBean(DocumentService.class);
        DeliveryLogDocument dLDoc = (DeliveryLogDocument) dOS.getByDocumentHeaderIdSessionless(documentNumber);
        
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, oStream);
        document.open();
       
        List <DeliveryLine> dlList = dLDoc.getDeliveryLines();
        
        PdfPTable table= new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell (new Paragraph ("University's Online Storehouse - Drivers Log", new Font(Font.TIMES_ROMAN, 20)));
        cell.setColspan (2);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (10.0f);
        table.addCell (cell);
        cell = new PdfPCell (new Paragraph ("Driver Log Number: " + documentNumber, new Font(Font.TIMES_ROMAN, 12)));
        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
        cell.setPadding (10.0f);
        table.addCell (cell);
        cell = new PdfPCell (new Paragraph ("Date Generated: " + DateFormat.getInstance().format(new Date()), new Font(Font.TIMES_ROMAN, 12)));
        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
        cell.setPadding (10.0f);
        cell.setFixedHeight(20);
        table.addCell (cell);                       
        document.add(table);
              
        PdfPTable tableC = new PdfPTable(1);
        tableC.setWidthPercentage(new Float(100));
        PdfPCell cellC1  = new PdfPCell(new Paragraph ("Route Code: " + dLDoc.getRouteCd(), new Font(Font.TIMES_ROMAN, 12)));
        cellC1.setBorder(0);
        cellC1.setPadding (8.0f);
        tableC.addCell(cellC1);
        document.add(tableC);
        
        float[] widths = {0.06f, 0.18f, 0.1f, 0.1f, 0.15f, 0.13f, 0.1f, 0.09f, 0.12f};
        PdfPTable tableD = new PdfPTable(widths);
        tableD.setWidthPercentage(100);
        
        cell = new PdfPCell (new Paragraph ("Stop Code", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Department Name", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Building Code", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Building Room #", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Building Name", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Order Number", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("PickList Number", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Cartons", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);
        
        cell = new PdfPCell (new Paragraph ("Instructions", new Font(Font.TIMES_ROMAN, 12)));        
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        tableD.addCell (cell);        
        
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        
        
        // Sort the delivery Line with respect to the SortCode
        sortDeliveryLabelDocumentLines(dlList, bOS);
        
        for (Iterator iterator = dlList.iterator(); iterator.hasNext();) {
            DeliveryLine dLObject = (DeliveryLine) iterator.next();
            String packListLineId = dLObject.getPackListLineId();
            DeliveryLabelDocumentLines pickListLine = bOS.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, packListLineId);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getStopCd(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getDeliveryDepartmentNm(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getDeliveryBuildingCd(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getDeliveryBuildingRmNbr(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getDeliveryBuildingNm(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            PickListLine pLL = bOS.findBySinglePrimaryKey(PickListLine.class, pickListLine.getPickListLineId());
            cell = new PdfPCell (new Paragraph (pLL.getOrderDetail().getOrderDocumentNbr(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (packListLineId, new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getNbrOfCartons(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);
            
            cell = new PdfPCell (new Paragraph (pickListLine.getDeliveryInstructionTxt(), new Font(Font.COURIER, 10)));        
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            tableD.addCell (cell);               
        }
        
        document.add(tableD);  
        
        PdfPTable tableE = new PdfPTable(1);
        tableE.setWidthPercentage(new Float(100));
        PdfPCell cellE1  = new PdfPCell(new Paragraph ("Total Number of Shipments: " + dlList.size(), new Font(Font.TIMES_ROMAN, 12)));
        cellE1.setBorder(0);
        cellE1.setPadding (8.0f);
        tableE.addCell(cellE1);
        document.add(tableE);          
        
        PdfPTable tableF = new PdfPTable(new float[]{0.40f, 0.30f, 0.2f} );
        tableF.setWidthPercentage(new Float(100));
        
        PdfPCell cellF0 = new PdfPCell(new Paragraph(""));
        cellF0.setPadding (10.0f);
        cellF0.setBorder(0);        
        
        PdfPCell cellF1  = new PdfPCell(new Paragraph ("PRINT NAME:       ___________________________", new Font(Font.TIMES_ROMAN, 10)));
        PdfPCell cellF2  = new PdfPCell(new Paragraph ("SIGNATURE: ____________________", new Font(Font.TIMES_ROMAN, 10)));
        PdfPCell cellF3  = new PdfPCell(new Paragraph ("DATE: _______________", new Font(Font.TIMES_ROMAN, 10)));
        cellF1.setBorder(0);
        cellF2.setBorder(0);
        cellF3.setBorder(0);
        cellF1.setPadding (1.0f);
        cellF1.setPadding (1.0f);
        cellF1.setPadding (1.0f);
        tableF.addCell(cellF0);
        tableF.addCell(cellF0);
        tableF.addCell(cellF0);
        tableF.addCell(cellF0);
        tableF.addCell(cellF0);
        tableF.addCell(cellF0);
        tableF.addCell(cellF1);
        tableF.addCell(cellF2);
        tableF.addCell(cellF3);
        document.add(tableF);          
        
        PdfPTable tableG = new PdfPTable(new float[]{0.16f, 0.84f});
        tableG.setWidthPercentage(new Float(100));
        PdfPCell cellG1 = new PdfPCell(new Paragraph("REMARKS:", new Font(Font.TIMES_ROMAN, 10)));
        PdfPCell cellG11 = new PdfPCell(new Paragraph("______________________________________________________________________________________", new Font(Font.TIMES_ROMAN, 10)));
        PdfPCell cellG2 = new PdfPCell();
        PdfPCell cellG22 = new PdfPCell(new Paragraph("THIS SHIPMENT FROM CENTRAL STOREHOUSE WAS RECEIVED IN GOOD CONDITION (NO VISIBLE DAMAGE) UNLESS NOTED ABOVE", new Font(Font.TIMES_ROMAN, 6))); 
        cellG1.setBorder(0);
        cellG2.setBorder(0);
        cellG11.setBorder(0);
        cellG22.setBorder(0);
        tableG.addCell(cellF0);
        tableG.addCell(cellF0);
        tableG.addCell(cellG1);
        tableG.addCell(cellG11);
        tableG.addCell(cellG2);        
        tableG.addCell(cellG22);
        document.add(tableG);  
        
        PdfPTable tableH = new PdfPTable(new float[]{0.16f, 0.84f});
        tableH.setWidthPercentage(new Float(100));
        PdfPCell cellH1  = new PdfPCell(new Paragraph ("DRIVER'S INITIAL:", new Font(Font.TIMES_ROMAN, 10)));
        PdfPCell cellH2  = new PdfPCell(new Paragraph ("_____________________________________", new Font(Font.TIMES_ROMAN, 10)));
        cellH1.setBorder(0);
        cellH2.setBorder(0);
        tableH.addCell(cellF0);
        tableH.addCell(cellF0);
        tableH.addCell(cellH1);
        tableH.addCell(cellH2);
        document.add(tableH);  
        
        document.close();        
        StringBuffer content = new StringBuffer();
        String fileName = "DriverLogNumber-" + documentNumber + ".pdf";        
        byte[] bs = content.toString().getBytes();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/pdf", oStream, fileName);
        return (null);
    }
    
    /**
     * Overrides refresh method in order to allow the return of multiple items from the multiple value lookup.
     *
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#refresh(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    @Override
    public ActionForward refresh(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.refresh(mapping, form, request, response);

        DeliveryLogForm dLForm = (DeliveryLogForm) form;

        Collection<PersistableBusinessObject> rawValues = null;
        Map<String, Set<String>> segmentedSelection = new HashMap<String, Set<String>>();

        if (StringUtils.equals(MMConstants.MULTIPLE_VALUE, dLForm.getRefreshCaller())) {
            String lookupResultsSequenceNumber = dLForm.getLookupResultsSequenceNumber();

            if (StringUtils.isNotBlank(lookupResultsSequenceNumber)) {

                // actually returning from a multiple value lookup
                Set<String> selectedIds = SpringContext
                        .getBean(SegmentedLookupResultsService.class)
                        .retrieveSetOfSelectedObjectIds(lookupResultsSequenceNumber,
                                GlobalVariables.getUserSession().getPerson().getPrincipalId());
                for (String selectedId : selectedIds) {
                    String selectedObjId = StringUtils.substringBefore(selectedId, ".");
                    String selectedMonthData = StringUtils.substringAfter(selectedId, ".");

                    if (!segmentedSelection.containsKey(selectedObjId)) {
                        segmentedSelection.put(selectedObjId, new HashSet<String>());
                    }
                    segmentedSelection.get(selectedObjId).add(selectedMonthData);
                }
                rawValues = SpringContext.getBean(SegmentedLookupResultsService.class)
                        .retrieveSelectedResultBOs(lookupResultsSequenceNumber,
                                segmentedSelection.keySet(), DeliveryLabelDocumentLines.class,
                                GlobalVariables.getUserSession().getPerson().getPrincipalId());
            }

            // Reset line list and Summary values
            List<DeliveryLabelDocumentLines> dLLineList = dLForm.getDeliveryLogDocument().getDeliveryLabelLines();
            resetSummaryValues(dLForm);

            if (rawValues != null) {
                for (PersistableBusinessObject bo : rawValues) {
                    DeliveryLabelDocumentLines line = (DeliveryLabelDocumentLines) bo;
 
                    boolean addIt = true;
                    for (DeliveryLabelDocumentLines detail : dLLineList) {
                        if (detail.getPickListLineId().compareTo(line.getPickListLineId()) == 0) {
                            addIt = false;
                            break;
                        }
                    }

                    if (addIt)
                        dLLineList.add(line);
                }     
            }
        }
        computePickListSummaryValues(dLForm);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    /**
     * Computes number of orders, number of lines, and oldest date from the pick list lines that have been assigned to the document.
     *
     * @param form
     */
    private void computePickListSummaryValues(DeliveryLogForm form) {
        DeliveryLogDocument doc = form.getDeliveryLogDocument();        
        if(null!=doc.getDeliveryLabelLines()){           
            form.setNumberOfLines(doc.getDeliveryLabelLines().size());
        }
    }
    
    /**
     * Resets the form values for summary computation
     *
     * @param form
     */
    private void resetSummaryValues(DeliveryLogForm form) {
        form.setNbrOfCartons("");
        form.setNumberOfLines(0);
        form.setNumberOfWillCalls(0);
        form.setNumberOfOrders(0);
        form.setSingleWarehouse(true);
        form.setOldestDate(null);
    }
    
    /*
     * Sort with reference to the Stop Code
     */
    private void sortDeliveryLabelDocumentLines(List<DeliveryLine> searchResults, final BusinessObjectService bOS) {       
        Collections.sort(searchResults, new Comparator<DeliveryLine>() {
            /**
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare(DeliveryLine d1, DeliveryLine d2) {
                String packListLineId1 = d1.getPackListLineId();
                String packListLineId2 = d2.getPackListLineId();
                
                DeliveryLabelDocumentLines pickListLine1 = bOS.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, packListLineId1);
                DeliveryLabelDocumentLines pickListLine2 = bOS.findBySinglePrimaryKey(DeliveryLabelDocumentLines.class, packListLineId2);
                
                if (Integer.parseInt(pickListLine1.getStopCd()) == Integer.parseInt(pickListLine2.getStopCd()))
                    return 0;
                else if (Integer.parseInt(pickListLine2.getStopCd()) > Integer.parseInt(pickListLine1.getStopCd()))
                    return -1;
                else
                    return 1;
            }
        });
    }
}
