package com.hossi.spring6;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
  public static void main(String[] args) throws IOException {
    PaymentService paymentService = new WebApiPaymentService();
    Payment payment = paymentService.prepare(100L, "USD", "KRW", BigDecimal.valueOf(50.7));
    System.out.println(payment);
  }
}
