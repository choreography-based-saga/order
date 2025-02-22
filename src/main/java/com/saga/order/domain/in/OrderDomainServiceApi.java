package com.saga.order.domain.in;


import com.saga.order.domain.model.CreateOrder;
import com.saga.order.domain.model.Payment;
import com.saga.order.domain.model.enums.OrderDomainStatus;

import java.util.List;

public interface OrderDomainServiceApi {

    void updateOrderStatus(Integer orderId, OrderDomainStatus orderStatus);

    boolean itemServicing(String orderId, Integer itemId);

    boolean createOrder(CreateOrder order);

    void processPayment(Payment payment);

    List<String> getOrderIds();
}
