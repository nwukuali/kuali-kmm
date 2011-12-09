/**
 *
 */
package org.kuali.ext.mm.sys.batch.scheduling;

import java.util.Properties;

/**
 * @author harsha07
 */
public class LuceneIndexerSchedulerBean extends
        org.springframework.scheduling.quartz.SchedulerFactoryBean {
    private boolean enableLuceneIndexing;
    private Properties quartzPropertiesRef;

    /**
     * @see org.springframework.scheduling.quartz.SchedulerFactoryBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.enableLuceneIndexing) {
            quartzPropertiesRef.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
            super.setQuartzProperties(quartzPropertiesRef);
            super.afterPropertiesSet();
        }
    }

    /**
     * Gets the enableLuceneIndexing property
     * 
     * @return Returns the enableLuceneIndexing
     */
    public boolean isEnableLuceneIndexing() {
        return this.enableLuceneIndexing;
    }

    /**
     * Sets the enableLuceneIndexing property value
     * 
     * @param enableLuceneIndexing The enableLuceneIndexing to set
     */
    public void setEnableLuceneIndexing(boolean batchEnabled) {
        this.enableLuceneIndexing = batchEnabled;
    }

    /**
     * Gets the quartzProperties property
     * 
     * @return Returns the quartzProperties
     */
    public Properties getQuartzPropertiesRef() {
        return this.quartzPropertiesRef;
    }

    /**
     * Sets the quartzProperties property value
     * 
     * @param quartzProperties The quartzProperties to set
     */
    public void setQuartzPropertiesRef(Properties quartzProperties) {
        this.quartzPropertiesRef = quartzProperties;
    }
}
