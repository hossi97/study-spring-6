package com.hossi.spring6.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hossi.spring6.OrderConfig;
import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceTest {
  @Autowired
  OrderService orderService;
  @Autowired
  private DataSource dataSource;

  @Test
  void createOrder() {
    var order = orderService.createOrder("100", BigDecimal.ONE);
    Assertions.assertThat(order.getId()).isGreaterThan(0);
  }

  @Test
  void createOrders() {
    List<OrderRequest> orderRequests = List.of(
        new OrderRequest("200", BigDecimal.TEN),
        new OrderRequest("300", BigDecimal.ONE)
    );

    List<Order> orders = orderService.createOrders(orderRequests);

    assertThat(orders).hasSize(2);
    orders.forEach(order -> assertThat(order.getId()).isGreaterThan(0));
  }

  @Test
  void createDuplicatedOrders() {
    List<OrderRequest> orderRequests = List.of(
        new OrderRequest("400", BigDecimal.TEN),
        new OrderRequest("400", BigDecimal.ONE)
    );

    assertThatThrownBy(() -> {
      orderService.createOrders(orderRequests);
    }).isInstanceOf(DataIntegrityViolationException.class);

    JdbcClient jdbcClient = JdbcClient.create(dataSource);
    Long single = jdbcClient.sql("select count(*) from orders where no = '400'").query(Long.class).single();
    assertThat(single).isEqualTo(0);
  }

}
