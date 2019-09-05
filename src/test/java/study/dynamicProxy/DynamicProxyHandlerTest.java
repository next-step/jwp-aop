package study.dynamicProxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyHandlerTest {

    @Test
    void jdkDynamicProxyTest() {
        Hello helloProxyInstance = (Hello) Proxy.newProxyInstance(DynamicProxyHandlerTest.class.getClassLoader()
                , new Class[]{Hello.class}, new DynamicProxyHandler(new HelloTarget(), (method, targetClass, args) -> method.getName().startsWith("say")));

        assertThat(helloProxyInstance.sayHello("changjun")).isEqualTo("HELLO CHANGJUN");
        assertThat(helloProxyInstance.sayHi("changjun")).isEqualTo("HI CHANGJUN");
        assertThat(helloProxyInstance.sayThankYou("changjun")).isEqualTo("THANK YOU CHANGJUN");
        assertThat(helloProxyInstance.pingpong("changjun")).isEqualTo("Pong changjun");
    }
}
