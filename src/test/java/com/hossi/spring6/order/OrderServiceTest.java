package com.hossi.spring6.order;

import com.hossi.spring6.OrderConfig;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceTest {
  @Autowired
  OrderService orderService;

  @Test
  void createOrder() {
    var order = orderService.createOrder("100", BigDecimal.ONE);
    Assertions.assertThat(order.getId()).isGreaterThan(0);
  }

}
