package com.hossi.spring6;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

abstract public class PaymentService {
  abstract BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException;

  // Currency API: https://open.er-api.com/v6/latest/USD
  public Payment prepare(Long orderId, String fromCurrencyType, String toCurrencyType, BigDecimal originalAmount) throws IOException {
    BigDecimal exchangeRate = getExchangeRate(fromCurrencyType, toCurrencyType);
    BigDecimal convertedAmount = originalAmount.multiply(exchangeRate);
    LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

    return new Payment(orderId, toCurrencyType, exchangeRate, originalAmount, convertedAmount, validUntil);
  }
}
