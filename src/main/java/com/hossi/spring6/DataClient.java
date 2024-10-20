package com.hossi.spring6;

import com.hossi.spring6.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataClient {

  public static void main(String[] args) {
    BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
    EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory.class);
    try (
      EntityManager em = emf.createEntityManager();
    ) {
      em.getTransaction().begin();
      Order order = new Order("100", BigDecimal.TEN);
      System.out.println(order);
      em.persist(order);
      em.getTransaction().commit();
    }
  }

}
