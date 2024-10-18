package com.hossi.spring6.payment;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExchangeProvider {
  BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException;
}
