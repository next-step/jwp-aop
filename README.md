# AOP 구현

## 진행 방법

* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 - JDK Proxy와 CGLib Proxy

### 요구사항 - Java Dynamic Proxy 적용

- 인터페이스와 구현 클래스가 있을 때 JDK Dynamic Proxy로 메소드의 반환 값을 대문자로 변환

### 요구사항 - CGLib Proxy 적용

- CGLib Proxy를 이용하여 Hello 구현체의 메소드의 값을 대문자로 반환

### 요구사항

- `HelloTarget` 에 `pingpong()` 메소드 추가
- `say` 로 시작하는 메소드만 반환 값을 대문자로 변환
- JDK Dynamic Proxy와 CGLib Proxy 모두 동작하도록 구현


## 2단계 - Proxy와 Bean 의존관계

### 요구사항 - Bean 컨테이너의 Bean과 Proxy를 연결

- Bean 컨테이너의 Bean과 Proxy를 연결하도록 Bean 컨테이너를 개선  
- 재사용 가능한 `FactoryBean` 생성

```java
public interface FactoryBean<T> {
    T getObject() throws Exception;
}
```

```java
if (beanInstance instanceof FactoryBean) {
        FactoryBean factory = (FactoryBean) beanInstance;
        beanInstance = factory.getObject();
}
return beanInstance;
```
