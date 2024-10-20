package com.hossi.spring6.payment;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.hossi.spring6.PaymentConfig;
import com.hossi.spring6.exchangeRate.WebApiExchangeRateProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import net.bytebuddy.asm.Advice.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = PaymentConfig.class)
class PaymentServiceTest {
  @Autowired
  PaymentService paymentService;
  @MockBean
  Clock clock;

  @BeforeEach
  void setUp() {
    when(clock.instant()).thenReturn(Instant.now());
    when(clock.getZone()).thenReturn(ZoneId.systemDefault());
  }

  @Test
  @DisplayName("prepare 메서드가 요구사항 3가지를 충족했는지를 검증")
  void prepare() throws IOException {
    Payment payment = paymentService.prepare(1L, "USD", "KRW", BigDecimal.TEN);

    // 1. 환율 정보 가져오기
    Assertions.assertThat(payment.getExchangeRate()).isNotNull();

    // 2. 원화 환산금액 계산하기
    Assertions.assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExchangeRate().multiply(payment.getOriginalAmount()));

    // 3. 원화 환산금액의 유효시간 계산
    LocalDateTime now = LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    Assertions.assertThat(payment.getVaildUntil()).isAfter(now);
    System.out.println("Start Time: " + now);
    Assertions.assertThat(payment.getVaildUntil()).isBeforeOrEqualTo(now.plusMinutes(30));
    System.out.println("End Time: " + now.plusMinutes(30));
  }
}