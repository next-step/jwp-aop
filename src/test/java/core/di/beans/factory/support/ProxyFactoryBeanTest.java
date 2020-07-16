package core.di.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.Pointcut;
import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.TargetProxyFactoryBean;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {

    @Test
    void upperProxyFactoryBean() throws Exception {
        Pointcut pointcut = (method, targetClass, args) -> method.getName().equals("echo");
        Advice advice = invocation -> invocation.proceed().toString().toUpperCase();

        Advisor uppper = new Advisor(pointcut, advice);
        ProxyFactoryBean<Echo> proxyFactoryBean = new ProxyFactoryBean(Echo.class, new Object[]{}, uppper);

        Echo echo = proxyFactoryBean.getObject();

        assertThat(echo.echo("hi")).isEqualTo("HI");
        assertThat(echo.echo2("hi")).isEqualTo("hi");
    }

    @Test
    void upperTargetProxyFactoryBean() throws Exception {
        Pointcut pointcut = (method, targetClass, args) -> method.getName().equals("echo");
        Advice advice = invocation -> invocation.proceed().toString().toUpperCase();

        Advisor uppper = new Advisor(pointcut, advice);
        TargetProxyFactoryBean<Echo> proxyFactoryBean = new TargetProxyFactoryBean(new Echo(), uppper);

        Echo echo = proxyFactoryBean.getObject();

        assertThat(echo.echo("hi")).isEqualTo("HI");
        assertThat(echo.echo2("hi")).isEqualTo("hi");
    }
}
