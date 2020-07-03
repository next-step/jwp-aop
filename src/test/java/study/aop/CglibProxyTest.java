package study.aop;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibProxyTest {
    private static final Logger log = LoggerFactory.getLogger(CglibProxyTest.class);

    @DisplayName("CGLIB 학습 테스트")
    @Test
    void cglib() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloCglib.class);
        enhancer.setCallback(new HelloCglibInterceptor());

        final Object obj = enhancer.create();

        final HelloCglib helloProxyInstance = (HelloCglib) obj;
        final String hyeyoom = helloProxyInstance.sayHello("hyeyoom");
        log.debug("message: {}", hyeyoom);
        log.debug("class: {}", helloProxyInstance.getClass());

        final String greeting = "hyeyoom";
        assertThat(helloProxyInstance.sayHello(greeting))
                .isEqualTo("HELLO " + greeting.toUpperCase());
        assertThat(helloProxyInstance.sayHi(greeting))
                .isEqualTo("HI " + greeting.toUpperCase());
        assertThat(helloProxyInstance.sayThankYou(greeting))
                .isEqualTo("THANK YOU " + greeting.toUpperCase());
    }
}
