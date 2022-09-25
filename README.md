# AOP 구현

## 1단계 요구사항
- [x] Java Dynamic Proxy 적용
- [x] CGLib Proxy 적용
- [x] 부가기능이 추가될 Method 선정을 위한 MethodMatcher 적용

## 3단계 요구사항
- [ ] DB Transaction 처리를 하고 싶은 메소드에 @Transactional 애노테이션을 추가하면 Transaction 처리가 가능하도록 해야 한다.
- [ ] 다음 예제 코드와 같이 addAnswer() 메소드에 Transactional을 설정하면 addAnswer() 메소드는 하나의 Transaction으로 묶여서 처리되어야 한다.

---

## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
