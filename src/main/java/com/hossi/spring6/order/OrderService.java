package com.hossi.spring6.order;

import com.hossi.spring6.data.JpaOrderRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final PlatformTransactionManager transactionManager;

  public OrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
    this.orderRepository = orderRepository;
    this.transactionManager = transactionManager;
  }

  public Order createOrder(String no, BigDecimal totalPrice) {
    Order order = new Order(no, totalPrice);

    return new TransactionTemplate(transactionManager).execute(status -> {
      this.orderRepository.save(order);
      return order;
    });
  }

  public List<Order> createOrders(List<OrderRequest> requests) {
    return new TransactionTemplate(transactionManager).execute(status ->
      requests.stream().map(req -> {
        Order order = new Order(req.no(), req.totalPrice());
        this.orderRepository.save(order);
        return order;
      }).toList()
    );
  }
}
