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