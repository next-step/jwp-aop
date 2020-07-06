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
 
레퍼런스

- https://www.youtube.com/watch?v=eR4mwoDj-6I
- 스프링의 FactoryBean 인터페이스 설명 (가장 명확)

> Interface to be implemented by objects used within a {@link BeanFactory} which
> are themselves factories for individual objects. If a bean implements this
> interface, it is used as a factory for an object to expose, not directly as a
> bean instance that will be exposed itself.

> 개별 객체에 대한 자기 자신에 대한 팩토리로, BeanFactory 내에서 사용되는 객체를 구현하기 위한 인터페이스.  
> 빈이 이 인터페이스를 구현하는 경우, 노출시킬 객체를 위한 팩토리로 사용되며, 빈 인스턴스 마냥 직접적으로 노출되는 친구들은 아님.

한 번에 와닿은 문장. 결국 BeanFactory는 FactoryBean의 대상이 될 Target 객체의 팩토리를 가지고 있다는 거.  

```java
ctx.getBean("target");
```

이 때 나오는 것이 FactoryBean의 implementation이 아닌, `getObject()` invoke해서 나오는 객체를 받게된다는거.  
으어어.. 이런것도 모르고 살다니  

그렇다면 빌드업해보자.  

계획

- FactoryBean 정의
- ProxyFactoryBean 구현 (프록시 구현체)
    - Advice
    - PointCut
    - Target
- ProxyBeanDefinition 정의
    - BeanFactory가 이 친구 잘 쓰도록 해주면 됨 
    
어제 새벽보단 과제 이해 정도가 나아졌다. 

