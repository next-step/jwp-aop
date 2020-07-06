package core.di.beans.factory.config.converter;

import core.aop.example.di.SomeComponent;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.di.beans.factory.support.InjectType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("기본 BeanDefinition 테스트")
class DefaultBeanDefinitionConverterTest {

    @Test
    @DisplayName("정보를 잘 가져오는지")
    void getBeanDefinition() {
        DefaultBeanDefinition beanDefinition = new DefaultBeanDefinition(SomeComponent.class);

        assertThat(beanDefinition.getBeanClass()).isEqualTo(SomeComponent.class);
        assertThat(beanDefinition.getInjectConstructor()).isNull();
        assertThat(beanDefinition.getInjectFields()).isEmpty();
        assertThat(beanDefinition.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_NO);
    }

}