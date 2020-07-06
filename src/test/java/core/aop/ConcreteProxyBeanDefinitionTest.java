package core.aop;

import core.aop.example.di.ProxyClass;
import core.aop.example.di.SomeComponent;
import core.aop.example.di.TargetClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProxyBeanFactory 를 상속하는 클래스를 표현하는 BeanDefinition")
class ConcreteProxyBeanDefinitionTest {

    @Test
    @DisplayName("타겟에 대한 정보를 잘 표현하는지")
    void target() {
        ConcreteProxyBeanDefinition beanDefinition = new ConcreteProxyBeanDefinition(ProxyClass.class);

        assertThat(beanDefinition.getBeanClass()).isEqualTo(TargetClass.class);
        assertThat(beanDefinition.getInjectConstructor()).isNotNull();
        assertThat(beanDefinition.getTargetConstructorParameterTypes()).hasSize(1);
        assertThat(beanDefinition.getTargetConstructorParameterTypes()[0]).isEqualTo(SomeComponent.class);
    }

}