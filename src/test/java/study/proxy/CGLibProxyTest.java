package study.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CGLibProxy 학습 테스트")
public class CGLibProxyTest {
    /*
        Enhancer: 원하는 프록시 객체를 생성할 수 있음
        Callback: 프록시 객체 조작
     */
    private Enhancer enhancer;

    @BeforeEach
    void setEnv() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTargetConcrete.class); // 프록시의 타겟?
    }

    @Test
    @DisplayName("인터페이스가 아니라 구현체만 있더라도 프록시를 생성할 수 있다")
    void proxy() {
        enhancer.setCallback(new UppercaseInterceptor(new MethodNameStartWithMatcher("say")));

        HelloTargetConcrete hello = (HelloTargetConcrete) enhancer.create();

        assertThat(hello.sayHello("nokchax")).isEqualTo("HELLO NOKCHAX");
        assertThat(hello.sayHi("nokchax")).isEqualTo("HI NOKCHAX");
        assertThat(hello.sayThankYou("nokchax")).isEqualTo("THANK YOU NOKCHAX");
        assertThat(hello.pingpong("nokchax")).isEqualTo("Pong nokchax");
    }

    private class UppercaseInterceptor implements MethodInterceptor {
        private final MethodMatcher methodMatcher;

        public UppercaseInterceptor(MethodMatcher methodMatcher) {
            this.methodMatcher = methodMatcher;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (!methodMatcher.matches(method, obj.getClass(), args)) {
                return proxy.invokeSuper(obj, args);
            }

            return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
        }
    }
}
