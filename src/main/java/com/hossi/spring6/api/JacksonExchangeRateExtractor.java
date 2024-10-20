package com.hossi.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hossi.spring6.exchangeRate.ExchangeRateData;
import java.math.BigDecimal;

public class JacksonExchangeRateExtractor implements ExchangeRateExtractor {
  @Override
  public BigDecimal extractExchangeRate(String toCurrencyType, String response) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
    BigDecimal exchangeRate = data.rates().get(toCurrencyType);
    return exchangeRate;
  }
}
