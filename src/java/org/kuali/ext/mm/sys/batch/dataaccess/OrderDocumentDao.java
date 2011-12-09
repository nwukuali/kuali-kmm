/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.ArrayList;

import org.kuali.ext.mm.document.OrderDocument;

/**
 * @author rshrivas
 */
public interface OrderDocumentDao {

    public ArrayList<OrderDocument> getOrderDocuments();
}
