## 학습 내용
### Record
Java 16 부터 지원하는 기능으로 **불변 데이터 객체** 생성을 목적으로 만들어진 클래스입니다. 일반적으로 DTO 혹은 값 객체 생성에 사용됩니다.
- **특징:**
    1. `getter` / `equals()` / `hashCode()` / `toString()` 메서드들이 자동으로 생성됩니다.
    2. 멤버 필드는 final 필드로 구성됩니다.
    3. 컴팩트 생성자를 지원해 추가적인 검증을 할 수 있습니다.
- **장점:**
    - 가독성을 높이고, 불변성을 보장할 수 있습니다.
- **단점:**
    - 필드 추가가 어렵고,상속이 불가능합니다.
- **코드 예제:**
    ```java
    public record Person(String name, int age) {
        // 컴팩트 생성자
        public Person {
            // 유효성 검사
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150");
            }
            
            // 값 변형 (선택적)
            name = name.trim(); // 앞뒤 공백 제거
        }
        
        // 추가 메서드
        public boolean isAdult() {
            return age >= 18;
        }
    }
    ```

### TIP
- **Double / Float / BigDecimal:**
    - 부동소수점은 오차가 있을 수 있기 때문에, 금전적인 부분과 같이 정확한 값이 중요한 서비스에는 사용하는 것은 좋지 않다.

- **생성자의 한계:**
    - 동일한 타입의 파라미터를 사용할 때, 파라미터 순서를 바꾸면 의도치 않게 오류를 일으킬 수 있다.

- **ObjectMapper.readValue():**
  - `@JsonIgnoreProperties`를 통해 존재하지 않는 속성을 무시하고 직렬화를 진행할 수 있습니다.

