package study.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CglibProxyTest {
    private static final Logger log = LoggerFactory.getLogger(DynamicProxyTest.class);

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(Hello2Target.class);
        enhancer.setCallback(getMethodInterceptor());
    }

    @Test
    @DisplayName("Hello2Target 인터페이스에 CGLib Proxy를 적용해본다.")
    void name() {
        String name = "dave";
        Hello2Target hello = (Hello2Target) enhancer.create();

        assertThat(hello.sayHello(name)).isEqualTo("HELLO DAVE");
        assertThat(hello.sayHi(name)).isEqualTo("HI DAVE");
        assertThat(hello.sayThankYou(name)).isEqualTo("THANK YOU DAVE");
    }

    private MethodInterceptor getMethodInterceptor() {
        return (obj, method, args, proxy) -> {
            log.debug("before method invoke");

            String returnValue = (String) proxy.invokeSuper(obj, args);

            String upperCaseResult = returnValue.toUpperCase();

            log.debug("after to upper case , result : {}", upperCaseResult);
            return upperCaseResult;
        };
    }
}
