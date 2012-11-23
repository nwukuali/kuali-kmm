/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc.mysql;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.upload.FormFile;
import org.kuali.ext.mm.businessobject.CatalogItemPending;
import org.kuali.ext.mm.businessobject.CatalogPendingDoc;
import org.kuali.ext.mm.sys.batch.dataaccess.CatalogPendingDocDao;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author sravani
 */
public class CatalogPendingDocDaoJdbc extends PlatformAwareDaoBaseJdbc implements
        CatalogPendingDocDao {


    /**
     * @throws IOException
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogItemPendingDocDao#getCatalogPendingDocs()
     */
    public ArrayList<CatalogPendingDoc> getCatalogPendingDocs(Date time) {
        String query = "SELECT FDOC_NBR, CATALOG_TIMESTAMP FROM MM_CATALOG_PENDING_DOC_T WHERE CATALOG_UPLOAD_STATUS = 'UPLOAD_READY'";
        final ArrayList<CatalogPendingDoc> returnedList = new ArrayList<CatalogPendingDoc>();
        getJdbcTemplate().query(query, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    CatalogPendingDoc cPD = new CatalogPendingDoc();
                    String fDocNbr = rs.getString("FDOC_NBR");
                    Timestamp td = rs.getTimestamp("CATALOG_TIMESTAMP");
                    cPD.setFdocNbr(fDocNbr);
                    cPD.setCatalogTimestamp(td);
                    returnedList.add(cPD);
                }
                return returnedList;
            }

        });
        return returnedList;
    }

    public File createTempCatalogFile(String fdocNbr) {
        final File tempFile;
        tempFile = new File(KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(
                "external.temp.directory")
                + File.separator + fdocNbr + ".csv");
        String query = "SELECT CATALOG_FILE FROM MM_CATALOG_PENDING_DOC_T WHERE FDOC_NBR = '"
                + fdocNbr + "' ";
        getJdbcTemplate().query(query, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    Clob fileContent = rs.getClob("CATALOG_FILE");
                    convertStreamToFile(fileContent, tempFile);
                }
                return tempFile;
            }

            private void convertStreamToFile(Clob fileContent, File tempFile) {
                try {
                    FileOutputStream os = new FileOutputStream(tempFile);
                    InputStream iStream = fileContent.getAsciiStream();
                    try {
                        byte[] buf = null;
                        int bufLen = 20000 * 1024;
                        buf = new byte[bufLen];
                        int readCount = 0;
                        while ((readCount = iStream.read(buf, 0, bufLen)) != -1) {
                            os.write(buf, 0, readCount);
                        }
                    }
                    finally {
                        os.flush();
                        os.close();
                        if (iStream != null) {
                            iStream.close();
                        }
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return tempFile;

    }

    @SuppressWarnings("unchecked")
    public String getCatalogCodeFromPendingDocNbr(String catalogPendingDocNbr) {
        String sql = "SELECT CATALOG_CD FROM MM_CATALOG_PENDING_DOC_T WHERE FDOC_NBR = '"
                + catalogPendingDocNbr + "'";
        Collection<ListOrderedMap> values = getJdbcTemplate().queryForList(sql);
        String catalogCode = null;
        for (Iterator iterator = values.iterator(); iterator.hasNext();) {
            ListOrderedMap listOrderedMap = (ListOrderedMap) iterator.next();
            catalogCode = (String) listOrderedMap.get("CATALOG_CD");
        }
        return catalogCode;
    }


    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogPendingDocDao#uplaodCatalogPendingDoc(java.lang.String)
     */
    public void uplaodCatalogPendingDoc(String catalogPendingDocNbr,
            final ArrayList<CatalogItemPending> loadList, boolean endOfFile) {
        getJdbcTemplate()
                .batchUpdate(
                        "INSERT INTO MM_CATALOG_ITEM_PENDING_T (CATALOG_ITEM_PND_ID,OBJ_ID,VER_NBR,CATALOG_PENDING_DOC_NBR,"
                                + "DISTRIBUTOR_NBR,MANUFACTURER_NBR,CATALOG_UNIT_OF_ISSUE_CD,CATALOG_PRC,CATALOG_DESC,"
                                + "RECYCLED_IND,UNSPSC_CD,ACTV_IND,LAST_UPDATE_DT, TAXABLE_IND,CATALOG_TIMESTAMP,"
                                + "CATALOG_IMAGE_URL,SPAID_ID,CATALOG_SUBGROUP_DESC,"
                                + "CATALOG_GROUP_NM) VALUES((select last_insert_id()+1 from mm_catalog_item_pending_s) as nextval, uuid(), '1', "
                                + "?,?,?,?,?,?,?,?,?,current_date,?,?,?,?,?,?)",
                        new BatchPreparedStatementSetter() {
                            public int getBatchSize() {
                                return loadList.size();
                            }

                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                CatalogItemPending catalogItemPending = loadList.get(i);
                                ps.setString(1, catalogItemPending.getCatalogPendingDocNbr());
                                ps.setString(2, catalogItemPending.getDistributorNbr());
                                ps.setString(3, catalogItemPending.getManufacturerNbr());
                                ps.setString(4, catalogItemPending.getCatalogUnitOfIssueCd());
                                ps.setDouble(5, catalogItemPending.getCatalogPrc().doubleValue());
                                ps.setString(6, catalogItemPending.getCatalogDesc());
                                ps.setString(7, catalogItemPending.isRecycledInd() ? "Y" : "N");
                                ps.setString(8, catalogItemPending.getUnspscCd());
                                ps.setString(9, catalogItemPending.isActive() ? "Y" : "N");
                                ps.setString(10, catalogItemPending.isTaxableInd() ? "Y" : "N");
                                ps.setTimestamp(11, catalogItemPending.getCatalogTimestamp());
                                ps.setString(12, catalogItemPending.getCatalogImageUrl());
                                ps.setString(13, catalogItemPending.getSpaidId());
                                ps.setString(14, catalogItemPending.getCatalogSubgroupDesc());
                                ps.setString(15, catalogItemPending.getCatalogGroupNm());
                            }

                        });
        if (endOfFile) {
            String sql = "UPDATE MM_CATALOG_PENDING_DOC_T SET CATALOG_UPLOAD_STATUS = 'UPLOAD' WHERE FDOC_NBR = '"
                    + catalogPendingDocNbr + "'";
            getJdbcTemplate().execute(sql);
        }

    }

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.CatalogPendingDocDao#uploadCatalogActionSave(java.lang.String, java.lang.String,
     *      java.lang.String, org.apache.struts.upload.FormFile)
     */
    public void uploadCatalogActionSave(String docNumber, String catalogCd, String catalogDesc,
            FormFile uploadedFile) {
        Connection conn = null;
        String sqlText = null;
        Statement stmt = null;
        ResultSet rset = null;
        BufferedInputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] byteBuffer;
        try {
            try {
                conn = getJdbcTemplate().getDataSource().getConnection();
                stmt = conn.createStatement();
                inputStream = new BufferedInputStream(uploadedFile.getInputStream());
                sqlText = "UPDATE MM_CATALOG_PENDING_DOC_T SET CATALOG_FILE = EMPTY_CLOB(), "
                        + "BATCH_LOG = '" + uploadedFile.getFileName() + "' WHERE FDOC_NBR = '"
                        + docNumber + "'";
                stmt.executeUpdate(sqlText);
                sqlText = "SELECT CATALOG_FILE FROM MM_CATALOG_PENDING_DOC_T WHERE  FDOC_NBR = '"
                        + docNumber + "' FOR UPDATE";
                rset = stmt.executeQuery(sqlText);
                rset.next();
                Clob clob = rset.getClob("CATALOG_FILE");
                outputStream = clob.setAsciiStream(0);
                byteBuffer = new byte[20000 * 1024];
                int readCount = 0;
                while ((readCount = inputStream.read(byteBuffer)) != -1) {
                    outputStream.write(byteBuffer, 0, readCount);
                }
            }
            finally {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (rset != null) {
                    rset.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
