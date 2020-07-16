package core.aop;

import static org.assertj.core.api.Assertions.assertThat;

import core.aop.support.Echo;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {

    @Test
    void uppperProxyFactoryBean() throws Exception {
        Pointcut pointcut = (method, targetClass, args) -> method.getName().equals("echo");
        Advice advice = invocation -> invocation.proceed().toString().toUpperCase();

        Advisor uppper = new Advisor(pointcut, advice);
        ProxyFactoryBean<Echo> proxyFactoryBean = new ProxyFactoryBean(Echo.class, new Object[]{}, uppper);

        Echo echo = proxyFactoryBean.getObject();

        assertThat(echo.echo("hi")).isEqualTo("HI");
    }

}
