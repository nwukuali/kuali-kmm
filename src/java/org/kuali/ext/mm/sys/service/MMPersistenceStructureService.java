/**
 * 
 */
package org.kuali.ext.mm.sys.service;

import org.kuali.rice.kns.service.PersistenceStructureService;


/**
 * @author schneppd
 *
 */
public interface MMPersistenceStructureService extends PersistenceStructureService {

    public String getColumnName(Class clazz, String fieldName);

}