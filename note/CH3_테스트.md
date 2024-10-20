## 학습 내용
### JUnit 5

- 테스트 클래스의 재생성
  - 모든 테스트는 다른 테스트의 영향을 받지 않고 독립적으로 실행돼야 하기 때문

### PaymentService 테스트의 문제점
1. 우리가 제어할 수 없는 외부 시스템에 문제가 생기는 경우
2. ExchangeRateProvider가 제공하는 환율 값으로 계산한 것인지 불분명
3. 환율 유효 시간 계산이 정확한 것인지 불분명

### 테스트의 구성 요소
- 테스트 대상 (SUT, System Under Test):
  - PaymentService
- 테스트 (클래스):
  - PaymentServiceTest
- 협력자 (의존하고 있는):
  - WebApiExchangeProvider → ExchangeRateStub (Test Double or Imposter 라고 부름)

### 수동 DI를 이용한 테스트
- 원하는 결과 값을 반환하도록 임의의 테스트 전용 객체를 생성해서 테스트에 활용할 수 있음
- @ExtendWith(MockitoExtension.class)
- @Mock
- @Spy
- @InjectMock

### Spring DI를 이용한 테스트
- @ExtendWith(SpringExtension.class)
- @ContextConfiguration
- @Autowired

### Clock 을 이용한 시간 테스트
- 제한시간을 정확하게 측정하고 테스트하기 위해서는 시작 시간에 주기를 정확하게 더해줘야 한다.
- 이를 위해서 Clock 객체를 사용할 수 있다.

### 도메인 주도 개발
- Payment 와 같은 객체에 로직을 추가하는 형태
- ex. 생성자 대신 정적 팩토리 메서드를 활용하여 의미 있는 이름과 적절한 로직을 추가할 수 있다.