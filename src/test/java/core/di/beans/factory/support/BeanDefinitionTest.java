package core.di.beans.factory.support;

import core.di.beans.factory.config.BeanDefinition;
import core.di.factory.example.JdbcQuestionRepository;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.MyUserController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanDefinitionTest {
    private static final Logger log = LoggerFactory.getLogger(BeanDefinitionTest.class);

    @Test
    public void getResolvedAutowireMode() {
        BeanDefinition dbd = new DefaultBeanDefinition(JdbcQuestionRepository.class);
        assertThat(dbd.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_NO);

        dbd = new DefaultBeanDefinition(MyUserController.class);
        assertThat(dbd.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_FIELD);

        dbd = new DefaultBeanDefinition(MyQnaService.class);
        assertThat(dbd.getResolvedInjectMode()).isEqualTo(InjectType.INJECT_CONSTRUCTOR);
    }

    @Test
    public void getInjectProperties() throws Exception {
        BeanDefinition dbd = new DefaultBeanDefinition(MyUserController.class);
        Set<Field> injectFields = dbd.getInjectFields();
        for (Field field : injectFields) {
            log.debug("inject field : {}", field);
        }
    }

    @Test
    public void getConstructor() throws Exception {
        BeanDefinition dbd = new DefaultBeanDefinition(MyQnaService.class);
        Set<Field> injectFields = dbd.getInjectFields();
        assertThat(injectFields).isEmpty();
        Constructor<?> constructor = dbd.getInjectConstructor();
        log.debug("inject constructor : {}", constructor);
    }
}
