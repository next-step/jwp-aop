package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SayPointcutAdvisorTest {

    @Test
    @DisplayName("say로 시작하는 메소드명일 경우 결과 값을 대문자로 바꾸어 반환한다")
    void uppercaseAdviceIfMethodNameStartWithSay() throws Throwable {
        // given
        HelloTarget target = new HelloTarget();
        Advisor advisor = new SayPointcutAdvisor(UppercaseAdvice.getInstance());

        // when
        String sayHello = methodResult("sayHello", target, advisor);
        String sayHi = methodResult("sayHi", target, advisor);
        String sayThankYou = methodResult("sayThankYou", target, advisor);
        String pingpong = methodResult("pingpong", target, advisor);

        // then
        assertAll(
                () -> assertThat(sayHello).isEqualTo("HELLO TEST"),
                () -> assertThat(sayHi).isEqualTo("HI TEST"),
                () -> assertThat(sayThankYou).isEqualTo("THANK YOU TEST"),
                () -> assertThat(pingpong).isEqualTo("ping pong test")
        );
    }

    private String methodResult(String methodName, HelloTarget target, Advisor advisor) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UppercaseAdvice uppercaseAdvice = UppercaseAdvice.getInstance();
        Pointcut pointcut = advisor.pointcut();
        Method method = method(methodName, target);

        if (pointcut.matches(method, null)) {
            return (String) uppercaseAdvice.invoke(target, method, new Object[]{"test"});
        }

        return (String) method.invoke(target, new Object[]{"test"});
    }

    private Method method(String methodName, HelloTarget target) throws NoSuchMethodException {
        return target.getClass().getMethod(methodName, String.class);
    }
}
