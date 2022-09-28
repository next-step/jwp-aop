package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import core.di.aop.exception.ProxyGenerateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloService;

class CglibAopProxyTest {

    @DisplayName("CGLib 프록시 생성")
    @Test
    void create() {
        // given
        final Advice advice = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
        final ProxyPointcutAdvisor advisor = new ProxyPointcutAdvisor(advice, SayMethodPointcut.getInstance(), null);
        final CglibAopProxy cglibAopProxy = new CglibAopProxy(HelloService.class, advisor);

        // when
        final HelloService proxy = (HelloService) cglibAopProxy.getProxy();
        final String actual = proxy.sayHello("yongju");

        // then
        Assertions.assertAll(
            () -> assertThat(proxy.getClass().toString()).contains("CGLIB$$"),
            () -> assertThat(actual).contains("HELLO YONGJU")
        );

    }

    @DisplayName("프록시 대상이 없으면 객체 생성 시 예외가 발생한다")
    @Test
    void must_have_target() {
        final Advice advice = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
        final ProxyPointcutAdvisor advisor = new ProxyPointcutAdvisor(advice, SayMethodPointcut.getInstance(), null);

        assertThatThrownBy(() -> new CglibAopProxy(null, advisor))
            .isInstanceOf(ProxyGenerateException.class)
            .hasMessage("프록시 대상이 없어 프록시 객체를 생성할 수 없습니다.");
    }

    @DisplayName("advisor가 없으면 객체 생성 시 예외가 발생한다")
    @Test
    void must_have_advisor() {
        assertThatThrownBy(() -> new CglibAopProxy(HelloService.class, null))
            .isInstanceOf(ProxyGenerateException.class)
            .hasMessage("Advisor가 없어 프록시 객체를 생성할 수 없습니다.");
    }

}
