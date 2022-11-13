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

# 기능 요구사항 (Transaction AOP 구현)
- DB Transaction 처리를 하고 싶은 메소드에 @Transactional 애노테이션을 추가하면 Transaction 처리가 가능하도록 해야 한다.
- 다음 예제 코드와 같이 addAnswer() 메소드에 Transactional 을 설정하면 addAnswer() 메소드는 하나의 Transaction 으로 묶여서 처리되어야 한다.
```java
@Service
class QnaService {
    @Inject
    private QuestionRepository questionRepository;
    
    @Inject
    private AnswerRepository answerRepository;
    
    @Transactional
    public void addAnswer(long questionId, Answer answer) {
        answerRepository.insert(questionId, answer);
        questionRepository.updateAnswerCount(questionId);
    }
}
```
- 위 코드에서 questionRepository.updateAnswerCount() 에서 Exception 이 발생하면 앞에 추가한 댓글로 롤백되어야 한다.

# 기능 요구사항 (ControllerAdvice, ExceptionHandler 구현)
- Controller, ArgumentResolver 와 같은 곳에서 Exception 이 발생할 경우, Exception 을 처리할 수 있어야 한다.
- 현재 구현된 버전의 코드는 로그인 하지 않은 사용자가 글쓰기 버튼을 클릭하면 Exception 이 발생하여 에러 메시지가 보인다. Exception 처리를 통해 로그인 페이지로 이동하도록 해야 한다.
```java
@ControllerAdvice 
public class MyAdvice { 
    @ExceptionHandler(LoginRequiredException.class) 
    public ModelAndView loginRequired() { 
        return jspView("redirect:/users/loginForm")
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

** Advice, PointCut, Advisor 이용
- Advice 인터페이스
  - 특정 타겟 클래스에 대한 메서드를 실행하는 invoke 메서드를 제공한다.
  - UpperCaseAdvice 구현체
    - 특정 메서드의 반환 타입이 String 일 경우 대문자로 반환하는 부가 로직을 담당한다.
  - TransactionalAdvice 구현체
    - dataSource 를 필드로 관리한다.
    - 특정 메서드에 @Transactional 이 붙은 경우, 해당 메서드의 비즈니스 로직이 정상 처리될 경우 트랜잭션을 commit, 비즈니스 로직이 비정상 처리될 경우 트랜잭션을 rollback 하는 부가 로직을 담당한다. 
- PointCut 인터페이스
  - 특정 타겟 클래스에 대해 특정 조건과 일치하는 지에 대한 matches 메서드를 제공한다.
  - SayPrefixPointCut 구현체
    - 특정 메서드 이름이 "say" 로 시작하는지 에 대한 판별을 담당한다.
  - TransactionalPointCut 구현체
    - 타겟 클래스에 @Transactional 애노테이션이 붙어있거나, 메서드에 @Transactional 이 애노테이션이 붙어있는지 확인하는 역할을 담당한다.
- Advisor 객체
  - Advice 와 PointCut 을 필드로 관리한다.
  - 해당 advisor 를 통해 특정 메서드 조건과 일치하는 메서드를 찾고, 실행하는 역할을 담당한다.
- AopProxy 인터페이스
  - 타겟 클래스에 대한 프록시 객체를 반환하는 getProxy 메서드를 제공한다.
  - JdkDynamicAopProxy 구현체
    - 타겟 클래스와 어드바이저를 필드로 관리한다.
    - 타겟 클래스에 대한 JdkDynamicProxy 를 반환한다.
      - PointCut 에 해당하는 메서드는 프록시를 통해 advice 추가 로직과 원본 메서드의 invoke 가 함꼐 이루어 진다.
      - PointCut 에 해당하지 않는 메서드는 원본 메서드만 invoke 한다.
  - CglibAopProxy 구현체
    - 타겟 클래스와 어드바이저를 필드로 관리한다.
    - 타겟 클래스에 대한 CglibProxy 를 반환한다.
      - 기본 생성자가 없을 경우, 선언된 생성자를 통해 프록시를 반환한다.
    - PointCut 에 해당하는 메서드는 프록시를 통해 advice 추가 로직과 원본 메서드의 invoke 가 함꼐 이루어 진다.
    - PointCut 에 해당하지 않는 메서드는 원본 메서드만 invoke 한다.
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
- BeanPostProcessor 인터페이스
  - postProcessAfterInitialization 메서드
    - 빈 후처리기를 통해 빈 컨테이너에 등록될 빈의 후처리를 담당한다.
  - TransactionalBeanPostProcessor 구현체
    - 전달받은 빈의 클래스에서 @Transactional 애노테이션이 붙어있는 클래스 혹은 메서드가 있다면 ProxyFactoryBean 을 통해 만들어진 프록시를 빈 컨테이너에 등록한다.
- ConnectionHolder 객체
  - Connection 을 스레드 로컬로 관리한다.
  - 현재 스레드에 커넥션이 있는지 확인할 수 있다.
  - 현재 스레드에 커넥션을 셋팅할 수 있다.
  - 현재 스레드의 커넥션을 닫고, 스레드 로컬에서 지울 수 있다.
- DataSourceUtils 객체
  - ConnectionHolder 를 이용한 유틸성 클래스이다.
  - 현재 스레드에 커넥션을 셋팅하도록 한다.
  - 현재 스레드의 커넥션을 닫고, 스레드 로컬에서 지울 수 있도록 한다.
  - 현재 스레드에 커넥션이 있다면 해당 커넥션을 반환하고, 현재 스레드에 커넥션이 없다면 새로운 커넥션을 반환한다.
  - releaseConnection 메서드
    - 인자로 전달 받은 커넥션이 현재 스레드 로컬에서 관리하고있는 커넥션이라면 커넥션을 닫지 않는다.
    - 인자로 전달 받은 커넥션이 현재 스레드 로컬에서 관리하고 있지 않는 커넥션이라면 해당 커넥션을 닫는다.

- QnaService 객체
  - addAnswer 메서드
    - @Transactional 애노테이션이 붙어있다.
    - 질문에 대한 답변을 등록하는 메서드이다.
    - 만약 질문이 없다면 답변을 등록할 수 없도록 예외를 발생하고, 데이터베이스에는 답변이 등록되지 않도록 롤백된다.
  - addAnswerNoTransactional 메서드
    - @Transactional 애노테이션이 붙어있지 않다.
    - addAnswer 와 로직은 동일하다.
    - 만약 질문이 없다면 답변을 등록할 수 없도록 예외가 발생한다. 하지만 트랜잭션 롤백이 되지 않기 떄문에 데이터베이스에 답변이 등록된다.

- ControllerAdvice 애노테이션
  - @Controller 애노테이션이 붙은 클래스의 메서드에서 특정 예외를 전역으로 처리하도록 한다.
- ExceptionHandler 애노테이션
  - @Controller 애노테이션이 붙은 클래스의 메서드에서 특정 예외가 발생할 경우 @ExceptionHandler 애노테이션이 붙은 메서드에서 예외처리를 새롭게 진행하도록 한다.
- ExceptionHandlerMapping 인터페이스
  - 특정 예외에 대한 예외 처리를 담당하는 핸들러 역할을 담당한다.
  - ControllerExceptionHandlerMapping 구현체
    - @Controller 애노테이션이 붙은 클래스 내부에 @ExceptionHandler 애노테이션이 붙은 메서드가 존재할 경우 @ExceptionHandler 가 붙은 메서드를 찾아서 우선적으로 예외 처리한다.
  - ControllerAdviceExceptionHandlerMapping 구현체
    - @Controller 애노테이션 붙은 클래스 내부에 @ExceptionHandler 애노테이션이 붙은 메서드가 존재하지 않을 경우, @ControllerAdvice 애노테이션이 붙은 전역 예외 처리 핸들러를 찾아서 예외 처리한다.
- ExceptionHandlerConverter 객체
  - @ExceptionHandler 에 의해 예외 처리할 HandlerExecution 을 예외 클래스 정보와 함께 반환한다.
- ExceptionHandlerMappingRegistry 객체
  - ExceptionHandlerMapping 을 관리하는 일급 컬렉션
  - 특정 예외와, 예외가 발생한 핸들러를 이용하여 예외 핸들러를 찾아온다.
