package com.saga.order.infra.model;

import com.saga.order.infra.model.enums.Currency;
import com.saga.order.infra.model.enums.OrderStatus;
import com.saga.order.infra.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    UUID customerId;
    String orderId;
    @OneToMany(mappedBy = "order")
    Set<SuborderEntity> suborders;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;
    BigDecimal grandTotal;
    @Enumerated(EnumType.STRING)
    Currency currency;
    @LastModifiedDate
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;
    LocalDateTime confirmedAt;
    LocalDateTime packedAt;

}
