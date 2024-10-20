package com.hossi.spring6;

import com.hossi.spring6.exchangeRate.CachedExchangeRateProvider;
import com.hossi.spring6.exchangeRate.RestTemplateExchangeRateProvider;
import com.hossi.spring6.payment.ExchangeProvider;
import com.hossi.spring6.exchangeRate.WebApiExchangeRateProvider;
import com.hossi.spring6.payment.PaymentService;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * BeanFactory 에 제공할 Bean 클래스 & 의존관계에 대한 구성 정보
 * 구성 정보를 바탕으로 스프링 컨테이녀(= BeanFactory)가 구현체를 생성 및 의존관계를 주입하고, 구현체를 제공
 * (기본적으로 구현체를 Singleton 으로 유지)
 */
@Configuration
public class PaymentConfig {
  @Bean
  public PaymentService paymentService() {
    return new PaymentService(exchangeRateProvider(), clock());
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(/* 원하는 전략 객체를 할당 */);
  }

  @Bean
  public ExchangeProvider exchangeRateProvider() {
    return new RestTemplateExchangeRateProvider(restTemplate());
  }

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
