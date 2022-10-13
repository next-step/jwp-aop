# AOP 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 1단계 - JDK Proxy와 CGLib Proxy
1. Java Dynamic Proxy 적용 
   1. 인터페이스와 구현 클래스가 있을 때, 모든 메소드의 반환 값을 대문자로 변환한다.
   2. Java에서 기본으로 제공하는 JDK Dynamic Proxy를 적용해 해결한다.
2. CGLib Proxy 적용
   1. 인터페이스가 없고 구현체 밖에 없다. 이에 대한 Proxy를 생성해 대문자로 반환한다.
   2. Java에서 기본으로 제공하는 CGLib Proxy를 적용해 해결한다.
3. pingpong()과 같은 새로운 메소드가 추가되었을 때, say로 시작하는 메소드에 한해서만 메소드의 반환 값을 대문자로 변환한다. 
   1. JDK Dynamic Proxy와 CGLib Proxy 모두 동작하도록 구현한다. 
   2. JDK Dynamic Proxy의 InvocationHandler, CGLib Proxy의 MethodInterceptor에서 하드코딩해서 구현한다.
   3. 하드코딩 대신 Interface를 추가해 추상화한다.
   
# 2단계 - Proxy와 Bean 의존관계

### 기능 요구사항
1. Bean 컨테이너의 Bean과 proxy를 연결
    1. 기존에는 Bean 생성을 생성자를 통해 생성하였는데, Proxy를 사용해 생성할 수 있도록 Bean 컨테이너 개선
    2. 자바 객체가 특정 interface를 구현하는 경우 빈을 생성할 때 예외 처리하도록 Bean 컨테이너 개선
    3. Bean으로 등록할 구현체를 바로 Bean으로 등록하는 것이 아니라, getObject() 메소드를 통해 반환되는 Bean으로 등록한다.
2. 재사용 가능한 Factory Bean
    1. Proxy가 추가될 때마다 FactoryBean을 생성하는 것이 아닌, 공통적으로 사용할 FactoryBean 생성
    2. Target, Advice, PointCut을 연결해 Proxy를 생성하는 재사용 가능한 FactoryBean을 추가한다.
    
### 기능 목록
1. [] Bean 컨테이너의 Bean과 Proxy를 연결한다.
    1. [x] FactoryBean의 인터페이스를 정의한다.
    2. [x] FactoryBean 구현체를 생성하고, getObject() 메서드를 구현한다.
    3. [] bean을 생성하는 위치 (DefaultBeanFactory)에서 bean을 생성시 FactoryBean을 사용하는 형태로 구현한다.
2. [] 재사용 가능한 Factory Bean
    1. [] Target, Advice, PointCut 인터페이스를 생성한다.
    2. [] 생성한 Target, Advice, PointCut을 사용해서 재사용 가능하도록 FactoryBean을 개선한다.