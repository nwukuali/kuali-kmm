/**
 *
 */
package org.kuali.ext.mm.sys.batch.dataaccess.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * @author schneppd
 *
 */
public class CarouselDataBuilderDaoJdbc extends PlatformAwareDaoBaseJdbc implements CarouselDataBuilderDao {

	private static final String LINE_DATA = "LineData";
	private static final String LINE_ID = "LineId";
	private static final String CAROUSEL_PICK_TYPE_CODE = "100";
	private static final String CAROUSEL_CHECKIN_TYPE_CODE = "103";
	private static final String CAROUSEL_STOCK_COUNT_TYPE_CODE = "105";


	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#getPickLinesForCarousel(java.lang.String, java.util.List)
	 */
	public Map<Long, String> getPickLinesForCarousel(String warehouseCd, List<String> zoneCodes) {
		final Map<Long, String> lineData = new TreeMap<Long, String>();
		String zoneCodesString = "";
		Iterator<String> zoneIt = zoneCodes.iterator();
		while(zoneIt.hasNext()) {
			zoneCodesString += "'" + zoneIt.next() + "'";
			if(zoneIt.hasNext())
				zoneCodesString += ", ";
		}
		getJdbcTemplate()
        .query("select '" + CAROUSEL_PICK_TYPE_CODE + "' ||"
                + " rpad(' ',64) ||"
                + " rpad(t.order_id, 64) ||"
                + " lpad(rownum, 6, '0') ||"
                + " '01' ||"
                + " substr(e.zone_cd,2,1) ||"
                + " '0' ||"
                + " substr(d.bin_nbr,1,2) ||"
                + " d.shelf_id ||"
                + " d.shelf_id_nbr ||"
                + " lpad(a.stock_qty, 6) ||"
                + " rpad(f.stock_distributor_nbr,64) ||"
                + " rpad(f.stock_desc, 128) ||"
                + " rpad(' ',64) ||"
                + " rpad(f.sales_unit_of_issue_cd,64)        as " + LINE_DATA
                + " , a.pick_list_line_id as " + LINE_ID
                + " from MM_PICK_LIST_LINE_T a"
                + " left outer join MM_CAROUSEL_LOG_T l"
                + " on a.pick_list_line_id = l.carousel_line_id and l.carousel_line_type_cd='" + CAROUSEL_PICK_TYPE_CODE + "'"
                + " join MM_PICK_TICKET_T b"
                + " on b.pick_ticket_nbr = a.pick_ticket_nbr"
                + "  and b.pick_status_cd = 'PRTD'"
                + " join MM_STOCK_BALANCE_T c"
                + " on c.stock_id = a.stock_id"
                + " and c.bin_id = a.bin_id"
                + " join MM_BIN_T d"
                + " on d.bin_id = c.bin_id"
                + " join MM_ZONE_T e"
                + " on e.zone_id = d.zone_id"
                + " and e.warehouse_cd = '" + warehouseCd + "'"
                + " and e.zone_cd in (" + zoneCodesString + ")"
                + " join MM_STOCK_T f"
                + " on f.stock_id = a.stock_id"
                + " join MM_ORDER_DETAIL_T o"
                + " on o.order_detail_id = a.order_detail_id"
                + " join MM_ORDER_DOC_T t"
                + " on t.fdoc_nbr = o.order_doc_nbr"
                + " where l.carousel_line_id is null",
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        while (rs != null && rs.next()) {
                        	lineData.put(rs.getLong(LINE_ID), rs.getString(LINE_DATA));
                        }
                        return lineData;
                    }
                });

		return lineData;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#getPickLinesOrderSummaryForCarousel(java.lang.String, java.util.List)
	 */
	public List<String> getPickLinesOrderSummaryForCarousel(String warehouseCd, List<String> zoneCodes) {
		final List<String> lineData = new ArrayList<String>();
		String zoneCodesString = "";
		Iterator<String> zoneIt = zoneCodes.iterator();
		while(zoneIt.hasNext()) {
			zoneCodesString += "'" + zoneIt.next() + "'";
			if(zoneIt.hasNext())
				zoneCodesString += ", ";
		}
		getJdbcTemplate()
        .query("select distinct '101' ||"
        		+ " rpad(' ',64) ||"
        		+ " rpad(t.order_id, 64) ||"
                + " '0100'        as " + LINE_DATA
                + " from MM_PICK_LIST_LINE_T a"
                + " left outer join MM_CAROUSEL_LOG_T l"
                + " on a.pick_list_line_id = l.carousel_line_id and l.carousel_line_type_cd='" + CAROUSEL_PICK_TYPE_CODE + "'"
                + " join MM_PICK_TICKET_T b"
                + " on b.pick_ticket_nbr = a.pick_ticket_nbr"
                + "  and b.pick_status_cd = 'PRTD'"
                + " join MM_STOCK_BALANCE_T c"
                + " on c.stock_id = a.stock_id"
                + " and c.bin_id = a.bin_id"
                + " join MM_BIN_T d"
                + " on d.bin_id = c.bin_id"
                + " join MM_ZONE_T e"
                + " on e.zone_id = d.zone_id"
                + " and e.warehouse_cd = '" + warehouseCd + "'"
                + " and e.zone_cd in (" + zoneCodesString + ")"
                + " join MM_STOCK_T f"
                + " on f.stock_id = a.stock_id"
                + " join MM_ORDER_DETAIL_T o"
                + " on o.order_detail_id = a.order_detail_id"
                + " join MM_ORDER_DOC_T t"
                + " on t.fdoc_nbr = o.order_doc_nbr"
                + " where l.carousel_line_id is null",
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        while (rs != null && rs.next()) {
                        	lineData.add(rs.getString(LINE_DATA));
                        }
                        return lineData;
                    }
                });

		return lineData;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#getCheckinLinesForCarousel(java.lang.String, java.util.List)
	 */
	public Map<Long, String> getCheckinLinesForCarousel(String warehouseCd, List<String> zoneCodes) {
		final Map<Long, String> lineData = new TreeMap<Long, String>();
		String zoneCodesString = "";
		Iterator<String> zoneIt = zoneCodes.iterator();
		while(zoneIt.hasNext()) {
			zoneCodesString += "'" + zoneIt.next() + "'";
			if(zoneIt.hasNext())
				zoneCodesString += ", ";
		}
		getJdbcTemplate()
        .query("select '" + CAROUSEL_CHECKIN_TYPE_CODE + "' ||"
        + " rpad(' ',64) ||"
        + " rpad(t.order_id, 64) ||"
        + " lpad(rownum, 6, '0') ||"
        + " '01' ||"
        + " substr(e.zone_cd,2,1) ||"
        + " '0' ||"
        + " substr(d.bin_nbr,1,2) ||"
        + " d.shelf_id ||"
        + " d.shelf_id_nbr ||"
        + " lpad(b.accepted_item_qty, 6) ||"
        + " rpad(f.stock_distributor_nbr,64) ||"
        + " rpad(f.stock_desc, 128) ||"
        + " rpad(' ',64) ||"
        + " rpad(f.sales_unit_of_issue_cd,64) ||"
        + " rpad(t.order_id, 64) as " + LINE_DATA
        + " , b.checkin_detail_id as " + LINE_ID
        + " from MM_CHECKIN_DETAIL_T b"
        + " left outer join MM_CAROUSEL_LOG_T l"
        + " on b.checkin_detail_id = l.carousel_line_id and l.carousel_line_type_cd='" + CAROUSEL_CHECKIN_TYPE_CODE + "'"
        + " join MM_ORDER_DETAIL_T a"
        + " on b.order_detail_id = a.order_detail_id"
        + " join MM_STOCK_BALANCE_T c"
        + " on c.stock_id = b.stock_id"
        + " and c.bin_id = b.bin_id"
        + " join MM_BIN_T d"
        + " on d.bin_id = c.bin_id"
        + " join MM_ZONE_T e"
        + " on e.zone_id = d.zone_id"
        + " and e.warehouse_cd = '" + warehouseCd + "'"
        + " and e.zone_cd in (" + zoneCodesString + ")"
        + " join MM_STOCK_T f"
        + " on f.stock_id = b.stock_id"
        + " join MM_ORDER_DOC_T t"
        + " on t.fdoc_nbr = a.order_doc_nbr"
        + " where a.ORDER_DETAIL_STATUS_CD in ('R', 'S')"
        + " and l.carousel_line_id is null",
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        while (rs != null && rs.next()) {
                        	lineData.put(rs.getLong(LINE_ID), rs.getString(LINE_DATA));
                        }
                        return lineData;
                    }
                });

		return lineData;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#getCheckinOrderSummaryForCarousel(java.lang.String, java.util.List)
	 */
	public List<String> getCheckinOrderSummaryForCarousel(String warehouseCd, List<String> zoneCodes) {
		final List<String> lineData = new ArrayList<String>();
		String zoneCodesString = "";
		Iterator<String> zoneIt = zoneCodes.iterator();
		while(zoneIt.hasNext()) {
			zoneCodesString += "'" + zoneIt.next() + "'";
			if(zoneIt.hasNext())
				zoneCodesString += ", ";
		}
		getJdbcTemplate()
        .query("select distinct '104' ||"
        		+ " rpad(' ',64) ||"
        		+ " rpad(t.order_id, 64) ||"
                + " '0400'        as " + LINE_DATA
                + " from MM_CHECKIN_DETAIL_T b"
                + " left outer join MM_CAROUSEL_LOG_T l"
                + " on b.checkin_detail_id = l.carousel_line_id and l.carousel_line_type_cd='" + CAROUSEL_CHECKIN_TYPE_CODE + "'"
                + " join MM_ORDER_DETAIL_T a"
                + " on b.order_detail_id = a.order_detail_id"
                + " join MM_STOCK_BALANCE_T c"
                + " on c.stock_id = b.stock_id"
                + " and c.bin_id = b.bin_id"
                + " join MM_BIN_T d"
                + " on d.bin_id = c.bin_id"
                + " join MM_ZONE_T e"
                + " on e.zone_id = d.zone_id"
                + " and e.warehouse_cd = '" + warehouseCd + "'"
                + " and e.zone_cd in (" + zoneCodesString + ")"
                + " join MM_STOCK_T f"
                + " on f.stock_id = b.stock_id"
                + " join MM_ORDER_DOC_T t"
                + " on t.fdoc_nbr = a.order_doc_nbr"
                + " where a.ORDER_DETAIL_STATUS_CD in ('R', 'S')"
                + " and l.carousel_line_id is null",
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        while (rs != null && rs.next()) {
                        	lineData.add(rs.getString(LINE_DATA));
                        }
                        return lineData;
                    }
                });

		return lineData;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#getStockCountLinesForCarousel(java.lang.String, java.util.List)
	 */
	public Map<Long, String> getStockCountLinesForCarousel(String warehouseCd, List<String> zoneCodes) {
		final Map<Long, String> lineData = new TreeMap<Long, String>();
		String zoneCodesString = "";
		Iterator<String> zoneIt = zoneCodes.iterator();
		while(zoneIt.hasNext()) {
			zoneCodesString += "'" + zoneIt.next() + "'";
			if(zoneIt.hasNext())
				zoneCodesString += ", ";
		}
		getJdbcTemplate()
        .query("select '" + CAROUSEL_STOCK_COUNT_TYPE_CODE + "' ||"
        		+ " rpad(' ',64) ||"
        		+ " rpad(w.fdoc_nbr, 64) ||"
        		+ " lpad(rownum, 6, '0') ||"
        		+ " '01' ||"
        		+ " substr(e.zone_cd,2,1) ||"
        		+ " '0' ||"
        		+ " substr(d.bin_nbr,1,2) ||"
        		+ " d.shelf_id ||"
        		+ " d.shelf_id_nbr ||"
        		+ " lpad(a.before_item_qty, 6) ||"
                + " rpad(f.stock_distributor_nbr, 64) ||"
                + " rpad(f.stock_desc,128) ||"
                + " rpad(' ', 64) ||"
                + " rpad(a.before_item_unit_of_issue_cd, 64)"
                + " as " + LINE_DATA
                + " , a.stock_count_id as " + LINE_ID
                + " from MM_STOCK_COUNT_T a"
                + " left outer join MM_CAROUSEL_LOG_T l"
                + " on a.stock_count_id = l.carousel_line_id and l.carousel_line_type_cd='" + CAROUSEL_STOCK_COUNT_TYPE_CODE + "'"
                + " join MM_WORKSHEET_COUNT_DOC_T w"
                + " on a.worksheet_count_doc_nbr = w.fdoc_nbr"
                + " join MM_STOCK_BALANCE_T c"
                + " on c.stock_id = a.stock_id"
                + " and c.bin_id = a.bin_id"
                + " join MM_BIN_T d"
                + " on d.bin_id = c.bin_id"
                + " join MM_ZONE_T e"
                + " on e.zone_id = d.zone_id"
                + " and e.warehouse_cd = '" + warehouseCd + "'"
                + " and e.zone_cd in (" + zoneCodesString + ")"
                + " join MM_STOCK_T f"
                + " on f.stock_id = a.stock_id"
                + " where w.worksheet_status_cd = 'PRTD'"
                + " and l.carousel_line_id is null",
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        while (rs != null && rs.next()) {
                        	lineData.put(rs.getLong(LINE_ID), rs.getString(LINE_DATA));
                        }
                        return lineData;
                    }
                });

		return lineData;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#getCountWorksheetSummaryForCarousel(java.lang.String, java.util.List)
	 */
	public List<String> getCountWorksheetSummaryForCarousel(String warehouseCd, List<String> zoneCodes) {
		final List<String> lineData = new ArrayList<String>();
		String zoneCodesString = "";
		Iterator<String> zoneIt = zoneCodes.iterator();
		while(zoneIt.hasNext()) {
			zoneCodesString += "'" + zoneIt.next() + "'";
			if(zoneIt.hasNext())
				zoneCodesString += ", ";
		}
		getJdbcTemplate()
        .query("select distinct '106' ||"
        		+ " rpad(' ',64) ||"
        		+ " rpad(w.fdoc_nbr, 64) ||"
                + " '0500'        as " + LINE_DATA
                + " from MM_STOCK_COUNT_T a"
                + " left outer join MM_CAROUSEL_LOG_T l"
                + " on a.stock_count_id = l.carousel_line_id and l.carousel_line_type_cd='" + CAROUSEL_STOCK_COUNT_TYPE_CODE + "'"
                + " join MM_WORKSHEET_COUNT_DOC_T w"
                + " on a.worksheet_count_doc_nbr = w.fdoc_nbr"
                + " join MM_STOCK_BALANCE_T c"
                + " on c.stock_id = a.stock_id"
                + " and c.bin_id = a.bin_id"
                + " join MM_BIN_T d"
                + " on d.bin_id = c.bin_id"
                + " join MM_ZONE_T e"
                + " on e.zone_id = d.zone_id"
                + " and e.warehouse_cd = '" + warehouseCd + "'"
                + " and e.zone_cd in (" + zoneCodesString + ")"
                + " join MM_STOCK_T f"
                + " on f.stock_id = a.stock_id"
                + " where w.worksheet_status_cd = 'PRTD'"
                + " and l.carousel_line_id is null",
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        while (rs != null && rs.next()) {
                        	lineData.add(rs.getString(LINE_DATA));
                        }
                        return lineData;
                    }
                });

		return lineData;
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#postCheckinLineToCarouselLog(java.lang.Long)
	 */
	public int postCheckinLineToCarouselLog(final Long checkinLineId) {
		return getJdbcTemplate()
        .update(
                "INSERT INTO MM_CAROUSEL_LOG_T (CAROUSEL_LINE_ID, CAROUSEL_LINE_TYPE_CD, LAST_UPDATE_DT)"
                        + "VALUES(?, ?, ?)",
                new PreparedStatementSetter() {
                    /**
                     * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                     */
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, checkinLineId);
                        ps.setString(2, CAROUSEL_CHECKIN_TYPE_CODE);
                        ps.setTimestamp(3, (new Timestamp(new java.util.Date().getTime())));
                    }
                });
	}


	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#postCountLineToCarouselLog(java.lang.String)
	 */
	public int postCountLineToCarouselLog(final Long countLineId) {
		return getJdbcTemplate()
        .update(
                "INSERT INTO MM_CAROUSEL_LOG_T (CAROUSEL_LINE_ID, CAROUSEL_LINE_TYPE_CD, LAST_UPDATE_DT)"
                        + "VALUES(?, ?, ?)",
                new PreparedStatementSetter() {
                    /**
                     * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                     */
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, countLineId);
                        ps.setString(2, CAROUSEL_STOCK_COUNT_TYPE_CODE);
                        ps.setTimestamp(3, (new Timestamp(new java.util.Date().getTime())));
                    }
                });
	}

	/**
	 * @see org.kuali.ext.mm.sys.batch.dataaccess.CarouselDataBuilderDao#postPickLineToCarouselLog(java.lang.String)
	 */
	public int postPickLineToCarouselLog(final Long pickLineId) {
		 return getJdbcTemplate()
         .update(
                 "INSERT INTO MM_CAROUSEL_LOG_T (CAROUSEL_LINE_ID, CAROUSEL_LINE_TYPE_CD, LAST_UPDATE_DT)"
                         + "VALUES(?, ?, ?)",
                 new PreparedStatementSetter() {
                     /**
                      * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                      */
                     public void setValues(PreparedStatement ps) throws SQLException {
                         ps.setLong(1, pickLineId);
                         ps.setString(2, CAROUSEL_PICK_TYPE_CODE);
                         ps.setTimestamp(3, (new Timestamp(new java.util.Date().getTime())));
                     }
                 });
	}
}
