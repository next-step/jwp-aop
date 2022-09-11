package study.aop;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HelloTargetProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(HelloTargetProxyTest.class);

    @Test
    void printTest() {
        Hello hello = new HelloTarget();

        assertThat(hello.sayHello("Name")).isEqualTo("Hello Name");
        assertThat(hello.sayHi("Name")).isEqualTo("Hi Name");
        assertThat(hello.sayThankYou("Name")).isEqualTo("Thank You Name");
    }

    @Test
    void jdkProxyTest() {
        Hello hello = (Hello) Proxy.newProxyInstance(
            HelloTargetProxyTest.class.getClassLoader(),
            new Class[]{Hello.class}, new DynamicInvocationHandler(new HelloTarget())
        );

        assertThat(hello.sayHello("Name")).isEqualTo("HELLO NAME");
        assertThat(hello.sayHi("Name")).isEqualTo("HI NAME");
        assertThat(hello.sayThankYou("Name")).isEqualTo("THANK YOU NAME");
    }

    @Test
    void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloTargetCglibProxyTest());

        Hello hello = (HelloTarget) enhancer.create();

        assertThat(hello.sayHello("Name")).isEqualTo("HELLO NAME");
        assertThat(hello.sayHi("Name")).isEqualTo("HI NAME");
        assertThat(hello.sayThankYou("Name")).isEqualTo("THANK YOU NAME");
    }

}
