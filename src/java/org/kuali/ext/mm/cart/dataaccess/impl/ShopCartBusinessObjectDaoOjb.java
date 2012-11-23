package org.kuali.ext.mm.cart.dataaccess.impl;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.ext.mm.cart.dataaccess.ShopCartBusinessObjectDao;
import org.kuali.ext.mm.cart.valueobject.PagingElement;
import org.kuali.ext.mm.cart.valueobject.SortElement;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.rice.kns.dao.impl.BusinessObjectDaoOjb;
import org.kuali.rice.krad.service.PersistenceStructureService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


public class ShopCartBusinessObjectDaoOjb extends BusinessObjectDaoOjb implements ShopCartBusinessObjectDao {

    MMBusinessObjectDao mmBusinessObjectDao;
    
	/**
     * Gets the mmBusinessObjectDao property
     * @return Returns the mmBusinessObjectDao
     */
    public MMBusinessObjectDao getMmBusinessObjectDao() {
        return this.mmBusinessObjectDao;
    }


    /**
     * Sets the mmBusinessObjectDao property value
     * @param mmBusinessObjectDao The mmBusinessObjectDao to set
     */
    public void setMmBusinessObjectDao(MMBusinessObjectDao mmBusinessObjectDao) {
        this.mmBusinessObjectDao = mmBusinessObjectDao;
    }


    /**
     * @param persistenceStructureService
     */
    public ShopCartBusinessObjectDaoOjb(PersistenceStructureService persistenceStructureService) {
        super(persistenceStructureService);
    }


    /**
	 * @see org.kuali.ext.mm.cart.dataaccess.ShopCartBusinessObjectDao#findMatching(java.lang.Class, org.kuali.ext.mm.cart.businessobject.QueryElement)
	 */
	public Collection findMatching(Class clazz, QueryElement queryElement) {
		Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);

		return getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(clazz, criteria));
	}
	

    /**
     * @see org.kuali.ext.mm.cart.dataaccess.ShopCartBusinessObjectDao#findMatchingOrderBy(java.lang.Class, org.kuali.ext.mm.cart.businessobject.QueryElement, java.lang.String, boolean)
     */
    public Collection findMatchingOrderBy(Class clazz, QueryElement queryElement, String sortField, boolean sortAscending) {
        Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);
        QueryByCriteria queryByCriteria = new QueryByCriteria(clazz, criteria);
        if (sortAscending) {
            queryByCriteria.addOrderByAscending(sortField);
        }
        else {
            queryByCriteria.addOrderByDescending(sortField);
        }
        return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
    }

	public Collection findMatchingBoundedAndOrderBy(Class clazz, Map<String, Object> fieldValues, String sortField, boolean sortAscending, int maxRows) {
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

	public Collection findMatchingBoundedAndOrderBy(Class clazz, QueryElement queryElement, SortElement sortElement, int maxRows) {
		Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);
		getDbPlatform().applyLimit(maxRows, criteria);
        ReportQueryByCriteria queryByCriteria = new ReportQueryByCriteria(clazz, criteria);
//        queryByCriteria.setEndAtIndex(maxRows);
        for(String sortField : sortElement.getSortFields()){ 
            if (sortElement.isFieldSortAscending(sortField)) {
                queryByCriteria.addOrderByAscending(sortField);
            }
            else {
                queryByCriteria.addOrderByDescending(sortField);
            }
        }
        return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
	}
	
	public Collection findMatchingPerPageBoundedAndOrderBy(Class clazz, QueryElement queryElement, SortElement sortElement, PagingElement pagingElement) {
        Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);
        ReportQueryByCriteria queryByCriteria = new ReportQueryByCriteria(clazz, criteria);
        
        int startIndex = (pagingElement.getPageNumber() - 1) * pagingElement.getResultsPerPage() + 1;
        int endIndex = startIndex + (pagingElement.getResultsPerPage() * pagingElement.getPageCount()) - 1;
        queryByCriteria.setStartAtIndex(startIndex);
        queryByCriteria.setEndAtIndex(endIndex);
        for(String sortField : sortElement.getSortFields()){ 
            if (sortElement.isFieldSortAscending(sortField)) {
                queryByCriteria.addOrderByAscending(sortField);
            }
            else {
                queryByCriteria.addOrderByDescending(sortField);
            }
        }
        return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
    }
	
   
    public Collection<Object[]> findMatchingPerPageBoundedAndOrderBy2(Class clazz, QueryElement queryElement, String[] attributes,
            SortElement sortElement, PagingElement pagingElement) {
        Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);
        ReportQueryByCriteria queryByCriteria = QueryFactory.newReportQuery(clazz, attributes, criteria, false);
        
        int startIndex = (pagingElement.getPageNumber() - 1) * pagingElement.getResultsPerPage() + 1;
        int endIndex = startIndex + (pagingElement.getResultsPerPage() * pagingElement.getPageCount()) - 1;
        queryByCriteria.setStartAtIndex(startIndex);
        queryByCriteria.setEndAtIndex(endIndex);
        for(String sortField : sortElement.getSortFields()){ 
            if (sortElement.isFieldSortAscending(sortField)) {
                queryByCriteria.addOrderByAscending(sortField);
            }
            else {
                queryByCriteria.addOrderByDescending(sortField);
            }
        }
        Iterator<Object[]> it = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(queryByCriteria);
        Collection<Object[]> results = new ArrayList<Object[]>();

        while(it!=null && it.hasNext()) {
            results.add(it.next());
        }

        return results;
    }

	public Integer countMatching(Class clazz, QueryElement queryElement) {
		Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);
		return getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(clazz, criteria));
	}
	
   public Integer countMatchingDistinct(Class clazz, QueryElement queryElement, String[] primaryKeys) {
        Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);
        
        ReportQueryByCriteria queryByCriteria = new ReportQueryByCriteria(clazz, primaryKeys, criteria);
        queryByCriteria.setDistinct(true);
        
        return getPersistenceBrokerTemplate().getCount(queryByCriteria);
    }

	public Collection<Object[]> getReport(Class clazz, QueryElement queryElement, String[] attributes, String[] groupBy, String[] orderBy, boolean orderAscending, int maxRows) {
		return SpringContext.getBean(MMBusinessObjectDao.class).getReport(clazz, queryElement, attributes, groupBy, orderBy, orderAscending, maxRows);
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

	public Collection findByReportQuery(Class clazz, Map<String, Object> fieldValues, String sortField, boolean sortAscending) {
		Criteria criteria = buildCriteria(fieldValues);

		ReportQueryByCriteria queryByCriteria = new ReportQueryByCriteria(clazz, criteria, true);

		if (sortAscending) {
            queryByCriteria.addOrderByAscending(sortField);
        }
        else {
            queryByCriteria.addOrderByDescending(sortField);
        }

		return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
	}

	public Collection findByReportQuery(Class clazz, QueryElement queryElement, String sortField, boolean sortAscending) {
		Criteria criteria = mmBusinessObjectDao.buildCriteriaFromQueryElement(clazz, queryElement);

		ReportQueryByCriteria queryByCriteria = new ReportQueryByCriteria(clazz, criteria, true);
		if (sortAscending) {
            queryByCriteria.addOrderByAscending(sortField);
        }
        else {
            queryByCriteria.addOrderByDescending(sortField);
        }
		
		return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
	}

	 /**
     * This method will build out criteria in the key-value paradigm (attribute-value).
     *
     * @param fieldValues
     * @return
     */
    private Criteria buildCriteria(Map<String, Object> fieldValues) {
        Criteria criteria = new Criteria();
        for (Iterator<Map.Entry<String, Object>> i = fieldValues.entrySet().iterator(); i.hasNext();) {
            Map.Entry<String, Object> e = i.next();

            String key = e.getKey();
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
