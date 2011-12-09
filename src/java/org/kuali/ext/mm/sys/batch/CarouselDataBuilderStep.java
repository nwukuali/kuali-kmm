/**
 *
 */
package org.kuali.ext.mm.sys.batch;

import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService;

/**
 * @author harsha07
 */
public class CarouselDataBuilderStep implements BatchStep {
    private static final Logger LOG = Logger.getLogger(CarouselDataBuilderStep.class);
    private CarouselDataBuilderService carouselDataBuilderService;

    /**
     * @see org.kuali.ext.mm.sys.batch.BatchStep#execute(java.lang.String, java.util.Date)
     */
    public void execute(String jobName, Date time) {
        LOG.info("Starting step CarouselDataBuilderStep of batch job " + jobName);
        getCarouselDataBuilderService().process();
        LOG.info("Finished step CarouselDataBuilderStep of batch job " + jobName);
    }

	/**
	 * Sets the CarouselDataBuilderService
	 *
	 * @param carouselDataBuilderService
	 */
	public void setCarouselDataBuilderService(CarouselDataBuilderService carouselDataBuilderService) {
		this.carouselDataBuilderService = carouselDataBuilderService;
	}

	/**
	 * Gets the CarouselDataBuilderService
	 *
	 * @return a CarouselDataBuilderService object
	 */
	public CarouselDataBuilderService getCarouselDataBuilderService() {
		return carouselDataBuilderService;
	}

}
