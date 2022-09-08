package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class HelloMethodMatcherTest {

    private final Class<HelloTarget> targetClass = HelloTarget.class;
    private final MethodMatcher methodMatcher = new HelloMethodMatcher("say");

    @DisplayName("프록시 적용 대상 여부 확인 - 적용")
    @Test
    void matches() throws NoSuchMethodException {
        Method method = targetClass.getMethod("sayHello", String.class);
        assertThat(methodMatcher.matches(method, targetClass, null)).isTrue();
    }

    @DisplayName("프록시 적용 대상 여부 확인 - 미적용")
    @Test
    void notMatches() throws NoSuchMethodException {
        Method method = targetClass.getMethod("pingPong", String.class);
        assertThat(methodMatcher.matches(method, targetClass, null)).isFalse();
    }
}
