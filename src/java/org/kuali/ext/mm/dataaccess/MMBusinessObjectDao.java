package org.kuali.ext.mm.dataaccess;

import java.util.Collection;
import java.util.Map;

import org.apache.ojb.broker.query.Criteria;

public interface MMBusinessObjectDao {
    /**
     * @param clazz
     * @param fieldValues
     * @param sortField
     * @param sortAscending
     * @param maxRows
     * @return
     */
    public Collection findMatchingBoundedAndOrderBy(Class clazz, Map fieldValues, String sortField, boolean sortAscending, int maxRows);

    /**
     * @param clazz
     * @param fieldValues
     * @return
     */
    public Collection findMatching(Class clazz, Map fieldValues);
    
    /**
     * @param clazz
     * @param fieldValues
     * @param sortField
     * @param sortAscending
     * @return
     */
    public Collection findMatchingOrderBy(Class clazz, Map fieldValues, String sortField, boolean sortAscending);

    /**
     * @param clazz
     * @param fieldValues
     * @return
     */
    public int countMatching(Class clazz, Map fieldValues);
    
    /**
     * @param clazz
     * @param queryElement
     * @return
     */
    public int countMatching(Class clazz, QueryElement queryElement);
    
    /**
     * @param clazz
     * @param queryElement
     * @return
     */
    public Collection findMatching(Class clazz, QueryElement queryElement);
    
    /**
     * @param clazz
     * @param queryElement
     * @param sortField
     * @param sortAscending
     * @return
     */
    public Collection findMatchingOrderBy(Class clazz, QueryElement queryElement, String sortField, boolean sortAscending);
    
    /**
     * @param clazz
     * @param queryElement
     * @param attributes
     * @param groupBy
     * @param orderBy
     * @param orderAscending
     * @param maxRows - less than 1 will return all results
     * @return a collection of Object arrays containing the desired report information
     */
    public Collection<Object[]> getReport(Class clazz, QueryElement queryElement, String[] attributes, String[] groupBy, String[] orderBy, boolean orderAscending, int maxRows);
    
    /**
     * @param queryElement
     * @return a Criteria object built from a QueryElement tree structure
     */
    public Criteria buildCriteriaFromQueryElement(Class clazz, QueryElement queryElement);
}
