package br.com.saga_choreography.order.core.service;

import br.com.saga_choreography.order.core.document.Event;
import br.com.saga_choreography.order.core.document.Order;
import br.com.saga_choreography.order.core.dto.OrderRequest;
import br.com.saga_choreography.order.core.producer.SagaProducer;
import br.com.saga_choreography.order.core.repository.OrderRepository;
import br.com.saga_choreography.order.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private static final String TRANSACTION_ID_PATTERN = "%s_%s";

    private final EventService eventService;
    private final SagaProducer producer;
    private final JsonUtil jsonUtil;
    private final OrderRepository repository;

    public Order createOrder(OrderRequest request) {
        var order = Order.builder()
                .products(request.getProducts())
                .createdAt(LocalDateTime.now())
                .transactionId(
                        String.format(TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID())
                )
                .build();

        repository.save(order);
        producer.sendEvent(jsonUtil.toJson(eventService.createEvent(order)));

        return order;
    }
}
