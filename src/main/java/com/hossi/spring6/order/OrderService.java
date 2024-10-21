package com.hossi.spring6.order;

import com.hossi.spring6.data.JpaOrderRepository;
import java.math.BigDecimal;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {

  private final JpaOrderRepository orderRepository;
  private final JpaTransactionManager transactionManager;

  public OrderService(JpaOrderRepository orderRepository, JpaTransactionManager transactionManager) {
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
}
