package com.hossi.spring6.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {
  @Id @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String no;

  private BigDecimal totalPrice;

  public Order() {}

  public Order(String no, BigDecimal totalPrice) {
    this.no = no;
    this.totalPrice = totalPrice;
  }

  public Long getId() {
    return id;
  }

  public String getNo() {
    return no;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  @Override
  public String toString() {
    return "Order{" +
        "no='" + no + '\'' +
        ", totalPrice=" + totalPrice +
        '}';
  }
}
