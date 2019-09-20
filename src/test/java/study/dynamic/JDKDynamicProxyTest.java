package study.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.HelloTarget;
import study.PrefixSayMethodMatcher;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static study.HelloTarget.*;
import static study.PrefixSayMethodMatcher.PREFIX_METHOD;

public class JDKDynamicProxyTest {

    private static final Logger log = LoggerFactory.getLogger(JDKDynamicProxyTest.class);

    private Hello proxyHello;
    private static final String REQUEST_PARAMETER = "Juyoung";

    @DisplayName("hello proxy 생성")
    @BeforeEach
    void setUp() {
        proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget(), new PrefixSayMethodMatcher()));
        log.debug("Create proxy!");
    }

    @DisplayName(PREFIX_METHOD + "로 시작하는 메서드 호출 시 결과 값을 대문자로 반환한다")
    @Test
    void toUpperCaseWhenRequestMethodStartWithSay() {
        assertThat(proxyHello.sayHello(REQUEST_PARAMETER)).isEqualTo(formatToUpperCase(TEXT_OF_HELLO));
        assertThat(proxyHello.sayHi(REQUEST_PARAMETER)).isEqualTo(formatToUpperCase(TEXT_OF_HI));
        assertThat(proxyHello.sayThankYou(REQUEST_PARAMETER)).isEqualTo(formatToUpperCase(TEXT_OF_THANK_YOU));
    }

    @DisplayName("pingpong 메서드 호출 시 결과 값을 target 그대로 반환한다")
    @Test
    void pingpong() {
        assertThat(proxyHello.pingpong(REQUEST_PARAMETER)).isEqualTo(format(TEXT_OF_PINGPONG));
    }

    private String format(String expectedText) {
        return String.format(expectedText, REQUEST_PARAMETER);
    }

    private String formatToUpperCase(String expectedText) {
        return format(expectedText).toUpperCase();
    }
}
