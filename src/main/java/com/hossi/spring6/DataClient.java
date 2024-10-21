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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class DataClient {

  public static void main(String[] args) {
    BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
    OrderRepository orderRepository = beanFactory.getBean(OrderRepository.class);
    JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

    try {
      new TransactionTemplate(transactionManager).execute(status -> {
        Order order1 = new Order("100", BigDecimal.TEN);
        Order order2 = new Order("100", BigDecimal.ONE);
        orderRepository.save(order1);
        orderRepository.save(order2);
        return null;
      });
    } catch (DataIntegrityViolationException e) {
      // DataAccessException 의 서브 클래스로 Spring Repository or DAO 에서 발생하는 예외는 DataAccessException 로 래핑되어 던져짐
      System.out.println("중복된 주문번호 저장");
      throw new RuntimeException(e);
    }

  }

}
