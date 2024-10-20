package com.hossi.spring6.data;

import com.hossi.spring6.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

public class OrderRepository {
  private final EntityManagerFactory emf;

  public OrderRepository(EntityManagerFactory emf) {
    this.emf = emf;
  }

  public void save(Order order) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    try (em) {
      em.persist(order);
      transaction.commit();
    } catch (DataAccessException e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

}
