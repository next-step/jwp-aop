# AOP 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 기능 요구사항 (Java Dynamic Proxy 적용)
- 다음과 같은 인터페이스와 구현 클래스가 있을 때, HelloTarget 코드 변경 없이 모든 메소드의 반환 값을 대문자로 변환하도록 한다.
```java
interface Hello {
    String sayHello(String name);
    String sayHi(String name);
    String sayThankYou(String name);
}
```

```java
class HelloTarget implements Hello {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }
}
```
- Java 에서 기본으로 제공하는 JDK Dynamic Proxy 를 적용하여 해결한다.

# 기능 요구사항 (CGLib Proxy 적용)
앞의 Hello 예제에서 Hello 인터페이스가 없고 구현체 밖에 없는 상황이다. 이 구현체에 대한 Proxy 를 생성하여 대문자로 반환하는 메서드를 구현한다.
```java
class HelloTarget {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }
}
```
- Java 에서 기본으로 제공하는 CGLib Proxy 를 적용하여 해결한다.

# 기능 요구사항 (특정 메서드만 Proxy 적용)
- 앞 예제의 HelloTarget 에 say 로 시작하는 메소드 이외에 pingpong() 과 같은 새로운 메서드가 추가되었다.
- say 로 시작하는 메소드에 한해서만 **메소드의 반환 값을 대문자로 변환해야 한다.**
- JDK Dynamic Proxy 와 CGLib Proxy 모두 동작하도록 구현한다.

```java
public class JdkProxyTest {
    @Test
    void toUppercase() {
        // TODO InvocationHandler와 적용할 메소드 처리 부분을 구현

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class},
                uppercaseHandler);
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }
}
```

# 기능 요구사항 (Bean 컨테이너의 Bean 과 Proxy 를 연결)
- Bean 컨테이너가 Bean 의 생성과 생명주기를 담당하고 있다.
- 지금까지 Bean 컨테이너가 Bean 을 생성하는 방법은 `생성자`를 활용해서 생성했다. 그런데 Proxy 는 생성자를 활용해 생성하는 것이 아니라 훨씬 더 복잡한 과정을 거쳐야 한다.
- Bean 컨테이너의 Bean 과 Proxy 를 연결하도록 Bean 컨테이너를 개선해야 한다.

# 기능 요구사항 (재사용 가능한 FactoryBean)
- Proxy 가 추가될 때마다 FactoryBean 을 매번 생성하는 것도 귀찮다. 공통적으로 사용할 수 있는 FactoryBean 을 만들자.
- Target, Advice, PointCut 을 연결해 Proxy 를 생성하는 재사용 가능한 FactoryBean 을 추가한다.

# 기능 목록
- MethodMatcher 인터페이스
  - target 클래스의 특정 조건에 해당하는 메서드와 일치하는지에 대한 메서드를 제공한다.
  - NameMethodMatcher 구현체
    - 생성 시 name 을 입력받는다.
    - matches 메서드에서 해당 name 으로 시작하는 method 와 일치할 경우 true 를 반환한다.

** Jdk Dynamic Proxy
- HelloUpperCaseMethodInterceptor 구현체 (InvocationHandler 인터페이스 구현)
  - 프록시 target 객체, methodMatcher, target 클래스의 메서드들을 필드로 관리한다.
  - jdk dynamic proxy 생성 시, invoke 메서드가 호출되며 target 클래스의 메서드를 호출하기 전에 전후처리를 담당한다.
  - methodMatcher 를 통해, 특정 조건의 메서드에 대한 처리를 수행할 수 있다.

** CGLIB Proxy
- HelloUpperCaseMethodInterceptor 구현체 (MethodInterceptor 인터페이스 구현)
  - 프록시 target 객체, methodMatcher 를 필드로 관리한다.
  - cglib proxy 생성 시, intercept 메서드가 호출되며 target 클래스의 메서드를 호출하기 전에 전후처리를 담당한다.
  - methodMatcher 를 통해, 특정 조건의 메서드에 대한 처리를 수행할 수 있다.

** Advice, PointCut, Advisor 이용
- Advice 인터페이스
  - 특정 타겟 클래스에 대한 메서드를 실행하는 invoke 메서드를 제공한다.
  - UpperCaseAdvice 구현체
    - 특정 메서드의 반환 타입이 String 일 경우 대문자로 반환하는 부가 로직을 담당한다.
- PointCut 인터페이스
  - 특정 타겟 클래스에 대해 특정 조건과 일치하는 지에 대한 matches 메서드를 제공한다.
  - SayPrefixPointCut 구현체
    - 특정 메서드 이름이 "say" 로 시작하는지 에 대한 판별을 담당한다.
- Advisor 객체
  - Advice 와 PointCut 을 필드로 관리한다.
  - 해당 advisor 를 통해 특정 메서드 조건과 일치하는 메서드를 찾고, 실행하는 역할을 담당한다.
- AopProxy 인터페이스
  - 타겟 클래스에 대한 프록시 객체를 반환하는 getProxy 메서드를 제공한다.
  - JdkDynamicAopProxy 구현체
    - 타겟 클래스와 어드바이저를 필드로 관리한다.
    - 타겟 클래스에 대한 JdkDynamicProxy 를 반환한다.
  - CglibAopProxy 구현체
    - 타겟 클래스와 어드바이저를 필드로 관리한다.
    - 타겟 클래스에 대한 CglibProxy 를 반환한다.
- AopProxyFactory 객체
  - 타겟 클래스에 따라 CglibProxy, 혹은 JdkDynamicProxy 를 반환하는 로직을 담당한다.  
  - createAopProxy 메서드 (AopProxy 인터페이스 반환)
    - 타겟 클래스와 어드바이저를 인자로 받는다.
    - 타겟 클래스가 인터페이스를 구현할 경우 JdkDynamicProxy 를 사용한다.
    - 타겟 클래스가 인터페이스를 구현하지 않은 경우 createAopProxy 를 사용한다.
- FactoryBean 인터페이스
  - 타겟 클래스에 대한 프록시 객체를 반환하는 getObject 메서드를 제공한다.
  - 타겟 클래스의 타입을 반환하는 getObjectType 메서드를 제공한다.
    - DefaultBeanFactory 에서 빈을 등록할 때 FactoryBean 인스턴스라면 타겟 클래스의 타입으로 빈을 등록한다. 
  - ProxyFactoryBean 구현체
    - 타겟 클래스와 어드바이저를 필드로 관리한다.
      - 타겟 클래스의 타입을 제네릭으로 사용한다.
    - 타겟 클래스에 대한 프록시 객체를 반환한다.
    - 타겟 클래스의 타입을 반환한다.