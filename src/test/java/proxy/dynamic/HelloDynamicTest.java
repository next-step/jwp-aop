package proxy.dynamic;

import org.junit.jupiter.api.Test;
import proxy.MethodMatcher;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloDynamicTest {

    private MethodMatcher methodMatcher = (method, clazz, args) -> method.getName().startsWith("say") && clazz == String.class;

    @Test
    void jdkDynamicProxy() {
        final Hello hello = (Hello) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Hello.class}, new HelloInvocationHandler(new HelloTarget(), methodMatcher));
        assertThat(hello.sayHello("jun")).isEqualTo("HELLO JUN");
        assertThat(hello.sayHi("jun")).isEqualTo("HI JUN");
        assertThat(hello.sayThankYou("jun")).isEqualTo("THANK YOU JUN");
        assertThat(hello.pingpong("jun")).isEqualTo("Pong jun");
    }

}
