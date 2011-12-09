/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.LuceneIndexingManager;
import org.kuali.ext.mm.sys.batch.dataaccess.LuceneIndexerDao;
import org.kuali.ext.mm.sys.batch.service.SearchIndexerService;

/**
 * @author harsha07
 */
public class SearchIndexerServiceImpl implements SearchIndexerService {
    private static final Logger LOG = Logger.getLogger(SearchIndexerServiceImpl.class);
    private LuceneIndexerDao luceneIndexerDao;
    private static FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isFile();
        }
    };

    /**
     * @see org.kuali.ext.mm.sys.batch.service.SearchIndexerService#indexCatalogItems()
     */
    @Override
    public void indexCatalogItems() {
        boolean success = false;
        try {
            // first close the searcher
            if (LuceneIndexingManager.beginRefresh()) {
                // index is closed, so can be backed up for recovery
                backupCurrentFiles();
            }
            // run indexer
            this.luceneIndexerDao.indexCatalogItems();
            success = true;
        }
        finally {
            if (success) {
                // clean up the backup
                removeBackupFiles();
            }
            else {
                // restore the back up
                restoreBackupFiles();
            }
            // mark refresh done
            LuceneIndexingManager.endRefresh();

            if (LuceneIndexingManager.getSearcher() != null) {
                LOG.info("Indexer is successfully re-initialized.");
            }
            else {
                LOG.error("Indexer failed to re-initialize.");
            }
        }
    }

    /**
     * 
     */
    protected void backupCurrentFiles() {
        LOG.info("Creating backup files ");
        File lucenceIndexDir = LuceneIndexingManager.getLucenceIndexDir();
        File backupDir = LuceneIndexingManager.getLucenceIndexBackupDir();
        File[] files = lucenceIndexDir.listFiles(fileFilter);
        for (File file : files) {
            if (file.renameTo(new File(backupDir, file.getName()))) {
                LOG.info("Moved " + file.getPath());
            }
            else {
                LOG.error("Failed to move " + file.getPath());
            }
        }
    }

    protected void removeBackupFiles() {
        LOG.info("Removing backup files ");
        File backupDir = LuceneIndexingManager.getLucenceIndexBackupDir();
        File[] files = backupDir.listFiles(fileFilter);
        for (File file : files) {
            if (file.delete()) {
                LOG.info("Deleted " + file.getPath());
            }
            else {
                LOG.error("Failed to delete " + file.getPath());
            }
        }
    }

    protected void restoreBackupFiles() {
        LOG.info("Restoring backup files ");
        File lucenceIndexDir = LuceneIndexingManager.getLucenceIndexDir();

        File[] files = lucenceIndexDir.listFiles(fileFilter);
        for (File file : files) {
            if (file.delete()) {
                LOG.info("Deleted " + file.getPath());
            }
            else {
                LOG.error("Failed to delete " + file.getPath());
            }
        }
        File backupDir = LuceneIndexingManager.getLucenceIndexBackupDir();
        files = backupDir.listFiles(fileFilter);
        for (File file : files) {
            if (file.renameTo(new File(lucenceIndexDir, file.getName()))) {
                LOG.info("Moved " + file.getPath());
            }
            else {
                LOG.error("Failed to move " + file.getPath());
            }
        }
    }

    /**
     * Gets the luceneIndexerDao property
     * 
     * @return Returns the luceneIndexerDao
     */
    public LuceneIndexerDao getLuceneIndexerDao() {
        return this.luceneIndexerDao;
    }

    /**
     * Sets the luceneIndexerDao property value
     * 
     * @param luceneIndexerDao The luceneIndexerDao to set
     */
    public void setLuceneIndexerDao(LuceneIndexerDao luceneIndexerDao) {
        this.luceneIndexerDao = luceneIndexerDao;
    }

}
