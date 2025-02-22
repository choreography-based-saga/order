package com.saga.order.domain.out;

import com.saga.order.domain.model.Order;
import com.saga.order.domain.model.Product;
import com.saga.order.domain.model.Suborder;
import com.saga.order.domain.model.enums.OrderDomainStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepositoryApi {

    void updateStatus(Integer orderId, OrderDomainStatus orderStatus);

    Optional<Product> findByOrderIdAndItemId(String orderId, Integer itemId);

    Order upsertOrder(Order order);

    Set<Suborder> createSuborders(Set<Suborder> suborders);

    Optional<Order> findByOrderId(String orderId);

    List<Order> findAll();
}
