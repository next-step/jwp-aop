# AOP 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

### 미션 요구사항
#### Step1
* JDK Dynamic Proxy를 사용하여 Hello 인터페이스 구현클래스의 모든 메소드의 반환 값을 대문자로 변환
* CGLib Proxy를 사용하여 Hello 인터페이스 구현클래스의 모든 메소드의 반환 값을 대문자로 변환
* Hello 인터페이스 구현클래스에 pingpong()과 같은 새로운 메소드가 추가 될 경우 say로 시작하는 메소드에 한해서만 메소드의 반환 값을 대문자로 변환
  * JDK Dynamic Proxy를 사용
  * CGLib Proxy를 사용
#### Step2
* Bean 컨테이너의 Bean과 Proxy를 연결
* 재사용 가능한 FactoryBean을 생성
