package com.hossi.spring6;

import com.hossi.spring6.data.OrderRepository;
import com.hossi.spring6.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataClient {

  public static void main(String[] args) {
    BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
    OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
    Order order = new Order("100", BigDecimal.TEN);
    orderRepository.save(order);
    System.out.println(order);
  }

}
