package br.com.saga_choreography.order.core.controller;

import br.com.saga_choreography.order.core.document.Order;
import br.com.saga_choreography.order.core.dto.OrderRequest;
import br.com.saga_choreography.order.core.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request){
        return service.createOrder(request);
    }
}
