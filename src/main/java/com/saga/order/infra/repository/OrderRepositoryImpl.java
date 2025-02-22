package com.saga.order.infra.repository;

import com.saga.order.domain.model.Order;
import com.saga.order.domain.model.Product;
import com.saga.order.domain.model.Suborder;
import com.saga.order.domain.model.enums.OrderDomainStatus;
import com.saga.order.domain.out.OrderRepositoryApi;
import com.saga.order.infra.mapper.OrderEntityMapper;
import com.saga.order.infra.mapper.ProductEntityMapper;
import com.saga.order.infra.model.SuborderEntity;
import com.saga.order.infra.model.SuborderItemEntity;
import com.saga.order.infra.repository.jpa.OrderEntityRepository;
import com.saga.order.infra.repository.jpa.SuborderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryApi {
    private final OrderEntityRepository orderEntityRepository;
    private final SuborderEntityRepository suborderEntityRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public void updateStatus(Integer orderId, OrderDomainStatus orderStatus) {
        orderEntityRepository.updateOrderStatus(orderId, orderEntityMapper.toOrderStatusEntity(orderStatus));
    }

    @Override
    public Optional<Product> findByOrderIdAndItemId(String orderId, Integer itemId) {
        return orderEntityRepository.findByOrderIdAndSuborderItemId(
                        orderId,
                        itemId)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Order upsertOrder(Order order) {
        return orderEntityMapper.toOrderDomain(orderEntityRepository.save(orderEntityMapper.toEntity(order)));
    }

    @Override
    public Set<Suborder> createSuborders(Set<Suborder> suborders) {
        Set<SuborderEntity> suborderEntities = new HashSet<>();
        for (Suborder suborder : suborders) {
            Set<SuborderItemEntity> itemEntities = orderEntityMapper.toSuborderItemEntities(suborder.items());
            SuborderEntity suborderEntity = orderEntityMapper.mapWithSuborderItems(suborder, itemEntities);
            suborderEntities.add(suborderEntity);
        }
        suborderEntities = new HashSet<>(suborderEntityRepository.saveAll(suborderEntities));
        return orderEntityMapper.toSubordersDomain(suborderEntities);
    }

    @Override
    public Optional<Order> findByOrderId(String orderId) {
        return orderEntityRepository.findByOrderId(orderId).map(orderEntityMapper::toOrderDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderEntityRepository.findAll().stream().map(orderEntityMapper::toOrderDomain).toList();
    }
}
