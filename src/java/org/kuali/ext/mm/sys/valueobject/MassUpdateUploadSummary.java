package org.kuali.ext.mm.sys.valueobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.businessobject.MassUpdateDetail;
/**
 * @author schneppd
 *
 */
public class MassUpdateUploadSummary implements Serializable {
//    private Map<Integer, String> lineErrors;    
    private Integer lineCount;
    
    private List<MassUpdateDetail> massUpdateDetails = new ArrayList<MassUpdateDetail>();
    
    public MassUpdateUploadSummary() {
//        lineErrors = new HashMap<Integer, String>();
        lineCount = 0;
    }
    
    public Integer getLineCount() {
        return this.lineCount;
    }
    
    public void setLineCount(Integer lineCount) {
        this.lineCount = lineCount;
    }

//    public void setLineErrors(Map<Integer, String> lineErrors) {
//        this.lineErrors = lineErrors;
//    }
//
//    public Map<Integer, String> getLineErrors() {
//        return lineErrors;
//    }
//    
    public Integer getErrorCount() {
        return getLineCount() - getMassUpdateDetails().size();
    }
//    
//    public Set<Integer> getErrorKeySet() {
//        return lineErrors.keySet();
//    }

    public void setMassUpdateDetails(List<MassUpdateDetail> massUpdateDetails) {
        this.massUpdateDetails = massUpdateDetails;
    }

    public List<MassUpdateDetail> getMassUpdateDetails() {
        return massUpdateDetails;
    }
}
