package com.hossi.spring6.exchangeRate;

import com.hossi.spring6.api.JacksonExchangeRateExtractor;
import com.hossi.spring6.api.WebApiExecutorV2;
import com.hossi.spring6.payment.ExchangeProvider;
import java.math.BigDecimal;
import org.springframework.web.client.RestTemplate;

public class RestTemplateExchangeRateProvider implements ExchangeProvider {
  private final RestTemplate restTemplate;

  public RestTemplateExchangeRateProvider(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) {
    String url = "https://open.er-api.com/v6/latest/" + fromCurrencyType;
    return restTemplate.getForObject(url, ExchangeRateData.class).rates().get(toCurrencyType);
  }
}
