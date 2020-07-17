package core.di.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.Pointcut;
import core.aop.ProxyFactoryBean;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {

    @Test
    void upperTargetProxyFactoryBean() throws Exception {
        Pointcut pointcut = (method, targetClass, args) -> method.getName().equals("echo");
        Advice add1 = invocation -> invocation.proceed().toString()+"1";
        Advice add2 = invocation -> invocation.proceed().toString().toUpperCase()+"2";


        ProxyFactoryBean<Echo> proxyFactoryBean = new ProxyFactoryBean(new Echo(), Arrays.asList(new Advisor(pointcut, add1), new Advisor(pointcut, add2)));

        Echo echo = proxyFactoryBean.getObject();

        assertThat(echo.echo("hi")).isEqualTo("hi21");
        assertThat(echo.echo2("hi")).isEqualTo("hi");
    }
}
