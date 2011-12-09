package org.kuali.ext.mm.dataaccess;

import java.util.Collection;
import java.util.Map;

import org.kuali.rice.kns.dao.BusinessObjectDao;

public interface PickTicketDao extends BusinessObjectDao {

	public Collection findMatchingBoundedAndOrderBy(Class clazz, Map fieldValues, String sortField, boolean sortAscending, int maxRows);
}
