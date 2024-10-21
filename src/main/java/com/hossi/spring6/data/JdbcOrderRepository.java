package com.hossi.spring6.data;

import com.hossi.spring6.order.Order;
import com.hossi.spring6.order.OrderRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.JdbcClient;

public class JdbcOrderRepository implements OrderRepository {
  private final JdbcClient jdbcClient;

  public JdbcOrderRepository(DataSource dataSource) {
    this.jdbcClient = JdbcClient.create(dataSource);
  }

  @PostConstruct
  void initDB() {
    jdbcClient.sql("""
      create table "orders" (id bigint not null, no varchar(255) not null, totalPrice numeric(38,2), primary key (id));
      alter table if exists "orders" drop constraint if exists UK_orders_no;
      alter table if exists "orders" add constraint UK_orders_no unique (no);
      create sequence "orders_SEQ" start with 1 increment by 50;
    """).update();
  }

  @Override
  public void save(Order order) {
    Long id = jdbcClient.sql("select next value for orders_SEQ").query(Long.class).single();
    order.setId(id);
    jdbcClient.sql("""
      insert into orders (id, no, totalPrice) values (?, ?, ?)
    """).params(order.getId(), order.getNo(), order.getTotalPrice()).update();
  }
}
