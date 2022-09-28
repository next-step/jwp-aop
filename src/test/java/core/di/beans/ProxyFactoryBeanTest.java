package core.di.beans;

import core.aop.Advisor;
import core.aop.SayPrefixPointCut;
import core.aop.UpperCaseAdvice;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProxyFactoryBeanTest {
    @Test
    @DisplayName("ProxyBeanFactory 를 생성하고, 타겟 클래스에 어드바이저를 적용한 프록시 인스턴스를 반환한다.")
    void getProxyObject() {
        Advisor advisor = new Advisor(UpperCaseAdvice.getInstance(), SayPrefixPointCut.getInstance());
        HelloTarget helloTarget = new HelloTarget();
        ProxyFactoryBean<HelloTarget> proxyFactoryBean = new ProxyFactoryBean<>(helloTarget, advisor);
        HelloTarget proxy = proxyFactoryBean.getObject();

        proxyExampleAssertTest(proxy);
    }

    @Test
    @DisplayName("프록시 빈을 등록한다.")
    void registerProxyBean() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ProxyConfig.class);
        HelloTarget proxy = context.getBean(HelloTarget.class);

        proxyExampleAssertTest(proxy);
    }

    private static void proxyExampleAssertTest(HelloTarget proxy) {
        assertAll(
                () -> assertThat(proxy.sayHello("wu2ee")).isEqualTo("HELLO WU2EE"),
                () -> assertThat(proxy.sayHi("wu2ee")).isEqualTo("HI WU2EE"),
                () -> assertThat(proxy.sayThankYou("wu2ee")).isEqualTo("THANK YOU WU2EE"),
                () -> assertThat(proxy.pingPong("wu2ee")).isEqualTo("Pong wu2ee")
        );
    }
}