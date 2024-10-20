package com.hossi.spring6.exchangeRate;

import com.hossi.spring6.api.ApiTemplate;
import com.hossi.spring6.api.WebApiExecutorV1;
import com.hossi.spring6.api.JacksonExchangeRateExtractor;
import com.hossi.spring6.api.WebApiExecutorV2;
import com.hossi.spring6.payment.ExchangeProvider;
import java.math.BigDecimal;

public class WebApiExchangeRateProvider implements ExchangeProvider {
  private final ApiTemplate apiTemplate = new ApiTemplate();

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) {
    String url = "https://open.er-api.com/v6/latest/" + fromCurrencyType;
    return apiTemplate.executeApiForExchangeRate(toCurrencyType, url, new WebApiExecutorV2(), new JacksonExchangeRateExtractor());
  }
}
