package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.Hello;
import study.proxy.HelloService;
import study.proxy.HelloTarget;

class AopProxyFactoryTest {

    private final Advice advice = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
    private final PointcutAdvisor advisor = new PointcutAdvisor(advice, SayMethodPointcut.getInstance());


    @DisplayName("인터페이스의 구현 객체를 전달하면 JdkDynamicAopProxy 객체를 생성한다")
    @Test
    void jdkDynamicAopProxy() {
        //given
        final Hello helloTarget = new HelloTarget();

        //when
        final AopProxy actual = AopProxyFactory.of(helloTarget, advisor);

        //then
        assertThat(actual).isInstanceOf(JdkDynamicAopProxy.class);

    }

    @DisplayName("콘트리트 객체를 전달하면 CGLibAopProxy 객체를 생성한다")
    @Test
    void cglibAopProxy() {
        //given
        final HelloService helloTarget = new HelloService();

        //when
        final AopProxy actual = AopProxyFactory.of(helloTarget, advisor);

        //then
        assertThat(actual).isInstanceOf(CglibAopProxy.class);
    }

}
