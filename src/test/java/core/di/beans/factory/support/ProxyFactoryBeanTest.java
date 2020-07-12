package core.di.beans.factory.support;

import core.di.beans.factory.ProxyFactoryBean;
import core.di.factory.example.MyAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.SayMethodMatcher;
import study.cglib.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyFactoryBeanTest {
    private String hello_expected = "HELLO EESEUL";
    private String hi_expected = "HI EESEUL";
    private String thankYou_expected = "THANK YOU EESEUL";
    private String pingPong_expected = "Pong eeseul";

    private ProxyFactoryBean proxyFactoryBean;

    @BeforeEach
    void setUp() {
        MethodMatcher matcher = new SayMethodMatcher();
        this.proxyFactoryBean = new ProxyFactoryBean(new HelloTarget(), new MyAdvice(matcher));
    }

    @Test
    void getObject() throws Exception {
        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        String hello_actual = helloTarget.sayHello("eeseul");
        String hi_actual = helloTarget.sayHi("eeseul");
        String thankYou_actual = helloTarget.sayThankYou("eeseul");
        String pingPong_actual = helloTarget.pingPong("eeseul");

        assertThat(hello_actual).isEqualTo(hello_expected);
        assertThat(hi_actual).isEqualTo(hi_expected);
        assertThat(thankYou_actual).isEqualTo(thankYou_expected);
        assertThat(pingPong_actual).isEqualTo(pingPong_expected);
    }
}
