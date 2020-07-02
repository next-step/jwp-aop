package study.java;

import org.junit.jupiter.api.Test;

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
        Hello hello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Hello.class}, new UppercaseInvocationHandler(new HelloTarget()));

        assertThat(hello.sayHello("kingcjy")).isEqualTo("HELLO KINGCJY");
        assertThat(hello.sayHi("kingcjy")).isEqualTo("HI KINGCJY");
        assertThat(hello.sayThankYou("kingcjy")).isEqualTo("THANK YOU KINGCJY");
    }

    class UppercaseInvocationHandler implements InvocationHandler {

        private Object target;

        public UppercaseInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object returnValue = method.invoke(target, args);
            return returnValue instanceof String ? String.valueOf(returnValue).toUpperCase() : returnValue;
        }
    }
}
