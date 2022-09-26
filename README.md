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


## 2단계 - Proxy와 Bean 의존관계
### 1. 요구사항 - Bean 컨테이너의 Bean과 Proxy를 연결
- 현재 Bean 컨테이너가 Bean의 생성과 생명주기를 담당
- Bean 컨테이너가 Bean을 생성하는 방법은 생성자를 활용해 생성
- Proxy는 생성자를 활용해 생성하는 것이 아니라 훨씬 더 복잡한 과정을 거쳐야 함
- Bean 컨테이너의 Bean과 Proxy를 연결하도록 Bean 컨테이너를 개선
### 2. 요구사항 - 재사용 가능한 FactoryBean
- Proxy가 추가될 때마다 FactoryBean을 매번 생성
- 공통적으로 사용할 수 있는 FactoryBean 생성
- Target, Advice, PointCut을 연결해 Proxy를 생성하는 재사용 가능한 FactoryBean을 추가

<hr />