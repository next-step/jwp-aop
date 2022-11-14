# AOP 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 🚀 1단계 - JDK Proxy와 CGLib Proxy

### 기능 목록
- [x] JDK Dynamic Proxy 적용 (Hello, HelloTarget)
  - [x] Hello 인터페이스와 구현 클래스가 있을 때 모든 메서드의 반환 값을 대문자로 변환한다
- [x] CGLib 적용 (HelloService)
  - [x] 인터페이스가 없고 구현 클래스만 있을 때 모든 메서드의 반환 값을 대문자로 변환한다
- [x] HelloTarget, HelloService 클래스에 say로 시작하는 메서드 이외에 pingpong 메서드 추가
  - [x] say 로 시작하는 메서드에 한해서만 메서드의 반환 값을 대문자로 변환한다.
  - [x] JDK Dynamic Proxy, CGLib Proxy 둘 다 적용 되어야 한다.
    - [x] SayMethodMatcher 인터페이스 추가
  - [x] pingPong 메서드에 한해서만 메서드의 반환 값을 대문자로 변환한다.

---

# 🚀 2단계 - Proxy와 Bean 의존관계

### 요구 사항
- [x] Bean 컨테이너의 Bean과 Proxy를 연결
  - [x] 특정 Interface(FactoryBean)를 구현하는 경우 빈을 생성할 때 예외 처리하도록 Bean 컨테이너 개선
    - [x] FactoryBean의 구현체를 빈으로 등록하지 않고 FactoryBean 의 메서드 수행결과로 반환된 인스턴스를 빈으로 등록
- [ ] 재사용 가능한 FactoryBean
  - [ ] FactoryBean 을 제네릭 타입을 활용하여 프록시 빈 생성을 하나의 FactoryBean으로 생성 가능하게 해야한다.
    - [ ] Target: 프록시 대상
    - [ ] Advice: 부가기능 수행
    - [ ] Pointcut: 부가기능 수행 대상 선정

### 기능 목록
- [x] FactoryBean 인터페이스 추가
  - [x] 제네릭 타입을 활용하여 리턴하도록 한다
- [x] FactoryBean 인터페이스를 구현한 ProxyFactoryBean 구현체 추가
  - [x] 빈으로 등록할 인스턴스(target)를 주입받는다
    - [x] target의 타입(interface or concrete class)에 따라 프록시 객체 생성 방법을 다르게 한다
      - [x] interface: 구현체의 인스턴스와 인스턴스의 인터페이스 타입을 주입 받아서 JDK Dynamic Proxy 객체를 생성한다
        - [x] 반드시 구현하는 인터페이스를 주입 받아야 한다
        - [x] 반드시 InvocationHandler 를 주입 받아야 한다
      - [x] concrete class: 구현하는 인터페이스가 없는 클래스는 CGLib Proxy 객체를 생성한다.
        - [x] 반드시 MethodInterceptor 를 주입 받아야 한다
  - [ ] Advisor(Advice, Pointcut)을 주입 받아 Proxy 객체를 생성할 수 있게 한다
  - [x] Advice Interface
    - [x] 부가 기능 로직을 수행할 인터페이스
  - [x] PointCut Interface
    - [x] 적용 대상을 선정하는 인터페이스
  - [x] Advisor Interface
    - [x] 1개의 Advice와 1개의 PointCut을 주입받아 적용 대상에 부가 기능을 수행할 인터페이스
- [x] DefaultBeanFactory 개선
  - [x] 등록하려는 빈의 타입이 FactoryBean인 경우 빈으로 등록하지 않고 메서드를 통해 생성된 프록시 객체를 빈으로 등록한다 
  - [x] AnnotatedBeanDefinitionReader BeanDifinition을 생성할 때 프록시 빈의 경우 
- [x] AopProxy
  - [x] Jdk Dynamic Proxy, CGLib Proxy를 생성할 수 있는 공통 인터페이스 

---

# 🚀 3단계 - Transaction AOP 구현

### 기능 목록
- [x] `@Transaction` 애너테이션 메서드가 있으면 Transaction 처리한다.
  - [x] BeanFactory에 ProxyBean을 등록하기 위한 BeanPostProcessor
    - [x] Transaction 처리를 위한 Pointcut, Advice, ProxyPointcutAdvisor, BeanPostProcessor
    - [x] Advisor 타입을 추가하여 다형성 활용, JdkProxy, CglibProxy 모두 적용
      - [x] PointcutAdvisor, ProxyPointcutAdvisor
- [x] Thread 별로 Connection 을 유지할 수 있도록 ThreadLocal 으로 관리한다.
  - [x] ConnectionHolder
    - [x] Connection을 ThreadLocal로 관리한다
  - [x] DataSourceUtils
    - [x] ConnectionHolder를 활용하여 Connection 객체를 얻거나 반환한다.
- [x] DefaultBeanFactory에 BeanPostProcessor 등록
  - [x] 빈을 생성한 후 등록된 BeanPostProcessor 을 거쳐 후처리 한다.
    - [x] 트랜잭션이 필요한 빈은 프록시 빈으로 등록한다

---

# 🚀 4단계 - ControllerAdvice, ExceptionHandler 구현

### 기능 목록
- [x] ControllerAdvice 애너테이션 추가 
  - [x] `@Component` 애너테이션 추가
  - [x] 클래스에 적용할 수 있도록 target TYPE 지정 
- [x] ExceptionHandler 애너테이션 추가
  - [x] 예외 클래스를 값으로 받을 수 있다.
  - [x] 메서드에 적용할 수 있도록 target METHOD 로 지정
- [x] ExceptionArgumentResolver
  - [x] MethodParameter 의 타입이 예외인지 확인한다
  - [x] ExceptionHandler 애너테이션이 적용된 메서드의 인자를 반환한다
- [x] ExceptionHandlerConverter
  - [x] ExceptionArgumentResolver 활용 HandlerExecution 생성
- [x] ExceptionHandlerMapping
  - [x] ExceptionHandlerConverter를 주입받아 HandlerExcution을 관리
- [x] DispatcherServlet
  - [x] ExceptionHandlerMapping을 주입 받는다.
  - [x] service 수행 중(handler를 가져오거나, hander 수행 중) 예외가 발생하면 ExceptionHandlerExecution을 찾아 수행한다. 

### 피드백
- [ ] Controller에서 ExceptionHandler 애너테이션 적용
  - [x] ControllerExceptionHandlerMapping 추가
    - [x] Controller에서 ExceptionHandler 애너테이션 적용한 메서드를 찾아 HandlerExecution을 생성한다.
  - [ ] HandlerMappingRegistry를 활용하여 여러 ExceptionHandlerMapping을 관리한다.
  - [ ] Application 초기화 시 ControllerExceptionHandlerMapping을 DispatcherServlet에 추가한다.
    - [ ] Controller 내부의 ExceptionHandler를 우선하기 위해 먼저 등록한다.
