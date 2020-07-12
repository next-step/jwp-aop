package study.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import study.MethodMatcher;

public class DynamicProxyTest {

    @Test
    void toUppercase(){
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{Hello.class},
            new InvocationHandler() {

                Hello hello = new HelloTarget();
                MethodMatcher methodMatcher = (method, targetClass, args) -> method.getName().startsWith("say");

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Object ret = method.invoke(hello, args);
                    if(methodMatcher.matches(method, HelloTarget.class, args)){
                        return ret.toString().toUpperCase();
                    }
                    return ret;
                }
            });

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");

    }

}
