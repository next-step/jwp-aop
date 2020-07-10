package core.di.factory;

import core.di.beans.factory.ClassBeanDefinition;
import core.di.beans.factory.ClassBeanScanner;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.factory.example.NameController;
import core.di.factory.example.QnaController;
import core.di.factory.example2.TestComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ClassBeanScannerTest {

    private DefaultBeanFactory beanFactory;
    private ClassBeanScanner classBeanScanner;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultBeanFactory();
        classBeanScanner = new ClassBeanScanner(beanFactory);
    }

    @Test
    @DisplayName("BeanFactory에 BeanDefinition등록 테스트")
    public void registerBeanDefinitionTest() {
        classBeanScanner.scan("core.di.factory.example");

        ClassBeanDefinition classBeanDefinition = (ClassBeanDefinition) beanFactory.getBeanDefinition(QnaController.class.getName());

        assertThat(classBeanDefinition.getType()).isEqualTo(QnaController.class);
    }

    @Test
    @DisplayName("BeanFactory에 이름으로 BeanDefinition 등록 테스트")
    public void registerBeanDefinitionWithNameTest() {
        classBeanScanner.scan("core.di.factory.example");

        ClassBeanDefinition classBeanDefinition = (ClassBeanDefinition) beanFactory.getBeanDefinition(NameController.class.getName());
        ClassBeanDefinition classBeanDefinition1 = (ClassBeanDefinition) beanFactory.getBeanDefinition("name");

        assertThat(classBeanDefinition).isNull();
        assertThat(classBeanDefinition1.getType()).isEqualTo(NameController.class);
    }

    @Test
    @DisplayName("@ComponentScan 동작 테스트")
    public void componentScanTest() {
        classBeanScanner.scan("core.di.factory.example");

        assertThat(beanFactory.getBeanDefinition(TestComponent.class.getName())).isNotNull();
        assertThat(beanFactory.getBeanDefinition(TestComponent.class.getName())).isNotNull();
    }
}
