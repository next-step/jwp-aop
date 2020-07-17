package core.aop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {

    @Test
    void proxyFactoryBean() throws Exception {
        Pointcut pointcut = (method, targetClass, args) -> method.getName().equals("echo");
        Advice add1 = invocation -> invocation.proceed().toString() + "1";
        Advice add2 = invocation -> invocation.proceed().toString() + "2";

        ProxyFactoryBean<Echo> proxyFactoryBean = new ProxyFactoryBean(new Echo()
            , Arrays.asList(
            new Advisor() {
                @Override
                public Advice getAdvice() {
                    return add1;
                }

                @Override
                public Pointcut getPointcut() {
                    return pointcut;
                }
            }, new Advisor() {
                @Override
                public Advice getAdvice() {
                    return add2;
                }

                @Override
                public Pointcut getPointcut() {
                    return pointcut;
                }
            }));

        Echo echo = proxyFactoryBean.getObject();

        assertThat(echo.echo("hi")).isEqualTo("hi21");
        assertThat(echo.echo2("hi")).isEqualTo("hi");
    }
}
