/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

/**
 * @author harsha07
 */
public interface BatchStep {

    /**
     *
     */
    void execute(String jobName, Date time);

}
