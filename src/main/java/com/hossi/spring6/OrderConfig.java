package com.hossi.spring6;

import com.hossi.spring6.data.JpaOrderRepository;
import com.hossi.spring6.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {
  @Bean
  public JpaOrderRepository orderRepository() {
    return new JpaOrderRepository();
  }

  @Bean
  public OrderService orderService(JpaOrderRepository orderRepository, JpaTransactionManager transactionManager) {
    return new OrderService(orderRepository, transactionManager);
  }
}
