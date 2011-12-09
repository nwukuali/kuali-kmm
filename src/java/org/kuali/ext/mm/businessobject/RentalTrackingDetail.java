package org.kuali.ext.mm.businessobject;

import java.util.List;

public interface RentalTrackingDetail {
    
    public Integer getRentalTrackingDetailId();
    
    public boolean isTrackableStock();
    
    public List<StagingRental> getStagingRentals();
    
//    public List<StagingRental> setStagingRentals();
    
    public String getRentalsForDisplay();
    
    public boolean isRentalAddable();
    
}
