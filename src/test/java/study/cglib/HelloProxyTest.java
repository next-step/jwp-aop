package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.HelloTarget;
import study.PrefixSayMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static study.HelloTarget.*;
import static study.PrefixSayMethodMatcher.PREFIX_METHOD;

class HelloProxyTest {

    private Enhancer enhancer;
    private static final String REQUEST_PARAMETER = "Juyoung";

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor(new PrefixSayMethodMatcher()));
    }

    @DisplayName(PREFIX_METHOD + "로 시작하는 메서드 호출할 경우 리턴 값은 모두 대문자이다")
    @Test
    void whenStartWithSayThenToUpperCase() {
        HelloTarget proxy = (HelloTarget) enhancer.create();

        assertThat(proxy.sayHello(REQUEST_PARAMETER)).isEqualTo(formatToUpperCase(TEXT_OF_HELLO));
        assertThat(proxy.sayHi(REQUEST_PARAMETER)).isEqualTo(formatToUpperCase(TEXT_OF_HI));
        assertThat(proxy.sayThankYou(REQUEST_PARAMETER)).isEqualTo(formatToUpperCase(TEXT_OF_THANK_YOU));
    }

    @DisplayName("Hello의 pingpong 결과를 출력한다")
    @Test
    void pingpong() {
        HelloTarget proxy = (HelloTarget) enhancer.create();

        assertThat(proxy.pingpong(REQUEST_PARAMETER)).isEqualTo(format(TEXT_OF_PINGPONG));
    }

    private String format(String expectedText) {
        return String.format(expectedText, REQUEST_PARAMETER);
    }

    private String formatToUpperCase(String expectedText) {
        return format(expectedText).toUpperCase();
    }
}
