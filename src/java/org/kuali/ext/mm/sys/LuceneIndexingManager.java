/**
 * 
 */
package org.kuali.ext.mm.sys;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class LuceneIndexingManager {
    private static final Logger LOG = Logger.getLogger(LuceneIndexingManager.class);
    public static final StandardAnalyzer ANALYZER = new StandardAnalyzer(Version.LUCENE_30);
    private static Searcher searcher;
    private static boolean refreshInProgress;
    private static IndexReader indexReader;
    
    public static final File getLucenceIndexDir() {
        File indexDir = new File(KNSServiceLocator.getKualiConfigurationService()
                .getPropertyString("lucene.index.storage.dir"));
        if (!indexDir.exists()) {
            LOG.info("Created dir path " + indexDir);
            indexDir.mkdirs();
        }
        return indexDir;
    }

    public static final File getLucenceIndexBackupDir() {
        File indexDir = new File(KNSServiceLocator.getKualiConfigurationService()
                .getPropertyString("lucene.index.storage.dir"), "backup");
        if (!indexDir.exists()) {
            LOG.info("Created dir path " + indexDir);
            indexDir.mkdirs();
        }
        return indexDir;
    }

    public static final Searcher getSearcher() {
        try {
            if (searcher == null || indexReader == null) {
                indexReader = IndexReader.open(new SimpleFSDirectory(getLucenceIndexDir()));
                searcher = new IndexSearcher(indexReader);
            }
        }
        catch (Exception e) {
            searcher = null;
            LOG.error("Index searcher is not ready.", e);
        }

        return searcher;
    }

    public static final boolean beginRefresh() {
        refreshInProgress = true;
        try {
            if (indexReader != null) {
                indexReader.close();
                indexReader = null;
            }
            if (searcher != null) {
                searcher.close();
                searcher = null;
            }
        }
        catch (IOException e) {
            // wait 30 seconds and re-attempt again
            try {
                LOG.info("Waiting 30 seconds to re-attempt index closure.");
                Thread.sleep(30 * 1000);
            }
            catch (InterruptedException e1) {
                LOG.error("Sleep failed.", e1);
            }
            return beginRefresh();
        }
        return refreshInProgress;
    }

    public static final void endRefresh() {
        refreshInProgress = false;
    }
    
    public static final boolean isRefreshInProgress() {
        return refreshInProgress;
    }

    
}
