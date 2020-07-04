package study.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkProxyTest {
    private static final Logger log = LoggerFactory.getLogger(JdkProxyTest.class);

    @DisplayName("JDK Proxy 테스트")
    @Test
    void proxy() {
        final Hello helloProxyInstance = (Hello) Proxy.newProxyInstance(
                JdkProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget())
        );
        final String greeting = "hyeyoom";
        assertThat(helloProxyInstance.sayHello(greeting))
                .isEqualTo("HELLO " + greeting.toUpperCase());
        assertThat(helloProxyInstance.sayHi(greeting))
                .isEqualTo("HI " + greeting.toUpperCase());
        assertThat(helloProxyInstance.sayThankYou(greeting))
                .isEqualTo("THANK YOU " + greeting.toUpperCase());
    }
}
