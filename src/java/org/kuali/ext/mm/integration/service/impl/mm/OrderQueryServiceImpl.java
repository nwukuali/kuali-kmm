/**
 *
 */
package org.kuali.ext.mm.integration.service.impl.mm;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.Address;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.businessobject.Profile;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.ObjectUtils;

import edu.msu.ebsp.mm.service.OrderQueryService;
import edu.msu.ebsp.mm.service.dto.OrderInfoDto;

/**
 * @author harsha07
 */
public class OrderQueryServiceImpl implements OrderQueryService {

    /**
     * @see edu.msu.ebsp.mm.service.OrderQueryService#findOrderInfoById(java.lang.Long)
     */
    public OrderInfoDto findOrderInfoById(Long orderId) {
        if (orderId != null) {
            HashMap<String, Long> fieldValues = new HashMap<String, Long>();
            fieldValues.put("orderId", orderId);
            Collection<?> matching = SpringContext.getBean(BusinessObjectService.class)
                    .findMatching(OrderDocument.class, fieldValues);
            OrderDocument orderDocument = null;
            if (matching != null
                    && (orderDocument = (OrderDocument) matching.iterator().next()) != null) {
                OrderInfoDto orderInfoDto = new OrderInfoDto();
                Address shippingAddress = orderDocument.getShippingAddress();
                Profile customerProfile = orderDocument.getCustomerProfile();
                orderInfoDto.setCustomerProfileId(orderDocument.getCustomerProfileId());
                orderInfoDto.setDeliveryBuildingCd(orderDocument.getDeliveryBuildingCd());
                orderInfoDto.setDeliveryDepartmentNm(orderDocument.getDeliveryDepartmentNm());
                orderInfoDto.setOrderId(orderDocument.getOrderId());
                if (ObjectUtils.isNotNull(customerProfile)) {
                    orderInfoDto.setOrgCode(customerProfile.getOrganizationCode());
                    orderInfoDto.setProfileEmail(customerProfile.getProfileEmail());
                    orderInfoDto.setProfilePhoneNumber(customerProfile.getProfilePhoneNumber());
                    Person person = SpringContext.getBean(PersonService.class)
                            .getPersonByPrincipalName(customerProfile.getPrincipalName());
                    if (person != null) {
                        orderInfoDto.setFirstName(person.getFirstName());
                        orderInfoDto.setLastName(person.getLastName());
                        orderInfoDto.setMiddleName(person.getMiddleName());
                    }
                }
                orderInfoDto.setShippingAddressId(orderDocument.getShippingAddressId());
                if (ObjectUtils.isNotNull(shippingAddress)) {
                    orderInfoDto.setDeliveryAddressName(shippingAddress.getAddressName());
                    orderInfoDto.setDeliveryAddressLine1(shippingAddress.getAddressLine1());
                    orderInfoDto.setDeliveryAddressLine2(shippingAddress.getAddressLine2());
                }
                orderInfoDto.setVendorNm(orderDocument.getVendorNm());
                // find the org reference id from the first account line
                List<OrderDetail> orderDetails = orderDocument.getOrderDetails();
                if (ObjectUtils.isNotNull(orderDetails) && !orderDetails.isEmpty()) {
                    OrderDetail firstDetail = orderDetails.get(0);
                    if (ObjectUtils.isNotNull(firstDetail)) {
                        List<Accounts> accounts = firstDetail.getAccounts();
                        if (ObjectUtils.isNotNull(accounts) && !accounts.isEmpty()) {
                            orderInfoDto.setOrgReferenceId(accounts.get(0)
                                    .getDepartmentReferenceText());
                        }
                    }
                }
                return orderInfoDto;
            }
        }
        return null;
    }
}
