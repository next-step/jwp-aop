package study.proxy.factorybean;

import core.di.beans.factory.aop.Aspect;
import core.di.beans.factory.aop.DefaultPointcut;
import core.di.beans.factory.aop.ProxyFactoryBean;
import core.di.beans.factory.aop.Target;
import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.ApplicationContext;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.ExampleConfig;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyFactoryBeanTest {


    @Test
    void getObject() throws Exception {
        Target target = new Target(new HelloTarget(), HelloTarget.class);
        Aspect aspect = new Aspect(new DefaultPointcut(), new ToUpperCaseAdvice());

        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setAspect(aspect);
        factoryBean.setTarget(target);

        HelloTarget helloTarget = (HelloTarget) factoryBean.getObject();

        assertThat(helloTarget.sayHello("catsbi")).isEqualTo("HELLO CATSBI");
    }


    @Test
    void getObjectWithBeanFactory() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ProxyConfiguration.class);

        HelloTarget helloTarget = context.getBean(HelloTarget.class);

        assertThat(helloTarget.sayHello("catsbi")).isEqualTo("HELLO CATSBI");
    }
}
