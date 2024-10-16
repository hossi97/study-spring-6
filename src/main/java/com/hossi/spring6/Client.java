package com.hossi.spring6;

import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {
  public static void main(String[] args) throws IOException {
    BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
    PaymentService paymentService = beanFactory.getBean(PaymentService.class);
    ObjectFactory factory = beanFactory.getBean(ObjectFactory.class);
    PaymentService paymentService1 = factory.paymentService();
    PaymentService paymentService2 = factory.paymentService();

    /**
     * [True 인 이유]
     * - @Configuration 으로 구성 정보 역할을 하는 클래스 내부의 빈들은 기본적으로 싱글톤 형태로 클래스를 반환함
     * - Proxy 를 이용해서 위와 같은 방식이 가능
     */
    System.out.println(paymentService1 == paymentService2);

    Payment payment = paymentService.prepare(100L, "USD", "KRW", BigDecimal.valueOf(50.7));
    System.out.println(payment);
  }
}
