package org.kuali.ext.mm.document.dataaccess.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.businessobject.OrderDetailPickStatusDTO;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.document.dataaccess.OrderStatusDao;
import org.kuali.rice.kns.dao.jdbc.PlatformAwareDaoBaseJdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;


/**
 * @author harsha07
 */
public class OrderStatusDaoJdbc extends PlatformAwareDaoBaseJdbc implements OrderStatusDao {

    /**
     * @see org.kuali.ext.mm.document.dao.OrderStatusDao#getOrderDetailsPickStatus(java.lang.String)
     */
    public List<OrderDetailPickStatusDTO> getOrderDetailsPickStatus(final String pickTicketNumber) {
        final ArrayList<OrderDetailPickStatusDTO> list = new ArrayList<OrderDetailPickStatusDTO>();
        getJdbcTemplate()
                .query(
                        "select dtl.order_doc_nbr, dtl.order_detail_id, sum(dtl.order_item_qty) ordr, sum(line.pick_stock_qty) picked "
                                + "from mm_pick_list_line_t line, mm_order_detail_t dtl where line.order_detail_id = dtl.order_detail_id "
                                + "and line.pick_status_cd in ('"
                                + MMConstants.PickStatusCode.PICK_STATUS_PCKD
                                + "', '"
                                + MMConstants.PickStatusCode.PICK_STATUS_PBCK
                                + "') and line.order_detail_id in (select order_detail_id from mm_pick_list_line_t x where x.pick_ticket_nbr = ?) "
                                + "group by dtl.order_detail_id, dtl.order_doc_nbr",
                        new PreparedStatementSetter() {
                            /**
                             * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
                             */
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, pickTicketNumber);

                            }
                        }, new ResultSetExtractor() {
                            /**
                             * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
                             */
                            public Object extractData(ResultSet rs) throws SQLException,
                                    DataAccessException {
                                while (rs != null && rs.next()) {
                                    OrderDetailPickStatusDTO pickStatusDTO = new OrderDetailPickStatusDTO();
                                    pickStatusDTO.setOrderDocumentNbr(rs.getString(1));
                                    pickStatusDTO.setOrderDetailId(rs.getInt(2));
                                    pickStatusDTO.setOrderItemQty(rs.getInt(3));
                                    pickStatusDTO.setPickedQty(rs.getInt(4));
                                    list.add(pickStatusDTO);
                                }
                                return list;
                            }
                        });
        return list;
    }

    /**
     * @see org.kuali.ext.mm.document.dao.OrderStatusDao#updateOrderDetailsComplete(java.util.List)
     */
    public int updateOrderDetailsComplete(List<Integer> orderDetailIds) {
        if (orderDetailIds == null || orderDetailIds.isEmpty()) {
            return 0;
        }
        String ids = "";
        int size = orderDetailIds.size();
        for (int i = 0; i < size; i++) {
            ids = ids + orderDetailIds.get(i);
            if (i < size - 1) {
                ids = ids + ",";
            }
        }
        return getJdbcTemplate()
                .update(
                        "update mm_order_detail_t set order_detail_status_cd='"
                                + MMConstants.OrderStatus.ORDER_LINE_COMPLETE
                                + "', last_update_dt = current_date where order_detail_id in ("
                                + ids + ")");
    }

    public int updateOrderDetailsReceiveComplete(List<Integer> orderDetailIds) {
        if (orderDetailIds == null || orderDetailIds.isEmpty()) {
            return 0;
        }
        String ids = "";
        int size = orderDetailIds.size();
        for (int i = 0; i < size; i++) {
            ids = ids + orderDetailIds.get(i);
            if (i < size - 1) {
                ids = ids + ",";
            }
        }
        return getJdbcTemplate()
                .update(
                        "update mm_order_detail_t set order_detail_status_cd='"
                                + MMConstants.OrderStatus.ORDER_LINE_RECEIVED_COMPLETE
                                + "', last_update_dt = current_date where order_detail_id in ("
                                + ids + ")");
    }

    /**
     * @see org.kuali.ext.mm.document.dao.OrderStatusDao#isOrderComplete(java.lang.String)
     */
    public boolean isOrderComplete(String orderDocNumber) {
        return getJdbcTemplate().queryForInt(
                "select count(1) from mm_order_detail_t dtl where dtl.order_doc_nbr = '"
                        + orderDocNumber + "' and dtl.order_detail_status_cd <> '"
                        + MMConstants.OrderStatus.ORDER_LINE_COMPLETE + "'") == 0;
    }

    public boolean isOrderReceiveComplete(String orderDocNumber) {
        return getJdbcTemplate().queryForInt(
                "select count(1) from mm_order_detail_t dtl where dtl.order_doc_nbr = '"
                        + orderDocNumber + "' and dtl.order_detail_status_cd <> '"
                        + MMConstants.OrderStatus.ORDER_LINE_RECEIVED_COMPLETE
                        + "' and dtl.order_detail_status_cd <> '"
                        + MMConstants.OrderStatus.ORDER_LINE_CLOSED + "'") == 0;
    }

    /**
     * @see org.kuali.ext.mm.document.dao.OrderStatusDao#updateOrderComplete(java.lang.String)
     */
    public int updateOrderComplete(String orderDocNumber) {
        return getJdbcTemplate().update(
                "update mm_order_doc_t set order_doc_status_cd = '"
                        + MMConstants.OrderStatus.ORDER_LINE_COMPLETE
                        + "', last_update_dt = current_date where fdoc_nbr='" + orderDocNumber
                        + "'");
    }

    public int updateOrderReceived(String orderDocNumber) {
        return getJdbcTemplate().update(
                "update mm_order_doc_t set order_doc_status_cd = '"
                        + MMConstants.OrderStatus.ORDER_LINE_RECEIVED_COMPLETE
                        + "', last_update_dt = current_date where fdoc_nbr='" + orderDocNumber
                        + "'");
    }

    public int updateOrderStatus(String orderDocNumber, String orderStatusCd) {
        return getJdbcTemplate().update(
                "update mm_order_doc_t set order_doc_status_cd = '" + orderStatusCd
                        + "', last_update_dt = current_date where fdoc_nbr='" + orderDocNumber
                        + "'");
    }
}
