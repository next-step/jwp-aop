package study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;
import study.proxy.MethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTargetTest {

    @Test
    void toUppercase() {
        MethodMatcher methodMatcher = (method, targetClass, args) -> method.getName().startsWith("say");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloTargetInterceptor(methodMatcher));

        HelloTarget proxiedHello = (HelloTarget) enhancer.create();

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

}