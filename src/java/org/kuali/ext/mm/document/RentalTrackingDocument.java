package org.kuali.ext.mm.document;

import java.util.List;

import org.kuali.ext.mm.businessobject.RentalTrackingDetail;

public interface RentalTrackingDocument<T extends RentalTrackingDetail> {    
    public List<T> getRentalTrackingDetails();
}