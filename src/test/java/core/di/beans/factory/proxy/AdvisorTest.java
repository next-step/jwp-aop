package core.di.beans.factory.proxy;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import study.cglib.mission1.Hello;

class AdvisorTest {

    private Advice advice;
    private Pointcut pointcut;

    @BeforeEach
    void setUp() {
        advice = joinPoint -> ((String)(joinPoint.proceed())).toUpperCase();
        pointcut = (targetClass, method) -> method.getName()
            .startsWith("sayHello");
    }

    @DisplayName("메서드이름과 클래스가 일치하는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({"sayHello, true", "sayHi, false"})
    void matches(String methodName, boolean expected) {
        var advisor = new Advisor(advice, pointcut);
        var targetMethod = Arrays.stream(Hello.class.getDeclaredMethods())
            .filter(it -> it.getName().equals(methodName))
            .findAny()
            .get();

        var matches = advisor.matches(Hello.class, targetMethod);

        assertThat(matches).isEqualTo(expected);
    }

    @DisplayName("advice에 등록된 부가기능을 실행할 수 있다.")
    @Test
    void invoke() {
        var advisor = new Advisor(advice, pointcut);
        JoinPoint joinPoint = () -> "hi";

        var actual = advisor.invoke(joinPoint);

        assertThat(actual).isEqualTo("HI");
    }
}