package study.dynamicproxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProxyTest {

    @Test
    void get_upper_case_by_proxy() {
        final HelloTarget helloTarget = new HelloTarget();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(helloTarget));

        final String sayHello = proxyInstance.sayHello("jiwon");
        final String sayHi = proxyInstance.sayHi("jiwon");
        final String sayThankYou = proxyInstance.sayThankYou("jiwon");

        assertEquals("HELLO JIWON", sayHello);
        assertEquals("HI JIWON", sayHi);
        assertEquals("THANK YOU JIWON", sayThankYou);
    }
}
