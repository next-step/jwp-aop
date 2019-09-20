# AOP 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


---
### 요구사항 정리 
1. 1단계 : JDK Proxy와 CGLib Proxy
- [x] 인터페이스와 구현 클래스가 있을 때 모든 메소드의 반환 값을 대문자로 변환한다.

2. 2단계 : CGLib Proxy 적용
- [x] 앞의 Hello 예제에서 Hello 인터페이스가 없고 구현체 밖에 없다. 이에 대한 Proxy를 생성해 대문자로 반환하도록 한다.

3. 3단계 : say로 시작하는 메소드에 한해서만 메소드의 반환 값을 대문자로 변환해야한다. 이 외 target 결과값 그대로 반환.
- [x] HelloTarget pingpong method 추가
(공통) method say로 시작 시 결과값 대문자로 반환, 이외 target 결과값 그대로 반환 
- [x] JDKDynamicProxy 구현
- [x] CGLib proxy 구현 

---
Proxy 
- JDK Dynamic Proxy 
Java Proxy는 인터페이스를 반드시 구현 필요 
(리플렉션을 이용하여 구현한 기술로 퍼포먼스가 상대적으로 떨어진다 )


#### CGLib (Code Generator Library) 
> 런타임에 동적으로 자바 클래스 프록시를 생성해주는 기능 제공 
CGLIib는 프록시 객체를 쉽게 생성할 수 있으며, 성능도 JDK Dynamic Proxy에 비해 좋다. 
Interface가 아닌 클래스에서도 동적 프록시를 생성할 수 있기 때문에 다양한 프로젝트에 사용된다.
(Hibernate, Spring AOP)

##### 사용 방법
- Enhancer를 사용하여 프록시 객체 생성 및 MethodInterceptor 사용
  - net.sf.cglib.proxy.Enhancer 클래스를 사용하여 원하는 프록시 객체 만들기
  - net.sf.cglib.proxy.Callback을 사용하여 프록시 객체 조작하기

MethodInterceptor : 프록시와 원본 객체 사이에 위치해 메소드 호출 조작 가능
```
---> proxy ---> method interceptor ---> origin object
request --> 
```
- Object interceptor(Object obj, Method method, Object[] args, MethodProxy methodProxy)
    - object : 원본 객체
    - method : 원본 객체의 호출될 메소드를 나타내는 method 객체
    - args : 원본 객체에 전달 될 파라미터 
    - methodProxy : CGLIB 제공하는 원본 객체의 프록시 


 
 