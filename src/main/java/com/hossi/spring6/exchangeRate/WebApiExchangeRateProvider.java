package com.hossi.spring6.exchangeRate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hossi.spring6.api.ApiExecutor;
import com.hossi.spring6.api.ExchangeRateExtractor;
import com.hossi.spring6.api.WebApiExecutor;
import com.hossi.spring6.api.JacksonExchangeRateExtractor;
import com.hossi.spring6.payment.ExchangeProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApiExchangeRateProvider implements ExchangeProvider {

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) {
    String url = "https://open.er-api.com/v6/latest/" + fromCurrencyType;
    return executeApiForExchangeRate(toCurrencyType, url, new WebApiExecutor(), new JacksonExchangeRateExtractor());
  }

  private static BigDecimal executeApiForExchangeRate(String toCurrencyType, String url, ApiExecutor apiExecutor, ExchangeRateExtractor exchangeRateExtractor) {
    URI uri;
    try {
      uri = new URI(url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }

    String response;
    try {
      response = apiExecutor.execute(uri);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      return exchangeRateExtractor.extractExchangeRate(toCurrencyType, response);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
