/**
 *
 */
package org.kuali.ext.mm.integration.service.impl.kfs;

import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.purap.document.FinancialRequisitionDocument;
import org.kuali.ext.mm.integration.service.FinancialRequisitionService;
import org.kuali.kfs.module.purap.document.RequisitionDocument;
import org.kuali.kfs.module.purap.document.service.RequisitionService;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.routeheader.service.RouteHeaderService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;


/**
 * @author harsha07
 */
public class KfsRequisitionServiceAdaptor extends KfsServiceAdaptor<RequisitionService> implements
        FinancialRequisitionService {

    /**
     * @param name
     */
    public KfsRequisitionServiceAdaptor(QName name) {
        super(name);
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialRequisitionService#getCountOfRequisitionsAwaitingContractManagerAssignment()
     */
    public int getCountOfRequisitionsAwaitingContractManagerAssignment() {
        return getService().getCountOfRequisitionsAwaitingContractManagerAssignment();
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialRequisitionService#getRequisitionById(java.lang.Integer)
     */
    public FinancialRequisitionDocument getRequisitionById(Integer id) {
        RequisitionDocument requisitionById = getService().getRequisitionById(id);
        if (requisitionById != null) {
            FinancialRequisitionDocument financialRequisitionDocument = new FinancialRequisitionDocument();
            DocumentRouteHeaderValue routeHeader = SpringContext.getBean(RouteHeaderService.class)
                    .getRouteHeader(requisitionById.getDocumentNumber());
            financialRequisitionDocument.setWorkflowStatusCode(routeHeader.getDocRouteStatus());
            financialRequisitionDocument.setReqsId(requisitionById.getPurapDocumentIdentifier());
            financialRequisitionDocument.setDocumentNumber(requisitionById.getDocumentNumber());
            return financialRequisitionDocument;
        }
        return null;
    }

    /**
     * @see org.kuali.ext.mm.integration.service.FinancialRequisitionService#getRequisitionsAwaitingContractManagerAssignment()
     */
    public List<FinancialRequisitionDocument> getRequisitionsAwaitingContractManagerAssignment() {
        List<FinancialRequisitionDocument> returnVal = new ArrayList<FinancialRequisitionDocument>();
        List<RequisitionDocument> reqsList = getService()
                .getRequisitionsAwaitingContractManagerAssignment();
        for (RequisitionDocument requisitionDocument : reqsList) {
            FinancialRequisitionDocument financialRequisitionDocument = new FinancialRequisitionDocument();
            financialRequisitionDocument
                    .setReqsId(requisitionDocument.getPurapDocumentIdentifier());
            financialRequisitionDocument.setDocumentNumber(requisitionDocument.getDocumentNumber());
            returnVal.add(financialRequisitionDocument);
        }
        return returnVal;
    }

}
