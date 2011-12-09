/**
 * 
 */
package org.kuali.ext.mm.sys.batch.web.struts;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.BatchRuntimeException;
import org.kuali.ext.mm.sys.batch.service.BatchControlService;
import org.kuali.ext.mm.sys.batch.service.impl.BatchStatusVO;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.struts.action.KualiAction;


/**
 * @author harsha07
 */
public class BatchControlAction extends KualiAction {
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(KNSConstants.MAPPING_PORTAL);
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        BatchControlForm batchControlForm = (BatchControlForm) form;
        BatchStatusVO jobStatus = null;
        try {
            String jobName = batchControlForm.getJobName();
            if (StringUtils.isBlank(jobName)) {
                batchControlForm.setMessage("Job " + jobName + " not found.");
                return mapping.findForward(RiceConstants.MAPPING_BASIC);
            }
            BatchControlService batchControlService = SpringContext
                    .getBean(BatchControlService.class);
            jobStatus = batchControlService.performJob(jobName, new Date());
            batchControlForm.setMessage("Job " + jobName + " ran successfully.");
            batchControlForm.setBatchStatus(jobStatus);
        }

        catch (BatchRuntimeException bre) {
            batchControlForm.setBatchStatus(bre.getBatchStatusVO());
            batchControlForm.setMessage("Job " + batchControlForm.getJobName()
                    + " failed with exception. Check console for stacktrace.");
            bre.printStackTrace();
        }
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }
}
