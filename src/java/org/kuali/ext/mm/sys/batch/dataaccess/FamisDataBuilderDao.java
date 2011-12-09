/**
 * 
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.List;


/**
 * @author harsha07
 *
 */
public interface FamisDataBuilderDao {

    public List<String> retrieveFamisLinesByAccount(String chart, String accountNumber);

}