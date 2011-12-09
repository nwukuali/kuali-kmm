/**
 *
 */
package org.kuali.ext.mm.sys.batch.service;

import java.util.List;
import java.util.Map;

/**
 * @author schneppd
 *
 */
public interface CarouselDataBuilderService {

	/**
	 * Executes the CarouselDataBuilderService, builds line data, and writes it to a carousel file
	 * for all System Parameter configured warehouses with carousel zones.
	 */
	public void process();

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return a map of Pick List Line IDs to formatted Carousel data lines found by warehouseCd
	 * and zoneCodes
	 */
	public Map<Long, String> getPickLinesDataForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return List of formatted Carousel data summary lines per Order having items to be picked
	 */
	public List<String> getPickLinesSummaryForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return a map of Check-in Detail IDs to formatted Carousel data lines found by warehouseCd
	 * and zoneCodes
	 */
	public Map<Long, String> getCheckinDataForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return List of formatted Carousel data summary lines per Check-in Document having items to be checked in.
	 */
	public List<String> getCheckinSummaryForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return a map of StockCount IDs to formatted Carousel data lines found by warehouseCd
	 * and zoneCodes
	 */
	public Map<Long, String> getStockCountDataForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return List of formatted Carousel data summary lines per StockCount Worksheet having items to be counted.
	 */
	public List<String> getStockCountSummaryForCarousel(String warehouseCd, List<String> zoneCodes);


}
