package com.hossi.spring6.exchangeRate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hossi.spring6.payment.ExchangeProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExchangeRateProvider implements ExchangeProvider {

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException {
    URL url = new URL("https://open.er-api.com/v6/latest/" + fromCurrencyType);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String response = br.lines().collect(Collectors.joining());
    br.close();

    ObjectMapper mapper = new ObjectMapper();
    ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);

    BigDecimal exchangeRate = data.rates().get(toCurrencyType);
    System.out.println("API ExRate: " + exchangeRate);

    return exchangeRate;
  }
}
