package com.hossi.spring6.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class PaymentService {
  private final ExchangeProvider exchangeRateProvider;
  private final Clock clock;

  public PaymentService(ExchangeProvider exchangeRateProvider, Clock clock) {
    this.exchangeRateProvider = exchangeRateProvider;
    this.clock = clock;
  }

  // Currency API: https://open.er-api.com/v6/latest/USD
  public Payment prepare(Long orderId, String fromCurrencyType, String toCurrencyType, BigDecimal originalAmount) {
    BigDecimal exchangeRate = exchangeRateProvider.getExchangeRate(fromCurrencyType, toCurrencyType);
    return Payment.createPrepared(orderId, toCurrencyType, exchangeRate, originalAmount, LocalDateTime.now(clock));
  }
}
