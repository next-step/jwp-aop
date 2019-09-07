package study.proxy.jdkdynamic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HelloJdkDynamicProxyTest {

    @Test
    void toUpperProxy() {
        HelloTarget target = new HelloTarget();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                HelloJdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new ToUpperJdkDynamicProxy(target));
        String name = "J";

        assertAll("says",
                () -> assertThat(proxyInstance.sayHello(name)).isEqualTo(target.sayHello(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayHi(name)).isEqualTo(target.sayHi(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(target.sayThankYou(name).toUpperCase())
        );
    }
}
