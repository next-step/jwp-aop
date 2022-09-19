package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import core.di.aop.exception.ProxyGenerateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.Hello;
import study.proxy.HelloTarget;

class JdkDynamicAopProxyTest {

    @DisplayName("Jdk Dynamic Proxy 생성")
    @Test
    void create_jdk_dynamic_proxy() {
        //given
        final Hello helloTarget = new HelloTarget();
        final Advice2 advice2 = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
        final PointcutAdvisor advisor = new PointcutAdvisor(advice2, SayMethodPointcut.getInstance());
        final JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(helloTarget, Hello.class, advisor);

        //when
        final Hello proxy = (Hello) jdkDynamicAopProxy.getProxy();
        final String actual = proxy.sayHello("yongju");

        //then
        Assertions.assertAll(
            () -> assertThat(proxy.getClass().toString()).contains("$Proxy"),
            () -> assertThat(actual).contains("HELLO YONGJU")
        );
    }

    @DisplayName("프록시 대상이 없으면 객체 생성 시 예외가 발생한다")
    @Test
    void must_have_target() {
        final Advice2 advice2 = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
        final PointcutAdvisor advisor = new PointcutAdvisor(advice2, SayMethodPointcut.getInstance());

        assertThatThrownBy(() -> new JdkDynamicAopProxy(null, Hello.class, advisor))
            .isInstanceOf(ProxyGenerateException.class)
            .hasMessage("프록시 대상이 없어 프록시 객체를 생성할 수 없습니다.");
    }

    @DisplayName("프록시 대상의 타입이 없으면 객체 생성 시 예외가 발생한다")
    @Test
    void must_have_super_type() {
        final Hello helloTarget = new HelloTarget();
        final Advice2 advice2 = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
        final PointcutAdvisor advisor = new PointcutAdvisor(advice2, SayMethodPointcut.getInstance());

        assertThatThrownBy(() -> new JdkDynamicAopProxy(helloTarget, null, advisor))
            .isInstanceOf(ProxyGenerateException.class)
            .hasMessage("프록시 대상의 타입이 없어 프록시 객체를 생성할 수 없습니다.");
    }

    @DisplayName("advisor가 없으면 객체 생성 시 예외가 발생한다")
    @Test
    void must_have_advisor() {
        final Hello helloTarget = new HelloTarget();
        assertThatThrownBy(() -> new JdkDynamicAopProxy(helloTarget, Hello.class, null))
            .isInstanceOf(ProxyGenerateException.class)
            .hasMessage("Advisor가 없어 프록시 객체를 생성할 수 없습니다.");
    }

}
