package com.hossi.spring6.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
  private Long orderId;
  private String currencyType;
  private BigDecimal exchangeRate;
  private BigDecimal originalAmount;
  private BigDecimal convertedAmount;
  private LocalDateTime vaildUntil;

  public Payment(
      Long orderId,
      String currencyType,
      BigDecimal exchangeRate,
      BigDecimal originalAmount,
      BigDecimal convertedAmount,
      LocalDateTime vaildUntil
  ) {
    this.orderId = orderId;
    this.currencyType = currencyType;
    this.exchangeRate = exchangeRate;
    this.originalAmount = originalAmount;
    this.convertedAmount = convertedAmount;
    this.vaildUntil = vaildUntil;
  }

  @Override
  public String toString() {
    return "Payment{" +
        "orderId=" + orderId +
        ", currencyType='" + currencyType + '\'' +
        ", exchangeRate=" + exchangeRate +
        ", originalAmount=" + originalAmount +
        ", convertedAmount=" + convertedAmount +
        ", vaildUntil=" + vaildUntil +
        '}';
  }
}