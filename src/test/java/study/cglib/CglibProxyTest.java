package study.cglib;

import net.sf.cglib.proxy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.Hello;
import study.HelloTarget;
import study.MethodMatcher;

import java.awt.*;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibProxyTest {

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
    }

    @Test
    @DisplayName("반환 값을 같도록 구현하는 Proxy")
    void same_return() {
        enhancer.setCallback((FixedValue) () -> "Hello JavaJiGi!");
        PersonService proxy = (PersonService) enhancer.create();

        String res = proxy.sayHello(null);

        assertThat(res).isEqualTo("Hello JavaJiGi!");
    }

    @Test
    @DisplayName("메소드 signature에 따라 다르게 동작하는 Proxy")
    void method_signature() {
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                return "Hello JavaJiGi!";
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });

        PersonService proxy = (PersonService) enhancer.create();

        assertThat(proxy.sayHello(null)).isEqualTo("Hello JavaJiGi!");
        assertThat(proxy.lengthOfName("SanJiGi")).isEqualTo(7);
    }

    @Test
    public void CGLIBToUpperCaseTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Object returnValue = proxy.invokeSuper(obj, args);
            return returnValue instanceof String ? String.valueOf(returnValue).toUpperCase() : returnValue;
        });

        HelloTarget hello = (HelloTarget) enhancer.create();
        System.out.println(hello.sayHello("kingcjy"));

        testSayMethod(hello);
    }

    @Test
    @DisplayName("say로 시작하는 메서드의 한해서만 반환값을 대문자로 변환")
    public void CGLIBToUpperCaseTestWithMethodName() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);

        enhancer.setCallback(new UppercaseMethodInterceptor(method -> method.getName().startsWith("say") && String.class.equals(method.getReturnType())));

        HelloTarget hello = (HelloTarget) enhancer.create();
        System.out.println(hello.sayHello("kingcjy"));

        testSayMethod(hello);
        assertThat(hello.pingpong()).isEqualTo("pingpong");
    }

    private void testSayMethod(Hello hello) {
        assertThat(hello.sayHello("kingcjy")).isEqualTo("HELLO KINGCJY");
        assertThat(hello.sayHi("kingcjy")).isEqualTo("HI KINGCJY");
        assertThat(hello.sayThankYou("kingcjy")).isEqualTo("THANK YOU KINGCJY");
    }

    static class UppercaseMethodInterceptor implements MethodInterceptor {
        private final MethodMatcher methodMatcher;

        public UppercaseMethodInterceptor(MethodMatcher methodMatcher) {
            this.methodMatcher = methodMatcher;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object returnValue = proxy.invokeSuper(obj, args);

            if(methodMatcher.matches(method)) {
                return String.valueOf(returnValue).toUpperCase();
            }

            return returnValue;
        }
    }
}
