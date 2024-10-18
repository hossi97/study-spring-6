package com.hossi.spring6.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
  private final ExchangeProvider exchangeRateProvider;

  public PaymentService(ExchangeProvider exchangeRateProvider) {
    this.exchangeRateProvider = exchangeRateProvider;
  }

  // Currency API: https://open.er-api.com/v6/latest/USD
  public Payment prepare(Long orderId, String fromCurrencyType, String toCurrencyType, BigDecimal originalAmount) throws IOException {
    BigDecimal exchangeRate = exchangeRateProvider.getExchangeRate(fromCurrencyType, toCurrencyType);
    BigDecimal convertedAmount = originalAmount.multiply(exchangeRate);
    LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

    return new Payment(orderId, toCurrencyType, exchangeRate, originalAmount, convertedAmount, validUntil);
  }
}
