package com.hossi.spring6.exchangeRate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hossi.spring6.api.ApiExecutor;
import com.hossi.spring6.api.ApiTemplate;
import com.hossi.spring6.api.ExchangeRateExtractor;
import com.hossi.spring6.api.WebApiExecutor;
import com.hossi.spring6.api.JacksonExchangeRateExtractor;
import com.hossi.spring6.payment.ExchangeProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApiExchangeRateProvider implements ExchangeProvider {
  private final ApiTemplate apiTemplate = new ApiTemplate();

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) {
    String url = "https://open.er-api.com/v6/latest/" + fromCurrencyType;
    return apiTemplate.executeApiForExchangeRate(toCurrencyType, url, new WebApiExecutor(), new JacksonExchangeRateExtractor());
  }
}
