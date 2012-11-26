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
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.PickListLine;
import org.kuali.ext.mm.businessobject.RouteMap;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.DeliveryLabelDocument;
import org.kuali.ext.mm.document.DeliveryLabelDocumentLines;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding;
import org.kuali.ext.mm.service.PickListService;
import org.kuali.ext.mm.sys.service.SegmentedLookupResultsService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.util.AutoPopulatingList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;


/**
 * @author rshrivas
 *
 */
public class DeliveryLabelAction extends KualiTransactionalDocumentActionBase{
    
    @Override
    @SuppressWarnings("unused")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

       DeliveryLabelForm dLForm = (DeliveryLabelForm) form;
       DeliveryLabelDocument dLDoc = (DeliveryLabelDocument)dLForm.getDocument();
       BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
       if(dLDoc!=null){
           dLDoc.setPackStatusCodeCd(MMConstants.PackStatusCode.PACK_STATUS_INIT);
           
           List<DeliveryLabelDocumentLines> list = new ArrayList<DeliveryLabelDocumentLines>();
           
           List<PickListLine> pickLineList = dLDoc.getPickListLines();
           int packListLineNbr = 0;
           for (Iterator itr = pickLineList.iterator(); itr.hasNext();) {
               packListLineNbr++;
               PickListLine pLL = (PickListLine) itr.next();
               
               DeliveryLabelDocumentLines dlLine = new DeliveryLabelDocumentLines();               
               dlLine.setPackListDocNbr(dLDoc.getDocumentNumber());
               dlLine.setPickListLineId(pLL.getPickListLineId());
               dlLine.setNbrOfCartons(dLDoc.getNbrOfCartons());
               dlLine.setPackListLineNbr(packListLineNbr+"");               
               dlLine.setPackDate(new java.sql.Date(new java.util.Date().getTime()));
               dlLine.setPackStatusCode(MMConstants.PackStatusCode.PACK_STATUS_INIT);
               dlLine.setPackPrincipalId(GlobalVariables.getUserSession().getPerson().getPrincipalId());
               int orderDetailId = pLL.getOrderDetailId();
               loadDeliveryInformation(orderDetailId, dlLine);               
               list.add(dlLine);
           }
           dLDoc.setDeliveryLabelDocumentLines(list);
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
        DeliveryLabelForm dlForm = (DeliveryLabelForm) form;

        String documentNumber = dlForm.getDocument().getDocumentNumber();        
        
        // automatically create document description template
        dlForm.getDocument().getDocumentHeader().setDocumentDescription("Delivery Label Number: " +  documentNumber);

        if (dlForm.getDocument().getDocumentHeader().getWorkflowDocument().isInitiated()) {
            dlForm.getDeliveryLabelDocument().setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PRTD);
        }
        
        if (dlForm.getDocument().getDocumentHeader().getWorkflowDocument().isFinal()) {
            dlForm.getDeliveryLabelDocument().setPickStatusCodeCd(MMConstants.PickStatusCode.PICK_STATUS_PRTD);
            BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
            HashMap<String, String> fieldValues = new HashMap<String, String>();
            fieldValues.put(MMConstants.PackStatusCode.PACK_LIST_DOC_NBR, documentNumber);
            List<DeliveryLabelDocumentLines> dLs = 
                (List<DeliveryLabelDocumentLines>) bOS.findMatching(DeliveryLabelDocumentLines.class, fieldValues);
            for (Iterator iterator = dLs.iterator(); iterator.hasNext();) {
                DeliveryLabelDocumentLines deliveryLabelDocumentLines = (DeliveryLabelDocumentLines) iterator.next();
                String pickListLineId = deliveryLabelDocumentLines.getPickListLineId();
                PickListLine pLL = bOS.findBySinglePrimaryKey(PickListLine.class, pickListLineId);
                dlForm.getDeliveryLabelDocument().setPickTicketNumber(Integer.parseInt(pLL.getPickTicketNumber()));
                break;
            }            
        }
        
        resetSummaryValues((DeliveryLabelForm) form);
        computePickListSummaryValues((DeliveryLabelForm) form);        
        return actionForward;
    }    
       
    @SuppressWarnings("unchecked")
    public ActionForward printDeliveryLabel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String documentNumber = request.getParameter(MMConstants.PackStatusCode.DOC_ID);
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.PackStatusCode.PACK_LIST_DOC_NBR, documentNumber);
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
             
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 36, 36, 36);
        PdfWriter.getInstance(document, oStream);
        document.open();
                                    
        List<DeliveryLabelDocumentLines> dLLineList = (List<DeliveryLabelDocumentLines>) bOS.findMatching(DeliveryLabelDocumentLines.class, fieldValues);
        
        for (Iterator iterator = dLLineList.iterator(); iterator.hasNext();) {
            DeliveryLabelDocumentLines dLLine = (DeliveryLabelDocumentLines) iterator.next();
            String pickListLineId = dLLine.getPickListLineId();
            PickListLine pLLineObject = bOS.findBySinglePrimaryKey(PickListLine.class, pickListLineId);
            String orderDocNbr = pLLineObject.getOrderDetail().getOrderDocumentNbr();
            
            for(int i = 0; i < Integer.parseInt(dLLine.getNbrOfCartons()); i++){
                
                PdfPCell cell;
                PdfPTable tableA=new PdfPTable(1);  
                tableA.setWidthPercentage(100);
                PdfPCell d1 = new PdfPCell();               
                tableA.addCell(d1);
                PdfPCell cell1 = new PdfPCell (new Paragraph ("Delivery Label # " + documentNumber));
                PdfPCell cell2 = new PdfPCell (new Paragraph ("Please place this label on the delivery package"));
                tableA.addCell(cell1);
                tableA.addCell(cell2);
                tableA.addCell(d1);
                document.add(tableA);               
                
                PdfPTable table=new PdfPTable(2);
                table.setWidthPercentage(100);
                table.addCell("Delivery Label for Order Number");                    
                    cell = new PdfPCell (new Paragraph (orderDocNbr, new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);                
                table.addCell("Route Code ");            
                    cell = new PdfPCell (new Paragraph (dLLine.getRouteCd(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);
                table.addCell("Package ");  
                    cell = new PdfPCell (new Paragraph ((i+1) + " of " + dLLine.getNbrOfCartons(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);                                                         
                table.addCell("Building Name: ");
                    cell = new PdfPCell (new Paragraph (dLLine.getDeliveryBuildingNm(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);
                table.addCell("Delivery Department Name: ");
                    cell = new PdfPCell (new Paragraph (dLLine.getDeliveryDepartmentNm(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);
                table.addCell("Delivery Building Room Number: ");    
                    cell = new PdfPCell (new Paragraph (dLLine.getDeliveryBuildingRmNbr(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);
                table.addCell("Campus Code: ");       
                    cell = new PdfPCell (new Paragraph (dLLine.getDeliveryCampusCd(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);
                table.addCell("Delivery Instruction Text: ");    
                    cell = new PdfPCell (new Paragraph (StringUtils.isBlank(dLLine.getDeliveryInstructionTxt())? "": dLLine.getDeliveryInstructionTxt(), new Font(Font.COURIER, 10)));        
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    table.addCell (cell);
                document.add(table);
                
                PdfPTable table2=new PdfPTable(1);
                table2.setWidthPercentage(100);
                table2.addCell("Thank you for shopping at the University's Online Storehouse.");
                table2.addCell("Your order was packaged on " + new java.util.Date());
                table2.addCell(d1);
                document.add(table2);
                document.newPage();
            }
            break;
        }        
        document.close();        
        StringBuffer content = new StringBuffer();
        String fileName = "DeliveryLabel.pdf";        
        byte[] bs = content.toString().getBytes();
        oStream.write(bs);
        WebUtils.saveMimeOutputStreamAsFile(response, "application/pdf", oStream, fileName);
        return (null);
    }

    /**
     * Computes number of orders, number of lines, and oldest date from the pick list lines that have been assigned to the document.
     *
     * @param form
     */
    private void computePickListSummaryValues(DeliveryLabelForm form) {
        DeliveryLabelDocument doc = form.getDeliveryLabelDocument();

        for (PickListLine line : doc.getPickListLines()) {
            if (form.getOldestDate() == null)
                form.setOldestDate(line.getCreatedDate());
            if (form.getOldestDate().after(line.getCreatedDate()))
                form.setOldestDate(line.getCreatedDate());

            if (line.getOrderDetail().isWillCall()) {
                form.setNumberOfWillCalls(form.getNumberOfWillCalls() + 1);
            }
        }
        form.setNumberOfOrders(SpringContext.getBean(PickListService.class).getUniqueOrderCount(
                doc.getPickListLines()));
        form.setNumberOfLines(doc.getPickListLines().size());
    }


    /**
     * Resets the form values for summary computation
     *
     * @param form
     */
    private void resetSummaryValues(DeliveryLabelForm form) {
        form.setNbrOfCartons("");
        form.setNumberOfLines(0);
        form.setNumberOfWillCalls(0);
        form.setNumberOfOrders(0);
        form.setSingleWarehouse(true);
        form.setOldestDate(null);
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

        DeliveryLabelForm dLForm = (DeliveryLabelForm) form;

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
                                segmentedSelection.keySet(), PickListLine.class,
                                GlobalVariables.getUserSession().getPerson().getPrincipalId());
            }

            // Reset line list and Summary values
            List<PickListLine> pickListLineList = new AutoPopulatingList(PickListLine.class);
            resetSummaryValues(dLForm);

            if (rawValues != null) {
                for (PersistableBusinessObject bo : rawValues) {
                    PickListLine line = (PickListLine) bo;

                    boolean addIt = true;
                    for (PickListLine detail : pickListLineList) {
                        if (detail.getPickListLineId().compareTo(line.getPickListLineId()) == 0) {
                            addIt = false;
                            break;
                        }
                    }

                    if (addIt)
                        pickListLineList.add(line);
                }
                dLForm.getDeliveryLabelDocument().setPickListLines(pickListLineList);
            }
        }
        computePickListSummaryValues(dLForm);
        return mapping.findForward(MMConstants.MAPPING_BASIC);
    }
    
    /*
     * This method uses the orderDetailId to extract out the OrderDocument
     * USing the buildingCd and the campusCd from the orderDocument, find the routeCd
     * and set it in deliveryLabeldocumentLines
     */
    private DeliveryLabelDocumentLines loadDeliveryInformation (int orderDetailId, DeliveryLabelDocumentLines dLLine){
        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        OrderDetail oDetail = bOS.findBySinglePrimaryKey(OrderDetail.class, orderDetailId);
        FinancialSystemAdaptorFactory financialSystemAdaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
        if(oDetail != null){
            String orderDocNbr = oDetail.getOrderDocumentNbr();
            DocumentService dOS = SpringContext.getBean(DocumentService.class);
                OrderDocument oDocument;
                try {
                    oDocument = (OrderDocument) dOS.getByDocumentHeaderId(orderDocNbr);
                }
                catch (WorkflowException e) {
                   throw new RuntimeException(e);
                }
                
                String buildingCd = oDocument.getDeliveryBuildingCd();
                String campusCd = oDocument.getCampusCd();
                if (financialSystemAdaptorFactory.isSystemAvailable()) { 
                    FinancialBuilding building = financialSystemAdaptorFactory.getFinancialLocationService().getBuilding(campusCd, buildingCd);
                    dLLine.setDeliveryBuildingNm(building.getBuildingName());
                }
                dLLine.setDeliveryCampusCd(campusCd);
                dLLine.setDeliveryBuildingRmNbr(oDocument.getDeliveryBuildingRmNbr());
                dLLine.setDeliveryDepartmentNm(oDocument.getDeliveryDepartmentNm());
                dLLine.setDeliveryInstructionTxt(oDocument.getDeliveryInstructionTxt());
                
                // Find RouteCd using the buildingCd and the campusCd from the orderDocument
                HashMap<String, String> fieldValues = new HashMap<String, String>();
                fieldValues.put(MMConstants.PackStatusCode.DELIVERY_BLDG_CD, buildingCd);
                fieldValues.put(MMConstants.PackStatusCode.DELIVERY_CAMPUS_CD, campusCd);
                List<RouteMap> routeMapList = (List<RouteMap>) bOS.findMatching(RouteMap.class, fieldValues);
                if(!routeMapList.isEmpty()){
                    for (Iterator iterator = routeMapList.iterator(); iterator.hasNext();) {
                        RouteMap routeMap = (RouteMap) iterator.next();
                        dLLine.setRouteCd(routeMap.getRouteCd());
                        dLLine.setStopCd(routeMap.getStopSequnce());
                        break;
                    }
                }                
        }
        return dLLine;
    }
}
