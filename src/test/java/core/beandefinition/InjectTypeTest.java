package core.beandefinition;

import core.beandefinition.example.HigherClass;
import core.beandefinition.example.HigherClassUsingConstructor;
import core.beandefinition.example.LowerClass;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.di.beans.factory.support.InjectType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("인젝트 타입에 대한 학습 테스트")
public class InjectTypeTest {

    @Test
    @DisplayName("인젝트가 없는 경우")
    void noInject() {
        DefaultBeanDefinition defaultBeanDefinition = new DefaultBeanDefinition(LowerClass.class);

        assertThat(defaultBeanDefinition.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_NO);
        // beanClass는 차이가 없다
        assertThat(defaultBeanDefinition.getBeanClass()).isEqualTo(LowerClass.class);
        // inject constructor 는 생성자로 하는 경우를 제외하고는 없다
        assertThat(defaultBeanDefinition.getInjectConstructor()).isNull();
        // inject field는 inject type이 field인 경우에만 리턴한다.
        assertThat(defaultBeanDefinition.getInjectFields()).isEmpty();
    }

    @Test
    @DisplayName("인젝트를 생성자로 하는 경우")
    void injectWithConstructor() {
        DefaultBeanDefinition defaultBeanDefinition = new DefaultBeanDefinition(HigherClassUsingConstructor.class);

        assertThat(defaultBeanDefinition.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_CONSTRUCTOR);
        assertThat(defaultBeanDefinition.getBeanClass()).isEqualTo(HigherClassUsingConstructor.class);
        assertThat(defaultBeanDefinition.getInjectConstructor()).isNotNull();
        assertThat(defaultBeanDefinition.getInjectFields()).isEmpty();
    }

    @Test
    @DisplayName("인젝트를 필드로 하는 경우")
    void injectWithField() {
        DefaultBeanDefinition defaultBeanDefinition = new DefaultBeanDefinition(HigherClass.class);

        assertThat(defaultBeanDefinition.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_FIELD);
        assertThat(defaultBeanDefinition.getBeanClass()).isEqualTo(HigherClass.class);
        assertThat(defaultBeanDefinition.getInjectConstructor()).isNull();
        assertThat(defaultBeanDefinition.getInjectFields()).hasSize(1);
    }
}
