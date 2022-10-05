package aop;

import core.aop.test.DynamicInvocationHandler;
import core.aop.test.Hello;
import core.aop.test.HelloTarget;
import core.aop.test.PrefixSayMatcher;
import net.sf.cglib.proxy.Proxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JdkProxyTest {

    @Test
    void jdk_upper_test() {
        HelloTarget helloTarget = new HelloTarget();

        Hello targetProxy = (Hello) Proxy.newProxyInstance(JdkProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(helloTarget, new PrefixSayMatcher()));

        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.sayHi("java")).isEqualTo("HI JAVA"),
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.sayHello("java")).isEqualTo("HELLO JAVA"),
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.sayHello("java")).isEqualTo("HELLO JAVA"),
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.pinpong("java")).isEqualTo("pingpong java")
        );
    }
}
