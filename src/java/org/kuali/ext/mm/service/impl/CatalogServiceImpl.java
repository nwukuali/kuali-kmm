package org.kuali.ext.mm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogGroup;
import org.kuali.ext.mm.businessobject.CatalogItem;
import org.kuali.ext.mm.businessobject.CatalogRestriction;
import org.kuali.ext.mm.businessobject.CatalogSubgroup;
import org.kuali.ext.mm.businessobject.CatalogSubgroupItem;
import org.kuali.ext.mm.businessobject.Markup;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.dataaccess.MMBusinessObjectDao;
import org.kuali.ext.mm.integration.FinancialSystemAdaptorFactory;
import org.kuali.ext.mm.integration.service.FinancialTaxService;
import org.kuali.ext.mm.service.AddressService;
import org.kuali.ext.mm.service.CatalogService;
import org.kuali.ext.mm.service.MarkupService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DateTimeService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;


/**
 * @author schneppd
 *
 */
public class CatalogServiceImpl implements CatalogService {

	private BusinessObjectService businessObjectService;

	private MMBusinessObjectDao mmBusinessObjectDao;

	private MarkupService markupService;

	public MMDecimal computeCatalogItemPrice(CatalogItem item, Profile profile, Integer quantity) {
		return computeCatalogItemPrice(item, profile, quantity, false);
	}
	
	public MMDecimal computeCatalogItemPriceNoPersonalUse(CatalogItem item, Profile profile, Integer quantity) {
        return computeCatalogItemPrice(item, profile, quantity, true);
    }
	
	private MMDecimal computeCatalogItemPrice(CatalogItem item, Profile profile, Integer quantity, boolean ignorePersonalUse) {
	    if(ObjectUtils.isNull(item))
            return null;

        Markup markup = getMarkupService().getMarkup(item, profile, quantity, ignorePersonalUse);

        if(markup == null)
            return item.getCatalogPrc();

        return getMarkupService().applyMarkup(markup, item);
	}

	public MMDecimal computeCatalogItemPriceByIds(String distributorNumber, String catalogId, String profileId, Integer quantity) {
		CatalogItem item = null;
		Profile profile = null;
		if(StringUtils.isNotBlank(distributorNumber) || StringUtils.isNotBlank(catalogId))
			item = getCatalogItem(distributorNumber.trim(), catalogId);
		if(StringUtils.isNotBlank(profileId))
			profile = SpringContext.getBean(BusinessObjectService.class).findBySinglePrimaryKey(Profile.class, profileId);

		if(item == null || profile == null)
			return null;

		Markup markup = getMarkupService().getMarkup(item, profile, quantity, false);

		if(markup == null)
			return item.getCatalogPrc();

		return getMarkupService().applyMarkup(markup, item);
	}
	
	public MMDecimal computeCatalogItemTax(CatalogItem item, MMDecimal priceAmount, Address shippingAddress, boolean willCall) {
	    DateTimeService dateService = KNSServiceLocator.getDateTimeService();
        FinancialSystemAdaptorFactory adaptorFactory = SpringContext.getBean(FinancialSystemAdaptorFactory.class);
        FinancialTaxService taxService = null;
        if(adaptorFactory.isSystemAvailable()) {
            taxService = adaptorFactory.getFinancialTaxService();
        }
        Address taxAddress = shippingAddress;
        MMDecimal finalTax = new MMDecimal(0.0);
        if(item.isTaxableInd()) {
            //Items for pick up should be taxed based on the pickup location
            if(willCall && ObjectUtils.isNotNull(item.getCatalog().getWarehouse())
                    && ObjectUtils.isNotNull(item.getCatalog().getWarehouse().getBillingProfile())) {
                Profile warehouseProfile = item.getCatalog().getWarehouse().getBillingProfile();
                taxAddress = SpringContext.getBean(AddressService.class).getShippingAddress(warehouseProfile);
            }
    
            if(taxAddress != null && taxService != null) {
                KualiDecimal tax = taxService.getTotalSalesTaxAmount(dateService.getCurrentSqlDate(), taxAddress.getAddressPostalCode(), priceAmount.kualiDecimalValue());
                finalTax = new MMDecimal(tax);
            }
        }
        
        return finalTax;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCatalogService#isCatalogAuthorized(org.kuali.ext.mm.businessobject.Catalog, org.kuali.ext.mm.businessobject.Profile)
	 */
	public boolean isCatalogAuthorized(Catalog catalog, Profile customerProfile) {
        String orgCode = (customerProfile != null) ? customerProfile.getOrganizationCode() : "";
        String chartCode = (customerProfile != null) ? customerProfile.getFinacialChartOfAccountsCode() : "";
        String accountNumber = (customerProfile != null) ? customerProfile.getAccountNumber() : "";
        
        return isCatalogAuthorized(catalog, chartCode, accountNumber, orgCode);
	}
	
	/**
	 * @param catalog
	 * @param chartCode
	 * @param accountNumber
	 * @param orgCode
	 * @return true if profile is authorized to access catalog
	 */
	private boolean isCatalogAuthorized(Catalog catalog, String chartCode, String accountNumber, String orgCode) {
	    Integer allowMatchRank = 1;
        Integer disallowMatchRank = 0;        
        
        List<CatalogRestriction> allowRestrictions = new ArrayList<CatalogRestriction>();
        List<CatalogRestriction> disallowRestrictions = new ArrayList<CatalogRestriction>();
        
        for(CatalogRestriction restriction : getCatalogRestrictions(catalog)) {
            if(MMConstants.CatalogRestriction.OPTION_ALLOW.equals(restriction.getRestrictionCode()))
                allowRestrictions.add(restriction);
            else if(MMConstants.CatalogRestriction.OPTION_DISALLOW.equals(restriction.getRestrictionCode()))
                disallowRestrictions.add(restriction);
        }
        
        if(!allowRestrictions.isEmpty()) {
            allowMatchRank = getCatalogRestrictionMatchRank(allowRestrictions, chartCode, accountNumber, orgCode);
        }    
        if(!disallowRestrictions.isEmpty()) {
            disallowMatchRank = getCatalogRestrictionMatchRank(disallowRestrictions, chartCode, accountNumber, orgCode);
        }
        
        if(allowMatchRank > disallowMatchRank)
            return true;
        
        return false;
	}

	/**
     * @param catalog
     * @return collection of CatalogRestrictions for a catalog with the given restrict code.
     */
    private Collection<CatalogRestriction> getCatalogRestrictions(Catalog catalog) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        fieldValues.put(MMConstants.CatalogRestriction.CATALOG_ID, catalog.getCatalogId());

        return getBusinessObjectService().findMatching(CatalogRestriction.class, fieldValues);
    }
    
    /**
     * Used to determine if a list of catalog restrictions contains a match for Organization code or a match
     * for Chart and Account.  In this case a match is defined as (rank)
     * 0. No matches
     * 1. Chart match
     * 2. Chart/Org match
     * 3. Chart/Account match
     * 
     * @param catalogRestrictions
     * @param chartCode
     * @param accountNumber
     * @param orgCode     
     * @return an Integer representing the match rank, or quality of the match.
     */
    private Integer getCatalogRestrictionMatchRank(Collection<CatalogRestriction> catalogRestrictions, String chartCode, String accountNumber, String orgCode) {
        Iterator<CatalogRestriction> it = catalogRestrictions.iterator();

        Integer bestRank = 0;
        while(it.hasNext()) {
            CatalogRestriction catalogRestriction = it.next();            
            Integer currentRank = 0;
            if(StringUtils.isNotBlank(catalogRestriction.getFinacialChartOfAccountsCode())
                    && StringUtils.equals(chartCode, catalogRestriction.getFinacialChartOfAccountsCode())) {
                currentRank += 1;
            
                if(StringUtils.isNotBlank(catalogRestriction.getAccountNumber())) {
                    currentRank = (StringUtils.equals(accountNumber, catalogRestriction.getAccountNumber()) ? currentRank + 2 : 0);
                }   
                else if(StringUtils.isNotBlank(catalogRestriction.getOrganizationCode())) {
                    currentRank = (StringUtils.equals(orgCode, catalogRestriction.getOrganizationCode()) ? currentRank + 1 : 0);                        
                }
                             
            }            
            if(bestRank < currentRank)
                bestRank = currentRank;
            if(bestRank >= 3)
                return bestRank;         
        }
        return bestRank;
    }
    

    public boolean isCatalogItemAuthorized(CatalogItem item, String chartCode, String accountNumber, String orgCode) {
        if(ObjectUtils.isNull(item.getCatalog())) {
            item.refreshReferenceObject(MMConstants.CatalogItem.CATALOG);
        }
        return isCatalogAuthorized(item.getCatalog(), chartCode, accountNumber, orgCode);
    }
    

    /**
     * @see org.kuali.ext.mm.cart.service.ShopCartCatalogService#getCatalogById(java.lang.String)
     */
    public Catalog getCatalogById(String catalogId) {
        return getBusinessObjectService().findBySinglePrimaryKey(Catalog.class, catalogId);
    }

    public CatalogItem getCatalogItemById(String catalogItemId) {
		return getBusinessObjectService().findBySinglePrimaryKey(CatalogItem.class, catalogItemId);
	}

	public CatalogItem getCatalogItem(String distributorNumber, String catalogId) {
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		fieldValues.put(MMConstants.CatalogItem.CATALOG_ID, catalogId);
		fieldValues.put(MMConstants.CatalogItem.DIST_NUMBER, distributorNumber);

		Collection results = getBusinessObjectService().findMatching(CatalogItem.class, fieldValues);

		return (results != null && results.iterator().hasNext() ? (CatalogItem)results.iterator().next() : null);
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public MMBusinessObjectDao getMmBusinessObjectDao() {
        return mmBusinessObjectDao;
    }

    public void setMmBusinessObjectDao(MMBusinessObjectDao mmBusinessObjectDao) {
        this.mmBusinessObjectDao = mmBusinessObjectDao;
    }

    public Collection<CatalogGroup> getCatalogGroups() {
	    Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
        fieldValues.put(MMConstants.CatalogGroup.DISPLAYABLE_IND, "Y");

        return getBusinessObjectService().findMatchingOrderBy(CatalogGroup.class, fieldValues, MMConstants.CatalogGroup.CATALOG_GROUP_NM, true);
	}

	public CatalogGroup getCatalogGroupbyId(String groupId) {
		return getBusinessObjectService().findBySinglePrimaryKey(CatalogGroup.class, groupId);
	}

	public CatalogSubgroup getCatalogSubgroupbyId(String subgroupId) {
		return getBusinessObjectService().findBySinglePrimaryKey(CatalogSubgroup.class, subgroupId);
	}

	public List<CatalogSubgroup> getDirectSubgroupsFromCatalogGroup(CatalogGroup group) {
		List<CatalogSubgroup> children = new ArrayList<CatalogSubgroup>();
		for(CatalogSubgroup subgroup : group.getCatalogSubgroups()) {
			if(ObjectUtils.isNull(subgroup.getPriorCatalogSubgroup()))
				children.add(subgroup);
		}
		return children;
	}

	/**
	 * @see org.kuali.ext.mm.cart.service.ShopCartCatalogService#getCurrentCatalogByCatalogCode(java.lang.String)
	 */
	public Catalog getCurrentCatalogByCatalogCode(String catalogCode) {
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		fieldValues.put(MMConstants.Catalog.CATALOG_CD, catalogCode);
		fieldValues.put(MMConstants.Catalog.CURRENT_IND, "Y");

		Collection results = getBusinessObjectService().findMatching(Catalog.class, fieldValues);

		return (results.iterator().hasNext() ? (Catalog)results.iterator().next() : null);
	}

	public Collection<CatalogSubgroupItem> getCatalogItemSubgroups(CatalogItem item) {
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put(MMConstants.MMPersistableBusinessObject.ACTIVE, "Y");
		fieldValues.put(MMConstants.CatalogSubgroupItem.CATALOG_ITEM_ID, item.getCatalogItemId());

		return getBusinessObjectService().findMatching(CatalogSubgroupItem.class, fieldValues);
	}
	
	
	
	public List<List<CatalogSubgroup>> getCatalogItemSubgroupHierarchy(CatalogItem item) {
	    List<List<CatalogSubgroup>> subgroupHierarchyList = new ArrayList<List<CatalogSubgroup>>();
	    Collection<CatalogSubgroupItem> subgroupItems = getCatalogItemSubgroups(item);
	    for(CatalogSubgroupItem subgroupItem : subgroupItems) {
	        List<CatalogSubgroup> subgroupHierarchy = new ArrayList<CatalogSubgroup>();
	        subgroupHierarchy.add(subgroupItem.getCatalogSubgroup());	
	        CatalogSubgroup priorSubgroup = subgroupHierarchy.get(subgroupHierarchy.size()-1).getPriorCatalogSubgroup();
	        while(ObjectUtils.isNotNull(priorSubgroup)) {
	            subgroupHierarchy.add(priorSubgroup);
	            priorSubgroup = subgroupHierarchy.get(subgroupHierarchy.size()-1).getPriorCatalogSubgroup();
	        }
	        subgroupHierarchyList.add(subgroupHierarchy);
	    }
	    return subgroupHierarchyList;
	}
	
    public Collection<Catalog> getTrueBuyoutCatalogs(Profile customerProfile, boolean allowAll) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MMConstants.Catalog.CATALOG_TYPE_CD, MMConstants.CatalogType.TRUE_BUYOUT);
        fieldValues.put(MMConstants.Catalog.CURRENT_IND, "Y");

        Collection<Catalog> results = getBusinessObjectService().findMatching(Catalog.class, fieldValues);
        Iterator<Catalog> itCatalog = results.iterator();
        Collection<Catalog> availableCatalogs = new ArrayList<Catalog>();
        while(itCatalog.hasNext()) {
            Catalog catalog = itCatalog.next();
            if(isCatalogAuthorized(catalog, customerProfile) || allowAll) {
                availableCatalogs.add(catalog);
            }
        }
        return availableCatalogs;
    }
	

	public void setMarkupService(MarkupService markupService) {
		this.markupService = markupService;
	}


	public MarkupService getMarkupService() {
		return markupService;
	}

}
