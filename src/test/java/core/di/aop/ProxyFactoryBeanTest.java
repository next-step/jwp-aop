package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.Hello;
import study.proxy.HelloService;
import study.proxy.HelloTarget;

class ProxyFactoryBeanTest {

    private final Advice advice = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
    private final PointcutAdvisor advisor = new PointcutAdvisor(advice, SayMethodPointcut.getInstance());

    @DisplayName("concrete class 객체를 주입하여 CGLib 프록시 객체를 생성한다")
    @Test
    void inject_target_object_of_concrete_class() throws Exception {
        //given

        final ProxyFactoryBean<HelloService> proxyFactoryBean = new ProxyFactoryBean<>(new HelloService(), advisor);

        //when
        final HelloService actual = proxyFactoryBean.getObject();

        //then
        assertThat(actual.getClass().toString()).contains("CGLIB$$");
    }

    @DisplayName("interface 구현체 객체를 주입하여 JDK Dynamic 프록시 객체를 생성한다")
    @Test
    void inject_target_object_of_implemented_class() throws Exception {
        //given
        final Hello target = new HelloTarget();

        final ProxyFactoryBean<Hello> proxyFactoryBean = new ProxyFactoryBean<>(target, advisor);

        //when
        final Hello actual = proxyFactoryBean.getObject();

        //then
        assertThat(actual.getClass().toString()).contains("$Proxy");
    }
}
