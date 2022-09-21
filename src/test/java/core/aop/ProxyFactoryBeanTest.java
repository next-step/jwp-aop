package core.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import study.aop.Hello;
import study.aop.HelloTarget;

class ProxyFactoryBeanTest {

    @Test
    void advisorTest() throws Exception {
        Advice advice = new UpperCaseAdvice();
        PointCut pointCut = new MethodNameStartsWithPointCut("say");
        Advisor advisor = new Advisor(advice, pointCut);
        Hello hello = new HelloTarget();

        ProxyFactoryBean helloProxyFactoryBean = new ProxyFactoryBean(hello, Arrays.asList(advisor));

        Hello helloProxy = (Hello) helloProxyFactoryBean.getObject();

        assertNotNull(helloProxy);
        assertThat(helloProxy.sayHello("test")).isEqualTo("HELLO TEST");
        assertThat(helloProxy.sayHi("test")).isEqualTo("HI TEST");
        assertThat(helloProxy.sayThankYou("test")).isEqualTo("THANK YOU TEST");
    }

}
