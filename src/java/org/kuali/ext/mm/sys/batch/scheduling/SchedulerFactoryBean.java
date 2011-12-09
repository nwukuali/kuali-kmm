/**
 *
 */
package org.kuali.ext.mm.sys.batch.scheduling;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * @author harsha07
 */
public class SchedulerFactoryBean extends
        org.springframework.scheduling.quartz.SchedulerFactoryBean {
    private boolean batchEnabled;
    private boolean useJdbcJobStore;
    private Properties quartzPropertiesRef;
    private DataSource dataSourceRef;

    /**
     * @see org.springframework.scheduling.quartz.SchedulerFactoryBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.batchEnabled) {
            if (useJdbcJobStore) {
                quartzPropertiesRef.put("org.quartz.jobStore.class",
                        "org.quartz.impl.jdbcjobstore.JobStoreTX");
                quartzPropertiesRef.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
                quartzPropertiesRef.put("org.quartz.jobStore.isClustered", "true");
                quartzPropertiesRef.put("org.quartz.jobStore.useProperties", "false");
                setDataSource(getDataSourceRef());
            }
            else {
                quartzPropertiesRef
                        .put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
            }
            super.setQuartzProperties(quartzPropertiesRef);
            super.afterPropertiesSet();
        }
    }

    /**
     * Gets the batchEnabled property
     *
     * @return Returns the batchEnabled
     */
    public boolean isBatchEnabled() {
        return this.batchEnabled;
    }

    /**
     * Sets the batchEnabled property value
     *
     * @param batchEnabled The batchEnabled to set
     */
    public void setBatchEnabled(boolean batchEnabled) {
        this.batchEnabled = batchEnabled;
    }

    /**
     * Gets the useJdbcJobStore property
     *
     * @return Returns the useJdbcJobStore
     */
    public boolean isUseJdbcJobStore() {
        return this.useJdbcJobStore;
    }

    /**
     * Sets the useJdbcJobStore property value
     *
     * @param useJdbcJobStore The useJdbcJobStore to set
     */
    public void setUseJdbcJobStore(boolean useJdbcJobStore) {
        this.useJdbcJobStore = useJdbcJobStore;
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

    /**
     * Gets the dataSourceProperty property
     *
     * @return Returns the dataSourceProperty
     */
    public DataSource getDataSourceRef() {
        return this.dataSourceRef;
    }

    /**
     * Sets the dataSourceProperty property value
     *
     * @param dataSourceProperty The dataSourceProperty to set
     */
    public void setDataSourceRef(DataSource dataSourceProperty) {
        this.dataSourceRef = dataSourceProperty;
    }

}
