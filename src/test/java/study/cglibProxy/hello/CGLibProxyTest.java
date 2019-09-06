package study.cglibProxy.hello;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CGLibProxyTest {

    @Test
    void helloCglibProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloInterceptor((method, targetClass, args) -> method.getName().startsWith("say")));
        Object proxy = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxy;
        assertThat(helloTarget.sayHello("changjun")).isEqualTo("HELLO CHANGJUN");
        assertThat(helloTarget.sayHi("changjun")).isEqualTo("HI CHANGJUN");
        assertThat(helloTarget.sayThankYou("changjun")).isEqualTo("THANK YOU CHANGJUN");
        assertThat(helloTarget.pingpong("changjun")).isEqualTo("Pong changjun");
    }
}
