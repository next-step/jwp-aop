package core.di.factory;

import core.di.beans.factory.BeanFactoryUtils;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.scanner.ClassBeanScanner;
import core.di.beans.factory.scanner.MethodBeanScanner;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QuestionRepository;
import core.di.factory.example.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
class BeanFactoryUtilsTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultBeanFactory();
        new ClassBeanScanner(beanFactory).scan("core.di.factory.example");
        new MethodBeanScanner(beanFactory).scan("core.di.factory.example");
    }

    @Test
    @DisplayName("Bean 생성시에 필요한 Parameter get tst")
    public void getParameterTest() throws NoSuchMethodException {
        Constructor<?> constructor = MyQnaService.class.getConstructor(UserRepository.class, QuestionRepository.class);

        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, constructor);

        assertThat(parameters[0]).isInstanceOf(UserRepository.class);
        assertThat(parameters[1]).isInstanceOf(QuestionRepository.class);
    }
}
