package org.kuali.ext.mm.dataaccess.impl;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.ext.mm.dataaccess.PickTicketDao;
import org.kuali.rice.kns.dao.impl.BusinessObjectDaoOjb;
import org.kuali.rice.krad.service.PersistenceStructureService;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class PickTicketDaoOjb extends BusinessObjectDaoOjb implements PickTicketDao {

	/**
     * @param persistenceStructureService
     */
    public PickTicketDaoOjb(PersistenceStructureService persistenceStructureService) {
        super(persistenceStructureService);
        // TODO Auto-generated constructor stub
    }
    public PickTicketDaoOjb(){
        super(null);
    }

    public Collection findMatchingBoundedAndOrderBy(Class clazz, Map fieldValues, String sortField, boolean sortAscending, int maxRows) {
		Criteria criteria = buildCriteria(fieldValues);
		getDbPlatform().applyLimit(maxRows, criteria);
        QueryByCriteria queryByCriteria = new QueryByCriteria(clazz, criteria);

        if (sortAscending) {
            queryByCriteria.addOrderByAscending(sortField);
        }
        else {
            queryByCriteria.addOrderByDescending(sortField);
        }

        return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
	}

	/**
     * This is the default impl that comes with Kuali - uses OJB.
     *
     * It was necessary to override this because the default implementation does not
     * support blank or null field values.
     *
     * @see org.kuali.rice.kns.dao.BusinessObjectDao#findMatching(java.lang.Class, java.util.Map)
     */
	@Override
    public Collection findMatching(Class clazz, Map fieldValues) {
        Criteria criteria = buildCriteria(fieldValues);

        return getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(clazz, criteria));
    }

	 /**
     * This method will build out criteria in the key-value paradigm (attribute-value).
     *
     * @param fieldValues
     * @return
     */
    private Criteria buildCriteria(Map fieldValues) {
        Criteria criteria = new Criteria();
        for (Iterator i = fieldValues.entrySet().iterator(); i.hasNext();) {
            Map.Entry e = (Map.Entry) i.next();

            String key = (String) e.getKey();
            Object value = e.getValue();
            if (value instanceof Collection) {
                criteria.addIn(key, (Collection) value);
            }
            else if(value == null) {
            	criteria.addIsNull(key);
            }
            else {
                criteria.addEqualTo(key, value);
            }
        }

        return criteria;
    }

}
