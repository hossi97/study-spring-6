package com.hossi.spring6;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExchangeProvider {
  BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException;
}
