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