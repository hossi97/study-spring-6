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

public class PaymentService {

  // Currency API: https://open.er-api.com/v6/latest/USD
  public Payment prepare(Long orderId, String currencyType, BigDecimal originalAmount) throws IOException {
    URL url = new URL("https://open.er-api.com/v6/latest/USD" + currencyType);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String response = br.lines().collect(Collectors.joining());
    br.close();

    ObjectMapper mapper = new ObjectMapper();
    ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
    BigDecimal exchangeRate = data.rates().get(currencyType);

    BigDecimal convertedAmount = originalAmount.multiply(exchangeRate);

    LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

    return new Payment(orderId, currencyType, exchangeRate, originalAmount, convertedAmount, validUntil);

  }

  public static void main(String[] args) throws IOException {
    PaymentService paymentService = new PaymentService();
    Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
    System.out.println(payment);
  }
}
