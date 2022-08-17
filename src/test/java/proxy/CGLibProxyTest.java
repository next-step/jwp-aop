package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CGLibProxyTest {
    @DisplayName("CGLib Proxy 적용")
    @Test
    void cglib() {
        String name = "dean";

        MethodInterceptor methodInterceptor = (obj, method, args, proxy) -> ((String) proxy.invokeSuper(obj, args)).toUpperCase();
        Object obj = Enhancer.create(HelloTargetCGLib.class, methodInterceptor);

        HelloTargetCGLib upperHello = (HelloTargetCGLib) obj;

        assertThat(upperHello.sayHello(name)).isEqualTo("HELLO DEAN");
        assertThat(upperHello.sayHi(name)).isEqualTo("HI DEAN");
        assertThat(upperHello.sayThankYou(name)).isEqualTo("THANK YOU DEAN");
    }
}
