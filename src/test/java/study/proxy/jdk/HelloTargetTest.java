package study.proxy.jdk;

import org.junit.jupiter.api.Test;
import study.proxy.Hello;
import study.proxy.MethodMatcher;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTargetTest {

    @Test
    void toUppercase() {
        MethodMatcher methodMatcher = (method, targetClass, args) -> method.getName().startsWith("say");

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class},
                new HelloHandler(new HelloTarget(), methodMatcher)
        );

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

}