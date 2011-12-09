package org.kuali.ext.mm.businessobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialUnitOfMeasure;
import org.kuali.ext.mm.service.StockService;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.ext.mm.util.MMUtil;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.TypedArrayList;



@Entity
@Table(name = "MM_STOCK_T")
public class Stock extends MMPersistableBusinessObjectBase implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String actionCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_AGREEMENT_NBR")
    private Agreement agreement;
    @Column(name = "STOCK_AGREEMENT_NBR", length = 15)
    private String agreementNbr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUY_UNIT_OF_ISSUE_CD")
    private FinancialUnitOfMeasure buyUnitOfIssue;
    @Column(name = "BUY_UNIT_OF_ISSUE_CD", length = 10)
    private String buyUnitOfIssueCd;
    @Column(name = "BUY_UNIT_OF_ISSUE_RT", precision = 8)
    private MMDecimal buyUnitOfIssueRt;
    @Column(name = "CATALOG_ID", precision = 18)
    private String catalogId;
    @Column(name = "CYCLE_CNT_CD", length = 1)
    private String cycleCntCd;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CYCLE_CNT_CD")
    private CycleCount cycleCount;

    private RentalObjectCode rentalObject;

    private String rentalObjectCode;

    private List<Rental> rentals;

    @Column(name = "DISTRIBUTOR_NBR", nullable = false, length = 30)
    private String distributorNbr;

    private HazardousMateriel hazardousMateriel;

    private List<HazardousMateriel> hazardousMateriels;

    @Column(name = "LAST_CHANGE_ACTV_IND_DT", length = 7)
    private Date lastChangeActvIndDt;

    @Column(name = "MANUFACTURER_NBR", length = 30)
    private String manufacturerNbr;

    @Column(name = "MARKUP_CD")
    private String markupCd;

    @Column(name = "MAXIMUM_ORDER_QTY", precision = 8)
    private KualiDecimal maximumOrderQty;

    @Column(name = "MINIMUM_ORDER_QTY", precision = 8)
    private KualiDecimal minimumOrderQty;


    @Column(name = "OBSOLETE_IND", length = 1)
    private boolean obsoleteInd;

    @Column(name = "PACKAGING_UNIT_DESC", length = 80)
    private String packagingUnitDesc;

    @Column(name = "PERISHABLE_IND", length = 1)
    private boolean perishableInd;

    @Column(name = "RECYCLED_IND", length = 1)
    private boolean recycledInd;

    private java.sql.Date removeUntilDate;

    @Column(name = "REORDER_POINT_QTY", precision = 8)
    private KualiDecimal reorderPointQty;

    @Column(name = "RESTRICTED_ROUTE_CD", length = 2)
    private String restrictedRouteCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTRICTED_ROUTE_CD")
    private RestrictedRouteCode restrictedRouteCode;

    @Column(name = "SAFETY_STOCK_DAYS", precision = 8)
    private Integer safetyStockDays;

    @Column(name = "SAFETY_STOCK_QTY", precision = 8)
    private KualiDecimal safetyStockQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALES_UNIT_OF_ISSUE_CD")
    private UnitOfIssue salesUnitOfIssue;

    @Column(name = "SALES_UNIT_OF_ISSUE_CD", length = 10)
    private String salesUnitOfIssueCd;

    @Column(name = "SALES_UNIT_OF_ISSUE_RT", precision = 8)
    private MMDecimal salesUnitOfIssueRt;

    @Column(name = "SHIPPING_HT", precision = 8)
    private KualiDecimal shippingHt;

    @Column(name = "SHIPPING_LH", precision = 8)
    private KualiDecimal shippingLh;

    @Column(name = "SHIPPING_WD", precision = 8)
    private KualiDecimal shippingWd;

    @Column(name = "SHIPPING_WGT", precision = 8)
    private KualiDecimal shippingWgt;

    @Column(name = "SOLE_SOURCE_IND", length = 1)
    private boolean soleSourceInd;

    private List<StockAttribute> stockAttributes;

    private List<StockBalance> stockBalances;

    private List<StockCount> stockCounts;

    @Column(name = "STOCK_CREATE_DT", length = 10)
    private Date stockCreateDt;

    @Column(name = "STOCK_DESC", length = 400)
    private String stockDesc;

    @Column(name = "STOCK_DISTRIBUTOR_NBR", length = 30)
    private String stockDistributorNbr;

    private StockHistory stockHistory;

    private List<StockHistory> stockHistoryList;

    @Id
    @Column(name = "STOCK_ID", unique = true, nullable = false, length = 36)
    private String stockId;

    private List<StockPackNote> stockPackNotes;

    private MMDecimal stockPrice = MMDecimal.ZERO;

    private List<StockCost> stockPrices;

    private StockTransReason stockTransReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_TYPE_CD")
    private StockType stockType;

    @Column(name = "STOCK_TYPE_CD", length = 2)
    private String stockTypeCode;

    @Column(name = "STOCK_URL_IMG", length = 250)
    private String stockUrlImg;

    @Column(name = "SUBSTITUTE_DISTRIBUTOR_NBR", length = 30)
    private String substituteDistributorNbr;

    @Column(name = "SURCHARGE_IND", length = 1)
    private boolean surchargeInd;

    @Column(name = "TAXABLE_IND", length = 1)
    private boolean taxableInd;

    @Column(name = "UPC_CD", length = 10)
    private String upcCd;

    @Column(name = "WILLCALL_IND", length = 1)
    private boolean willcallInd;

    @SuppressWarnings("unchecked")
    public Stock() {
        stockPrices = new TypedArrayList(StockCost.class);
        stockPackNotes = new TypedArrayList(StockPackNote.class);
        hazardousMateriels = new TypedArrayList(HazardousMateriel.class);
        stockBalances = new TypedArrayList(StockBalance.class);
        stockAttributes = new TypedArrayList(StockAttribute.class);
        stockHistoryList = new TypedArrayList(StockHistory.class);
        rentals = new ArrayList<Rental>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List buildListOfDeletionAwareLists() {
        List deletableCollection = super.buildListOfDeletionAwareLists();
        deletableCollection.add(stockPackNotes);
        deletableCollection.add(hazardousMateriels);
        deletableCollection.add(stockAttributes);
        return deletableCollection;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Stock other = (Stock) obj;
        if (stockDistributorNbr == null) {
            if (other.stockDistributorNbr != null)
                return false;
        }
        else if (!stockDistributorNbr.equals(other.stockDistributorNbr))
            return false;
        return true;
    }

    public String getActionCode() {
        return actionCode;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public String getAgreementNbr() {
        return agreementNbr;
    }


    /**
     * Gets the buyUnitOfIssue property
     * 
     * @return Returns the buyUnitOfIssue
     */
    public FinancialUnitOfMeasure getBuyUnitOfIssue() {
        return this.buyUnitOfIssue;
    }

    public String getBuyUnitOfIssueCd() {
        return buyUnitOfIssueCd;
    }

    public MMDecimal getBuyUnitOfIssueRt() {
        return buyUnitOfIssueRt;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public StockCost getCurrentStockCost() {
        return SpringContext.getBean(StockService.class).getStockCostForStock(this.getStockId());
    }

    public String getCycleCntCd() {
        return cycleCntCd;
    }

    public CycleCount getCycleCount() {
        return cycleCount;
    }

    public String getDistributorNbr() {
        return distributorNbr;
    }

    public HazardousMateriel getHazardousMateriel() {
        return this.hazardousMateriel;
    }

    public List<HazardousMateriel> getHazardousMateriels() {
        return hazardousMateriels;
    }

    public Date getLastChangeActvIndDt() {
        return lastChangeActvIndDt;
    }

    public String getManufacturerNbr() {
        return manufacturerNbr;
    }

    public String getMarkupCd() {
        return markupCd;
    }

    public KualiDecimal getMaximumOrderQty() {
        return maximumOrderQty;
    }

    public KualiDecimal getMinimumOrderQty() {
        return minimumOrderQty;
    }

    public String getPackagingUnitDesc() {
        return packagingUnitDesc;
    }

    public java.sql.Date getRemoveUntilDate() {
        return this.removeUntilDate;
    }

    public KualiDecimal getReorderPointQty() {
        return reorderPointQty;
    }

    public String getRestrictedRouteCd() {
        return restrictedRouteCd;
    }

    public RestrictedRouteCode getRestrictedRouteCode() {
        return restrictedRouteCode;
    }


    public Integer getSafetyStockDays() {
        return safetyStockDays;
    }

    public KualiDecimal getSafetyStockQty() {
        return safetyStockQty;
    }

    public UnitOfIssue getSalesUnitOfIssue() {
        return salesUnitOfIssue;
    }

    public String getSalesUnitOfIssueCd() {
        return salesUnitOfIssueCd;
    }

    public MMDecimal getSalesUnitOfIssueRt() {
        return salesUnitOfIssueRt;
    }

    public KualiDecimal getShippingHt() {
        return shippingHt;
    }

    public KualiDecimal getShippingLh() {
        return shippingLh;
    }

    public KualiDecimal getShippingWd() {
        return shippingWd;
    }

    public KualiDecimal getShippingWgt() {
        return shippingWgt;
    }

    public boolean getSoleSourceInd() {
        return soleSourceInd;
    }

    public List<StockAttribute> getStockAttributes() {
        return stockAttributes;
    }

    public List<StockBalance> getStockBalances() {
        return stockBalances;
    }

    public List<StockCount> getStockCounts() {
        return stockCounts;
    }

    public Date getStockCreateDt() {
        return stockCreateDt;
    }

    public String getStockDesc() {
        return stockDesc;
    }

    public String getStockDistributorNbr() {
        return stockDistributorNbr;
    }

    public StockHistory getStockHistory() {
        return stockHistory;
    }

    public List<StockHistory> getStockHistoryList() {
        return stockHistoryList;
    }

    public String getStockId() {
        return stockId;
    }

    public List<StockPackNote> getStockPackNotes() {
        return stockPackNotes;
    }


    public MMDecimal getStockPrice() {

        List<StockCost> stockPrices = this.getStockPrices();


        if (MMUtil.isCollectionEmpty(stockPrices))
            return stockPrice;

        String currentStockPriceCode = getCurrentStockPriceCode();

        for (StockCost sprice : stockPrices) {
            if (sprice.getCostCd().equals(currentStockPriceCode)) {
                stockPrice = sprice.getStockCst();
                return stockPrice;
            }
        }
        return stockPrice;
    }

    public List<StockCost> getStockPrices() {
        return stockPrices;
    }

    @SuppressWarnings("unchecked")
    /*
     * This method return an ArrayList Content of the ArrayList (WarehouseCode:StockQuntityOnHand)
     */
    public ArrayList getStockQuntityOnHandPerWarehouse() {

        BusinessObjectService bOS = SpringContext.getBean(BusinessObjectService.class);
        HashMap fieldValues = new HashMap();
        fieldValues.put("stockId", this.getStockId());
        List<StockBalance> sBal = (List<StockBalance>) bOS.findMatching(StockBalance.class,
                fieldValues);

        ArrayList warehouseList = new ArrayList();
        ArrayList warehouseQOH = new ArrayList();

        for (int ii = 0; ii < sBal.size(); ii++) {
            StockBalance sBL = sBal.get(ii);
            String warehouseCode = sBL.getBin().getZone().getWarehouse().getWarehouseCd();
            if (!warehouseList.contains(warehouseCode)) {
                warehouseList.add(warehouseCode);
            }
        }

        for (int i = 0; i < warehouseList.size(); i++) {
            String wareHouseLstCode = (String) warehouseList.get(i);
            int qtyOnHand = 0;
            String wareHCode = "";
            for (int y = 0; y < sBal.size(); y++) {
                StockBalance sB = sBal.get(y);
                wareHCode = sB.getBin().getZone().getWarehouse().getWarehouseCd();
                if (wareHCode.equalsIgnoreCase(wareHouseLstCode)) {
                    qtyOnHand = qtyOnHand + sB.getQtyOnHand();
                }
            }

            warehouseQOH.add(wareHouseLstCode + ":" + qtyOnHand);
        }
        return warehouseQOH;
    }

    public StockTransReason getStockTransReason() {
        return stockTransReason;
    }

    public StockType getStockType() {
        return stockType;
    }

    public String getStockTypeCode() {
        return stockTypeCode;
    }

    public String getStockUrlImg() {
        return stockUrlImg;
    }

    public String getSubstituteDistributorNbr() {
        return substituteDistributorNbr;
    }

    public boolean getTaxableInd() {
        return taxableInd;
    }

    public Integer getTotalStockedQuantity() {

        Integer qty = new Integer(0);

        for (StockBalance sbalance : this.stockBalances) {
            qty += sbalance.getQtyOnHand() != null ? sbalance.getQtyOnHand() : 0;
        }

        return qty;

    }

    public String getUpcCd() {
        return upcCd;
    }

    public String getWarehouses() {
        List<String> res = new ArrayList<String>(0);
        StringBuffer sb = new StringBuffer();

        for (StockBalance obj : this.stockBalances) {
            String name = obj.getBin().getZone().getWarehouse().getWarehouseNme();
            if (!StringUtils.isEmpty(name) && !res.contains(name))
                res.add(name);
        }

        for (String name : res) {
            sb.append(name).append(" ");
        }

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((stockDistributorNbr == null) ? 0 : stockDistributorNbr.hashCode());
        return result;
    }

    public boolean isRental() {
        return SpringContext.getBean(StockService.class).isTrackableWithSerialNumber(this);
    }
    
    public boolean isObsoleteInd() {
        return obsoleteInd;
    }

    public boolean isPerishableInd() {
        return perishableInd;
    }

    public boolean isRecycledInd() {
        return recycledInd;
    }

    public boolean isSurchargeInd() {
        return surchargeInd;
    }

    public boolean isWillcallInd() {
        return willcallInd;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public void setAgreementNbr(String agreementNbr) {
        this.agreementNbr = agreementNbr;
    }

    public void setBuyUnitOfIssue(FinancialUnitOfMeasure buyUnitOfIssue) {
        this.buyUnitOfIssue = buyUnitOfIssue;
    }

    public void setBuyUnitOfIssueCd(String buyUnitOfIssueCd) {
        this.buyUnitOfIssueCd = buyUnitOfIssueCd;
    }

    public void setBuyUnitOfIssueRt(MMDecimal buyUnitOfIssueRt) {
        this.buyUnitOfIssueRt = buyUnitOfIssueRt;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public void setCycleCntCd(String cycleCntCd) {
        this.cycleCntCd = cycleCntCd;
    }

    public void setCycleCount(CycleCount cycleCount) {
        this.cycleCount = cycleCount;
    }

    public void setDistributorNbr(String distributorNbr) {
        this.distributorNbr = distributorNbr;
    }

    public void setHazardousMateriel(HazardousMateriel hazardousMateriel) {
        this.hazardousMateriel = hazardousMateriel;
    }

    public void setHazardousMateriels(List<HazardousMateriel> hazardousMateriels) {
        this.hazardousMateriels = hazardousMateriels;
    }

    public void setLastChangeActvIndDt(Date lastChangeActvIndDt) {
        this.lastChangeActvIndDt = lastChangeActvIndDt;
    }

    public void setManufacturerNbr(String manufacturerNbr) {
        this.manufacturerNbr = manufacturerNbr;
    }

    public void setMarkupCd(String markupCd) {
        this.markupCd = markupCd;
    }

    public void setMaximumOrderQty(KualiDecimal maximumOrderQty) {
        this.maximumOrderQty = maximumOrderQty;
    }

    public void setMinimumOrderQty(KualiDecimal minimumOrderQty) {
        this.minimumOrderQty = minimumOrderQty;
    }

    public void setObsoleteInd(boolean obsoleteInd) {
        this.obsoleteInd = obsoleteInd;
    }

    public void setPackagingUnitDesc(String packagingUnitDesc) {
        this.packagingUnitDesc = packagingUnitDesc;
    }

    public void setPerishableInd(boolean perishableInd) {
        this.perishableInd = perishableInd;
    }

    public void setRecycledInd(boolean recycledInd) {
        this.recycledInd = recycledInd;
    }

    public void setRemoveUntilDate(java.sql.Date removeUntilDate) {
        this.removeUntilDate = removeUntilDate;
    }

    public void setReorderPointQty(KualiDecimal string) {
        this.reorderPointQty = string;
    }

    public void setRestrictedRouteCd(String restrictedRouteCd) {
        this.restrictedRouteCd = restrictedRouteCd;
    }

    public void setRestrictedRouteCode(RestrictedRouteCode restrictedRouteCode) {
        this.restrictedRouteCode = restrictedRouteCode;
    }

    public void setSafetyStockDays(Integer safetyStockDays) {
        this.safetyStockDays = safetyStockDays;
    }

    public void setSafetyStockQty(KualiDecimal safetyStockQty) {
        this.safetyStockQty = safetyStockQty;
    }

    public void setSalesUnitOfIssue(UnitOfIssue salesUnitOfIssue) {
        this.salesUnitOfIssue = salesUnitOfIssue;
    }

    public void setSalesUnitOfIssueCd(String salesUnitOfIssueCd) {
        this.salesUnitOfIssueCd = salesUnitOfIssueCd;
    }

    public void setSalesUnitOfIssueRt(MMDecimal salesUnitOfIssueRt) {
        this.salesUnitOfIssueRt = salesUnitOfIssueRt;
    }

    public void setShippingHt(KualiDecimal shippingHt) {
        this.shippingHt = shippingHt;
    }

    public void setShippingLh(KualiDecimal shippingLh) {
        this.shippingLh = shippingLh;
    }

    public void setShippingWd(KualiDecimal shippingWd) {
        this.shippingWd = shippingWd;
    }

    public void setShippingWgt(KualiDecimal shippingWgt) {
        this.shippingWgt = shippingWgt;
    }

    public void setSoleSourceInd(boolean soleSourceInd) {
        this.soleSourceInd = soleSourceInd;
    }

    public void setStockAttributes(List<StockAttribute> stockAttributes) {
        this.stockAttributes = stockAttributes;
    }

    public void setStockBalances(List<StockBalance> stockBalances) {
        this.stockBalances = stockBalances;
    }

    public void setStockCounts(List<StockCount> stockCounts) {
        this.stockCounts = stockCounts;
    }

    public void setStockCreateDt(Date stockCreateDt) {
        this.stockCreateDt = stockCreateDt;
    }

    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }

    public void setStockDistributorNbr(String stockDistributorNbr) {
        this.stockDistributorNbr = stockDistributorNbr;
    }

    public void setStockHistory(StockHistory stockHistory) {
        this.stockHistory = stockHistory;
    }

    public void setStockHistoryList(List<StockHistory> stockHistoryList) {
        this.stockHistoryList = stockHistoryList;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setStockPackNotes(List<StockPackNote> stockPackNotes) {
        this.stockPackNotes = stockPackNotes;
    }


    public void setStockPrices(List<StockCost> stockPrices) {
        this.stockPrices = stockPrices;
    }

    public void setStockTransReason(StockTransReason stockTransReason) {
        this.stockTransReason = stockTransReason;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public void setStockTypeCode(String stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    public void setStockUrlImg(String stockUrlImg) {
        this.stockUrlImg = stockUrlImg;
    }

    public void setSubstituteDistributorNbr(String substituteDistributorNbr) {
        this.substituteDistributorNbr = substituteDistributorNbr;
    }

    public void setSurchargeInd(boolean surchargeInd) {
        this.surchargeInd = surchargeInd;
    }

    public void setTaxableInd(boolean taxableInd) {
        this.taxableInd = taxableInd;
    }

    public void setUpcCd(String upcCd) {
        this.upcCd = upcCd;
    }

    public void setWillcallInd(boolean willcallInd) {
        this.willcallInd = willcallInd;
    }
           
    public static String getCurrentStockPriceCode() {
        return SpringContext.getBean(StockService.class).getCurrentStockPriceCode();
    }

    public void setRentalObject(RentalObjectCode rentalObject) {
        this.rentalObject = rentalObject;
    }

    public RentalObjectCode getRentalObject() {
        return rentalObject;
    }

    public void setRentalObjectCode(String rentalObjectCode) {
        this.rentalObjectCode = rentalObjectCode;
    }

    public String getRentalObjectCode() {
        return rentalObjectCode;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * toStringMapper
     * 
     * @return LinkedHashMap
     */
    @Override
    @SuppressWarnings("unchecked")
    public LinkedHashMap toStringMapper() {
        LinkedHashMap propMap = new LinkedHashMap();
        return propMap;
    } 

}
