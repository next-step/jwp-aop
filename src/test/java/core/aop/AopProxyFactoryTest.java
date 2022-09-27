package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;
import study.proxy.jdkdynamic.HelloTargetImpl;

import static org.assertj.core.api.Assertions.assertThat;

class AopProxyFactoryTest {
    @Test
    @DisplayName("타겟 클래스의 인터페이스가 있는 경우, JdkDynamicProxy 를 사용한다.")
    void useJdkDynamicProxy() {
        AopProxyFactory aopProxyFactory = AopProxyFactory.getInstance();
        Advisor advisor = new Advisor(UpperCaseAdvice.getInstance(), SayPrefixPointCut.getInstance());
        HelloTargetImpl helloTarget = new HelloTargetImpl();

        AopProxy aopProxy = aopProxyFactory.createAopProxy(helloTarget, advisor);
        assertThat(aopProxy)
                .isInstanceOf(JdkDynamicAopProxy.class)
                .isNotInstanceOf(CglibAopProxy.class);

    }

    @Test
    @DisplayName("타겟 클래스의 인터페이스가 없는 경우, CglibProxy 를 사용한다.")
    void useCglibProxy() {
        AopProxyFactory aopProxyFactory = AopProxyFactory.getInstance();
        Advisor advisor = new Advisor(UpperCaseAdvice.getInstance(), SayPrefixPointCut.getInstance());
        HelloTarget helloTarget = new HelloTarget();

        AopProxy aopProxy = aopProxyFactory.createAopProxy(helloTarget, advisor);
        assertThat(aopProxy)
                .isNotInstanceOf(JdkDynamicAopProxy.class)
                .isInstanceOf(CglibAopProxy.class);
    }
}
