package com.hossi.spring6.data;

import com.hossi.spring6.order.Order;
import com.hossi.spring6.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JpaOrderRepository implements OrderRepository {
  @PersistenceContext
  private EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

}
