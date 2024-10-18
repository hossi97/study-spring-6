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
     public class ObjectFactory {
       public PaymentService paymentService() {
         return new PaymentService(exchangeRateProvider());
       }

       private ExchangeProvider exchangeRateProvider() {
         return new WebApiExchangeRateProvider();
       }
     }
     ```

### 위의 코드에 적용된 객체지향 원칙과 패턴
> 원칙과 패턴을 적용했을 때의 핵심은 **변화에 대한 비용이 적다**는 것
  1. OCP (Open Closed Principle)
     - 기능을 확장할 때 코드의 변경이 없어야 한다.
  2. High Coherence, Low Coupling
     - 높은 응집도: 하나의 모듈은 하나의 책임과 관심사를 갖는다.
     - 낮은 결합도: 상호작용하는 모듈 간에는 서로 다른 책임과 관심사를 갖는다.
  3. Strategy Pattern
     - 기능 맥락(= `PaymentService`)에서 변경이 필요한 로직을 인터페이스(= `ExchangeRateProvider`)를 통해 외부로 분리
     - 구체적인 알고리즘 클래스(= `WebApiExchangeProvider`)를 필요에 따라 바꿔서 사용
     - Java 에서는 Comparator 클래스를 이용해 Sorting 전략을 바꾸는 것도 전략 패턴의 일종
  4. Inversion of Control
     - PaymentService 가 ExchangeProvider 구현체를 제어하던 부분을 Client 에서 제어하도록 변경 → 프레임워크의 제어의 역전과는 다름

### 싱글톤 패턴의 단점
- 싱글톤 패턴은 객체를 반환하는 메서드가 public static 하기 때문에 멀티 스레딩 환경이나 연속적인 테스트에 사용할 때는 주의가 필요하다.
- 이와 더불어, 기본 생성자가 private 하므로 상속에 어려움이 있고, 인터페이스와 함께 사용하지 않으면 객체의 수정에 민감하여 의존성이 커질 수 있다.

### 데코레이터 패턴을 이용한 API 캐싱
```java
public class CachedExchangeRateProvider implements ExchangeProvider {
  private final ExchangeProvider target;
  private BigDecimal cachedExchangeRate;
  private LocalDateTime cacheExpiryTime;

  public CachedExchangeRateProvider(ExchangeProvider target) {
    this.target = target;
  }

  @Override
  public BigDecimal getExchangeRate(String fromCurrencyType, String toCurrencyType) throws IOException {
    if (cachedExchangeRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())) {
      cachedExchangeRate = target.getExchangeRate(fromCurrencyType, toCurrencyType);
      cacheExpiryTime = LocalDateTime.now().plusSeconds(3);
      System.out.println("Cache Updated");
    }
    return cachedExchangeRate;
  }
}
```

### DIP (Dependency Inversion Principle)
- 상위 모듈은 하위 모듈에 의존하면 안 되며, 둘 다 추상화에 의존해야 한다.
- 추상화는 구체적인 사항에 의존하면 안 된다.