package com.hossi.spring6.order;

import com.hossi.spring6.data.OrderRepository;
import java.math.BigDecimal;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final JpaTransactionManager transactionManager;

  public OrderService(OrderRepository orderRepository, JpaTransactionManager transactionManager) {
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
