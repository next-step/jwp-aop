# AOP 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


## 미션1. 요구사항 

- 요구사항 - Java Dynamic Proxy 적용
  - 모든 메소드의 반환 값을 대문자로 변환해야한다.
- 요구사항 - CGLib Proxy 적용
  - 앞의 Hello 예제에서 Hello 인터페이스가 없고 구현체 밖에 없다. 이에 대한 Proxy를 생성해 대문자로 반환하도록 한다.
- 요구사항 3
  - HelloTarget에 say로 시작하는 메소드 이외에 pingpong()과 같은 새로운 메소드가 추가됐다.
    say로 시작하는 메소드에 한해서만 메소드의 반환 값을 대문자로 변환해야한다. 가 동작하도록 한다.
    JDK Dynamic Proxy와 CGLib Proxy 모두 동작하도록 구현한다.

## 미션3. 요구사항
- DB Transaction 처리를 하고 싶은 메소드에 @Transactional 애노테이션을 추가하면 Transaction 처리가 가능하도록 해야 한다.
- 예) addAnswer() 메소드에 Transactional을 설정하면 addAnswer() 메소드는 하나의 Transaction으로 묶여서 처리되어야 한다.
- Transaction은 각 Thread별로 Connection을 유지할 수 있어야 한다. 현재 Transaction을 시작하는 시점에 Thread에 Connection을 저정하고, 필요한 시점에 가져오기 위해 ThreadLocal을 활용한다. 앞의 힌트에서 ConnectionHolder는 ThreadLocal을 활용해 Connection을 관리할 수 있어야 한다.

## 미션4. 요구사항
- Controller, ArgumentResolver와 같은 곳에서 Exception이 발생할 경우 Exception을 처리할 수 있어야 한다.
- 현재 구현된 버전의 코드는 로그인하지 않은 사용자가 글쓰기 버튼을 클릭하면 Exception이 발생해 에러 메시지가 보인다. Exception 처리를 통해 로그인 페이지로 이동하도록 해야 한다.
- ControllerAdvice의 메소드도 HandlerExecution의 하나로 생각할 수 있다. 단, Mapping이 요청 URL 기반이 아니라 Exception 기반이로 매핑한다는 것이 다를 뿐이다.