/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc.mysql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.kuali.ext.mm.businessobject.Catalog;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao;
import org.kuali.ext.mm.util.MMDecimal;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;

/**
 * @author sravani
 */
public class CatalogItemPendingDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        CatalogItemPendingDao {
    private static final Logger LOG = Logger.getLogger(CatalogItemPendingDaoJdbc.class);


    @SuppressWarnings("unchecked")
    public ArrayList<String> getCatalogCount() {
        String query = "SELECT FDOC_NBR FROM MM_CATALOG_PENDING_DOC_T WHERE CATALOG_UPLOAD_STATUS = 'UPLOADING' ORDER BY CATALOG_TIMESTAMP ASC";
        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        ArrayList<String> fDocs = new ArrayList<String>();

        for (Iterator<ListOrderedMap> iterator = values.iterator(); iterator.hasNext();) {
            ListOrderedMap lOM = iterator.next();
            fDocs.add((String) lOM.get("FDOC_NBR"));
        }

        return fDocs;
    }

    public int getItemCount(String fDocNbr) {
        String query = "SELECT COUNT(*) FROM MM_CATALOG_ITEM_PENDING_T A, "
                + "MM_CATALOG_PENDING_DOC_T B WHERE B.FDOC_NBR = '" + fDocNbr
                + "' AND A.CATALOG_PENDING_DOC_NBR = B.FDOC_NBR";
        int count = getJdbcTemplate().queryForInt(query);
        return count;
    }

    public String getTotalCatalogPrc(String fDocNbr) {
        String query = "SELECT SUM(CATALOG_PRC) FROM MM_CATALOG_ITEM_PENDING_T WHERE CATALOG_PENDING_DOC_NBR = "
                + fDocNbr;
        Object count = getJdbcTemplate().queryForObject(query, String.class);
        return count + "";
    }

   

    @SuppressWarnings("unchecked")
    public String queryCatalogCode(String catalogGroupCd8, String catalogGroupCd10) {
        String catalogGroupCd = null;

        String query = "SELECT CATALOG_GROUP_CD FROM MM_CATALOG_GROUP_T WHERE UPPER(CATALOG_GROUP_CD) = UPPER('"
                + catalogGroupCd8
                + "') OR UPPER(CATALOG_GROUP_CD) = UPPER('"
                + catalogGroupCd10
                + "')";

        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);

        for (ListOrderedMap lOM : values) {
            catalogGroupCd = (String) lOM.get("CATALOG_GROUP_CD");
            break;
        }

        return catalogGroupCd;
    }

    @SuppressWarnings("unchecked")
    public String queryCatalogSubgroupCode(String catalogGroupCd, String derivedCatalogSubgroupCd,
            String derivedCatalogSubgroupCd10) {
        String catalogSubgroupCd = null;

        String query = "SELECT CATALOG_SUBGROUP_CD FROM MM_CATALOG_SUBGROUP_T WHERE UPPER(CATALOG_SUBGROUP_CD) = UPPER('"
                + derivedCatalogSubgroupCd
                + "') OR UPPER(CATALOG_SUBGROUP_CD) = UPPER('"
                + derivedCatalogSubgroupCd10
                + "') AND UPPER(CATALOG_GROUP_CD) = UPPER('"
                + catalogGroupCd + "')";

        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);

        for (ListOrderedMap lOM : values) {
            catalogSubgroupCd = (String) lOM.get("CATALOG_SUBGROUP_CD");
            break;
        }

        return catalogSubgroupCd;
    }


    @SuppressWarnings("unchecked")
    public String getPreviousCatalogTimeStamp(String catalogCd, String fDocNbr) {
        String ts = null;
        String query = "SELECT FDOC_NBR FROM MM_CATALOG_PENDING_DOC_T WHERE CATALOG_CD = '"
                + catalogCd + "' ORDER BY CATALOG_TIMESTAMP DESC";
        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        Iterator<ListOrderedMap> itr = values.iterator();

        while (itr.hasNext()) {

            ListOrderedMap lOM = itr.next();
            String fDNbr = (String) (lOM.get("FDOC_NBR"));

            if (Integer.parseInt(fDocNbr) > Integer.parseInt(fDNbr)) {
                ts = (String) lOM.get("FDOC_NBR");
                break;
            }
        }
        return ts;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao#getCollectionForCatalogAdditionsReport(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection<CatalogItemPending> getCollectionForCatalogAdditionsReport(String docNbr,
            String docNbr2) {
        String query = "SELECT A.DISTRIBUTOR_NBR, A.CATALOG_SUBGROUP_DESC, A.CATALOG_GROUP_NM, A.UNSPSC_CD, A.SPAID_ID, A.CATALOG_IMAGE_URL, "
                + "A.CATALOG_SUBGROUP_CD, A.TAXABLE_IND, A.CATALOG_TIMESTAMP, A.CATALOG_GROUP_CD, A.MANUFACTURER_NBR, "
                + "A.CATALOG_UNIT_OF_ISSUE_CD, A.CATALOG_PRC, A.CATALOG_DESC, A.RECYCLED_IND FROM MM_CATALOG_ITEM_PENDING_T A "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND A.DISTRIBUTOR_NBR IN (SELECT DISTRIBUTOR_NBR FROM "
                + "MM_CATALOG_ITEM_PENDING_T WHERE CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' MINUS SELECT DISTRIBUTOR_NBR "
                + "FROM MM_CATALOG_ITEM_PENDING_T WHERE CATALOG_PENDING_DOC_NBR = '"
                + docNbr2
                + "')";

        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        ArrayList<CatalogItemPending> returnedList = loadValues(values);
        return returnedList;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao#getCollectionForCatalogDeletionsReport(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection<CatalogItemPending> getCollectionForCatalogDeletionsReport(String docNbr,
            String docNbr2) {
        String query = "SELECT A.DISTRIBUTOR_NBR, A.CATALOG_SUBGROUP_DESC, A.CATALOG_GROUP_NM, A.UNSPSC_CD, A.SPAID_ID, A.CATALOG_IMAGE_URL, "
                + "A.CATALOG_SUBGROUP_CD, A.TAXABLE_IND, A.CATALOG_TIMESTAMP, A.CATALOG_GROUP_CD, A.MANUFACTURER_NBR, "
                + "A.CATALOG_UNIT_OF_ISSUE_CD, A.CATALOG_PRC, A.CATALOG_DESC, A.RECYCLED_IND, A.CATALOG_ITEM_PND_ID FROM MM_CATALOG_ITEM_PENDING_T A "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND A.DISTRIBUTOR_NBR IN (SELECT DISTRIBUTOR_NBR FROM "
                + "MM_CATALOG_ITEM_PENDING_T WHERE CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' MINUS SELECT DISTRIBUTOR_NBR "
                + "FROM MM_CATALOG_ITEM_PENDING_T WHERE CATALOG_PENDING_DOC_NBR = '"
                + docNbr2
                + "')";
        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        ArrayList<CatalogItemPending> returnedList = loadValues(values);
        return returnedList;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao#getCollectionForFivePercentOrLessReport(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection<CatalogItemPending> getCollectionForFivePercentOrLessReport(String docNbr,
            String docNbr2) {
        String query = "SELECT A.DISTRIBUTOR_NBR, A.CATALOG_SUBGROUP_DESC, A.CATALOG_GROUP_NM, A.UNSPSC_CD, A.SPAID_ID, A.CATALOG_IMAGE_URL, "
                + "A.CATALOG_SUBGROUP_CD, A.TAXABLE_IND, A.CATALOG_TIMESTAMP, A.CATALOG_GROUP_CD, A.MANUFACTURER_NBR, "
                + "A.CATALOG_UNIT_OF_ISSUE_CD, A.CATALOG_PRC, A.CATALOG_DESC, A.RECYCLED_IND FROM MM_CATALOG_ITEM_PENDING_T A "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' "
                + "AND A.DISTRIBUTOR_NBR IN (SELECT A.DISTRIBUTOR_NBR FROM MM_CATALOG_ITEM_PENDING_T A, MM_CATALOG_ITEM_PENDING_T B "
                + "WHERE a.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' "
                + "AND B.CATALOG_PENDING_DOC_NBR = '"
                + docNbr2
                + "' AND A.distributor_nbr = B.DISTRIBUTOR_NBR "
                + "AND A.CATALOG_PRC > B.CATALOG_PRC "
                + "AND A.CATALOG_PRC >= (B.CATALOG_PRC * 0.05 + B.CATALOG_PRC)) "
                + "UNION "
                + "SELECT A.DISTRIBUTOR_NBR, A.CATALOG_SUBGROUP_DESC, A.CATALOG_GROUP_NM, A.UNSPSC_CD, A.SPAID_ID, A.CATALOG_IMAGE_URL, "
                + "A.CATALOG_SUBGROUP_CD, A.TAXABLE_IND, A.CATALOG_TIMESTAMP, A.CATALOG_GROUP_CD, A.MANUFACTURER_NBR, "
                + "A.CATALOG_UNIT_OF_ISSUE_CD, A.CATALOG_PRC, A.CATALOG_DESC, A.RECYCLED_IND FROM MM_CATALOG_ITEM_PENDING_T A "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' "
                + "AND A.DISTRIBUTOR_NBR IN (SELECT A.DISTRIBUTOR_NBR FROM MM_CATALOG_ITEM_PENDING_T A, MM_CATALOG_ITEM_PENDING_T B "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND B.CATALOG_PENDING_DOC_NBR = '"
                + docNbr2
                + "' "
                + "AND A.distributor_nbr = B.DISTRIBUTOR_NBR "
                + "AND A.CATALOG_PRC > B.CATALOG_PRC "
                + "AND A.CATALOG_PRC <= (B.CATALOG_PRC * 0.05 + B.CATALOG_PRC))";

        // System.out.println(query);
        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        ArrayList<CatalogItemPending> returnedList = loadValues(values);
        return returnedList;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao#getCollectionForPriceDecreaseReport(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection<CatalogItemPending> getCollectionForPriceDecreaseReport(String docNbr,
            String docNbr2) {
        String query = "SELECT A.DISTRIBUTOR_NBR, A.CATALOG_SUBGROUP_DESC, A.CATALOG_GROUP_NM, A.UNSPSC_CD, A.SPAID_ID, "
                + "A.CATALOG_IMAGE_URL, A.CATALOG_SUBGROUP_CD, A.TAXABLE_IND, A.CATALOG_TIMESTAMP, A.CATALOG_GROUP_CD, "
                + "A.MANUFACTURER_NBR, A.CATALOG_UNIT_OF_ISSUE_CD, A.CATALOG_PRC, A.CATALOG_DESC, A.RECYCLED_IND, A.CATALOG_ITEM_PND_ID "
                + "FROM MM_CATALOG_ITEM_PENDING_T A WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND A.DISTRIBUTOR_NBR IN ("
                + "SELECT A.DISTRIBUTOR_NBR FROM MM_CATALOG_ITEM_PENDING_T A, MM_CATALOG_ITEM_PENDING_T B "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND B.CATALOG_PENDING_DOC_NBR = '"
                + docNbr2
                + "' AND A.distributor_nbr = B.DISTRIBUTOR_NBR AND A.CATALOG_PRC < B.CATALOG_PRC)";

        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        ArrayList<CatalogItemPending> returnedList = loadValues(values);
        return returnedList;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao#getCollectionForPriceIncreaseReport(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Collection<CatalogItemPending> getCollectionForPriceIncreaseReport(String docNbr,
            String docNbr2) {
        String query = "SELECT A.DISTRIBUTOR_NBR, A.CATALOG_SUBGROUP_DESC, A.CATALOG_GROUP_NM, A.UNSPSC_CD, A.SPAID_ID, "
                + "A.CATALOG_IMAGE_URL, A.CATALOG_SUBGROUP_CD, A.TAXABLE_IND, A.CATALOG_TIMESTAMP, A.CATALOG_GROUP_CD, "
                + "A.MANUFACTURER_NBR, A.CATALOG_UNIT_OF_ISSUE_CD, A.CATALOG_PRC, A.CATALOG_DESC, A.RECYCLED_IND, A.CATALOG_ITEM_PND_ID "
                + "FROM MM_CATALOG_ITEM_PENDING_T A WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND A.DISTRIBUTOR_NBR IN ("
                + "SELECT A.DISTRIBUTOR_NBR FROM MM_CATALOG_ITEM_PENDING_T A, MM_CATALOG_ITEM_PENDING_T B "
                + "WHERE A.CATALOG_PENDING_DOC_NBR = '"
                + docNbr
                + "' AND B.CATALOG_PENDING_DOC_NBR = '"
                + docNbr2
                + "' AND A.distributor_nbr = B.DISTRIBUTOR_NBR AND A.CATALOG_PRC > B.CATALOG_PRC)";

        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
        ArrayList<CatalogItemPending> returnedList = loadValues(values);
        return returnedList;
    }

    private ArrayList<CatalogItemPending> loadValues(Collection<ListOrderedMap> values) {

        ArrayList<CatalogItemPending> returnedList = new ArrayList<CatalogItemPending>();
        for (ListOrderedMap lOM : values) {
            CatalogItemPending cIP = new CatalogItemPending();
            cIP.setCatalogPendingDocNbr((String) lOM.get("CATALOG_PENDING_DOC_NBR"));
            cIP.setDistributorNbr((String) lOM.get("DISTRIBUTOR_NBR"));
            cIP.setManufacturerNbr((String) lOM.get("MANUFACTURER_NBR"));
            cIP.setCatalogUnitOfIssueCd((String) lOM.get("CATALOG_UNIT_OF_ISSUE_CD"));
            BigDecimal cP = (BigDecimal) lOM.get("CATALOG_PRC");
            cIP.setCatalogPrc(new MMDecimal(cP));
            cIP.setCatalogDesc((String) lOM.get("CATALOG_DESC"));
            cIP.setRecycledInd(((String) lOM.get("RECYCLED_IND")) == "Y" ? true : false);
            cIP.setUnspscCd((String) lOM.get("UNSPSC_CD"));
            cIP.setTaxableInd(((String) lOM.get("TAXABLE_IND")) == "Y" ? true : false);
            cIP.setCatalogTimestamp((Timestamp) lOM.get("CATALOG_TIMESTAMP"));
            cIP.setCatalogGroupCd((String) lOM.get("CATALOG_GROUP_CD"));
            cIP.setCatalogSubgroupCd((String) lOM.get("CATALOG_SUBGROUP_CD"));
            cIP.setCatalogImageUrl((String) lOM.get("CATALOG_IMAGE_URL"));
            cIP.setSpaidId((String) lOM.get("SPAID_ID"));
            cIP.setCatalogGroupNm((String) lOM.get("CATALOG_GROUP_NM"));
            cIP.setCatalogSubgroupDesc((String) lOM.get("CATALOG_SUBGROUP_DESC"));
            cIP.setActive(true);
            cIP.setLastUpdateDate((Timestamp) lOM.get("LAST_UPDATE_DT"));
            BigDecimal pendingId = (BigDecimal) lOM.get("CATALOG_ITEM_PND_ID");
            cIP.setCatalogItemPndId(pendingId + "");
            returnedList.add(cIP);
        }

        return returnedList;
    }

    public void updateCatalogPendingDoc(String fDocNumber, String catalogUplaodStatus) {
        String query = "UPDATE MM_CATALOG_PENDING_DOC_T SET CATALOG_UPLOAD_STATUS = '"
                + catalogUplaodStatus + "', LAST_UPDATE_DT = SYSDATE WHERE FDOC_NBR = '"
                + fDocNumber + "'";
        getJdbcTemplate().execute(query);
    }


    public void updateCatalogObject(Catalog catalog) {
        String query = "UPDATE MM_CATALOG_T SET CATALOG_PENDING_DOC_NBR = "
                + catalog.getCatalogPendingDocNbr() + " WHERE CATALOG_ID = "
                + catalog.getCatalogId();
        getJdbcTemplate().execute(query);
    }

    public HashMap<String, Double> getCatalogPriceDecreasesMap(Collection<CatalogItemPending> cIP) {
        HashMap<String, Double> hm = new HashMap<String, Double>();
        for (Iterator iterator = cIP.iterator(); iterator.hasNext();) {
            CatalogItemPending catalogItemPending = (CatalogItemPending) iterator.next();
            String query = "SELECT CATALOG_PRC FROM MM_CATALOG_ITEM_PENDING_T WHERE DISTRIBUTOR_NBR = '"
                    + catalogItemPending.getDistributorNbr()
                    + "' AND CATALOG_ITEM_PND_ID < "
                    + catalogItemPending.getCatalogItemPndId() + "ORDER BY CATALOG_ITEM_PND_ID";
            Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
            for (Iterator iterator2 = values.iterator(); iterator2.hasNext();) {
                ListOrderedMap listOrderedMap = (ListOrderedMap) iterator2.next();
                BigDecimal cP = (BigDecimal) listOrderedMap.get("CATALOG_PRC");
                hm.put(catalogItemPending.getCatalogItemPndId(), cP.doubleValue());
                break;
            }
        }
        return hm;
    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDao#getCatalogPriceIncreasesMap(java.util.Collection)
     */
    public HashMap<String, Double> getCatalogPriceIncreasesMap(Collection<CatalogItemPending> cIP) {
        HashMap<String, Double> hm = new HashMap<String, Double>();
        for (Iterator iterator = cIP.iterator(); iterator.hasNext();) {
            CatalogItemPending catalogItemPending = (CatalogItemPending) iterator.next();
            String query = "SELECT CATALOG_PRC FROM MM_CATALOG_ITEM_PENDING_T WHERE DISTRIBUTOR_NBR = '"
                    + catalogItemPending.getDistributorNbr()
                    + "' AND CATALOG_ITEM_PND_ID < "
                    + catalogItemPending.getCatalogItemPndId() + "ORDER BY CATALOG_ITEM_PND_ID";
            Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(query);
            for (Iterator iterator2 = values.iterator(); iterator2.hasNext();) {
                ListOrderedMap listOrderedMap = (ListOrderedMap) iterator2.next();
                BigDecimal cP = (BigDecimal) listOrderedMap.get("CATALOG_PRC");
                hm.put(catalogItemPending.getCatalogItemPndId(), cP.doubleValue());
                break;
            }
        }
        return hm;
    }


    public void publishApprovedCatalogItems(String fdocNbr, String catalogCode, Long catalogId) {
        deactivateItems(catalogId);
        addCatalogGroupsByUnspc(fdocNbr);
        addCatalogSubgroupsByUnspc(fdocNbr);
        assignCatalogGroupingByUnspc(fdocNbr);
        addCatalogGroups(fdocNbr, catalogCode);
        assignCatalogGroups(fdocNbr);
        addCatalogSubgroups(fdocNbr, catalogCode);
        assignCatalogSubgroups(fdocNbr);
        updateExistingItems(fdocNbr, catalogId);
        updateExistingImages(fdocNbr);
        clearExistingImageItems(fdocNbr, catalogId);
        addNewItems(fdocNbr, catalogId);
        addNewImages(fdocNbr);
        addNewImageItems(fdocNbr, catalogId);
        assignCatalogSubgroupsToItems(fdocNbr, catalogId);
    }

    /**
     * @param fdocNbr
     */
    private void addCatalogGroupsByUnspc(String fdocNbr) {
        int count = getJdbcTemplate()
                .update(
                        "insert into mm_catalog_group_t"
                        + " (CATALOG_GROUP_CD,"
                        + " OBJ_ID,"
                        + " VER_NBR,"
                        + " CATALOG_GROUP_NM,"
                        + " ACTV_IND,"
                        + " LAST_UPDATE_DT)"
                        + " select (select last_insert_id()+1 from mm_catalog_group_s) as nextval,"
                        + " uuid(),"
                        + " 1,"
                        + " grps.grpnm,"
                        + " 'Y',"
                        + " current_date"
                        + " from (select distinct ltrim(rtrim(substr(a.family_title, 0, 45))) grpnm"
                        + " from mm_unspsc_t a, mm_catalog_item_pending_t b"
                        + " where a.code = b.unspsc_cd"
                        + " and b.catalog_pending_doc_nbr = '" + fdocNbr + "') grps"
                        + " left outer join mm_catalog_group_t x"
                        + " on x.catalog_group_nm = grps.grpnm"
                        + " where x.catalog_group_cd is null"
                        + " and grps.grpnm is not null");
        LOG
                .info("addCatalogGroupsByUnspc - Inserted " + count
                        + " records into mm_catalog_group_t");
    }

    /**
     * @param fdocNbr
     */
    private void addCatalogSubgroupsByUnspc(String fdocNbr) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_subgroup_t" + " (CATALOG_SUBGROUP_ID," + " OBJ_ID,"
                        + " VER_NBR," + " CATALOG_GROUP_CD," + " CATALOG_SUBGROUP_CD,"
                        + " CATALOG_SUBGROUP_DESC," + " ACTV_IND," + " LAST_UPDATE_DT,"
                        + " PRIOR_CATALOG_SUBGROUP_ID)" + " select (select last_insert_id()+1 from MM_CATALOG_SUBGROUP_S) as nextval,"
                        + " uuid()," + " 1," + " y.catalog_group_cd,"
                        + " (select last_insert_id() from MM_CATALOG_SUBGROUP_S)," + " subrps.grpdesc," + " 'Y',"
                        + " current_date," + " null" + " from (select distinct a.family_title grp,"
                        + " ltrim(rtrim(substr(a.class_title, 0, 80))) grpdesc"
                        + " from mm_unspsc_t a, mm_catalog_item_pending_t b"
                        + " where a.code = b.unspsc_cd" + " and b.catalog_pending_doc_nbr = '"
                        + fdocNbr + "') subrps" + " left outer join mm_catalog_subgroup_t x"
                        + " on upper(ltrim(rtrim(substr(x.catalog_subgroup_desc, 0, 80)))) ="
                        + " upper(ltrim(rtrim(substr(subrps.grpdesc, 0, 80))))"
                        + " join mm_catalog_group_t y"
                        + " on ltrim(rtrim(upper(substr(y.catalog_group_nm, 0, 45)))) ="
                        + " ltrim(rtrim(upper(substr(subrps.grp, 0, 45))))"
                        + " where x.catalog_subgroup_id is null" + " and subrps.grp is not null"
                        + " and subrps.grpdesc is not null");
        LOG.info("addCatalogSubgroupsByUnspc - Inserted " + count
                + " records into mm_catalog_subgroup_t");
    }

    /**
     * @param fdocNbr
     */
    private void assignCatalogGroupingByUnspc(String fdocNbr) {
        int count = getJdbcTemplate().update(
                "update mm_catalog_item_pending_t a" + " set a.catalog_group_cd ="
                        + " (select distinct x.catalog_group_cd" + " from mm_unspsc_t z"
                        + " join mm_catalog_group_t y"
                        + " on ltrim(rtrim(upper(substr(y.catalog_group_nm, 0, 45)))) ="
                        + " ltrim(rtrim(upper(substr(z.family_title, 0, 45))))"
                        + " join mm_catalog_subgroup_t x"
                        + " on upper(ltrim(rtrim(substr(x.catalog_subgroup_desc, 0, 80)))) ="
                        + " upper(ltrim(rtrim(substr(z.class_title, 0, 80))))"
                        + " and x.catalog_group_cd = y.catalog_group_cd"
                        + " where z.code = a.unspsc_cd" + " and rownum < 2),"
                        + " a.catalog_subgroup_cd =" + " (select distinct x.catalog_subgroup_cd"
                        + " from mm_unspsc_t z" + " join mm_catalog_group_t y"
                        + " on ltrim(rtrim(upper(substr(y.catalog_group_nm, 0, 45)))) ="
                        + " ltrim(rtrim(upper(substr(z.family_title, 0, 45))))"
                        + " join mm_catalog_subgroup_t x"
                        + " on upper(ltrim(rtrim(substr(x.catalog_subgroup_desc, 0, 80)))) ="
                        + " upper(ltrim(rtrim(substr(z.class_title, 0, 80))))"
                        + " and x.catalog_group_cd = y.catalog_group_cd"
                        + " where z.code = a.unspsc_cd" + " and rownum < 2)"
                        + " where a.catalog_pending_doc_nbr = '" + fdocNbr + "'"
                        + " and a.catalog_group_cd is null");
        LOG.info("assignCatalogGroupingByUnspc - Updated " + count
                + " records into mm_catalog_item_pending_t");
    }

    /**
     * @param fdocNbr
     * @param catalogCode
     */
    private void addCatalogGroups(String fdocNbr, String catalogCode) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_group_t" + " (CATALOG_GROUP_CD," + " OBJ_ID," + " VER_NBR,"
                        + " CATALOG_GROUP_NM," + " ACTV_IND," + " LAST_UPDATE_DT)" + " select '"
                        + catalogCode + "' || (select last_insert_id()+1 as nextval from mm_catalog_group_s)," + " uuid()," + " 1,"
                        + " grps.grpnm," + " 'Y'," + " current_date"
                        + " from (select distinct ltrim(rtrim(a.catalog_group_nm)) grpnm"
                        + " from mm_catalog_item_pending_t a"
                        + " where a.catalog_group_cd is null and a.catalog_pending_doc_nbr = '"
                        + fdocNbr + "') grps" + " left outer join mm_catalog_group_t x"
                        + " on x.catalog_group_nm = grps.grpnm"
                        + " where x.catalog_group_cd is null" + " and grps.grpnm is not null");
        LOG.info("addCatalogGroups - Inserted " + count + " records into mm_catalog_group_t");
    }

    /**
     * @param fdocNbr
     */
    private void assignCatalogGroups(String fdocNbr) {
        int count = getJdbcTemplate().update(
                "update mm_catalog_item_pending_t a" + " set a.catalog_group_cd ="
                        + " (select catalog_group_cd" + " from mm_catalog_group_t x"
                        + " where ltrim(rtrim(upper(x.catalog_group_nm))) ="
                        + " ltrim(rtrim(upper(a.catalog_group_nm))))"
                        + " where a.catalog_pending_doc_nbr = '" + fdocNbr + "'"
                        + " and a.catalog_group_cd is null");
        LOG.info("assignCatalogGroups - Updated " + count
                + " records into mm_catalog_item_pending_t");
    }

    /**
     * @param fdocNbr
     * @param catalogCode
     */
    private void addCatalogSubgroups(String fdocNbr, String catalogCode) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_subgroup_t" + " (CATALOG_SUBGROUP_ID," + " OBJ_ID,"
                        + " VER_NBR," + " CATALOG_GROUP_CD," + " CATALOG_SUBGROUP_CD,"
                        + " CATALOG_SUBGROUP_DESC," + " ACTV_IND," + " LAST_UPDATE_DT,"
                        + " PRIOR_CATALOG_SUBGROUP_ID)" + " select (select last_insert_id()+1 from MM_CATALOG_SUBGROUP_S),"
                        + " uuid()," + " 1," + " subrps.grp," + " '" + catalogCode
                        + "' || MM_CATALOG_SUBGROUP_S.currval," + " subrps.grpdesc," + " 'Y',"
                        + " current_date," + " null"
                        + " from (select distinct a.catalog_group_cd grp,"
                        + " ltrim(rtrim(a.catalog_subgroup_desc)) grpdesc"
                        + " from mm_catalog_item_pending_t a"
                        + " where a.catalog_subgroup_cd is null and  a.catalog_pending_doc_nbr = '"
                        + fdocNbr + "') subrps" + " left outer join mm_catalog_subgroup_t x"
                        + " on x.catalog_group_cd = subrps.grp"
                        + " and x.catalog_subgroup_desc = subrps.grpdesc"
                        + " where x.catalog_subgroup_id is null" + " and subrps.grp is not null"
                        + " and subrps.grpdesc is not null");
        LOG.info("addCatalogSubgroups - Inserted " + count + " records into mm_catalog_subgroup_t");
    }

    /**
     * @param fdocNbr
     */
    private void assignCatalogSubgroups(String fdocNbr) {
        int count = getJdbcTemplate().update(
                "update mm_catalog_item_pending_t a" + " set a.catalog_subgroup_cd ="
                        + " (select x.catalog_subgroup_cd" + " from mm_catalog_subgroup_t x"
                        + " where rtrim(ltrim(upper(x.catalog_subgroup_desc))) ="
                        + " rtrim(ltrim(upper(a.catalog_subgroup_desc)))"
                        + " and x.catalog_group_cd = a.catalog_group_cd and rownum < 2 )"
                        + " where a.catalog_pending_doc_nbr = '" + fdocNbr + "'"
                        + " and a.catalog_subgroup_cd is null"
                        + " and a.catalog_subgroup_desc is not null"
                        + " and a.catalog_group_cd is not null");
        LOG.info("assignCatalogSubgroups - Updated " + count
                + " records into mm_catalog_item_pending_t");
    }

    /**
     * @param fdocNbr
     * @param catalogId
     */
    private void updateExistingItems(String fdocNbr, Long catalogId) {
        int count = getJdbcTemplate().update(
                "update mm_catalog_item_t x" + " set x.MANUFACTURER_NBR ="
                        + " (select MANUFACTURER_NBR" + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr" + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.CATALOG_UNIT_OF_ISSUE_CD ="
                        + " (select CATALOG_UNIT_OF_ISSUE_CD"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.CATALOG_PRC ="
                        + " (select CATALOG_PRC"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.CATALOG_DESC ="
                        + " (select CATALOG_DESC"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.RECYCLED_IND ="
                        + " (select RECYCLED_IND"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.UNSPSC_CD ="
                        + " (select UNSPSC_CD"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.SHIPPING_WGT ="
                        + " (select SHIPPING_WGT"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.SHIPPING_HT ="
                        + " (select SHIPPING_HT"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.SHIPPING_WD ="
                        + " (select SHIPPING_WD"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.SHIPPING_LH ="
                        + " (select SHIPPING_LH"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.CATALOG_ITEM_PND_ID ="
                        + " (select CATALOG_ITEM_PND_ID"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.TAXABLE_IND ="
                        + " (select TAXABLE_IND"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.SPAID_ID ="
                        + " (select SPAID_ID"
                        + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.distributor_nbr"
                        + " and x.catalog_id = "
                        + catalogId
                        + " and y.catalog_pending_doc_nbr = '"
                        + fdocNbr
                        + "'),"
                        + " x.LAST_UPDATE_DT = current_date"
                        + " where x.catalog_item_id in"
                        + " (select a.catalog_item_id"
                        + " from mm_catalog_item_t a, mm_catalog_item_pending_t b"
                        + " where a.distributor_nbr = b.distributor_nbr"
                        + " and a.catalog_id = "
                        + catalogId + "" + " and b.catalog_pending_doc_nbr = '" + fdocNbr + "')");
        LOG.info("updateExistingItems - Updated " + count + " records into mm_catalog_item_t");
    }

    /**
     * @param fdocNbr
     */
    private void updateExistingImages(String fdocNbr) {
        int count = getJdbcTemplate().update(
                "update mm_catalog_image_t x" + " set x.CATALOG_IMAGE_URL ="
                        + " (select CATALOG_IMAGE_URL" + " from mm_catalog_item_pending_t y"
                        + " where y.distributor_nbr = x.catalog_image_nm"
                        + " and y.catalog_pending_doc_nbr = '" + fdocNbr + "'),"
                        + " x.LAST_UPDATE_DT = current_date" + " where x.catalog_image_id in"
                        + " (select a.catalog_image_id"
                        + " from mm_catalog_image_t a, mm_catalog_item_pending_t b"
                        + " where b.distributor_nbr = a.catalog_image_nm"
                        + " and b.catalog_image_url is not null"
                        + " and b.catalog_pending_doc_nbr = '" + fdocNbr + "')");
        LOG.info("updateExistingImages - Updated " + count + " records into mm_catalog_image_t");
    }

    /**
     * @param fdocNbr
     * @param catalogId
     */
    private void clearExistingImageItems(String fdocNbr, Long catalogId) {
        int count = getJdbcTemplate()
                .update(
                        "delete from mm_catalog_item_image_t t where exists "
                                + "(select 1 from mm_catalog_item_pending_t a join mm_catalog_item_t b on b.distributor_nbr = a.distributor_nbr "
                                + "and a.catalog_pending_doc_nbr = '" + fdocNbr
                                + "' where b.catalog_id = " + catalogId
                                + " and b.catalog_item_id = t.catalog_item_id)");
        LOG.info("updateExistingImageItems - Deleted " + count
                + " records into mm_catalog_item_image_t");
    }

    /**
     * @param fdocNbr
     * @param catalogId
     */
    private void addNewItems(String fdocNbr, Long catalogId) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_item_t" + " (CATALOG_ITEM_ID," + " OBJ_ID," + " VER_NBR,"
                        + " DISTRIBUTOR_NBR," + " MANUFACTURER_NBR," + " CATALOG_UNIT_OF_ISSUE_CD,"
                        + " CATALOG_PRC," + " CATALOG_DESC," + " RECYCLED_IND," + " WILLCALL_IND,"
                        + " UNSPSC_CD," + " SHIPPING_WGT," + " SHIPPING_HT," + " SHIPPING_WD,"
                        + " SHIPPING_LH," + " CATALOG_ID," + " STOCK_ID," + " DISPLAYABLE_IND,"
                        + " CATALOG_ITEM_PND_ID," + " SUBSTITUTE_DISTRIBUTOR_NBR,"
                        + " TAXABLE_IND," + " SPAID_ID," + " ACTV_IND," + " LAST_UPDATE_DT)"
                        + " select (select last_insert_id()+1 from MM_CATALOG_ITEM_S)," + " uuid()," + " 1,"
                        + " a.DISTRIBUTOR_NBR," + " a.MANUFACTURER_NBR,"
                        + " a.CATALOG_UNIT_OF_ISSUE_CD," + " a.CATALOG_PRC," + " a.CATALOG_DESC,"
                        + " a.RECYCLED_IND," + " 'N'," + " a.UNSPSC_CD," + " a.SHIPPING_WGT,"
                        + " a.SHIPPING_HT," + " a.SHIPPING_WD," + " a.SHIPPING_LH," + " "
                        + catalogId + "," + " null," + " 'Y'," + " a.CATALOG_ITEM_PND_ID,"
                        + " null," + " a.TAXABLE_IND," + " a.SPAID_ID," + " 'Y'," + " current_date"
                        + " from mm_catalog_item_pending_t a"
                        + " left outer join mm_catalog_item_t b"
                        + " on a.distributor_nbr = b.distributor_nbr" + " and b.catalog_id = "
                        + catalogId + " " + " where b.catalog_item_id is null"
                        + " and a.catalog_pending_doc_nbr = '" + fdocNbr + "'");
        LOG.info("addNewItems - Inserted " + count + " records into mm_catalog_item_t");
    }

    /**
     * @param fdocNbr
     */
    private void addNewImages(String fdocNbr) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_image_t" + " (CATALOG_IMAGE_ID," + " OBJ_ID," + " VER_NBR,"
                        + " CATALOG_IMAGE_NM," + " CATALOG_IMAGE_URL," + " ACTV_IND,"
                        + " LAST_UPDATE_DT)" + " select (select last_insert_id()+1 from mm_catalog_image_s),"
                        + " uuid()," + " 1," + " a.distributor_nbr," + " a.CATALOG_IMAGE_URL,"
                        + " a.ACTV_IND," + " current_date" + " from mm_catalog_item_pending_t a"
                        + " left outer join mm_catalog_image_t b"
                        + " on a.distributor_nbr = b.catalog_image_nm"
                        + " where b.catalog_image_id is null"
                        + " and a.catalog_image_url is not null"
                        + " and a.catalog_pending_doc_nbr = '" + fdocNbr + "'");
        LOG.info("addNewImages - Inserted " + count + " records into mm_catalog_image_t");
    }

    /**
     * @param fdocNbr
     * @param catalogId
     */
    private void addNewImageItems(String fdocNbr, Long catalogId) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_item_image_t" + " (CATALOG_ITEM_IMAGE_ID," + " OBJ_ID,"
                        + " VER_NBR," + " CATALOG_ITEM_ID," + " CATALOG_IMAGE_ID," + " ACTV_IND,"
                        + " LAST_UPDATE_DT)" + " select (select last_insert_id() from mm_catalog_item_image_s),"
                        + " uuid()," + " 1," + " b.catalog_item_id," + " c.catalog_image_id,"
                        + " 'Y'," + " current_date" + " from mm_catalog_item_pending_t a"
                        + " join mm_catalog_item_t b" + " on b.distributor_nbr = a.distributor_nbr"
                        + " and b.catalog_id = " + catalogId + "" + " join mm_catalog_image_t c"
                        + " on c.catalog_image_nm = a.distributor_nbr"
                        + " left outer join mm_catalog_item_image_t d"
                        + " on d.catalog_item_id = b.catalog_item_id"
                        + " and d.catalog_image_id = c.catalog_image_id"
                        + " where d.catalog_item_image_id is null"
                        + " and a.catalog_pending_doc_nbr = '" + fdocNbr + "'");
        LOG.info("addNewImageItems - Inserted " + count + " records into mm_catalog_item_image_t");
    }

    /**
     * @param fdocNbr
     * @param catalogId
     */
    private void deactivateItems(Long catalogId) {
        int count = getJdbcTemplate().update(
                "update mm_catalog_item_t x set x.actv_ind = 'N'  where x.catalog_id = "
                        + catalogId);
        LOG.info("deactivateItems - Updated " + count + " records into mm_catalog_item_t");
    }

    /**
     * @param fdocNbr
     * @param catalogId
     */
    private void assignCatalogSubgroupsToItems(String fdocNbr, Long catalogId) {
        int count = getJdbcTemplate().update(
                "insert into mm_catalog_subgroup_item_t" + " (CATALOG_SUBGROUP_ITEM_ID,"
                        + " OBJ_ID," + " VER_NBR," + " CATALOG_ITEM_ID," + " CATALOG_SUBGROUP_ID,"
                        + " ACTV_IND," + " LAST_UPDATE_DT)"
                        + " select (select last_insert_id()+1 from MM_CATALOG_SUBGROUP_ITEM_S)," + " uuid()," + " 1,"
                        + " a.catalog_item_id," + " c.catalog_subgroup_id," + " 'Y',"
                        + " current_date" + " from mm_catalog_item_t a"
                        + " join mm_catalog_item_pending_t b"
                        + " on a.distributor_nbr = b.distributor_nbr" + " and a.catalog_id = "
                        + catalogId + "" + " join mm_catalog_subgroup_t c"
                        + " on b.catalog_group_cd = c.catalog_group_cd"
                        + " and b.catalog_subgroup_cd = c.catalog_subgroup_cd"
                        + " left outer join mm_catalog_subgroup_item_t d"
                        + " on a.catalog_item_id = d.catalog_item_id"
                        + " and c.catalog_subgroup_id = d.catalog_subgroup_id"
                        + " where d.catalog_subgroup_item_id is null"
                        + " and b.catalog_group_cd is not null"
                        + " and b.catalog_subgroup_cd is not null"
                        + " and b.catalog_pending_doc_nbr = '" + fdocNbr + "'");
        LOG.info("assignCatalogSubgroupsToItems - Inserted " + count
                + " records into mm_catalog_subgroup_item_t");
    }
}
