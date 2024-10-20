package com.hossi.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {
  public BigDecimal executeApiForExchangeRate(String toCurrencyType, String url, ApiExecutor apiExecutor, ExchangeRateExtractor exchangeRateExtractor) {
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
