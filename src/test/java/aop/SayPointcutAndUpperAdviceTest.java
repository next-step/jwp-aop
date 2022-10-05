package aop;

import core.aop.test.HelloTarget;
import core.aop.test.SayPointcut;
import core.aop.test.UpperAdvice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SayPointcutAndUpperAdviceTest {

    @Test
    @DisplayName("say로 시작하는 메서드 리턴 값을 대문자로 반환한다.")
    void pointcut_say_method() throws InvocationTargetException, IllegalAccessException {
        HelloTarget helloTarget = new HelloTarget();

        Method sayHello = getMethod("sayHello", helloTarget);

        Assertions.assertThat(methodResult(helloTarget, sayHello)).isEqualTo("HELLO JAVA");
    }

    private static Object methodResult(HelloTarget helloTarget, Method method) throws InvocationTargetException, IllegalAccessException {
        SayPointcut sayPointcut = new SayPointcut();
        UpperAdvice upperAdvice = new UpperAdvice();

        if (sayPointcut.matches(method, helloTarget.getClass())) {
            return upperAdvice.invoke(helloTarget, method, new Object[]{"java"});
        }

        return method.invoke(helloTarget, new Object[]{"java"});
    }

    private static Method getMethod(String name, HelloTarget helloTarget) {
        try {
            return helloTarget.getClass().getMethod(name, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
