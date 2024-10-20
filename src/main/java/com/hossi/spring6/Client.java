package com.hossi.spring6;

import com.hossi.spring6.payment.Payment;
import com.hossi.spring6.payment.PaymentService;
import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {
  public static void main(String[] args) throws IOException {
    BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
    PaymentService paymentService = beanFactory.getBean(PaymentService.class);

    Payment payment = paymentService.prepare(100L, "USD", "KRW", BigDecimal.valueOf(50.7));
    System.out.println(payment);
  }
}
