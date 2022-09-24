package study.cglib;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.MethodMatcher;

public class UpperProxyTest {
    @Test
    @DisplayName("메서드 반환 값 대문자 변환")
    public void toUpperCase() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UpperMethodInterceptor((method) -> method.getName().startsWith("say")));
        Object obj = enhancer.create();
        HelloTarget proxyInstance = (HelloTarget) obj;

        assertThat(proxyInstance.sayHello("dhlee")).isEqualTo("HELLO DHLEE");
        assertThat(proxyInstance.sayHi("dhlee")).isEqualTo("HI DHLEE");
        assertThat(proxyInstance.sayThankYou("dhlee")).isEqualTo("THANK YOU DHLEE");
        assertThat(proxyInstance.pingpong("dhlee")).isEqualTo("Pong dhlee");
    }

    private class UpperMethodInterceptor implements MethodInterceptor {

        private final MethodMatcher methodMatcher;

        public UpperMethodInterceptor(MethodMatcher methodMatcher) {
            this.methodMatcher = methodMatcher;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object returnValue = proxy.invokeSuper(obj, args);
            if (methodMatcher.matches(method)) {
                return returnValue.toString().toUpperCase();
            }
            return returnValue;
        }
    }
}
