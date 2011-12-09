package org.kuali.ext.mm.cart.dataaccess;

import java.util.Collection;
import java.util.Map;

import org.kuali.ext.mm.cart.valueobject.PagingElement;
import org.kuali.ext.mm.cart.valueobject.SortElement;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.rice.kns.dao.BusinessObjectDao;

public interface ShopCartBusinessObjectDao extends BusinessObjectDao {

	public Collection findMatchingBoundedAndOrderBy(Class clazz, Map<String, Object> fieldValues, String sortField, boolean sortAscending, int maxRows);

	public Collection findMatchingBoundedAndOrderBy(Class clazz, QueryElement queryElement, SortElement sortElement, int maxRows);
	
	public Collection findMatchingPerPageBoundedAndOrderBy(Class clazz, QueryElement queryElement, SortElement sortElement, PagingElement pagingElement);

	public Collection findMatchingPerPageBoundedAndOrderBy2(Class clazz, QueryElement queryElement, String[] attributes, SortElement sortElement, PagingElement pagingElement);
	
	public Collection findMatching(Class clazz, QueryElement queryElement);
	
	public Collection findMatchingOrderBy(Class clazz, QueryElement queryElement, String sortField, boolean sortAscending);

	public Integer countMatching(Class clazz, QueryElement queryElement);
	
	public Integer countMatchingDistinct(Class clazz, QueryElement queryElement, String[] primaryKeys);

	public Collection<Object[]> getReport(Class clazz, QueryElement queryElement, String[] attributes, String[] groupBy, String[] orderBy, boolean orderAscending, int maxRows);
	
	public Collection findByReportQuery(Class clazz, Map<String, Object> fieldValues, String sortField, boolean sortAscending);

	public Collection findByReportQuery(Class clazz, QueryElement queryElement, String sortField, boolean sortAscending);
}
