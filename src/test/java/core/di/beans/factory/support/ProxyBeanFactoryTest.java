package core.di.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.Hello;

class ProxyBeanFactoryTest {

    @DisplayName("FactoryBean interface 구현체로 등록되는 빈은 반환 타입의 구현체를 프록시로 감싼 객체이다.")
    @Test
    void proxyFactoryBeanTest() {
        ProxyBeanFactory proxyBeanFactory = new ProxyBeanFactory();
        ClasspathBeanDefinitionScanner cbds = new ClasspathBeanDefinitionScanner(proxyBeanFactory);
        cbds.doScan("study.aop");
        proxyBeanFactory.preInstantiateSingletons();

        Hello helloBean = proxyBeanFactory.getBean(Hello.class);

        assertNotNull(helloBean);
        assertThat(helloBean.sayHello("test")).isEqualTo("HELLO TEST");
        assertThat(helloBean.sayHi("test")).isEqualTo("HI TEST");
        assertThat(helloBean.sayThankYou("test")).isEqualTo("THANK YOU TEST");
    }

}
