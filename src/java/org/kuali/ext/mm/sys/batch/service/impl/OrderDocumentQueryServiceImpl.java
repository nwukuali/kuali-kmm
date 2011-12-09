/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.util.ArrayList;

import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.sys.batch.dataaccess.OrderDocumentDao;
import org.kuali.ext.mm.sys.batch.service.OrderDocumentQueryService;

/**
 * @author rshrivas
 */
public class OrderDocumentQueryServiceImpl implements OrderDocumentQueryService {


    private OrderDocumentDao orderDocumentDao;

    /**
     * Gets the orderDocumentDao property
     *
     * @return Returns the orderDocumentDao
     */
    public OrderDocumentDao getOrderDocumentDao() {
        return this.orderDocumentDao;
    }

    /**
     * Sets the orderDocumentDao property value
     *
     * @param orderDocumentDao The orderDocumentDao to set
     */
    public void setOrderDocumentDao(OrderDocumentDao orderDocumentDao) {
        this.orderDocumentDao = orderDocumentDao;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.service.OrderDocumentQueryService#getOrderDocuments()
     */
    public ArrayList<OrderDocument> getOrderDocuments() {
        return this.orderDocumentDao.getOrderDocuments();
    }
}
