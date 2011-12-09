/**
 *
 */
package org.kuali.ext.mm.businessobject;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.service.MMServiceLocator;

/**
 * @author rponraj
 */
public class OrderDetailForReorder extends OrderDetail {

    private static final long serialVersionUID = -875249384350824795L;

    private String distributorNumber;

    private String itemNumber;

    private String manufacturerNumber;

    private String stockId;

    public String getDistributorNumber() {
        return this.distributorNumber;
    }

    public String getItemNumber() {
        return this.itemNumber;
    }

    public String getManufacturerNumber() {
        return this.manufacturerNumber;
    }

    public OrderDetail getOrderDetail() {
        if (this.getCatalogItem() == null) {
            CatalogItem citem = null;
            if (StringUtils.isEmpty(this.getCatalogItemId()))
                citem = StoresPersistableBusinessObject.getObjectByPrimaryKey(CatalogItem.class,
                        this.getCatalogItemId());
            else
                citem = MMServiceLocator.getCheckinOrderService().getCatalogItem(
                        manufacturerNumber, itemNumber);

            if (citem != null) {
                super.setCatalogItem(citem);
                super.setCatalogItemId(citem.getCatalogItemId());
            }
        }
        return this;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setManufacturerNumber(String manufacturerNumber) {
        this.manufacturerNumber = manufacturerNumber;
    }

    public String getStockId() {
        return this.stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

}
