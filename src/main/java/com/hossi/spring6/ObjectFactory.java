package com.hossi.spring6;

public class ObjectFactory {
  public PaymentService paymentService() {
    return new PaymentService(exchangeRateProvider());
  }

  private ExchangeProvider exchangeRateProvider() {
    return new WebApiExchangeRateProvider();
  }
}
