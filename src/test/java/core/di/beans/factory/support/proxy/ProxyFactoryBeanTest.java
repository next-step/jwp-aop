package core.di.beans.factory.support.proxy;

import core.di.beans.factory.proxy.ProxyFactoryBean;
import core.di.beans.factory.support.proxy.example.NameMatchMethodPointcut;
import org.junit.jupiter.api.Test;
import study.proxy.example.Hello;
import study.proxy.example.HelloTarget;
import study.proxy.example.cglib.MethodCallLogInterceptor;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyFactoryBeanTest {

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
    }


}
