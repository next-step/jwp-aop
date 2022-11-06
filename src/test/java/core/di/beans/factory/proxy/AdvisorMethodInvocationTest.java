package core.di.beans.factory.proxy;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.cglib.mission1.Hello;
import study.cglib.mission1.HelloTarget;

class AdvisorMethodInvocationTest {

    private Advisor advisor;
    private HelloTarget helloTarget;

    @BeforeEach
    void setUp() {
        Advice advice = joinPoint -> ((String)(joinPoint.proceed())).toUpperCase();
        Pointcut pointcut = (targetClass, method) -> method.getName()
            .startsWith("sayHello");

        advisor = new Advisor(advice, pointcut);
        helloTarget = new HelloTarget();
    }

    @DisplayName("포인트컷을 만족하면 Advice가 적용된다")
    @Test
    void proceed() {
        Method sayHelloMethod = Arrays.stream(Hello.class.getDeclaredMethods())
            .filter(it -> it.getName().equals("sayHello"))
            .findAny()
            .get();

        var advisorMethodInvocation = new AdvisorMethodInvocation(
            advisor,
            helloTarget.getClass(),
            sayHelloMethod,
            () -> {
                try {
                    return sayHelloMethod.invoke(helloTarget, "hongbin");
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                    return null;
                }
            }
        );

        var actual = advisorMethodInvocation.proceed();

        assertThat(actual).isEqualTo("HELLO HONGBIN");
    }

    @DisplayName("포인트컷을 만족하지않으면 기존 method가 호출된다")
    @Test
    void proceed2() {
        Method sayThankYou = Arrays.stream(Hello.class.getDeclaredMethods())
            .filter(it -> it.getName().equals("sayThankYou"))
            .findAny()
            .get();

        var advisorMethodInvocation = new AdvisorMethodInvocation(
            advisor,
            helloTarget.getClass(),
            sayThankYou,
            () -> {
                try {
                    return sayThankYou.invoke(helloTarget, "hongbin");
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                    return null;
                }
            }
        );

        var actual = advisorMethodInvocation.proceed();

        assertThat(actual).isEqualTo("Thank You hongbin");
    }
}