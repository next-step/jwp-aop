package core.beandefinition;

import core.beandefinition.example.HigherClass;
import core.beandefinition.example.HigherClassUsingConstructor;
import core.beandefinition.example.LowerClass;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.di.beans.factory.support.DefaultBeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("빈 팩토리가 인젝트 타입에 따라 어떻게 동작하는지 학습하는 테스트")
public class HowBeanFactoryOperateTest {
    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setup() {
        beanFactory = new DefaultBeanFactory();
        beanFactory.registerBeanDefinition(LowerClass.class, new DefaultBeanDefinition(LowerClass.class));
        beanFactory.registerBeanDefinition(HigherClass.class, new DefaultBeanDefinition(HigherClass.class));
        beanFactory.registerBeanDefinition(
                HigherClassUsingConstructor.class,
                new DefaultBeanDefinition(HigherClassUsingConstructor.class)
        );
        beanFactory.preInstantiateSingletons();
    }

    @Test
    @DisplayName("인젝트가 없는 경우")
    void noInject() {
        LowerClass bean = beanFactory.getBean(LowerClass.class);

        // 인젝트할 빈이 없는경우 그냥 clazz.newInstance() 하고 끝
        assertThat(bean).isNotNull();
    }

    @Test
    @DisplayName("생성자에 인젝트가 있는 경우")
    void injectWithConstructor() {
        HigherClassUsingConstructor bean = beanFactory.getBean(HigherClassUsingConstructor.class);

        // 인젝트할 constructor와 파라미터를 전달하여 생성
        // 이때 컨스트럭터는 beanDefinition.getInjectConstructor(); 로 받아온다
        assertThat(bean).isNotNull();
    }

    @Test
    @DisplayName("필드에 인젝트가 있는 경우")
    void injectWithField() {
        HigherClass bean = beanFactory.getBean(HigherClass.class);

        // 1. beanDefinition.getBeanClass()를 통해 생성
        // 2. getInjectFields()를 통해 필요한 필드들을 가져옴
        // 3. 반복문을 통해 하나씩 직접 주입
        assertThat(bean).isNotNull();
    }
}
