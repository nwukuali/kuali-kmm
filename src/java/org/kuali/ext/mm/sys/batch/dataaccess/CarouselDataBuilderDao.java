/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess;

import java.util.List;
import java.util.Map;

/**
 * @author schneppd
 *
 */

public interface CarouselDataBuilderDao {

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return a map of Pick List Line IDs to formatted Carousel data lines found by warehouseCd
	 * and zoneCodes
	 */
	public Map<Long, String> getPickLinesForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return List of formatted Carousel data summary lines per Order having items to be picked
	 */
	public List<String> getPickLinesOrderSummaryForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return a map of Check-in Detail IDs to formatted Carousel data lines found by warehouseCd
	 * and zoneCodes
	 */
	public Map<Long, String> getCheckinLinesForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return List of formatted Carousel data summary lines per Check-in Document having items to be checked in.
	 */
	public List<String> getCheckinOrderSummaryForCarousel(String warehouseCd, List<String> zoneCodes);


	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return a map of StockCount IDs to formatted Carousel data lines found by warehouseCd
	 * and zoneCodes
	 */
	public Map<Long, String> getStockCountLinesForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * @param warehouseCd
	 * @param zoneCodes
	 * @return List of formatted Carousel data summary lines per StockCount Worksheet having items to be counted.
	 */
	public List<String> getCountWorksheetSummaryForCarousel(String warehouseCd, List<String> zoneCodes);

	/**
	 * Used to post Pick List Line Ids to the carousel log that have just been sent to the carousel file,
	 * to prevent duplicates on subsequent carousel files.
	 *
	 * @param pickLineId
	 * @return int result of update query
	 */
	public int postPickLineToCarouselLog(final Long pickLineId);

	/**
	 *  Used to post Check-in Detail Ids to the carousel log that have just been sent to the carousel file,
	 *  to prevent duplicates on subsequent carousel files.
	 *
	 * @param checkinLineId
	 * @return int result of update query
	 */
	public int postCheckinLineToCarouselLog(final Long checkinLineId);

	/**
	 *  Used to post Check-in Detail Ids to the carousel log that have just been sent to the carousel file,
	 *  to prevent duplicates on subsequent carousel files.
	 *
	 * @param countLineId
	 * @return int result of update query
	 */
	public int postCountLineToCarouselLog(final Long countLineId);
}
