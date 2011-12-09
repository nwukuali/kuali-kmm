/**
 *
 */
package org.kuali.ext.mm.businessobject;


import java.io.Serializable;
import java.util.Date;

import org.kuali.rice.kns.util.KualiDecimal;

/**
 * @author rponraj
 *
 */
public class SalesHistory implements Serializable{

    private static final long serialVersionUID = -3929127665713186976L;

    private Integer numberOfTranscations;

    public Integer getNumberOfTranscations() {
        return this.numberOfTranscations;
    }

    public void setNumberOfTranscations(Integer numberOfTranscations) {
        this.numberOfTranscations = numberOfTranscations;
    }

    public Integer getNumberOfUnits() {
        return this.numberOfUnits;
    }

    public void setNumberOfUnits(Integer numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public KualiDecimal getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(KualiDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Date getFormDate() {
        return this.formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    private Integer numberOfUnits;

    private KualiDecimal totalCost;

    private Date formDate;

    private Date toDate;

}
