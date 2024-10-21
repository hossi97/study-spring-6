package com.hossi.spring6.order;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;

  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order createOrder(String no, BigDecimal totalPrice) {
    Order order = new Order(no, totalPrice);
    this.orderRepository.save(order);
    return order;
  }

  public List<Order> createOrders(List<OrderRequest> requests) {
      return requests.stream().map(req -> {
        Order order = new Order(req.no(), req.totalPrice());
        this.orderRepository.save(order);
        return order;
      }).toList();
  }
}
