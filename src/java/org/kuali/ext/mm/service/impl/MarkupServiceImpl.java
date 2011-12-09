package org.kuali.ext.mm.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogItemMarkup;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.dataaccess.QueryElement;
import org.kuali.ext.mm.service.CatalogService;
import org.kuali.ext.mm.service.MarkupService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;


/**
 * @author schneppd
 *
 */
public class MarkupServiceImpl implements MarkupService {

	private BusinessObjectService businessObjectService;

	private MMBusinessObjectDao mmBusinessObjectDao;
	
	private CatalogService catalogService;
	
	public Markup getMarkup(CatalogItem item, Profile profile, Integer quantity, boolean ignorePersonalUse) {
		Markup markup = null;
		List<List<CatalogSubgroup>> subgroupHierarchyList = getCatalogService().getCatalogItemSubgroupHierarchy(item);
		
		Map<String, QueryElement> markupTypeQueryMap = buildMarkupTypeQueryMap(item, profile, subgroupHierarchyList);
		QueryElement markupQuery = new QueryElement();

		for(String markupTypeCode : markupTypeQueryMap.keySet()) {
		    if(ignorePersonalUse && getPersonalUseMarkupTypes().contains(markupTypeCode))
		        continue;
		    markupQuery.getOrList().add(markupTypeQueryMap.get(markupTypeCode));
		}

		QueryElement markupQueryBase = buildMarkupQueryBase(quantity);
		markupQueryBase.getAndList().add(markupQuery);
		Collection<Markup> markupList = getMmBusinessObjectDao().findMatching(Markup.class, markupQueryBase);
		
		markup = getBestMarkupFromList(markupList, subgroupHierarchyList);	
		
		return markup;
	}
	
    /**
     * @see org.kuali.ext.mm.cart.service.MarkupService#getMarkupByType(java.lang.String, org.kuali.ext.mm.businessobject.CatalogItem, org.kuali.ext.mm.businessobject.Profile, java.lang.Integer)
     */
    public Markup getMarkupByType(String markupTypeCode, CatalogItem item, Profile profile,
            Integer quantity) {
        Markup markup = null;
        List<List<CatalogSubgroup>> subgroupHierarchyList = getCatalogService().getCatalogItemSubgroupHierarchy(item);
        Map<String, QueryElement> markupTypeQueryMap = buildMarkupTypeQueryMap(item, profile, subgroupHierarchyList);
        QueryElement markupQuery = markupTypeQueryMap.get(markupTypeCode);
        
        if(markupQuery != null) {
            QueryElement markupQueryBase = buildMarkupQueryBase(quantity);
            markupQueryBase.getAndList().add(markupQuery);
            Collection<Markup> markupList = getMmBusinessObjectDao().findMatching(Markup.class, markupQueryBase);
            
            markup = getBestMarkupFromList(markupList, subgroupHierarchyList); 
        }
        
        return markup;
    }

	public MMDecimal applyMarkup(Markup markup, CatalogItem item) {
		MMDecimal itemPrice = item.getCatalogPrc();

		if(!MMConstants.MarkupType.PASS_THROUGH.equals(markup.getMarkupTypeCd())) {
			if(markup.getMarkupFixed() != null)	{
				itemPrice = new MMDecimal(markup.getMarkupFixed());
			}
			else if(markup.getMarkupRt() != null) {
				itemPrice = new MMDecimal(itemPrice.doubleValue() + (markup.getMarkupRt().doubleValue() * itemPrice.doubleValue()));
			}
		}

		return itemPrice;
	}
	
	private Map<String, QueryElement> buildMarkupTypeQueryMap(CatalogItem item, Profile profile, List<List<CatalogSubgroup>> subgroupHierarchyList) {
	    Map<String, QueryElement> markupTypeQueryMap = new HashMap<String, QueryElement>();
	    
	    Iterator<CatalogItemMarkup> catalogItemMarkups = getCatalogItemMarkups(item).iterator();
        List<String> markupCodes = new ArrayList<String>();
        while(catalogItemMarkups.hasNext()) {
            markupCodes.add(catalogItemMarkups.next().getMarkupCd());
        }
	    
        markupTypeQueryMap.put(MMConstants.MarkupType.PASS_THROUGH, buildPassThroughMarkupQuery(profile));
        markupTypeQueryMap.put(MMConstants.MarkupType.CASH_SALE_MARKUP, buildCashSaleMarkupQuery(profile, null));
        markupTypeQueryMap.put(MMConstants.MarkupType.ACCOUNT_MARKUP, buildChartOrgAccountMarkupQuery(profile, null));
        markupTypeQueryMap.put(MMConstants.MarkupType.ORGANIZATION_MARKUP, buildChartOrgMarkupQuery(profile));
        markupTypeQueryMap.put(MMConstants.MarkupType.ITEM_CATEGORY_MARKUP, buildGroupSubgroupMarkupQuery(null, subgroupHierarchyList));
        markupTypeQueryMap.put(MMConstants.MarkupType.WAREHOUSE_MARKUP, buildGlobalWarehouseMarkupQuery(item));
        
        if(!markupCodes.isEmpty()) {
            markupTypeQueryMap.put(MMConstants.MarkupType.CASH_SALE_CATALOG_ITEM_MARKUP, buildCashSaleMarkupQuery(profile, markupCodes));
            markupTypeQueryMap.put(MMConstants.MarkupType.CATALOG_FIX_MARKUP, buildFixedPriceCatalogItemMarkupQuery(markupCodes));
            markupTypeQueryMap.put(MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP, buildChartOrgAccountMarkupQuery(profile, markupCodes));
            markupTypeQueryMap.put(MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP, buildGroupSubgroupMarkupQuery(markupCodes, subgroupHierarchyList));
        }
	    
	    return markupTypeQueryMap;
	}

	/**
	 * @param profile
	 * @return
	 */
	private QueryElement buildPassThroughMarkupQuery(Profile profile) {
		QueryElement queryElement  = new QueryElement();
		if(profile != null) {
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.ACCOUNT_NUMBER, profile.getAccountNumber()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.COA_CODE, profile.getFinacialChartOfAccountsCode()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.ORG_CODE, profile.getOrganizationCode()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.PASS_THROUGH));
		}

		return queryElement;
	}

	/**
	 * @param profile
	 * @return
	 */
	private QueryElement buildCashSaleMarkupQuery(Profile profile, List<String> markupCodes) {
        boolean catalogItemMarkup = markupCodes != null && !markupCodes.isEmpty();
		QueryElement queryElement  = new QueryElement();
		if(profile == null || profile.isPersonalUseIndicator()) {
		    if(catalogItemMarkup) {
		        queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_CD, markupCodes));
		        queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.CASH_SALE_CATALOG_ITEM_MARKUP));
		    }
		    else 
		        queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.CASH_SALE_MARKUP));
		}

		return queryElement;
	}

	/**
	 * @param profile
	 * @return
	 */
	private QueryElement buildChartOrgAccountMarkupQuery(Profile profile, List<String> markupCodes) {
	    boolean catalogItemMarkup = markupCodes != null && !markupCodes.isEmpty();
	    QueryElement queryElement  = new QueryElement();
	    if(profile != null) {
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.ACCOUNT_NUMBER, profile.getAccountNumber()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.COA_CODE, profile.getFinacialChartOfAccountsCode()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.ORG_CODE, profile.getOrganizationCode()));
    		if(catalogItemMarkup) {
                queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_CD, markupCodes));
                queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP));
            }
            else
                queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ACCOUNT_MARKUP));
	    }
		return queryElement;
	}

	/**
	 * @param profile
	 * @param quantity
	 * @return
	 */
	private QueryElement buildChartOrgMarkupQuery(Profile profile) {
	    QueryElement queryElement  = new QueryElement();
	    if(profile != null) {
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.COA_CODE, profile.getFinacialChartOfAccountsCode()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.ORG_CODE, profile.getOrganizationCode()));
    		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ORGANIZATION_MARKUP));
	    }
		return queryElement;
	}

	/**
	 * @return
	 */
	private QueryElement buildFixedPriceCatalogItemMarkupQuery(List<String> markupCodes) {
		QueryElement queryElement  = new QueryElement();
		if(!markupCodes.isEmpty()) {
			queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_CD, markupCodes));
			queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.CATALOG_FIX_MARKUP));
		}
		return queryElement;
	}

	/**
	 * @param item
	 * @return
	 */
	private QueryElement buildGroupSubgroupMarkupQuery(List<String> markupCodes, List<List<CatalogSubgroup>> subgroupHierarchyList) {
	    QueryElement groupSubgroupRoot = new QueryElement();
		
		for(List<CatalogSubgroup> subgroupHierarchy : subgroupHierarchyList ) {
		    groupSubgroupRoot.getOrList().add(buildSubgroupMarkupQuery(subgroupHierarchy, markupCodes));
		    groupSubgroupRoot.getOrList().add(buildGroupMarkupQuery(subgroupHierarchy.get(0), markupCodes));
		}
		
		return groupSubgroupRoot;
	}

	private QueryElement buildSubgroupMarkupQuery(List<CatalogSubgroup> subgroupList, List<String> markupCodes) {
	    QueryElement queryElement  = new QueryElement();

		boolean catalogItemMarkup = markupCodes != null && !markupCodes.isEmpty();
		List<String> subgroupIds = new ArrayList<String>();
		for(CatalogSubgroup subgroup : subgroupList)
		    subgroupIds.add(subgroup.getCatalogSubgroupId());
		
		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.CATALOG_SUBGROUP_ID, subgroupIds));
				
		if(catalogItemMarkup) {
            queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_CD, markupCodes));
            queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP));
		}
		else
		    queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ITEM_CATEGORY_MARKUP));
		
		return queryElement;
	}

	private QueryElement buildGroupMarkupQuery(CatalogSubgroup subgroup, List<String> markupCodes) {
	    QueryElement queryElement  = new QueryElement();
		
//	    List<String> groupCodes = getShopCartCatalogService().getCatalogGroupCodesForItem(item);
		boolean catalogItemMarkup = markupCodes != null && !markupCodes.isEmpty();

		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.CATALOG_GROUP_CD, subgroup.getCatalogGroupCd()));
		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.CATALOG_SUBGROUP_ID, null));
		if(catalogItemMarkup) {
            queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_CD, markupCodes));
            queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP));
        }
        else
            queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.ITEM_CATEGORY_MARKUP));

		return queryElement;
	}

	private QueryElement buildGlobalWarehouseMarkupQuery(CatalogItem item) {
	    QueryElement queryElement  = new QueryElement();

		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.WAREHOUSE_CD, item.getCatalog().getWarehouseCd()));
		queryElement.getAndList().add(new QueryElement(MMConstants.Markup.MARKUP_TYPE_CD, MMConstants.MarkupType.WAREHOUSE_MARKUP));

		return queryElement;
	}

	private Collection<CatalogItemMarkup> getCatalogItemMarkups(CatalogItem item) {
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put(MMConstants.CatalogItemMarkup.CATALOG_ITEM_ID, item.getCatalogItemId());
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");

		return getBusinessObjectService().findMatching(CatalogItemMarkup.class, fieldValues);
	}

	/**
	 * Used to pick the best markup in the case that more than one valid markup
	 * is found based a set of rules, but can be overridden
	 * if desired for future implementations.
	 *
	 * This implementation chooses the markup with the highest fixed amount value.
	 * If no fixed amounts are found, then the markup with the highest rate is returned.
	 *
	 * @param markupList - list of markups to check
	 * @return the "best" markup that should be applied from the provided list of markups
	 */
	protected Markup getBestMarkupFromList(Collection<Markup> markupList, List<List<CatalogSubgroup>> subgroupHierarchyList) {
		Markup finalMarkup = null;
		
		Collection<Markup> filteredMarkupList = filterMarkupsByTypeHierarchy(markupList, subgroupHierarchyList);

		for(Markup markupCheck : filteredMarkupList) {

			if(finalMarkup == null) {
				finalMarkup = markupCheck;
				continue;
			}
			if(finalMarkup.getMarkupFixed() == null && markupCheck.getMarkupFixed() != null) {
				finalMarkup = markupCheck;
			}
			else if(finalMarkup.getMarkupFixed() != null && markupCheck.getMarkupFixed() != null) {
				if(markupCheck.getMarkupFixed().isGreaterThan(finalMarkup.getMarkupFixed()))
					finalMarkup = markupCheck;
			}
			else if(finalMarkup.getMarkupFixed() == null) {
				if(finalMarkup.getMarkupRt() == null && markupCheck.getMarkupRt() != null)
					finalMarkup = markupCheck;
				else if(finalMarkup.getMarkupRt() != null && markupCheck.getMarkupRt() != null) {
					if(markupCheck.getMarkupRt().isGreaterThan(finalMarkup.getMarkupRt()))
						finalMarkup = markupCheck;
				}
			}
		}

		return finalMarkup;
	}

	/**
	 * Filters the Markup list, keeping only Markups of one Markup Type.
	 * The Markup Type that is kept is determined by the Type Hierarchy. 
	 * 
     * @param markupList
     * @return Collection of Markups highest ranking markups in type hierarchy.
     */
    private Collection<Markup> filterMarkupsByTypeHierarchy(Collection<Markup> markupList, List<List<CatalogSubgroup>> subgroupHierarchyList) {
        Map<String, List<Markup>> markupMap = new HashMap<String, List<Markup>>();
        
        for(Markup markup : markupList) {
            if(!markupMap.containsKey(markup.getMarkupTypeCd())) {
                List<Markup> newMarkupList = new ArrayList<Markup>();
                markupMap.put(markup.getMarkupTypeCd(), newMarkupList);
            }
            markupMap.get(markup.getMarkupTypeCd()).add(markup);                
        }
        
        List<Markup> filteredMarkupList = new ArrayList<Markup>();
        for(String typeCode : getMarkupTypeHierarchy()) {
            if(markupMap.containsKey(typeCode)) {
                //Special handler needed for determining the correct Group/Subgroup markup
                if(MMConstants.MarkupType.ITEM_CATEGORY_MARKUP.equals(typeCode) 
                        || MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP.equals(typeCode)) {
                    filteredMarkupList = filterGroupSubgroupMarkups(markupMap.get(typeCode), subgroupHierarchyList);
                }
                else {
                    filteredMarkupList = markupMap.get(typeCode);
                }    
                break;
            }
        }
        
        return filteredMarkupList;
    }

    /**
     * Determines the correct group/subgroup markup by deepest common subgroup.
     * 
     * @param list
     * @return List of markups matching the correct group/subgroup
     */
    private List<Markup> filterGroupSubgroupMarkups(List<Markup> markupList, List<List<CatalogSubgroup>> subgroupHierarchyList) {
        List<Markup> filteredMarkupList = new ArrayList<Markup>();        
        List<Markup> groupOnlyMarkups = new ArrayList<Markup>();
        
        for(Markup markup : markupList) {
            if(StringUtils.isBlank(markup.getCatalogSubgroupId())) {
                groupOnlyMarkups.add(markup);
                break;
            }
            for(List<CatalogSubgroup> subgroupHierarchy : subgroupHierarchyList) {
                for(CatalogSubgroup subgroup : subgroupHierarchy) {
                    if(subgroup.getCatalogSubgroupId().equals(markup.getCatalogSubgroupId())) {
                        filteredMarkupList.add(markup);
                        break;
                    }
                }
            }            
        }
        if(filteredMarkupList.isEmpty())
            filteredMarkupList = groupOnlyMarkups;
        
        return filteredMarkupList;
    }

    /**
     * Determines the priority of Mark-up Type Codes.
     * 
     * Override this method to change the priority for which
     * Mark-up Type codes are applied.
     * 
     * @return List of Mark-up Type codes in hierarchical order.
     */
    protected List<String> getMarkupTypeHierarchy() {
        List<String> typeCodeHierarchyList = new ArrayList<String>();
        
        typeCodeHierarchyList.add(MMConstants.MarkupType.PASS_THROUGH);
        typeCodeHierarchyList.add(MMConstants.MarkupType.CASH_SALE_CATALOG_ITEM_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.CASH_SALE_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.CATALOG_FIX_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.ACCOUNT_CATALOG_ITEM_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.ACCOUNT_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.ORGANIZATION_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.ITEM_CATEGORY_CATALOG_ITEM_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.ITEM_CATEGORY_MARKUP);
        typeCodeHierarchyList.add(MMConstants.MarkupType.WAREHOUSE_MARKUP);
        
        return typeCodeHierarchyList;
    }
    
    /**
     * Override this method to change the personal use markups.
     * 
     * @return list of Mark-up type codes for personal use mark-ups
     */
    protected List<String> getPersonalUseMarkupTypes() {
        List<String> personalUseTypeList = new ArrayList<String>();
        
        personalUseTypeList.add(MMConstants.MarkupType.CASH_SALE_CATALOG_ITEM_MARKUP);
        personalUseTypeList.add(MMConstants.MarkupType.CASH_SALE_MARKUP);
        
        return personalUseTypeList;
    }

	private QueryElement buildMarkupQueryBase(Integer quantity) {
		QueryElement root = new QueryElement(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");

		//Date check
		Date currentDate = KNSServiceLocator.getDateTimeService().getCurrentSqlDate();
		QueryElement beginDateLT = new QueryElement(MMConstants.Markup.BEGIN_DATE, currentDate);
		beginDateLT.setLessThan(true);
		QueryElement beginDate = new QueryElement();
		beginDate.getOrList().add(beginDateLT);
		beginDate.getOrList().add(new QueryElement(MMConstants.Markup.BEGIN_DATE, null));

		QueryElement endDateGT = new QueryElement(MMConstants.Markup.END_DATE, currentDate);
		endDateGT.setGreaterThan(true);
		QueryElement endDate = new QueryElement();
		endDate.getOrList().add(endDateGT);
		endDate.getOrList().add(new QueryElement(MMConstants.Markup.END_DATE, null));

		QueryElement dateElement = new QueryElement();
		dateElement.getAndList().add(beginDate);
		dateElement.getAndList().add(endDate);

		//Quantity check
		QueryElement fromQuantityLT = new QueryElement(MMConstants.Markup.FROM_QTY, quantity);
		fromQuantityLT.setLessThan(true);
		QueryElement fromQuantity = new QueryElement();
		fromQuantity.getOrList().add(fromQuantityLT);
		fromQuantity.getOrList().add(new QueryElement(MMConstants.Markup.FROM_QTY, null));

		QueryElement toQuantityGT = new QueryElement(MMConstants.Markup.TO_QTY, quantity);
		toQuantityGT.setGreaterThan(true);
		QueryElement toQuantity = new QueryElement();
		toQuantity.getOrList().add(toQuantityGT);
		toQuantity.getOrList().add(new QueryElement(MMConstants.Markup.TO_QTY, null));

		QueryElement quantityElement = new QueryElement();
		quantityElement.getAndList().add(fromQuantity);
		quantityElement.getAndList().add(toQuantity);

		//Add date and quantity checks to root element
		root.getAndList().add(dateElement);
		root.getAndList().add(quantityElement);

		return root;
	}


    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    /**
     * Gets the catalogService property
     * @return Returns the catalogService
     */
    public CatalogService getCatalogService() {
        return this.catalogService;
    }

    /**
     * Sets the catalogService property value
     * @param catalogService The catalogService to set
     */
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public MMBusinessObjectDao getMmBusinessObjectDao() {
        return mmBusinessObjectDao;
    }

    public void setMmBusinessObjectDao(MMBusinessObjectDao mmBusinessObjectDao) {
        this.mmBusinessObjectDao = mmBusinessObjectDao;
    }
}
