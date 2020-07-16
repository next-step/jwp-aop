package core.di.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.Pointcut;
import core.di.beans.factory.ProxyFactoryBean;
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
