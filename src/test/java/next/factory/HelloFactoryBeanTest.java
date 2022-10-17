package next.factory;

import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.factory.example.HelloConfig;
import next.hello.HelloTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloFactoryBeanTest {

    @DisplayName("HelloConfig 파일 내부의 HelloTarget 빈을 등록하고, 정상적으로 등록되어있는지 확인한다.")
    @Test
    void register_HelloTarget() {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(HelloConfig.class);
        beanFactory.preInstantiateSingletons();

        HelloTarget helloTarget = beanFactory.getBean(HelloTarget.class);
        assertThat(helloTarget).isNotNull();
    }
}
