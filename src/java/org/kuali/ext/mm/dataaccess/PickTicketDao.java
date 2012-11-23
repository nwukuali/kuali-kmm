package org.kuali.ext.mm.dataaccess;

import org.kuali.rice.krad.dao.BusinessObjectDao;

import java.util.Collection;
import java.util.Map;


public interface PickTicketDao extends BusinessObjectDao {

	public Collection findMatchingBoundedAndOrderBy(Class clazz, Map fieldValues, String sortField, boolean sortAscending, int maxRows);
}
