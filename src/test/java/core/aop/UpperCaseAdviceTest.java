package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UpperCaseAdviceTest {
    @Test
    @DisplayName("타겟 클래스의 메서드 중 String 반환 값을 모두 대문자로 반환한다.")
    void findSayPrefixMethod() throws NoSuchMethodException {
        UpperCaseAdvice upperCaseAdvice = UpperCaseAdvice.getInstance();
        HelloTarget helloTarget = new HelloTarget();

        Method sayHelloMethod = HelloTarget.class.getMethod("sayHello", String.class);
        Method sayHiMethod = HelloTarget.class.getMethod("sayHi", String.class);
        Method sayThankYouMethod = HelloTarget.class.getMethod("sayThankYou", String.class);
        Method pingPongMethod = HelloTarget.class.getMethod("pingPong", String.class);

        assertAll(
                () -> assertThat(upperCaseAdvice.invoke(helloTarget, sayHelloMethod, new Object[]{"wu2ee"})).isEqualTo("HELLO WU2EE"),
                () -> assertThat(upperCaseAdvice.invoke(helloTarget, sayHiMethod, new Object[]{"wu2ee"})).isEqualTo("HI WU2EE"),
                () -> assertThat(upperCaseAdvice.invoke(helloTarget, sayThankYouMethod, new Object[]{"wu2ee"})).isEqualTo("THANK YOU WU2EE"),
                () -> assertThat(upperCaseAdvice.invoke(helloTarget, pingPongMethod, new Object[]{"wu2ee"})).isEqualTo("PONG WU2EE")
        );
    }
}