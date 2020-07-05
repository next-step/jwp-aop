# Aspect OP

## 2단계 - Proxy와 Bean 의존관계

문제는 요구사항 조차도 이해가 잘 안된다는 것이다..  
FactoryBean ?? di에서 BeanFactory한거 같은데.. 데자뷰인가.  

일단 스프링 FactoryBean을 따로 사용해보자..  

요구사항 정리

- 지금까지 Bean 컨테이너가 Bean을 생성하는 방법은 생성자를 활용해 생성했다. 그런데 Proxy는 생성자를 활용해 생성하는 것이 아니라 훨씬 더 복잡한 과정을 거쳐야 한다.
    - BeanFactory에서 Proxy를 통해 생성되는 Bean을 따로 처리하라는 의미 인듯..?
- Bean 컨테이너의 Bean과 Proxy를 연결하도록 Bean 컨테이너를 개선해야 한다.
    - 위의 내용과 결이 비슷
- Proxy가 추가될 때마다 FactoryBean을 매번 생성하는 것도 귀찮다. 공통적으로 사용할 수 있는 FactoryBean이 있으면 좋겠다.
    - Spring ProxyFactoryBean 학습 필요.  
- Target, Advice, PointCut을 연결해 Proxy를 생성하는 재사용 가능한 FactoryBean을 추가한다.
    - Target: 프록시 대상? 추가적으로 기능이 붙을 친구.  
        - 스프링에서 `@Scope`로 ProxyMode 지정하면 해당 annotated 된 클래스가 target
    - advice: 부가 기능
    - pointcut: advice가 적용될 target 지정

계획 (~~누구나 그럴싸한 계획을 갖고 있다.. 매우 맞기 전까지는~~) 

엌ㅋㅋㅋㅋ 일단 자고 일어나자.. 