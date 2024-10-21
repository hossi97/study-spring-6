package com.hossi.spring6.data;

import com.hossi.spring6.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

public class OrderRepository {
  @PersistenceContext
  private EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

}
