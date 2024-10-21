package com.hossi.spring6.order;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.transaction.support.TransactionTemplate;

public interface OrderService {
  Order createOrder(String no, BigDecimal totalPrice);
  List<Order> createOrders(List<OrderRequest> requests);
}
