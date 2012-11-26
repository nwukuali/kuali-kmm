package org.kuali.ext.mm.businessobject;

import org.kuali.rice.core.api.util.type.KualiDecimal;

import javax.persistence.*;

@Entity
@Table(name = "MM_SHOP_CART_DTL_ADDL_COST_ID")
public class ShopCartDetailAdditionalCost extends MMPersistableBusinessObjectBase {

	/**
     *
     */
    private static final long serialVersionUID = 8098135500028571497L;

    @Id
	@Column(name = "SHOP_CART_DTL_ADDL_COST_ID")
	private String shopCartDetailAdditionalCostId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHOP_CART_DETAIL_ID")
	private ShopCartDetail shopCartDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDITIONAL_COST_TYPE_CD")
	private AdditionalCostType additionalCostType;

	@Column(name = "SHOP_CART_DETAIL_ID")
	private String shopCartDetailId;

	@Column(name = "ADDITIONAL_COST_TYPE_CD")
	private String additionalCostTypeCode;

	@Column(name = "ADDITIONAL_COST")
	private KualiDecimal additionalCost;

	public ShopCartDetailAdditionalCost() {

	}

	public String getShopCartDetailAdditionalCostId() {
		return shopCartDetailAdditionalCostId;
	}

	public void setShopCartDetailAdditionalCostId(
			String shopCartDetailAdditionalCostId) {
		this.shopCartDetailAdditionalCostId = shopCartDetailAdditionalCostId;
	}

	public ShopCartDetail getShopCartDetail() {
		return shopCartDetail;
	}

	public void setShopCartDetail(ShopCartDetail shopCartDetail) {
		this.shopCartDetail = shopCartDetail;
	}

	public AdditionalCostType getAdditionalCostType() {
		return additionalCostType;
	}

	public void setAdditionalCostType(AdditionalCostType additionalCostType) {
		this.additionalCostType = additionalCostType;
	}

	public String getShopCartDetailId() {
		return shopCartDetailId;
	}

	public void setShopCartDetailId(String shopCartDetailId) {
		this.shopCartDetailId = shopCartDetailId;
	}

	public String getAdditionalCostTypeCode() {
		return additionalCostTypeCode;
	}

	public void setAdditionalCostTypeCode(String additionalCostTypeCode) {
		this.additionalCostTypeCode = additionalCostTypeCode;
	}

	public KualiDecimal getAdditionalCost() {
		return additionalCost;
	}

	public void setAdditionalCost(KualiDecimal additionalCost) {
		this.additionalCost = additionalCost;
	}

}
