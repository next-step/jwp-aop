package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloService;
import study.proxy.HelloTarget;

class ProxyFactoryBeanTest {

    @DisplayName("concrete class 객체를 주입하여 프록시 객체를 생성한다")
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

}
