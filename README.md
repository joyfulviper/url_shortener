## 기능 요구사항
1. URL 단축 서비스를 만들어야함
2. 단축 URL의 키는 8글자로 생성 - [www.abc.com/](http://www.abc.com/){단축 URL 키}
3. 키 생성 알고리즘은 자유롭게
4. 단축 URL은 원래 URL로 리다이렉트 되어야함
5. 원래 URL로 다시 단축 URL 생성해도 항상 새로운 단축 URL 생성되어야함
    - 기존 URL은 계속 동작 해야함
6. 단축 URL에서 리다이렉트 될 때 카운트 증가
7. 이러한 정보(단축 URL, 카운트, 원래 URL)를 확인할 수 있는 기능
8. DB 없이 컬렉션으로 데이터 저장
9. 위 기능을 확인할 수 있는 테스트 코드
10. 해당 서비스를 사용할 수 있는 UI 페이지

### 설계
- Spring Boot, Java를 이용해 개발
- 8자리 규칙을 지키기 위해 SHA256으로 해시화 후 BASE64인코딩 하여 앞자리 8개를 잘라서 사용
- Collection을 사용하기 때문에 HashMap의 동시성 문제를 해결하기 위해 ConcurrentHashMap 사용

### 개발 환경
- Java 17
- Gradle
- Spring Boot