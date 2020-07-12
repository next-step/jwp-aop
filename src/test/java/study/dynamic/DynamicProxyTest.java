package study.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

public class DynamicProxyTest {

    @Test
    void toUppercase(){
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{Hello.class},
            new InvocationHandler() {
                Hello hello = new HelloTarget();
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(hello, args).toString().toUpperCase();
                }
            });

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
    }

}
