package com.hossi.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hossi.spring6.exchangeRate.ExchangeRateData;
import java.math.BigDecimal;

public interface ExchangeRateExtractor {
  BigDecimal extractExchangeRate(String toCurrencyType, String response) throws JsonProcessingException;
}
