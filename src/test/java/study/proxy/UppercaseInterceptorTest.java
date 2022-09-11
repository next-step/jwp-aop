package study.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UppercaseInterceptorTest {

    @DisplayName("대문자로 변환하는 CGLib Proxy")
    @Test
    void toUppercase() {
        final HelloService helloService = new HelloService();

        assertThat(helloService.sayHello("Yongju")).isEqualTo("Hello Yongju");
        assertThat(helloService.sayHi("Yongju")).isEqualTo("Hi Yongju");
        assertThat(helloService.sayThankYou("Yongju")).isEqualTo("Thank You Yongju");

        final UppercaseInterceptor uppercaseInterceptor = new UppercaseInterceptor(helloService);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloService.class);
        enhancer.setCallback(uppercaseInterceptor);

        final HelloService actual = (HelloService) enhancer.create();

        assertThat(actual.sayHello("Yongju")).isEqualTo("HELLO YONGJU");
        assertThat(actual.sayHi("Yongju")).isEqualTo("HI YONGJU");
        assertThat(actual.sayThankYou("Yongju")).isEqualTo("THANK YOU YONGJU");
        assertThat(actual.getClass().toString()).contains("CGLIB$$");
    }
}
