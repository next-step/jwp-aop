package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UppercaseAdviceTest {

    @Test
    @DisplayName("결과 값 String을 대문자로 바꾸어 반환한다")
    void uppercaseAdvice() throws Throwable {
        // given
        HelloCGLibTarget target = new HelloCGLibTarget();
        UppercaseAdvice uppercaseAdvice = UppercaseAdvice.getInstance();

        // when
        String sayHello = (String) uppercaseAdvice.invoke(target,
                target.getClass().getMethod("sayHello", String.class), new Object[]{"test"});
        String sayHi = (String) uppercaseAdvice.invoke(target,
                target.getClass().getMethod("sayHi", String.class), new Object[]{"test"});
        String sayThankYou = (String) uppercaseAdvice.invoke(target,
                target.getClass().getMethod("sayThankYou", String.class), new Object[]{"test"});

        // then
        assertAll(
                () -> assertThat(sayHello).isEqualTo("HELLO TEST"),
                () -> assertThat(sayHi).isEqualTo("HI TEST"),
                () -> assertThat(sayThankYou).isEqualTo("THANK YOU TEST")
        );
    }
}
