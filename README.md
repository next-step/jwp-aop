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
- [ ] HelloTarget 클래스에 say로 시작하는 메서드 이외에 pingpong 메서드 추가
  - [ ] say 로 시작하는 메서드에 한해서만 메서드의 반환 값을 대문자로 변환한다.
  - [ ] JDK Dynamic Proxy, CGLib Proxy 둘 다 적용 되어야 한다.
