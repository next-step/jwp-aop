# Aspect OP
## 1단계 - JDK Proxy와 CGLib Proxy
### 1. 요구사항 - Java Dynamic Proxy 적용
- 인터페이스와 구현 클래스가 있을 때 모든 메소드의 반환 값을 대문자로 변환
- Java에서 기본으로 제공하는 JDK Dynamic Proxy를 적용해 해결
### 2. 요구사항 - CGLib Proxy 적용
- 앞의 Hello 예제에서 Hello 인터페이스가 없고 구현체 밖에 없을 경우 이에 대한 Proxy를 생성해 대문자로 반환
- Java에서 기본으로 제공하는 CGLib Proxy를 적용해 해결 
### 3. 요구사항 - JDK Dynamic Proxy와 CGLib Proxy 모두 동작하도록 구현

<hr />

