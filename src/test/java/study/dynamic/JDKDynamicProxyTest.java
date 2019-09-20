package study.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.HelloTarget;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static study.HelloTarget.TEXT_OF_HELLO;
import static study.HelloTarget.TEXT_OF_HI;
import static study.HelloTarget.TEXT_OF_THANK_YOU;

public class JDKDynamicProxyTest {

    private static final Logger log = LoggerFactory.getLogger(JDKDynamicProxyTest.class);

    private Hello proxyHello;
    private static final String NAME = "Juyoung";

    @DisplayName("hello proxy 생성")
    @BeforeEach
    void setUp() {
        proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));
        log.debug("Create proxy!");
    }

    @Test
    void initialize() {
        assertThat(proxyHello.sayHello(NAME)).isEqualTo(getFormat(TEXT_OF_HELLO));
        assertThat(proxyHello.sayHi(NAME)).isEqualTo(getFormat(TEXT_OF_HI));
        assertThat(proxyHello.sayThankYou(NAME)).isEqualTo(getFormat(TEXT_OF_THANK_YOU));
    }

    private String getFormat(String textOfHello) {
        return String.format(textOfHello, NAME).toUpperCase();
    }
}
