package com.hossi.spring6.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

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

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
  @InjectMocks
  PaymentService paymentService;
  @Mock
  private ExchangeProvider webApiExchangeRateProvider;

  @Test
  @DisplayName("prepare 메서드가 요구사항 3가지를 충족했는지를 검증")
  void prepare() throws IOException {
    // stub
    doReturn(BigDecimal.valueOf(1.5)).when(webApiExchangeRateProvider).getExchangeRate("USD", "KRW");

    Payment payment = paymentService.prepare(1L, "USD", "KRW", BigDecimal.TEN);

    // 1. 환율 정보 가져오기
    Assertions.assertThat(payment.getExchangeRate()).isEqualTo(BigDecimal.valueOf(1.5));

    // 2. 원화 환산금액 계산하기
    Assertions.assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExchangeRate().multiply(payment.getOriginalAmount()));

    // 3. 원화 환산금액의 유효시간 계산
    Assertions.assertThat(payment.getVaildUntil()).isAfter(LocalDateTime.now());
    Assertions.assertThat(payment.getVaildUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
  }
}