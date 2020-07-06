package core.di.beans.factory.config.converter;

import core.aop.example.Proxy;
import core.aop.example.di.ProxyClass;
import core.aop.example.di.SomeComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProxyFactoryBean 을 상속한 클래스를 ConcreteProxyBeanDefinition 으로 변환 시켜주는 클래스")
class ConcreteProxyBeanDefinitionConverterTest {
    private ConcreteProxyBeanDefinitionConverter converter = new ConcreteProxyBeanDefinitionConverter();

    @Test
    @DisplayName("지원 여부")
    void support() {
        assertThat(converter.support(ProxyClass.class)).isTrue();
        assertThat(converter.support(Proxy.class)).isTrue();
        assertThat(converter.support(SomeComponent.class)).isFalse();
    }

}