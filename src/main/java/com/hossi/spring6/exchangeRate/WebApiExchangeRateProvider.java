package com.hossi.spring6.exchangeRate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hossi.spring6.payment.ExchangeProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExchangeRateProvider implements ExchangeProvider {

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) {
    String url = "https://open.er-api.com/v6/latest/" + fromCurrencyType;
    URI uri;
    try {
      uri = new URI(url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }

    String response;
    try {
      HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
      try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
        response = br.lines().collect(Collectors.joining());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ObjectMapper mapper = new ObjectMapper();
    ExchangeRateData data;
    try {
      data = mapper.readValue(response, ExchangeRateData.class);
      BigDecimal exchangeRate = data.rates().get(toCurrencyType);
      return exchangeRate;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
