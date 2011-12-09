/**
 *
 */
package org.kuali.ext.mm.service;

import java.util.HashMap;

/**
 * @author rshrivas
 *
 */
public class BinChangedTransferHelper {

    private int oldBinId;

    private int newBinId;

    private String qtyToTransfer;

    private String stockId;

    private HashMap hashMap;

    public HashMap getHashMap() {
        return this.hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public int getOldBinId() {
        return this.oldBinId;
    }

    public void setOldBinId(int oldBinId) {
        this.oldBinId = oldBinId;
    }

    public int getNewBinId() {
        return this.newBinId;
    }

    public void setNewBinId(int newBinId) {
        this.newBinId = newBinId;
    }

    public String getQtyToTransfer() {
        return this.qtyToTransfer;
    }

    public void setQtyToTransfer(String qtyToTransfer) {
        this.qtyToTransfer = qtyToTransfer;
    }

    public String getStockId() {
        return this.stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }




}
