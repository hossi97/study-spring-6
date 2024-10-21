package com.hossi.spring6;

import com.hossi.spring6.data.JdbcOrderRepository;
import com.hossi.spring6.data.JpaOrderRepository;
import com.hossi.spring6.order.OrderRepository;
import com.hossi.spring6.order.OrderService;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {
  @Bean
  public OrderRepository orderRepository(DataSource dataSource) {
    return new JdbcOrderRepository(dataSource);
  }

  @Bean
  public OrderService orderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
    return new OrderService(orderRepository, transactionManager);
  }
}
