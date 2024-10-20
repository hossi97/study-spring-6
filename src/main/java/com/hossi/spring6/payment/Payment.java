package com.hossi.spring6.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class Payment {
  private Long orderId;
  private String currencyType;
  private BigDecimal exchangeRate;
  private BigDecimal originalAmount;
  private BigDecimal convertedAmount;
  private LocalDateTime vaildUntil;

  private Payment(
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

  public static Payment createPrepared(
      Long orderId,
      String currencyType,
      BigDecimal exchangeRate,
      BigDecimal originalAmount,
      LocalDateTime now
    ) {
    BigDecimal convertedAmount = originalAmount.multiply(exchangeRate);
    LocalDateTime validUntil = now.plusMinutes(30);
    return new Payment(orderId, currencyType, exchangeRate, originalAmount, convertedAmount, validUntil);
  }

  public boolean isValid(Clock clock) {
    return LocalDateTime.now(clock).isBefore(this.vaildUntil);
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

  public Long getOrderId() {
    return orderId;
  }

  public String getCurrencyType() {
    return currencyType;
  }

  public BigDecimal getExchangeRate() {
    return exchangeRate;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public BigDecimal getConvertedAmount() {
    return convertedAmount;
  }

  public LocalDateTime getVaildUntil() {
    return vaildUntil;
  }
}