package org.kuali.ext.mm.sys.batch.dataaccess.ojb;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.sys.batch.dataaccess.OrderDocumentDao;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author rshrivas
 */
public class OrderDocumentDaoOjb extends PlatformAwareDaoBaseOjb implements OrderDocumentDao {


    @SuppressWarnings("unchecked")
    public ArrayList<OrderDocument> getOrderDocuments() {

        Collection<String> orderTypeCodes = new ArrayList<String>();
        orderTypeCodes.add(MMConstants.OrderType.STOCK);
        //orderTypeCodes.add(MMConstants.OrderType.HOSTED);

        Collection<String> orderDocStatusCode = new ArrayList<String>();
        orderDocStatusCode.add(MMConstants.OrderStatus.ORDER_LINE_PRINTED);
        orderDocStatusCode.add(MMConstants.OrderStatus.ORDER_LINE_OPEN);
        orderDocStatusCode.add(MMConstants.OrderStatus.ORDER_LINE_RECEIVING);

        Criteria criteria = new Criteria();
        criteria.addIn("orderTypeCode", orderTypeCodes);
        criteria.addIn("orderStatusCd", orderDocStatusCode);
        criteria.addNotNull("reqsId");

        Query q = QueryFactory.newQuery(OrderDocument.class, criteria);

        ArrayList<OrderDocument> returnedList = new ArrayList<OrderDocument>();

        Collection<OrderDocument> values = getPersistenceBrokerTemplate().getCollectionByQuery(q);
        for (OrderDocument orderDocument : values) {
            returnedList.add(orderDocument);
        }

        return returnedList;
    }
}