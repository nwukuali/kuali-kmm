package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.document.OrderDocument;

public class OrderDocumentLookable extends OrderDocument {

    /**
     *
     */
    private static final long serialVersionUID = 4710791224293885443L;

    private String checkinDocNumber = null;

    private Stock stock = null;

    private String stockDistributorNbr;

    public String getStockDistributorNbr() {
        return this.stockDistributorNbr;
    }

    @Override
    public String getDocumentNumber(){
        String result = super.getDocumentNumber();
        return result;
    }

    public void setStockDistributorNbr(String stockDistributorNbr) {
        this.stockDistributorNbr = stockDistributorNbr;
    }

    public Stock getStock() {
        return this.stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

//    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(0);

    public String getCheckinDocNumber() {
        return this.checkinDocNumber;
    }

    public void setCheckinDocNumber(String checkinDocNumber) {
        this.checkinDocNumber = checkinDocNumber;
    }

    public String getReturnDocNumber() {
        return this.returnDocNumber;
    }

    public void setReturnDocNumber(String returnDocNumber) {
        this.returnDocNumber = returnDocNumber;
    }

    private String returnDocNumber = null;

//    public List<OrderDetail>  getOrderDetails(){
//        return orderDetails = super.getOrderDetails();
//    }
//
//    public void  setOrderDetails(List<OrderDetail> data){
//         super.setOrderDetails(data);
//         orderDetails = super.getOrderDetails();
//    }


}
