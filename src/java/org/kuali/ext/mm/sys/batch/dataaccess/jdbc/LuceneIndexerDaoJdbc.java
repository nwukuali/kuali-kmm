package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.SimpleFSDirectory;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.sys.LuceneIndexingManager;
import org.kuali.ext.mm.sys.batch.dataaccess.LuceneIndexerDao;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


public class LuceneIndexerDaoJdbc extends PlatformAwareDaoBaseJdbc implements LuceneIndexerDao {
    private static final Logger LOG = Logger.getLogger(LuceneIndexerDaoJdbc.class);

    /**
     * @see org.kuali.ext.mm.sys.batch.dataaccess.LuceneIndexerDao#indexCatalogItems()
     */
    public void indexCatalogItems() {
        try {
            File indexDir = LuceneIndexingManager.getLucenceIndexDir();
            final IndexWriter writer = new IndexWriter(new SimpleFSDirectory(indexDir),
                LuceneIndexingManager.ANALYZER, true, MaxFieldLength.UNLIMITED);
            try {
                getJdbcTemplate()
                        .query(
                                "select catalog_id, catalog_item_id, distributor_nbr, catalog_desc from mm_catalog_item_t where actv_ind='Y' and displayable_ind='Y'",
                                new ResultSetExtractor() {
                                    /**
                                     * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                                     */
                                    @Override
                                    public Object extractData(ResultSet rs) throws SQLException,
                                            DataAccessException {
                                        try {
                                            int count = 0;
                                            while (rs.next()) {
                                                count++;
                                                String desc = rs.getString("catalog_desc").trim();
                                                String savedDesc = buildSavedDescription(desc);
                                                Document d = new Document();
                                                d.add(new Field(
                                                    MMConstants.LuceneIndexing.CATALOG_ITEM_ID, rs
                                                            .getString("catalog_item_id"),
                                                    Field.Store.YES, Field.Index.NO));
                                                d.add(new Field(
                                                    MMConstants.LuceneIndexing.SAVED_DESC,
                                                    savedDesc, Field.Store.YES, Field.Index.NO));
                                                d.add(new Field(
                                                    MMConstants.LuceneIndexing.CATALOG_ID, rs
                                                            .getString("catalog_id"),
                                                    Field.Store.NO, Field.Index.NOT_ANALYZED));
                                                d.add(new Field(
                                                    MMConstants.LuceneIndexing.DISTRIBUTOR_NBR, rs
                                                            .getString("distributor_nbr"),
                                                    Field.Store.NO, Field.Index.ANALYZED));
                                                d.add(new Field(
                                                    MMConstants.LuceneIndexing.CATALOG_DESC, desc,
                                                    Field.Store.NO, Field.Index.ANALYZED));
                                                writer.addDocument(d);
                                                if (count % 100000 == 0) {
                                                    LOG.info("Indexed record count: " + count);
                                                }
                                            }
                                            LOG.info("Indexed record count: " + count);
                                        }
                                        catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                        return writer;
                                    }

                                });
            }
            finally {
                LOG.info("Optimizing index");
                writer.optimize();
                writer.close();
                LOG.info("Closed index writer");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Splits the string into word tokens and combines them back together until the string is greater than the max length to prevent
     * words from being cut in half by a simple desc.substring(0,max_length)
     * 
     * @param desc
     * @return a formatted String value of the catalog item description.
     */
    private String buildSavedDescription(String desc) {
        String savedDescription = "";
        String[] tokens = desc.split(" ");

        for (String word : tokens) {
            // ignore stop words from search suggestions
            if (!StopAnalyzer.ENGLISH_STOP_WORDS_SET.contains(word.toLowerCase())) {
                savedDescription += " " + word;
            }
            if (savedDescription.length() > 50) {
                break;
            }
        }
        return savedDescription.trim();
    }
}
