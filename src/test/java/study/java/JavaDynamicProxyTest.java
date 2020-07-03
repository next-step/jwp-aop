package study.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.Hello;
import study.HelloTarget;
import study.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class JavaDynamicProxyTest {

    @Test
    public void toUpperCaseTest() {
        Hello hello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseInvocationHandler(new HelloTarget()));

        testSayMethod(hello);
    }

    @Test
    @DisplayName("say로 시작하는 메서드의 한해서만 반환값을 대문자로 변환")
    public void toUpperCaseTest2() {
        Hello hello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseInvocationHandler(new HelloTarget(), method -> method.getName().startsWith("say")));

        testSayMethod(hello);
        assertThat(hello.pingpong()).isEqualTo("pingpong");
    }

    private void testSayMethod(Hello hello) {
        assertThat(hello.sayHello("kingcjy")).isEqualTo("HELLO KINGCJY");
        assertThat(hello.sayHi("kingcjy")).isEqualTo("HI KINGCJY");
        assertThat(hello.sayThankYou("kingcjy")).isEqualTo("THANK YOU KINGCJY");
    }

    static class UppercaseInvocationHandler implements InvocationHandler {

        private final Object target;
        private final MethodMatcher methodMatcher;

        public UppercaseInvocationHandler(Object target) {
            this(target, method -> true);
        }

        public UppercaseInvocationHandler(Object target, MethodMatcher methodMatcher) {
            this.target = target;
            this.methodMatcher = methodMatcher;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object returnValue = method.invoke(target, args);

            if(returnValue instanceof String && methodMatcher.matches(method)) {
                return String.valueOf(returnValue).toUpperCase();
            }

            return returnValue;
        }
    }
}
