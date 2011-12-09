/**
 * 
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.dataaccess.FamisDataBuilderDao;
import org.kuali.ext.mm.sys.batch.service.FamisDataBuilderService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.ParameterService;


/**
 * @author harsha07
 */
public class FamisDataBuilderServiceImpl implements FamisDataBuilderService {
    private static final Logger LOG = Logger.getLogger(FamisDataBuilderServiceImpl.class);
    private FamisDataBuilderDao famisDataBuilderDao;

    /**
     * @see org.kuali.ext.mm.sys.batch.service.FamisDataBuilderService#buildFamisDataFeedFile()
     */
    public void buildFamisDataFeedFile() {
        try {
            String famisDirPath = KNSServiceLocator.getKualiConfigurationService()
                    .getPropertyString("external.famis.directory");
            File dir = new File(famisDirPath);
            dir.mkdirs();
            String fileName = "kmm-orders-"
                    + new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss").format(new Date()) + ".txt";
            File famisFile = new File(dir, fileName);
            List<String> famisAccounts = SpringContext.getBean(ParameterService.class)
                    .getParameterValues(MMConstants.MM_NAMESPACE, MMConstants.Parameters.BATCH,
                            MMConstants.Parameters.FAMIS_FEED_ACCOUNTS);
            List<String> lines = new ArrayList<String>();
            for (String string : famisAccounts) {
                String[] split = string.split("-");
                lines.addAll(famisDataBuilderDao.retrieveFamisLinesByAccount(split[0], split[1]));
            }
            BufferedWriter writer = null;
            if (!lines.isEmpty()) {
                try {
                    writer = new BufferedWriter(new FileWriter(famisFile));
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                    LOG.info("Created file " + fileName);
                }
                finally {
                    if (writer != null) {
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Gets the famisDataBuilderDao property
     * 
     * @return Returns the famisDataBuilderDao
     */
    public FamisDataBuilderDao getFamisDataBuilderDao() {
        return this.famisDataBuilderDao;
    }

    /**
     * Sets the famisDataBuilderDao property value
     * 
     * @param famisDataBuilderDao The famisDataBuilderDao to set
     */
    public void setFamisDataBuilderDao(FamisDataBuilderDao famisDataBuilderDao) {
        this.famisDataBuilderDao = famisDataBuilderDao;
    }

}
