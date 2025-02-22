package com.saga.order.application.service;

import com.saga.order.application.api.request.CreateOrderRequest;
import com.saga.order.application.api.request.ServiceableItemRequest;
import com.saga.order.application.api.response.ServiceScheduledResponse;
import com.saga.order.application.mapper.OrderResponseMapper;
import com.saga.order.application.api.event.OrderStatusChange;
import com.saga.order.domain.in.OrderDomainServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDomainServiceApi orderDomainServiceApi;
    private final OrderResponseMapper orderResponseMapper;

    public void updateStatus(OrderStatusChange statusChange) {
        orderDomainServiceApi.updateOrderStatus(statusChange.id(), orderResponseMapper.toDomain(statusChange.status()));
    }

    public ServiceScheduledResponse scheduleItemServicing(ServiceableItemRequest request){
        if (!orderDomainServiceApi.itemServicing(request.orderId(), request.itemId())) {
            return new ServiceScheduledResponse("Item isn't serviceable", false);
        }
        return new ServiceScheduledResponse("Item servicing scheduled", true);
    }

    @Transactional
    public boolean createOrder(CreateOrderRequest request) {
        return orderDomainServiceApi.createOrder(orderResponseMapper.toDomain(request));
    }

    public List<String> getOrders() {
        return orderDomainServiceApi.getOrderIds();
    }

}
