package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.kuali.ext.mm.sys.batch.service.AgreementUpdateService;

public class AgreementUpdateStep implements BatchStep{

    private AgreementUpdateService agreementUpdateService;

    public AgreementUpdateService getAgreementUpdateService() {
        return this.agreementUpdateService;
    }

    public void setAgreementUpdateService(AgreementUpdateService agreementUpdateService) {
        this.agreementUpdateService = agreementUpdateService;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        agreementUpdateService.updateAgreement();
     }



}