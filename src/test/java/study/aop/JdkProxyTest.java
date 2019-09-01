package study.aop;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JdkProxyTest {

    @Test
    void toUppercase() {

        UpperInvocationHandler handler = new UpperInvocationHandler(new HelloTarget());
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class},
                handler);
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

    @Test
    void toUppercase2(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget2.class);
        enhancer.setCallback(new UpperInterceptor());
        Object obj = enhancer.create();
        HelloTarget2 proxiedHello = (HelloTarget2) obj;

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

}
