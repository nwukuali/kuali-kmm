package org.kuali.ext.mm.dataaccess.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.sys.service.MMPersistenceStructureService;
import org.kuali.ext.mm.utility.StopWatch;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.core.framework.persistence.platform.MySQLDatabasePlatform;
import org.kuali.rice.core.framework.persistence.platform.OracleDatabasePlatform;
import org.kuali.rice.krad.util.OjbCollectionAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class MMBusinessObjectDaoOjb extends PlatformAwareDaoBaseOjb implements MMBusinessObjectDao, OjbCollectionAware {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MMBusinessObjectDaoOjb.class);

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
	
    public Collection findMatching(Class clazz, Map fieldValues) {
        Criteria criteria = buildCriteria(fieldValues);

        return getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(clazz, criteria));
    }
    
    public Collection findMatchingOrderBy(Class clazz, Map fieldValues, String sortField, boolean sortAscending) {
        Criteria criteria = buildCriteria(fieldValues);
        QueryByCriteria queryByCriteria = new QueryByCriteria(clazz, criteria);

        if (sortAscending) {
            queryByCriteria.addOrderByAscending(sortField);
        }
        else {
            queryByCriteria.addOrderByDescending(sortField);
        }

        return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
    }

    public int countMatching(Class clazz, Map fieldValues) {
        Criteria criteria = buildCriteria(fieldValues);

        return getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(clazz, criteria));
    }
    
    public int countMatching(Class clazz, QueryElement queryElement) {
        Criteria criteria = buildCriteriaFromQueryElement(clazz, queryElement);

        return getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(clazz, criteria));
    }
    
    public Collection findMatching(Class clazz, QueryElement queryElement) {
        Criteria criteria = buildCriteriaFromQueryElement(clazz, queryElement);

        return getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(clazz, criteria));
    }
    
    public Collection findMatchingOrderBy(Class clazz, QueryElement queryElement, String sortField, boolean sortAscending) {
        Criteria criteria = buildCriteriaFromQueryElement(clazz, queryElement);
        QueryByCriteria queryByCriteria = new QueryByCriteria(clazz, criteria);

        if (sortAscending) {
            queryByCriteria.addOrderByAscending(sortField);
        }
        else {
            queryByCriteria.addOrderByDescending(sortField);
        }

        return getPersistenceBrokerTemplate().getCollectionByQuery(queryByCriteria);
    }
    
    public Collection<Object[]> getReport(Class clazz, QueryElement queryElement, String[] attributes, String[] groupBy, String[] orderBy, boolean orderAscending, int maxRows) {
        StopWatch.start();
        Criteria criteria = buildCriteriaFromQueryElement(clazz, queryElement);
        
        ReportQueryByCriteria queryByCriteria = QueryFactory.newReportQuery(clazz, attributes, criteria, false);
        if(maxRows > 0)
            queryByCriteria.setEndAtIndex(maxRows);
        for(String element : groupBy) {
            queryByCriteria.addGroupBy(element);
        }

        for(String element : orderBy) {
            queryByCriteria.addOrderBy(element, orderAscending);
        }
        
        Iterator<Object[]> it = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(queryByCriteria);
        Collection<Object[]> results = new ArrayList<Object[]>();

        while(it!=null && it.hasNext()) {
            results.add(it.next());
        }
        StopWatch.stop();
        return results;
    }

	 /**
     * This method will build out criteria in the key-value paradigm (attribute-value).
     * This supports null field values, which is the cause for not using BusinessObjectDao.
     *
     * @param fieldValues
     * @return
     */
    protected Criteria buildCriteria(Map fieldValues) {
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
    
    /**
     * Builds criteria from the QueryElement structure
     *
     * @param fieldValues
     * @return
     */
    public Criteria buildCriteriaFromQueryElement(Class clazz, QueryElement queryElement) {
        Criteria criteria = new Criteria();

        if(!queryElement.isTextIndexSearch()) {
            for(QueryElement andElement : queryElement.getAndList()) {
                criteria.addAndCriteria(buildCriteriaFromQueryElement(clazz, andElement));
            }
            for(QueryElement orElement : queryElement.getOrList()) {
                criteria.addOrCriteria(buildCriteriaFromQueryElement(clazz, orElement));
            }
        }

        if(StringUtils.isNotBlank(queryElement.getField())) {
            if(queryElement.getValue() instanceof Collection) {
                Collection collectionValue = (Collection)queryElement.getValue();
                if(!collectionValue.isEmpty()) {
                    criteria.addIn(queryElement.getField(), collectionValue);
                }
            }
            else if(queryElement.getValue() == null) {
                criteria.addIsNull(queryElement.getField());
            }
            else {                
                if(queryElement.isExactMatch())
                    criteria.addEqualTo(queryElement.getField(), queryElement.getValue());
                else if(queryElement.isTextIndexSearch()) 
                    criteria.addSql(getTextIndexSearchCriteriaSql(clazz, queryElement));
                else if(queryElement.isLessThan())
                    criteria.addLessOrEqualThan(queryElement.getField(), queryElement.getValue());
                else if(queryElement.isGreaterThan())
                    criteria.addGreaterOrEqualThan(queryElement.getField(), queryElement.getValue());
                else {
                    String upperCaseValue = String.valueOf(queryElement.getValue()).toUpperCase();
                    if(queryElement.isStartsWith())
                        criteria.addLike("UPPER(SUBSTR("+queryElement.getField()+",0," + (upperCaseValue.length()) + "))", upperCaseValue + "%");
                    else
                        criteria.addLike("UPPER("+queryElement.getField()+")", "%" + upperCaseValue + "%");                    
                }
            }
        }
        criteria.setNegative(queryElement.isNegative());
        return criteria;
    }

    /**
     * @param queryElement
     * @return
     */
    protected String getTextIndexSearchCriteriaSql(Class clazz, QueryElement queryElement) {
        String sql = null;
        String columnName = SpringContext.getBean(MMPersistenceStructureService.class).getColumnName(clazz, queryElement.getField());
        String text = String.valueOf(queryElement.getValue()).toUpperCase();
        if(getDbPlatform() instanceof OracleDatabasePlatform) {
            sql = "CATSEARCH(" + columnName + ",'" + text + "','') > 0";
        }
        else if(getDbPlatform() instanceof MySQLDatabasePlatform) {
            sql = "MATCH(" + columnName + ") AGAINST('" + text + "' IN NATURAL LANGUAGE MODE)";
        }
        return sql;
    }    
    
}
