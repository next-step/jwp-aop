package core.di.beans.factory.support.proxy;

import core.di.beans.factory.proxy.ProxyFactoryBean;
import core.di.beans.factory.support.proxy.example.NameMatchMethodPointcut;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.ExampleConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.proxy.example.Hello;
import study.proxy.example.HelloTarget;
import study.proxy.example.cglib.MethodCallLogInterceptor;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyFactoryBeanTest {

    ApplicationContext context;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext(ExampleConfig.class);
    }

    @Test
    public void proxyFactoryBean() throws Exception {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(HelloTarget.class);
        pfBean.addAdvice(new MethodCallLogInterceptor());
        pfBean.setPointcut(new NameMatchMethodPointcut("say", "talk"));

        Hello proxyHello = (Hello) pfBean.getObject();
        assertThat(proxyHello.sayHello("spring")).isEqualTo("HELLO SPRING");
        assertThat(proxyHello.sayHi("spring")).isEqualTo("HI SPRING");
        assertThat(proxyHello.sayThankYou("spring")).isEqualTo("THANK YOU SPRING");
        assertThat(proxyHello.pingpong("spring")).isEqualTo("spring");
        assertThat(proxyHello.talk("spring")).isEqualTo("TALK SPRING");
    }

    @Test
    public void getBean() throws Exception {
        Hello hello = (Hello) context.getBean(ProxyFactoryBean.class);

        assertThat(hello.sayHello("spring")).isEqualTo("HELLO SPRING");
        assertThat(hello.sayHi("spring")).isEqualTo("HI SPRING");
        assertThat(hello.sayThankYou("spring")).isEqualTo("THANK YOU SPRING");
        assertThat(hello.pingpong("spring")).isEqualTo("spring");
        assertThat(hello.talk("spring")).isEqualTo("TALK SPRING");
    }

}
