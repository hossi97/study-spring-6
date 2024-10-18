package com.hossi.spring6.exchangeRate;

import com.hossi.spring6.payment.ExchangeProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExchangeRateProvider implements ExchangeProvider {
  private final ExchangeProvider target;
  private BigDecimal cachedExchangeRate;
  private LocalDateTime cacheExpiryTime;

  public CachedExchangeRateProvider(ExchangeProvider target) {
    this.target = target;
  }

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException {
    if (cachedExchangeRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())) {
      cachedExchangeRate = target.getExchangeRate(fromCurrencyType, toCurrencyType);
      cacheExpiryTime = LocalDateTime.now().plusSeconds(3);
      System.out.println("Cache Updated");
    }
    return cachedExchangeRate;
  }
}
