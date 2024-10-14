## 학습 내용

### 오브젝트

- 클래스 → 오브젝트를 만들기 위한 설계도
- 인스턴스 → 클래스의 실체
- 오브젝트 → 클래스의 인스턴스 집합

### 의존관계

- 의존관계는 Compile Time 의존 관계와 Runtime 의존 관계로 이뤄진다.

### 관심사의 분리

- 변경에 따른 영향을 보고 관심사를 구분할 수 있다.
- 관심사 분리 방법
  1. 메소드의 분리
      ```java
      private BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + fromCurrencyType);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
        BigDecimal exchangeRate = data.rates().get(toCurrencyType);
        return exchangeRate;
      }
      ```
  2. [상속을 통한 확장](https://github.com/hossi97/study-spring-6/commit/a63ac61adf462b09fba2ff5328751cfeb87454b5)
      ```java
      public class WebApiPaymentService extends PaymentService {

        @Override
        BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException {
          URL url = new URL("https://open.er-api.com/v6/latest/" + fromCurrencyType);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String response = br.lines().collect(Collectors.joining());
          br.close();

          ObjectMapper mapper = new ObjectMapper();
          ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
          BigDecimal exchangeRate = data.rates().get(toCurrencyType);
          return exchangeRate;
        }
      }
      ```
     - 템플릿 메소드 패턴
     - 팩토리 메소드 패턴
  3. [클래스의 분리](https://github.com/hossi97/study-spring-6/commit/3e9e015437556cd5cebe544ad9bae555fef8bb2a)
     ```java
     public class PaymentService {
       private final WebApiExchangeRateProvider exchangeRateProvider;

       public PaymentService() {
         this.exchangeRateProvider = new WebApiExchangeRateProvider();
       }

       // Currency API: https://open.er-api.com/v6/latest/USD
       public Payment prepare(Long orderId, String fromCurrencyType, String toCurrencyType, BigDecimal originalAmount) throws IOException {
         BigDecimal exchangeRate = exchangeRateProvider.getExchangeRate(fromCurrencyType, toCurrencyType);
         BigDecimal convertedAmount = originalAmount.multiply(exchangeRate);
         LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

         return new Payment(orderId, toCurrencyType, exchangeRate, originalAmount, convertedAmount, validUntil);
       }
     }
     ```
  4. 인터페이스를 통한 확장
     - Compile Dependency: PaymentService ExchangeRateProvider
     - Runtime Dependency: PaymentService WebApiExchangeRateProvider
     - 관계 설정의 책임을 PaymentService에서 분리
     - 관계 설정은 클라이언트에서 하고, 사용은 PaymentService 에서 함
     ```java
     public class Client {
       public static void main(String[] args) throws IOException {
         PaymentService paymentService = new PaymentService(new WebApiExchangeRateProvider());
         Payment payment = paymentService.prepare(100L, "USD", "KRW", BigDecimal.valueOf(50.7));
         System.out.println(payment);
       }
     }
     ```
  5. 오브젝트 팩토리
     ```java
     
     ```