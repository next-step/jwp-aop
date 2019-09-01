package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import proxy.MethodMatcher;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloCglibTest {

    @Test
    void cglib() {
        HelloTarget helloTarget = getProxyHelloTarget();
        assertThat(helloTarget.sayHello("jun")).isEqualTo("HELLO JUN");
        assertThat(helloTarget.sayHi("jun")).isEqualTo("HI JUN");
        assertThat(helloTarget.sayThankYou("jun")).isEqualTo("THANK YOU JUN");
        assertThat(helloTarget.pingpong("jun")).isEqualTo("Pong jun");
    }

    private HelloTarget getProxyHelloTarget() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UpperStringInterceptor());
        return (HelloTarget) enhancer.create();
    }

    private static class UpperStringInterceptor implements MethodInterceptor, MethodMatcher {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (matches(method, method.getReturnType(), args)) {
                return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
            }

            return proxy.invokeSuper(obj, args);
        }

        @Override
        public boolean matches(Method method, Class clazz, Object[] args) {
            return method.getName().startsWith("say") && clazz == String.class;
        }
    }

}
