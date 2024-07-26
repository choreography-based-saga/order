package com.saga.order.infra.model;

import com.saga.order.infra.model.enums.Currency;
import com.saga.order.infra.model.enums.OrderStatus;
import com.saga.order.infra.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @OneToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    MerchantEntity merchant;
    UUID customerId;
    String orderId;
    @OneToMany(mappedBy = "order")
    Set<SuborderEntity> suborders;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;
    BigDecimal grandTotal;
    @Enumerated(EnumType.STRING)
    Currency currency;
    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime modifiedAt;
    LocalDateTime confirmedAt;
    LocalDateTime packedAt;
    LocalDateTime cancellationDate;


}
