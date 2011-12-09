/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import org.kuali.ext.mm.sys.batch.service.impl.BatchStatusVO;

/**
 * @author harsha07
 */
public class BatchRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8213934506605330025L;
    private BatchStatusVO batchStatusVO;

    /**
     *
     */
    public BatchRuntimeException(Throwable e, BatchStatusVO batchStatusVO) {
        super(e);
        this.batchStatusVO = batchStatusVO;
    }

    /**
     * Gets the batchStatusVO property
     *
     * @return Returns the batchStatusVO
     */
    public BatchStatusVO getBatchStatusVO() {
        return this.batchStatusVO;
    }

    /**
     * Sets the batchStatusVO property value
     *
     * @param batchStatusVO The batchStatusVO to set
     */
    public void setBatchStatusVO(BatchStatusVO batchStatusVO) {
        this.batchStatusVO = batchStatusVO;
    }
}
