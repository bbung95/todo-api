# todo-api
---
```text
- 주제 : todo 리스트 api 개발
- 구성 : spring boot + h2 + mybatis
- 필수 기능 및 항목
    - 목록 조회 : 내용으로 조회 가능
    - 상세 조회 / 등록 / 수정 / 삭제
```
### Stack  
- Java 8
- Spring Boot
- Maven
- H2

### Dependency  
- Spring Security
- thymeleaf
- lombok
- validation
- ModelMapper
- auth0

### ERD  

![ERD](https://user-images.githubusercontent.com/77668478/194481515-edb83152-b7f0-4ff2-b5aa-026a50555c93.png)

### 기능구현  
- User
  - 회원가입
  - 로그인
  - 닉네임 & 패스워드 변경
- Board
  - Board 등록
  - Board 목록
  - Board 삭제
- Task
  - Task 등록
  - Task 목록 조회 & 검색
  - Task 상태 변경
  - Task 삭제

### API
- PostMan Documents  
https://documenter.getpostman.com/view/17718132/2s83zfSRTD