/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service;

import org.kuali.ext.mm.businessobject.Warehouse;

/**
 * @author harsha07
 */
public interface GlCollectorFeedService {
    public void buildGlCollectorFeeds();

    public void processWarehouseGlEntries(Warehouse wh, String glCollectorDir);
}
