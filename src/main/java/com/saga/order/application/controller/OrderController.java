package com.saga.order.application.controller;

import com.saga.order.application.api.request.CreateOrderRequest;
import com.saga.order.application.api.request.ServiceableItemRequest;
import com.saga.order.application.api.response.ServiceScheduledResponse;
import com.saga.order.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/service")
    public ResponseEntity<ServiceScheduledResponse> serviceItem(@RequestBody ServiceableItemRequest request){
        ServiceScheduledResponse response = orderService.scheduleItemServicing(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request) {
        boolean successful = orderService.createOrder(request);
        if (!successful) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
