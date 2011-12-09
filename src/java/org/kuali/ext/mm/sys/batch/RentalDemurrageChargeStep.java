/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.RentalDemurrageService;

/**
 * @author harsha07
 */
public class RentalDemurrageChargeStep implements BatchStep {
    private RentalDemurrageService rentalDemurrageService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        rentalDemurrageService.processRentalDemurrageCharges();
    }

    /**
     * Gets the rentalDemurrageService property
     *
     * @return Returns the rentalDemurrageService
     */
    public RentalDemurrageService getRentalDemurrageService() {
        return this.rentalDemurrageService;
    }

    /**
     * Sets the rentalDemurrageService property value
     *
     * @param rentalDemurrageService The rentalDemurrageService to set
     */
    public void setRentalDemurrageService(RentalDemurrageService rentalDemurrageService) {
        this.rentalDemurrageService = rentalDemurrageService;
    }


}
