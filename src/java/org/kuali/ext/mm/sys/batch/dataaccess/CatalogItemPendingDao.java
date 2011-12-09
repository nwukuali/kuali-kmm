/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItemPending;

/**
 * @author rshrivas
 */
public interface CatalogItemPendingDao {

    public ArrayList<String> getCatalogCount();

    public int getItemCount(String fDocNbr);


    public String queryCatalogCode(String catalogGroupCd8, String catalogGroupCd10);

    public String queryCatalogSubgroupCode(String catalogGroupCd, String derivedCatalogSubgroupCd,
            String derivedCatalogSubgroupCd10);

    public void updateCatalogPendingDoc(String fDocNumber, String catalogUplaodStatus);

    public Collection<CatalogItemPending> getCollectionForFivePercentOrLessReport(String fDocNbr,
            String fDocNbr2);

    public Collection<CatalogItemPending> getCollectionForPriceIncreaseReport(String fDocNbr,
            String fDocNbr2);

    public Collection<CatalogItemPending> getCollectionForPriceDecreaseReport(String fDocNbr,
            String fDocNbr2);

    public Collection<CatalogItemPending> getCollectionForCatalogAdditionsReport(String fDocNbr,
            String fDocNbr2);

    public Collection<CatalogItemPending> getCollectionForCatalogDeletionsReport(String fDocNbr,
            String fDocNbr2);

    public String getPreviousCatalogTimeStamp(String catalogCd, String docNbr);

    public void updateCatalogObject(Catalog catalog);

    public String getTotalCatalogPrc(String fDocNbr);

    public HashMap<String, Double> getCatalogPriceDecreasesMap(Collection<CatalogItemPending> cIP);

    public HashMap<String, Double> getCatalogPriceIncreasesMap(Collection<CatalogItemPending> cIP);

    public void publishApprovedCatalogItems(String fdocNbr, String catalogCode, Long catalogId);
}
