package com.hossi.spring6.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import com.hossi.spring6.ObjectFactory;
import com.hossi.spring6.exchangeRate.WebApiExchangeRateProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ObjectFactory.class)
class PaymentServiceTest {

  @Autowired
  PaymentService paymentService;

  @Test
  @DisplayName("prepare 메서드가 요구사항 3가지를 충족했는지를 검증")
  void prepare() throws IOException {
    Payment payment = paymentService.prepare(1L, "USD", "KRW", BigDecimal.TEN);

    // 1. 환율 정보 가져오기
    Assertions.assertThat(payment.getExchangeRate()).isNotNull();

    // 2. 원화 환산금액 계산하기
    Assertions.assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExchangeRate().multiply(payment.getOriginalAmount()));

    // 3. 원화 환산금액의 유효시간 계산
    Assertions.assertThat(payment.getVaildUntil()).isAfter(LocalDateTime.now());
    Assertions.assertThat(payment.getVaildUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
  }
}