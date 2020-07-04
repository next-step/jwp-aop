package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloCglibProxyTest {
    private String hello_expected = "HELLO EESEUL";
    private String hi_expected = "HI EESEUL";
    private String thankYou_expected = "THANK YOU EESEUL";

    @Test
    void cglibTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            String result = (String) method.invoke(new HelloTarget(), args);
            return result.toUpperCase();
        });

        HelloTarget helloTarget = (HelloTarget) enhancer.create();

        String hello_actual = helloTarget.sayHello("eeseul");
        String hi_actual = helloTarget.sayHi("eeseul");
        String thankYou_actual = helloTarget.sayThankYou("eeseul");

        assertThat(hello_actual).isEqualTo(hello_expected);
        assertThat(hi_actual).isEqualTo(hi_expected);
        assertThat(thankYou_actual).isEqualTo(thankYou_expected);
    }
}