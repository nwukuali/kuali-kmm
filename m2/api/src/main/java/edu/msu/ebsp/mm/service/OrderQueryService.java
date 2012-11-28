package edu.msu.ebsp.mm.service;

import edu.msu.ebsp.mm.service.dto.OrderInfoDto;

public interface OrderQueryService {
    public OrderInfoDto findOrderInfoById(Long orderId);
}
