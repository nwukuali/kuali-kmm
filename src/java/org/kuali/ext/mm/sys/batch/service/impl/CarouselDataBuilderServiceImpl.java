/**
 *
 */
package org.kuali.ext.mm.sys.batch.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.MMKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao;
import org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService;
import org.kuali.rice.kns.service.DateTimeService;
import org.kuali.rice.kns.service.KNSServiceLocator;


/**
 * @author schneppd
 *
 */
public class CarouselDataBuilderServiceImpl implements CarouselDataBuilderService {

	private final String CAROUSEL_FILE_EXTENSION = ".dps";

	private CarouselDataBuilderDao carouselDataBuilderDao;


	/**
	 * Sets the CarouselDataBuilderDao
	 *
	 * @param carouselDataBuilderDao
	 */
	public void setCarouselDataBuilderDao(CarouselDataBuilderDao carouselDataBuilderDao) {
		this.carouselDataBuilderDao = carouselDataBuilderDao;
	}

	/**
	 * Gets the CarouselDataBuilderDao
	 *
	 * @return carouselDataBuilderDao
	 */
	public CarouselDataBuilderDao getCarouselDataBuilderDao() {
		return carouselDataBuilderDao;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#getPickLinesDataForCarousel(java.lang.String, java.util.List)
	 */
	public Map<Long, String> getPickLinesDataForCarousel(String warehouseCd, List<String> zoneCodes) {
		return getCarouselDataBuilderDao().getPickLinesForCarousel(warehouseCd, zoneCodes);
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#getPickLinesSummaryForCarousel(java.lang.String, java.util.List)
	 */
	public List<String> getPickLinesSummaryForCarousel(String warehouseCd, List<String> zoneCodes) {
		return getCarouselDataBuilderDao().getPickLinesOrderSummaryForCarousel(warehouseCd, zoneCodes);
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#getCheckinDataForCarousel(java.lang.String, java.util.List)
	 */
	public Map<Long, String> getCheckinDataForCarousel(String warehouseCd, List<String> zoneCodes) {
		return getCarouselDataBuilderDao().getCheckinLinesForCarousel(warehouseCd, zoneCodes);
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#getCheckinSummaryForCarousel(java.lang.String, java.util.List)
	 */
	public List<String> getCheckinSummaryForCarousel(String warehouseCd, List<String> zoneCodes) {
		return getCarouselDataBuilderDao().getCheckinOrderSummaryForCarousel(warehouseCd, zoneCodes);
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#getStockCountDataForCarousel(java.lang.String, java.util.List)
	 */
	public Map<Long, String> getStockCountDataForCarousel(String warehouseCd, List<String> zoneCodes) {
		return getCarouselDataBuilderDao().getStockCountLinesForCarousel(warehouseCd, zoneCodes);
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#getStockCountSummaryForCarousel(java.lang.String, java.util.List)
	 */
	public List<String> getStockCountSummaryForCarousel(String warehouseCd, List<String> zoneCodes) {
		return getCarouselDataBuilderDao().getCountWorksheetSummaryForCarousel(warehouseCd, zoneCodes);
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.service.CarouselDataBuilderService#process()
	 */
	public void process() {
		List<String> zonesByWarehouse = KNSServiceLocator.getParameterService().getParameterValues(MMConstants.MM_NAMESPACE, MMConstants.Parameters.BATCH, MMConstants.Parameters.CAROUSEL_WAREHOUSE_ZONES);
		Map<String, List<String>> warehouseZoneMap = getWarehouseZoneMap(zonesByWarehouse);
		for(String warehouseCode : warehouseZoneMap.keySet()) {
			List<String> carouselLines = new ArrayList<String>();

			Map<Long, String> carouselPickData = getPickLinesDataForCarousel(warehouseCode, warehouseZoneMap.get(warehouseCode));
			for(String line : carouselPickData.values())
				carouselLines.add(line);
			carouselLines.addAll(getPickLinesSummaryForCarousel(warehouseCode, warehouseZoneMap.get(warehouseCode)));

//			Map<Long, String> carouselCheckinData = getCheckinDataForCarousel(warehouseCode, warehouseZoneMap.get(warehouseCode));
//			for(String line : carouselCheckinData.values())
//				carouselLines.add(line);
//			carouselLines.addAll(getCheckinSummaryForCarousel(warehouseCode, warehouseZoneMap.get(warehouseCode)));
//
//			Map<Long, String> carouselStockCountData = getStockCountDataForCarousel(warehouseCode, warehouseZoneMap.get(warehouseCode));
//			for(String line : carouselStockCountData.values())
//				carouselLines.add(line);
//			carouselLines.addAll(getStockCountSummaryForCarousel(warehouseCode, warehouseZoneMap.get(warehouseCode)));

			if(carouselLines.isEmpty())
				continue;

			DateTimeService dateTimeService = SpringContext.getBean(DateTimeService.class);
			String filePath = KNSServiceLocator.getKualiConfigurationService().getPropertyString(MMKeyConstants.EXTERNAL_CAROUSEL_DIRECTORY_KEY) + File.separator;
			String fileName = dateTimeService.toDateTimeStringForFilename(dateTimeService.getCurrentDate());
			File directory = new File(filePath + "WH-" + warehouseCode);
			directory.mkdirs();
			File carouselFile = new File(directory, fileName + CAROUSEL_FILE_EXTENSION);
			OutputStreamWriter writer = null;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(carouselFile));
				for(String line : carouselLines) {
					writer.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			finally {
			    if(writer != null) {
                    try {
                        writer.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
			    }
			}

//			updateCarouselLog(carouselPickData.keySet(), carouselCheckinData.keySet(), carouselStockCountData.keySet());
	        updateCarouselLog(carouselPickData.keySet(), new ArrayList<Long>(), new ArrayList<Long>());

		}
	}

	/**
	 * Used to get all of the zones configured as carousel zones and their containing
	 * warehouses as configured in System Parameters.
	 *
	 * @param warehouseZonesParameterValue
	 * @return a Map of warehouseCodes to a list of carousel zones.
	 */
	private Map<String, List<String>> getWarehouseZoneMap(List<String> warehouseZonesParameterValue) {
		Map<String, List<String>> warehouseZoneMap = new HashMap<String, List<String>>();

		for(String parm : warehouseZonesParameterValue) {
			String[] parmSplit = parm.split("=");
			if(parmSplit.length < 2)
				continue;
			String warehouse = parmSplit[0].trim();
			List<String> zones = new ArrayList<String>();
			for(String zone : parmSplit[1].split(",")) {
				zones.add(zone.trim());
			}
			if(!zones.isEmpty())
				warehouseZoneMap.put(warehouse, zones);
		}

		return warehouseZoneMap;
	}

	/**
	 * Posts all line ids (pick list, check-in, stock count) to the Carousel Log.
	 * This should be called after all line data has been written to the carousel file.
	 *
	 * @param pickLineIds
	 * @param checkinLineIds
	 * @param stockCountLineIds
	 */
	private void updateCarouselLog(Collection<Long> pickLineIds, Collection<Long> checkinLineIds, Collection<Long> stockCountLineIds) {
		for(Long pickLineId : pickLineIds) {
			getCarouselDataBuilderDao().postPickLineToCarouselLog(pickLineId);
		}
		for(Long checkinLineId : checkinLineIds) {
			getCarouselDataBuilderDao().postCheckinLineToCarouselLog(checkinLineId);
		}
		for(Long stockCountLineId : stockCountLineIds) {
			getCarouselDataBuilderDao().postCountLineToCarouselLog(stockCountLineId);
		}
	}

}
