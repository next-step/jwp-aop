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
