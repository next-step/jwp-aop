package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DynamicProxyTest {
    private static final Logger log = LoggerFactory.getLogger(DynamicProxyTest.class);

    @Test
    @DisplayName("Hello 인터페이스에 Java Dynamic Proxy를 적용해본다.")
    void name() {
        String name = "dave";
        Hello hello = getHelloProxy();

        assertThat(hello.sayHello(name)).isEqualTo("HELLO DAVE");
        assertThat(hello.sayHi(name)).isEqualTo("HI DAVE");
        assertThat(hello.sayThankYou(name)).isEqualTo("THANK YOU DAVE");
    }

    private Hello getHelloProxy() {
        return (Hello) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{Hello.class},
                getInvocationHandler());
    }

    private InvocationHandler getInvocationHandler() {
        return (proxy, method, args) -> {
            log.debug("before method invoke");

            String result = (String) method.invoke(new HelloTarget(), args);
            String upperCaseResult = result.toUpperCase();

            log.debug("after to upper case , result : {}", upperCaseResult);
            return upperCaseResult;
        };
    }
}
