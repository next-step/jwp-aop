package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.Hello;
import study.proxy.HelloService;
import study.proxy.HelloTarget;

class ProxyFactoryBeanTest {

    @DisplayName("concrete class 객체를 주입하여 CGLib 프록시 객체를 생성한다")
    @Test
    void inject_target_object_of_concrete_class() throws Exception {
        //given
        final ProxyFactoryBean<HelloService> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(new HelloService());
        proxyFactoryBean.setMethodInterceptor((obj, method, args, proxy) -> proxy.invoke(obj, args));

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

        final ProxyFactoryBean<Hello> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.setSuperInterface(Hello.class);
        proxyFactoryBean.setInvocationHandler((proxy, method, args) -> method.invoke(target, args));

        //when
        final Hello actual = proxyFactoryBean.getObject();

        //then
        assertThat(actual.getClass().toString()).contains("$Proxy");
    }

}
