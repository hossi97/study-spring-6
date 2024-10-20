## 학습 내용
### 서비스의 종류
- 어플리케이션 서비스: 말 그대로 앱 내에 존재하는 서비스
- 도메인 서비스: 엔티티에 종속된 로직을 엔티티 내부에 정의하는 형태의 서비스
- 인프라 서비스: 기술을 제공하는 서비스로 메일, 캐시, 트랜잭션 등의 추상화 가능한 형태의 서비스

### 어댑터 패턴을 활용해 트랜잭션 객체 추상화
- PlatformTransactionManager: 앱 로컬 트랜잭션 매니저 / 글로벌 트랜잭션 매니저는 따로 있음
  - JpaTransactionManager → JPA Transaction 에 대한 어댑터
  - DataSourceTransactionManager → JDBC Transaction 에 대한 어댑터

### 프록시 패턴
- 타깃을 대신해서 아래와 같은 기능을 제공
  - 접근 제어
  - 원격 접속
  - 보안
  - 지연

### @Transactional 어노테이션
- 트랜잭션 안에서 실행되도록 프록시를 구성해준다.

### 프록시를 활용한 AOP
- @Transactional 과 같이 프록시 패턴을 활용해 중복된 작업을 대체할 수 있음
- Point-Cut Language, 디버깅 등이 까다로워서 활용이 어려움