package org.kuali.ext.mm.businessobject;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kns.util.KualiDecimal;

@SuppressWarnings("serial")
@Entity
@Table(name = "MM_CATALOG_ITEM_PENDING_T")
public class CatalogItemPending extends MMPersistableBusinessObjectBase {

	@Id
	@Column(name = "CATALOG_ITEM_PND_ID", unique = true, nullable = false, length = 36)
	private String catalogItemPndId;

    @Column(name="CATALOG_PENDING_DOC_NBR", nullable=false, length =14)
    private String catalogPendingDocNbr;

	@Column(name = "DISTRIBUTOR_NBR", length = 30)
	private String distributorNbr;

	@Column(name = "MANUFACTURER_NBR", length = 30)
	private String manufacturerNbr;

	@Column(name = "CATALOG_UNIT_OF_ISSUE_CD", nullable = false, length = 10)
	private String catalogUnitOfIssueCd;

	@Column(name = "CATALOG_PRC", nullable = false, scale = 4)
	private MMDecimal catalogPrc;

	@Column(name = "CATALOG_DESC", nullable = false, length = 400)
	private String catalogDesc;

	@Column(name = "RECYCLED_IND", length = 1)
	private boolean recycledInd;

	@Column(name = "TAXABLE_IND", length = 1)
	private boolean taxableInd;

	@Column(name = "UNSPSC_CD", length = 10)
	private String unspscCd;

	@Column(name = "SHIPPING_WGT", precision = 8)
	private KualiDecimal shippingWgt;

	@Column(name = "SHIPPING_HT", precision = 8)
	private KualiDecimal shippingHt;

	@Column(name = "SHIPPING_WD", precision = 8)
	private KualiDecimal shippingWd;

	@Column(name = "SHIPPING_LH", precision = 8)
	private KualiDecimal shippingLh;

	@Column (name = "CATALOG_GROUP_CD")
	private String catalogGroupCd;

	@Column(name = "CATALOG_SUBGROUP_CD", nullable = false, length = 12)
	private String catalogSubgroupCd;

	@Column(name = "CATALOG_IMAGE_URL", nullable = false, length = 60)
	private String catalogImageUrl;

	@Column(name = "CATALOG_TIMESTAMP", length=7)
	private Timestamp catalogTimestamp;

	@Column(name = "CATALOG_SUBGROUP_DESC", nullable = false, length = 80)
	private String catalogSubgroupDesc;

	@Column(name = "CATALOG_GROUP_NM", length = 80)
	private String catalogGroupNm;

	@Column(name = "SPAID_ID", length= 28)
	private String spaidId;

	/**
     * Gets the catalogTimestamp property
     * @return Returns the catalogTimestamp
     */
    public Timestamp getCatalogTimestamp() {
        return this.catalogTimestamp;
    }

    /**
     * Sets the catalogTimestamp property value
     * @param catalogTimestamp The catalogTimestamp to set
     */
    public void setCatalogTimestamp(Timestamp catalogTimestamp) {
        this.catalogTimestamp = catalogTimestamp;
    }

    public String getCatalogGroupCd() {
		return catalogGroupCd;
	}

	public void setCatalogGroupCd(String catalogGroupCd) {
		this.catalogGroupCd = catalogGroupCd;
	}

	public String getCatalogSubgroupCd() {
		return catalogSubgroupCd;
	}

	public void setCatalogSubgroupCd(String catalogSubgroupCd) {
		this.catalogSubgroupCd = catalogSubgroupCd;
	}

	public String getCatalogImageUrl() {
		return catalogImageUrl;
	}

	public void setCatalogImageUrl(String catalogImageUrl) {
		this.catalogImageUrl = catalogImageUrl;
	}

	public String getCatalogSubgroupDesc() {
		return catalogSubgroupDesc;
	}

	public void setCatalogSubgroupDesc(String catalogSubgroupDesc) {
		this.catalogSubgroupDesc = catalogSubgroupDesc;
	}

	public String getCatalogGroupNm() {
		return catalogGroupNm;
	}

	public void setCatalogGroupNm(String catalogGroupNm) {
		this.catalogGroupNm = catalogGroupNm;
	}

	public String getSpaidId() {
		return spaidId;
	}

	public void setSpaidId(String spaidId) {
		this.spaidId = spaidId;
	}

	public String getCatalogItemPndId() {
		return catalogItemPndId;
	}

	public void setCatalogItemPndId(String catalogItemPndId) {
		this.catalogItemPndId = catalogItemPndId;
	}

    public String getCatalogPendingDocNbr() {
        return catalogPendingDocNbr;
    }

    public void setCatalogPendingDocNbr(String catalogPendingDocNbr) {
        this.catalogPendingDocNbr = catalogPendingDocNbr;
    }

	public String getDistributorNbr() {
		return distributorNbr;
	}

	public void setDistributorNbr(String distributorNbr) {
		this.distributorNbr = distributorNbr;
	}

	public String getManufacturerNbr() {
		return manufacturerNbr;
	}

	public void setManufacturerNbr(String manufacturerNbr) {
		this.manufacturerNbr = manufacturerNbr;
	}

	public String getCatalogUnitOfIssueCd() {
		return catalogUnitOfIssueCd;
	}

	public void setCatalogUnitOfIssueCd(String catalogUnitOfIssueCd) {
		this.catalogUnitOfIssueCd = catalogUnitOfIssueCd;
	}



	/**
     * Gets the catalogPrc property
     * @return Returns the catalogPrc
     */
    public MMDecimal getCatalogPrc() {
        return this.catalogPrc;
    }

    /**
     * Sets the catalogPrc property value
     * @param catalogPrc The catalogPrc to set
     */
    public void setCatalogPrc(MMDecimal catalogPrc) {
        this.catalogPrc = catalogPrc;
    }

    public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public boolean isRecycledInd() {
		return recycledInd;
	}

	public void setRecycledInd(boolean recycledInd) {
		this.recycledInd = recycledInd;
	}

	public boolean isTaxableInd() {
		return taxableInd;
	}

	public void setTaxableInd(boolean taxableInd) {
		this.taxableInd = taxableInd;
	}

	public String getUnspscCd() {
		return unspscCd;
	}

	public void setUnspscCd(String unspscCd) {
		this.unspscCd = unspscCd;
	}

	public KualiDecimal getShippingWgt() {
		return shippingWgt;
	}

	public void setShippingWgt(KualiDecimal shippingWgt) {
		this.shippingWgt = shippingWgt;
	}

	public KualiDecimal getShippingHt() {
		return shippingHt;
	}

	public void setShippingHt(KualiDecimal shippingHt) {
		this.shippingHt = shippingHt;
	}

	public KualiDecimal getShippingWd() {
		return shippingWd;
	}

	public void setShippingWd(KualiDecimal shippingWd) {
		this.shippingWd = shippingWd;
	}

	public KualiDecimal getShippingLh() {
		return shippingLh;
	}

	public void setShippingLh(KualiDecimal shippingLh) {
		this.shippingLh = shippingLh;
	}

	/**
	 * toStringMapper
	 * @return LinkedHashMap
	 */
	@Override
    public LinkedHashMap toStringMapper() {
		LinkedHashMap propMap = new LinkedHashMap();
		return propMap;
	}

}
