package core.di.factory;

import core.di.beans.factory.BeanInitializer;
import core.di.beans.factory.ClassBeanDefinition;
import core.di.beans.factory.ClassBeanDefinitionInitializer;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.factory.example.JdbcQuestionRepository;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.MyQnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ClassBeanDefinitionInitializerTest {

    private DefaultBeanFactory beanFactory;

    private BeanInitializer beanInitializer;

    @BeforeEach
    public void setUp() {
        beanInitializer = new ClassBeanDefinitionInitializer();

        beanFactory = new DefaultBeanFactory();
        beanFactory.registerDefinition(new ClassBeanDefinition(JdbcQuestionRepository.class, JdbcQuestionRepository.class.getName()));
        beanFactory.registerDefinition(new ClassBeanDefinition(JdbcUserRepository.class, JdbcUserRepository.class.getName()));
    }

    @Test
    @DisplayName("@Component 기반 클래스 인스턴스 생성 테스트")
    public void initBeanTest() {
        ClassBeanDefinition beanDefinition = new ClassBeanDefinition(MyQnaService.class, MyQnaService.class.getName());
        MyQnaService myQnaService = (MyQnaService) beanInitializer.instantiate(beanDefinition, beanFactory);

        assertThat(beanInitializer.support(beanDefinition)).isTrue();
        assertThat(myQnaService.getUserRepository()).isNotNull();
        assertThat(myQnaService.getQuestionRepository()).isNotNull();
    }
}
