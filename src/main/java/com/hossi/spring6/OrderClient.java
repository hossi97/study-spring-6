package com.hossi.spring6;

import com.hossi.spring6.order.Order;
import com.hossi.spring6.order.OrderService;
import com.hossi.spring6.order.OrderServiceImpl;
import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderClient {

  public static void main(String[] args) {
    BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
    OrderService service = beanFactory.getBean(OrderService.class);
    Order order = service.createOrder("100", BigDecimal.TEN);
    System.out.println(order);
  }
}
